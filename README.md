# learning-spring
## spring注解驱动
在spring2.x的时代IOC容器主要依赖于xml配置文件。在spring3.x时代大量的Annotation出现spring进入注解驱动的井喷期，再到spring4.x时代完善注解内容，再到现在的spring5.x对于注解方面没有太多的增量。
在spring3.xspring新增@Configuration注解用来替代xml文件使用@Bean来代码<bean>标签。为此spring新增`AnnotationConfigApplicationContext`来帮助我们处理@Configuration这类型的配置信息。
进入@Configuration定义注解内容(spring源码版本`5.x`)：
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {
	@AliasFor(annotation = Component.class)
	String value() default "";
	
	boolean proxyBeanMethods() default true;
}
```
这里面有一个**极其重要**的注解*`@Component`*所有的配置Bean基本都是基于这个`@Component`来实现。如果大家熟悉@Controller，@Service等等注解就知道其实这些注解都是『继承』自@Component
这些新加入到spring的注解其实都是通过@Component注解帮助实现的。我们现在来看看spring是
如何处理这个@Component注解。
### Component注解实现源码分析
先来看一个简单的例子：
```java
@Configuration
public class BeansConfig {
    @Bean(name="userService")
    public UserService getUserService(){
        UserService userService = new UserService();
        userService.setId(1);
        userService.setName("Bean");
        return userService;
    }
}

public class TestAnnotationBeanConfig {
    public static void main(String[] args) {
        /**
         * 在spring3.x之后提供的@Configuration用来替代XML配置文件
         * 但是使用@Configuration注解的配置类又由谁来加载呢？
         * 这是spring提供了AnnotationConfigApplicationContext来装配
         * 在构造函数上面有如果使用AnnotationConfigApplicationContext(java.lang.Class[])
         * 的方式那么Class对象上面是可以不添加@Configuration注解
         * 如果使用basePackage的方式那么必须添加@Configuration注解
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.cjxz.annotationtest");
//        context.register(BeansConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(JSON.toJSONString(userService));
    }
}
```
上面定义了一个配置类BeansCofig，然后下面是测试类，通过AnnotationConfigApplicationContext作为IOC容器启动项目
下面的源码分析其实不是针对@Configuration，而是针对@Component。也就是说只有注解上面带有@Component那么这个类就会被IOC容器托管
分析源码一般先找入口，下面我们先从`AnnotationConfigApplicationContext`构造函数开始入手。
#### AnnotationConfigApplicationContext加载@Component
```java
	public AnnotationConfigApplicationContext() {
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}
	public AnnotationConfigApplicationContext(String... basePackages) {
    		this();
    		scan(basePackages);
    		refresh();
    }
```
可以看到上面有一个无参构造函数初始化了`AnnotatedBeanDefinitionReader`和`ClassPathBeanDefinitionScanner`
- reader主要是用来注册BeanDefinition。在注解中默认使用`AnnotatedGenericBeanDefinition`作为实现
- scanner用来扫描指定包下面含有@Component注解的类

从上面可以看出我们主要需要分析scanner过程`scan(basePackages);`下面是调用链
```java
org.springframework.context.annotation.AnnotationConfigApplicationContext.scan
org.springframework.context.annotation.ClassPathBeanDefinitionScanner#scan
//开始做事的人来了
org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
		//1.按照数组的方式挨个扫描basePackage目录
		for (String basePackage : basePackages) {
		//2.判断basePackage里面是否含有复合要求的Bean，这个『复合要求』是指含有@Component注解
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				//省略部分代码
		return beanDefinitions;
	}
org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#findCandidateComponents
org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#scanCandidateComponents
	private Set<BeanDefinition> scanCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<>();
		try {
		//找到指定包下面的所有class文件
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					resolveBasePackage(basePackage) + '/' + this.resourcePattern;
			Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
			boolean traceEnabled = logger.isTraceEnabled();
			boolean debugEnabled = logger.isDebugEnabled();
			//挨个遍历
			for (Resource resource : resources) {
				if (traceEnabled) {
					logger.trace("Scanning " + resource);
				}
				if (resource.isReadable()) {
					try {
					//从Resource中获得元信息读取器。可以简单理解MetadataReader含有这个类的所有注解数据
						MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
						//这个判断很重要，如果满足这个判断那么就是需要托管的Bean，不满足就不是托管Bean
						if (isCandidateComponent(metadataReader)) {
							ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
							sbd.setResource(resource);
							sbd.setSource(resource);
							if (isCandidateComponent(sbd)) {
								if (debugEnabled) {
									logger.debug("Identified candidate component class: " + resource);
								}
								candidates.add(sbd);
								
org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#isCandidateComponent(org.springframework.core.type.classreading.MetadataReader)
//开始进行过滤操作，默认includeFilters是@Component
	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
		for (TypeFilter tf : this.excludeFilters) {
			if (tf.match(metadataReader, getMetadataReaderFactory())) {
				return false;
			}
		}
		for (TypeFilter tf : this.includeFilters) {
			if (tf.match(metadataReader, getMetadataReaderFactory())) {
				return isConditionMatch(metadataReader);
			}
		}
		return false;
	}
	//includeFilters初始化过程
	this.includeFilters.add(new AnnotationTypeFilter(Component.class));
		ClassLoader cl = ClassPathScanningCandidateComponentProvider.class.getClassLoader();
		try {
			this.includeFilters.add(new AnnotationTypeFilter(
					((Class<? extends Annotation>) ClassUtils.forName("javax.annotation.ManagedBean", cl)), false));
			logger.trace("JSR-250 'javax.annotation.ManagedBean' found and supported for component scanning");
		}
//首先调用抽象类的match方法，match方法又会调用子类的matchself方法。AnnotationTypeFilter实现类自己的matchself方法。所以最终的逻辑在matchself里面
	@Override
	protected boolean matchSelf(MetadataReader metadataReader) {
		AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
		return metadata.hasAnnotation(this.annotationType.getName()) ||
				(this.considerMetaAnnotations && metadata.hasMetaAnnotation(this.annotationType.getName()));
	}
	//方法非常简单，前面在new AnnotationTypeFilter(Component.class)的时候已经将@Component作为参数传递进去直接判断注解元数据有没有@Component
```

#### 加载spring4.x新增的@Conditional注解




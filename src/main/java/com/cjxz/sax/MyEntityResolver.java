package com.cjxz.sax;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-16
 * @Version: 1.0
 */
public class MyEntityResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (systemId != null) {
            Resource resource = new ClassPathResource("myconfig.xsd");
            try {
                InputSource source = new InputSource(resource.getInputStream());
                source.setPublicId(publicId);
                source.setSystemId(systemId);

                return source;
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}

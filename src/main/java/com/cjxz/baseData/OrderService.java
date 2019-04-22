package com.cjxz.baseData;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019/04/12
 * @Version: 1.0
 */
public class OrderService {
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void createOrder(){
        System.out.println("id:"+userService.getId()+"/user:"+userService.getName());
    }
}

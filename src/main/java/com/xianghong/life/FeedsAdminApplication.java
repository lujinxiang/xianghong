package com.xianghong.life;

import com.xianghong.life.service.UserInfoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FeedsAdminApplication {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationTest.xml");
        UserInfoService userInfoService = context.getBean(UserInfoService.class);
        System.out.println(userInfoService);
        userInfoService.getUserInfoById(1);
    }

}


package com.xianghong.life.test;

import com.xianghong.life.dao.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lujinxiang
 * @date 2021/07/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HFSubjectCardServiceTest {

    @Autowired
    private UserInfoDao userInfoDao;

    @Test
    public void testGetUserInfo() {

    }
}

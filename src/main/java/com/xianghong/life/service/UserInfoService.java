package com.xianghong.life.service;

import com.xianghong.life.dao.UserInfoDao;
import com.xianghong.life.dto.userinfo.EditUserInfoRequest;
import com.xianghong.life.dto.userinfo.RegisterUserInfoRequest;
import com.xianghong.life.dto.userinfo.UserInfoResponse;
import com.xianghong.life.entity.UserInfoEntity;
import com.xianghong.life.redis.RedisUtils;
import com.xianghong.life.utils.UserInfoConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jinxianglu
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 用户注册
     *
     * @param request
     * @return
     */

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int registerUser(RegisterUserInfoRequest request) {
        int res = userInfoDao.saveUserInfo(UserInfoConvertUtil.convert2UserInfo(request));
        Jedis jedis = RedisUtils.getJedis();
        jedis.set(request.getUserName(), request.getPassword());
        RedisUtils.releaseJedisConnector(jedis);
        return res;
    }

    /**
     * 根据用户名查询用户信息
     */
    public List<UserInfoResponse> findUserByName(String userName) {
        List<UserInfoEntity> userInfoEntities = userInfoDao.selectUserInfoByUserName(userName);
        if (CollectionUtils.isEmpty(userInfoEntities)) {
            return Collections.emptyList();
        }
        return userInfoEntities.stream().map(userInfoEntity -> UserInfoConvertUtil.convert2UserInfoResponse(userInfoEntity)).collect(Collectors.toList());
    }

    /**
     * 更新用户信息
     */
    public int updateUserInfo(EditUserInfoRequest editUserInfoRequest) {
        int res = userInfoDao.updateUserInfo(UserInfoConvertUtil.convert2UserInfo(editUserInfoRequest));
        return res;
    }

    /**
     * 根据用户id获取用户详情
     */
    public UserInfoResponse getUserInfoById(Integer userId) {
        Optional<UserInfoEntity> userInfoById = userInfoDao.getUserInfoById(userId);
        if (!userInfoById.isPresent()) {
            return null;
        }
        return UserInfoConvertUtil.convert2UserInfoResponse(userInfoById.get());
    }

}

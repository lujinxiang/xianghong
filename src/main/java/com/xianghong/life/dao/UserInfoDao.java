package com.xianghong.life.dao;

import com.xianghong.life.entity.UserInfoEntity;
import com.xianghong.life.entity.UserInfoEntityExample;
import com.xianghong.life.mapper.UserInfoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserInfoDao {
    @Autowired
    private UserInfoEntityMapper userInfoMapper;

    /**
     * 返回自增主键id
     *
     * @param userInfo
     * @return
     */
    public int saveUserInfo(UserInfoEntity userInfo) {
        int result = userInfoMapper.insertSelective(userInfo);
        if (result > 0) {
            return userInfo.getId();
        } else {
            return result;
        }
    }

    /**
     * 根据userId获取用户信息
     *
     * @param id
     * @return
     */
    public Optional<UserInfoEntity> getUserInfoById(int id) {
        return Optional.ofNullable(userInfoMapper.selectByPrimaryKey(id));
    }

    /**
     * 更新用户信息
     *
     * @param userInfo
     * @return
     */
    public int updateUserInfo(UserInfoEntity userInfo) {
        Optional<UserInfoEntity> userInfoById = getUserInfoById(userInfo.getId());
        if (!userInfoById.isPresent()) {
            throw new RuntimeException("当前更新用户不存在");
        }
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 根据用户姓名查询用户
     */
    public List<UserInfoEntity> selectUserInfoByUserName(String userName) {
        UserInfoEntityExample userInfoExample = new UserInfoEntityExample();
        UserInfoEntityExample.Criteria criteria = userInfoExample.createCriteria();
        if (!Objects.isNull(userName)) {
            criteria.andUsernameEqualTo(userName);
        }
        List<UserInfoEntity> userInfos = userInfoMapper.selectByExample(userInfoExample);
        return userInfos;
    }
}

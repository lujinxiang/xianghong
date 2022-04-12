package com.xianghong.life.utils;

import com.xianghong.life.dto.userinfo.EditUserInfoRequest;
import com.xianghong.life.dto.userinfo.RegisterUserInfoRequest;
import com.xianghong.life.dto.userinfo.UserInfoResponse;
import com.xianghong.life.entity.UserInfoEntity;
import lombok.Data;

@Data
public class UserInfoConvertUtil {

    public static UserInfoEntity convert2UserInfo(RegisterUserInfoRequest registerUserInfoRequest) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setUsername(registerUserInfoRequest.getUserName());
        userInfo.setAge(registerUserInfoRequest.getAge());
        userInfo.setPassword(registerUserInfoRequest.getPassword());
        userInfo.setTelephone(registerUserInfoRequest.getTelePhoneNumber());
        return userInfo;
    }

    public static UserInfoEntity convert2UserInfo(EditUserInfoRequest editUserInfoRequest) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setUsername(editUserInfoRequest.getUserName());
        userInfo.setAge(editUserInfoRequest.getAge());
        userInfo.setPassword(editUserInfoRequest.getPassword());
        userInfo.setTelephone(editUserInfoRequest.getTelePhoneNumber());
        return userInfo;
    }

    public static UserInfoResponse convert2UserInfoResponse(UserInfoEntity userInfoEntity) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUsername(userInfoEntity.getUsername());
        userInfoResponse.setAge(userInfoEntity.getAge());
        userInfoResponse.setPassword(userInfoEntity.getPassword());
        userInfoResponse.setTelephone(userInfoEntity.getTelephone());
        userInfoResponse.setAddress(userInfoEntity.getAddress());
        return userInfoResponse;
    }
}

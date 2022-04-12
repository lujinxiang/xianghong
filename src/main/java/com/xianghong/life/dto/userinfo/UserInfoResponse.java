package com.xianghong.life.dto.userinfo;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String username;
    private Integer age;
    private String password;
    private String telephone;
    private String address;
}

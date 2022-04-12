package com.xianghong.life.dto.userinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户注册信息请求")
public class RegisterUserInfoRequest {

    @ApiModelProperty(value = "用户名", example = "xianghong")
    private String userName;

    @ApiModelProperty(value = "用户年龄", example = "12")
    private Integer age;

    @ApiModelProperty(value = "用户密码", example = "123***")
    private String password;

    @ApiModelProperty(value = "用户手机号", example = "1515187****")
    private String telePhoneNumber;
}

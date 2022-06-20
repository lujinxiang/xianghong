package com.xianghong.life.controller;

import com.xianghong.life.advise.CommonException;
import com.xianghong.life.advise.CommonResponse;
import com.xianghong.life.dto.userinfo.RegisterUserInfoRequest;
import com.xianghong.life.dto.userinfo.UserInfoResponse;
import com.xianghong.life.service.UserInfoService;
import com.xianghong.life.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/userInfo")
@Api(value = "祥红博客用户管理接口", tags = {"祥红博客用户管理接口"})
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/getUserInfoData")
    public CommonResponse<UserInfoResponse> getUserInfo(@RequestParam Integer id) {
        log.info("get UserDetailInfo userId is:{}", id);
        UserInfoResponse userInfoById = userInfoService.getUserInfoById(id);
        if (Objects.isNull(userInfoById)) {
            log.error("查询不到该用户信息");
        }
        return CommonResponse.ok(userInfoById);
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/registerUser")
    public CommonResponse<String> registerUser(@Valid @RequestBody RegisterUserInfoRequest request) {
        log.info("registerUser start userInfo is:{}", JsonUtil.toJsonString(request));
        int res = userInfoService.registerUser(request);
        if (res < 0) {
            throw new CommonException("用户注册失败,请稍后再试");
        }
        return CommonResponse.ok();
    }

    @ApiOperation(value = "根据姓名获取用户信息", notes = "根据姓名获取用户信息")
    @GetMapping("/username")
    public CommonResponse findUserInfoByUserName(String username) {
        log.info("findUserInfoByName start username is:{}", username);
        List<UserInfoResponse> userByName = userInfoService.findUserByName(username);
        if (CollectionUtils.isEmpty(userByName)) {
            throw new CommonException("没有查询到任何用户信息");
        }
        return CommonResponse.ok(JsonUtil.toJsonString(userByName));
    }

}


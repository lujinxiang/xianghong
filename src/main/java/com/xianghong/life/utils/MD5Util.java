package com.xianghong.life.utils;

import org.apache.commons.codec.digest.DigestUtils;


public class MD5Util {

    public static String encode(String strObj) {
        return DigestUtils.md5Hex(strObj);
    }

    public static String encode(byte[] input) {
        return DigestUtils.md5Hex(input);
    }
}
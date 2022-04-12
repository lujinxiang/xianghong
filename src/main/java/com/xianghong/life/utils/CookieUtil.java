package com.xianghong.life.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;

/**
 * @author jackie chen
 * @create 5/7/19
 * @description CookieUtil
 */
public class CookieUtil {

    public static String getToken(Cookie[] cookies, String cookieName) {
        if (!ArrayUtils.isEmpty(cookies) && StringUtils.isNotEmpty(cookieName)) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }
}

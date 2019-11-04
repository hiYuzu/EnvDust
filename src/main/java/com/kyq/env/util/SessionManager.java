package com.kyq.env.util;

import javax.servlet.http.HttpSession;

/**
 * [功能描述]：session管理
 *
 * @author kyq
 */
public class SessionManager {

    /**
     * [功能描述]：判断session是否过期
     */
    public static boolean isSessionValidate(HttpSession httpsession, String key) {
        boolean flag = true;
        try {
            if (httpsession != null && httpsession.getId() != null
                    && httpsession.getAttribute(key) != null) {
                flag = false;
            } else {
                flag = true;
            }
        } catch (Exception e) {
            flag = true;
        }
        return flag;
    }
}

package com.kyq.env.Interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kyq.env.util.DefaultArgument;
import com.kyq.env.util.SessionManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * [功能描述]：安全拦截器
 *
 * @author kyq
 */
public class SecurityInterceptor implements HandlerInterceptor {

    private List<String> excludedUrls;


    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        // 过滤不需要安全检查的访问
        String requestUri = request.getRequestURI();
        for (String url : excludedUrls) {
            if (requestUri.endsWith(url)) {
                return true;
            }
        }

        HttpSession session = request.getSession();
        if (SessionManager.isSessionValidate(session, DefaultArgument.LOGIN_USER)) {

            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { //如果是ajax请求响应头会有，x-requested-with
                //在响应头设置session状态
                response.setHeader("sessionstatus", "timeout");
            } else {
                String path = request.getContextPath();
                response.sendRedirect(path + "/index.html");
            }
            return false;
        } else {
            // 用户已经登录
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}

package com.xmu.aiad.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by PoemWhite on 16/9/9.
 */
public class BaseUrlInterceptor extends HandlerInterceptorAdapter {

    static Logger logger = LogManager.getLogger();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();

        String baseUrl = scheme + "://" + serverName + ":" + port + path;
        request.setAttribute("baseUrl", baseUrl);

        String staticUrl = CoreConfig.getInstance().getPackingConfig().getStaticUrl();
        request.setAttribute("staticUrl", staticUrl);

        request.setAttribute("uploadUrl", staticUrl+"/upload");

        String loginUrl = CoreConfig.getInstance().getPackingConfig().getLoginUrl();
        request.setAttribute("loginUrl", loginUrl);

        return true;
    }
}
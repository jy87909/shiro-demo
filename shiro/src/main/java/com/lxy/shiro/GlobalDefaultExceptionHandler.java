package com.lxy.shiro;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Shiro异常处理类,(@ControllerAdvice用来处理异常)
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String defaultAuthorizedExceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception e) {
        return defaultException(request,response);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public String defaultAuthenticatedExceptionHandler(HttpServletRequest request, HttpServletResponse response,Exception e) {
        return defaultException(request,response);
    }

    private String defaultException(HttpServletRequest request, HttpServletResponse response){
//        try {
//            if (isAjax(request)) {
//                onLoginFail(response);
//                return "";
//            }
//            else {
//                response.sendRedirect("/notlogin");
//            }
//        }
//        catch (Exception ex){
//
//        }
        return "您没有访问权限";
    }

    public boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void onLoginFail(HttpServletResponse response) throws IOException {
        response.setHeader("sessionstatus", "timeout");
        response.setHeader("basePath", "/notlogin");
    }

}
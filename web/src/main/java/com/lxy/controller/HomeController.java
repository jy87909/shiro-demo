package com.lxy.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HomeController {

    /**
     * 登录时的链接
     *
     * @param mv
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mv) {
        if (mv == null) {
            new ModelAndView();
        }
        //获取shiro中的session
        Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute("MEMBER_USER_KEY") == null) {
            log.info("用户没有登录,即将跳转登录页面");
            mv.setViewName("views/login");
        } else {
            log.info("用户已经登录,即将跳转登录页面");
            mv.setViewName("views/index");
        }
        return mv;
    }

    /**
     * 用户登录后的首页
     *
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/index");
        return mv;
    }

    /**
     * 错误页面
     *
     * @return
     */
    @RequestMapping(value = "guest/error", method = RequestMethod.GET)
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/error");
        return mv;
    }

    /**
     * 创建学校页面
     *
     * @return
     */
    @RequiresRoles("ROLE_ADMIN")
    @RequiresPermissions("school:create")
    @RequestMapping(value = "createSchool", method = RequestMethod.GET)
    public ModelAndView createSchool() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/createSchool");
        return mv;
    }

    /**
     * 创建学生页面
     *
     * @return
     */
    @RequiresRoles("ROLE_SCHOOLADMIN")
    @RequiresPermissions("student:create")
    @RequestMapping(value = "createStudent")
    public ModelAndView createStudent() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/createStudent");
        return mv;
    }
}

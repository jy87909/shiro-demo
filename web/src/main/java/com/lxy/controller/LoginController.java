package com.lxy.controller;

import com.lxy.entity.User;
import com.lxy.mapper.UserMapper;
import com.lxy.shiro.AuthRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Track;
import java.io.IOException;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    /**
     * 默认登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView viewLogin() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/login");
        return mv;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @param response 响应
     * @throws IOException
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(String username, String password, HttpServletResponse response) throws IOException {
        // 使用用户的登录信息创建令牌,token可以理解为用户令牌，登录的过程被抽象为Shiro验证令牌是否具有合法身份以及相关权限。
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取Subject单例对象
        Subject subject = SecurityUtils.getSubject();
        try {
            //用户登录
            log.info("对用户[" + username + "]进行登录验证..验证开始");
            subject.login(token);
            log.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (Exception e) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.info("对用户[" + username + "]进行登录验证..验证未通过,错误原因." + e.getMessage());
            e.printStackTrace();
        }

        //重定向到success接口
        response.sendRedirect("success");
    }

    /**
     * 用户退出登录(清除redis缓存)
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        //清除当前用户的redis缓存
        log.info("===============退出当前用户,清除当前用户的信息");
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        AuthRealm realm = (AuthRealm) securityManager.getRealms().iterator().next();
        realm.clearCache(SecurityUtils.getSubject().getPrincipals());
        SecurityUtils.getSubject().logout();

        return "redirect:/login";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("user:create")
    @RequestMapping(value = "success", method = RequestMethod.GET)
    public ModelAndView success() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/success");
        return mv;
    }

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("views/error");
        return mv;
    }

}

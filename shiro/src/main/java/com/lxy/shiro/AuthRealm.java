package com.lxy.shiro;


import com.lxy.entity.User;
import com.lxy.mapper.PermissionMapper;
import com.lxy.mapper.RoleMapper;
import com.lxy.mapper.UserMapper;
import com.lxy.service.IPermissionService;
import com.lxy.service.IRoleService;
import com.lxy.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Slf4j
public class AuthRealm extends AuthorizingRealm {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("===============进入身份认证");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取登录用户名
        String username = String.valueOf(usernamePasswordToken.getUsername());
        User user = userMapper.getUserByUserName(username);
        AuthenticationInfo authenticationInfo = null;
        //如果有此用户名的用户,则判断输入的密码和数据库中的密码是否一致
        if (user != null) {
            String password = new String(usernamePasswordToken.getPassword());
            if (password.equals(user.getPassword())) {
                authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            }
        }
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("===============进入要权限认证");
        //获取用户名
        String username = (String) principals.getPrimaryPrincipal();
        if (username != null && !("".equals(username))) {
            //通过用户名获取该用户所属角色名称
            Set<String> roleName = roleMapper.getRoleByUserName(username);
            //通过用户名获取该用户所拥有权限的名称
            Set<String> permName = permissionMapper.getPermissionNameByUserName(username);
            //设置用户角色和权限
            SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
            authenticationInfo.setRoles(roleName);
            authenticationInfo.setStringPermissions(permName);
        }
        return null;
    }


}

package com.lxy.mapper;

import com.lxy.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxy
 * @since 2018-12-10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户名获取该用户信息
     *
     * @param username 登录名
     * @return
     */
    User getUserByUserName(String username);
}

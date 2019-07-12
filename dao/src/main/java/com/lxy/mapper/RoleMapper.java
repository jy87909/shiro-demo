package com.lxy.mapper;

import com.lxy.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxy
 * @since 2018-12-10
 */
public interface RoleMapper extends BaseMapper<Role> {

    Set<String> getRoleByUserName(String username);
}

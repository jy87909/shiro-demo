package com.lxy.mapper;

import com.lxy.entity.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    Set<String> getPermissionNameByUserName(String username);
}

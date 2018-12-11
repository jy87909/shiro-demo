package com.lxy.service.impl;

import com.lxy.entity.Permission;
import com.lxy.mapper.PermissionMapper;
import com.lxy.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxy
 * @since 2018-12-10
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}

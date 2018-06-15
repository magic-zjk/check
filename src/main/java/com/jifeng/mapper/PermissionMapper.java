package com.jifeng.mapper;

import java.util.List;

import com.jifeng.entity.system.Permission;

public interface PermissionMapper {
	
	List<Permission> selectList();
	
    int deleteByPermId(Integer permId);

    int insert(Permission permission);

    Permission selectByPermId(Integer permId);

    int update(Permission permission);
}
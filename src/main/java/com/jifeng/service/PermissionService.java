package com.jifeng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeng.entity.system.Permission;
import com.jifeng.mapper.PermissionMapper;

@Service
public class PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	public List<Permission> getPermList(){
		return permissionMapper.selectList();
	}
}

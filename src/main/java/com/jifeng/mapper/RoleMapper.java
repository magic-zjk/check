package com.jifeng.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jifeng.entity.Page;
import com.jifeng.entity.system.Role;
import com.jifeng.util.PageData;

@Repository
public interface RoleMapper {

	List<Role> queryRoleListPage(Page page);
	List<Role> selectRoleList();
	
	Role selectByRoleId(Integer roleId);
	
	Role selectByRoleName(String roleName);
	
	Role validateRoleKey(PageData pd);
	
    int insert(Role role);

    int update(Role role);

    int deleteByRoleId(Integer roleId);
    
//    int insertUserRole(PageData pd);
    int insertUserRole(@Param("userId")int userId,@Param("roleids")int[] roleids);
    
    int deleteUserRoleByUserId(Integer userId);
    
    int deleteUserRoleByRoleId(Integer roleId);
    
    int insertRoleResc(PageData pd);
    
    int deleteRoleRescByRoleId(Integer roleId);
    
    int deleteRoleRescByRescId(Integer rescId);
    
    Set<String> findRolesByUserName(String userName);
    
    List<Role> selectRoleByUserId(Integer userId);
    
}
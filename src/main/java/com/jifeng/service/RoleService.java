package com.jifeng.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeng.entity.Page;
import com.jifeng.entity.system.Role;
import com.jifeng.mapper.RoleMapper;
import com.jifeng.util.PageData;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * 角色列表分页
	 * @param page
	 * @return
	 * @author sushengli 2015年5月27日下午2:20:04
	 */
	public List<Role> getRoleListPage(Page page){
		return roleMapper.queryRoleListPage(page);
	}
	/**
	 * 角色列表分页
	 * @param page
	 * @return
	 * @author sushengli
	 */
	public List<Role> getRoleList(){
		return roleMapper.selectRoleList();
	}
	/**
	 * 根据主键ID查询对象
	 * @param roleId
	 * @return
	 * @author sushengli 2015年5月27日下午2:20:16
	 */
	public Role getByRoleId(Integer roleId){
		return roleMapper.selectByRoleId(roleId);
	}
	/**
	 * 根据角色名称查询对象
	 * @param roleName
	 * @return
	 * @author sushengli 2015年5月27日下午2:20:26
	 */
	public Role getByRoleName(String roleName){
		return roleMapper.selectByRoleName(roleName);
	}
	/**
	 * 验证角色名称
	 * @param pd
	 * @return
	 * @author sushengli 2015年5月27日下午2:20:38
	 */
	public Role validateRoleKey(PageData pd){
		return roleMapper.validateRoleKey(pd);
	}
	/**
	 * 插入角色
	 * @param role
	 * @return
	 * @author sushengli 2015年5月27日下午2:20:50
	 */
	public int insert(Role role){
		return roleMapper.insert(role);
	}
	/**
	 * 更新角色
	 * @param role
	 * @return
	 * @author sushengli 2015年5月27日下午2:20:58
	 */
	public int update(Role role){
		return roleMapper.update(role);
	}
	/**
	 * 根据主键删除
	 * @param roleId
	 * @return
	 * @author sushengli 2015年5月27日下午2:21:03
	 */
	public int deleteByRoleId(Integer roleId){
		return roleMapper.deleteByRoleId(roleId);
	}
	/**
	 * 保存用户--角色对应关系
	 * @param pd
	 * @return
	 * @author sushengli 2015年5月27日下午2:21:11
	 */
//	public int saveUserRole(PageData pd){
//		return roleMapper.insertUserRole(pd);
//	}
	/**
	 * 保存用户--角色对应关系
	 * @param userId
	 * @param roleids
	 * @return
	 * @author sushengli
	 */
	public int insertUserRole(int userId,int[] roleids){
		return roleMapper.insertUserRole(userId,roleids);
	}
	/**
	 * 删除用户--角色（根据用户ID）
	 * @param userId
	 * @return
	 * @author sushengli 2015年5月27日下午2:21:42
	 */
	public int deleteUserRoleByUserId(Integer userId){
		return roleMapper.deleteUserRoleByUserId(userId);
	}
	/**
	 * 删除用户--角色（根据角色ID）
	 * @param roleId
	 * @return
	 * @author sushengli 2015年5月27日下午2:22:04
	 */
	public int deleteUserRoleByRoleId(Integer roleId){
		return roleMapper.deleteUserRoleByRoleId(roleId);
	}
	/**
	 * 保存角色--资源对应关系
	 * @param pd
	 * @return
	 * @author sushengli 2015年5月27日下午2:22:18
	 */
	public int saveRoleResc(PageData pd){
		return roleMapper.insertRoleResc(pd);
	}
	/**
	 * 删除角色--资源（根据角色ID）
	 * @param roleId
	 * @return
	 * @author sushengli 2015年5月27日下午2:22:31
	 */
	public int deleteRoleRescByRoleId(Integer roleId){
		return roleMapper.deleteRoleRescByRoleId(roleId);
	}
	/**
	 * 删除角色--资源（根据资源ID）
	 * @param rescId
	 * @return
	 * @author sushengli 2015年5月27日下午2:22:49
	 */
	public int deleteRoleRescByRescId(Integer rescId){
		return roleMapper.deleteRoleRescByRescId(rescId);
	}
	/**
	 * 根据用户名查询角色
	 * @param userName
	 * @return
	 * @author sushengli 2015年5月27日下午2:23:03
	 */
	public Set<String> findRolesByUserName(String userName){
		return roleMapper.findRolesByUserName(userName);
	}
	/**
	 * 保存角色授权操作
	 * @param pd
	 * @return
	 * @author sushengli 2015年5月27日下午2:23:27
	 */
	public int authSave(PageData pd){
		Integer roleId = pd.getInteger("roleId");
		this.deleteRoleRescByRoleId(roleId);
		return this.saveRoleResc(pd);
	}
	/**
	 * 根据用户Id查找用户角色集合
	 * @param userId 用户编号
	 * @return 角色集合
	 * @author sushengli
	 */
	public List<Role> selectRoleByUserId(int userId){
		return roleMapper.selectRoleByUserId(userId);
	}
}

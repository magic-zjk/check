package com.jifeng.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeng.entity.Page;
import com.jifeng.entity.system.User;
import com.jifeng.mapper.UserMapper;
import com.jifeng.util.PageData;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 用户列表分页
	 * @param page
	 * @return
	 * @author sushengli 2015年5月27日下午2:17:50
	 */
	public List<User> getUserListPage(Page page){
		return userMapper.queryUserListPage(page);
	}
	/**
	 * 查询列表
	 * @param pd
	 * @return
	 * @author sushengli 2015年7月30日下午2:59:33
	 */
	public List<User> getUserList(PageData pd){
		return userMapper.queryUserList(pd);
	}
	/**
	 * 根据主键ID查询
	 * @param userId
	 * @return
	 * @author sushengli 2015年5月27日下午2:18:06
	 */
	public User getByUserId(Integer userId){
		return userMapper.selectByUserId(userId);
	}
	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 * @author sushengli 2015年5月27日下午2:18:18
	 */
	public User getByUserName(String userName){
		return userMapper.selectByUserName(userName);
	}
	/**
	 * 验证用户名
	 * @param pd
	 * @return
	 * @author sushengli 2015年5月27日下午2:18:28
	 */
	public User validateUserName(PageData pd){
		return userMapper.validateUserName(pd);
	}
	/**
	 * 插入用户
	 * @param user
	 * @return
	 * @author sushengli 2015年5月27日下午2:18:40
	 */
	public int insert(User user){
		return userMapper.insert(user);
	}
	/**
	 * 更新用户
	 * @param user
	 * @return
	 * @author sushengli 2015年5月27日下午2:18:51
	 */
	public int update(User user){
		return userMapper.update(user);
	}
	/**
	 * 根据主键ID删除
	 * @param userId
	 * @return
	 * @author sushengli 2015年5月27日下午2:18:57
	 */
	public int deleteByUserId(Integer userId){
		return userMapper.deleteByUserId(userId);
	}
	/**
	 * 根据用户名查询角色role_key
	 * @param userName
	 * @return
	 * @author sushengli 2015年5月27日下午2:19:05
	 */
	public Set<String> findRoles(String userName){
		return roleService.findRolesByUserName(userName);
	}
	/**
	 * 根据用户名查询权限
	 * @param userName
	 * @return
	 * @author sushengli 2015年5月27日下午2:19:39
	 */
	public Set<String> findPermissions(String userName){
		return resourceService.findPermissionsByUserName(userName);
	}
	/**
	 * 关联用户和角色
	 * 一个用户对应多个角色
	 * @param userid
	 * @param roleids
	 * @return
	 */
	public int insertUserRoles(int userid,int[] roleids){
		return roleService.insertUserRole(userid, roleids);
	}
	/**
	 * 根据用户id删除用户角色
	 * @param userId
	 * @return
	 */
	public int deleteUserRoleByUserId(int userId){
		return roleService.deleteUserRoleByUserId(userId);
	}
}

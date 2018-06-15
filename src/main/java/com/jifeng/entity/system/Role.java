package com.jifeng.entity.system;

import java.io.Serializable;

/**
 * 角色对象
 * @author sushengli
 * 2015年5月20日下午12:07:33
 */
@SuppressWarnings("serial")
public class Role implements Serializable{
	private Integer roleId;		//主键ID
	private String roleName;	//角色名称
	private String roleKey;		//角色标识
	private Integer status;		//状态 0可用 1不可用
	
	/*getter and setter*/
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

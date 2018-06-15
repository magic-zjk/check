package com.jifeng.entity.system;

import java.io.Serializable;
import java.util.List;

/**
 * 用户对象
 * @author sushengli
 * 2015年5月20日下午12:06:31
 */
@SuppressWarnings("serial")
public class User implements Serializable{
	
	private Integer userId;		//用户id
	private String userName;	//用户名
	private String password; 	//密码
	private String realName;	//姓名
	private String salt;		//盐值
	private Integer age;		//年龄
	private Integer sex;		//性别 0女 1男
	private String phone;		//电话
	private Integer status;		//状态 0可用 1不可用
	private String skin;		//皮肤
	
	private List<Role> roles;   //用户对应角色
	
	/*getter and setter*/
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getCredentialsSalt() {
        return this.userName + this.salt;
    }
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}

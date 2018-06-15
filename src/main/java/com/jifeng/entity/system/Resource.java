package com.jifeng.entity.system;

import java.io.Serializable;
import java.util.List;
/**
 * 资源对象
 * @author sushengli
 * 2015年5月20日下午12:10:48
 */
@SuppressWarnings("serial")
public class Resource implements Serializable{
	
	private Integer rescId;		//主键ID
	private String rescName;	//资源名称
	private Integer parentId;	//父级ID
	private String identity;	//资源标识
	private String url;			//资源url
	private Integer type;		//类型
	private String icon;		//图标
	private Integer sort;		//排序
	private String rescDesc;	//描述
	
	private Resource parentResc;	//父级资源
	private List<Resource> subRescs;//子级资源列表
	private String permissions;		//对应操作权限
	
	/*getter and setter*/
	public Integer getRescId() {
		return rescId;
	}
	public void setRescId(Integer rescId) {
		this.rescId = rescId;
	}
	public String getRescName() {
		return rescName;
	}
	public void setRescName(String rescName) {
		this.rescName = rescName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getRescDesc() {
		return rescDesc;
	}
	public void setRescDesc(String rescDesc) {
		this.rescDesc = rescDesc;
	}
	public Resource getParentResc() {
		return parentResc;
	}
	public void setParentResc(Resource parentResc) {
		this.parentResc = parentResc;
	}
	public List<Resource> getSubRescs() {
		return subRescs;
	}
	public void setSubRescs(List<Resource> subRescs) {
		this.subRescs = subRescs;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	
}

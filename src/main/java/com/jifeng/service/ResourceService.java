package com.jifeng.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeng.entity.system.Resource;
import com.jifeng.mapper.ResourceMapper;
import com.jifeng.util.PageData;

@Service
public class ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 获取所有顶级资源列表（parentId为0）
	 * @return
	 * @author sushengli 2015年5月27日下午2:23:55
	 */
	public List<Resource> getAllParentResc(){
		return resourceMapper.selectAllParentResc();
	}
	/**
	 * 获取下级资源
	 * @param parentId
	 * @return
	 * @author sushengli 2015年5月27日下午2:24:39
	 */
	public List<Resource> getSubResc(Integer parentId){
		return resourceMapper.selectSubResc(parentId);
	}
	/**
	 * 获取所有下级
	 * @param rescList
	 * @param parentId
	 * @return
	 * @author sushengli 2015年5月27日下午2:24:56
	 */
	public List<Resource> getAllSubResc(List<Resource> rescList, Integer parentId){
		rescList = rescList == null?new ArrayList<Resource>():rescList;
		List<Resource> subRescList = this.getSubResc(parentId);
		for(Resource resc : subRescList){
			rescList.add(resc);
			this.getAllSubResc(rescList, resc.getRescId());
		}
		return rescList;
	}
	/**
	 * 获取首页的左边菜单列表
	 * @return
	 * @author sushengli 2015年5月27日下午2:25:25
	 */
	public List<Resource> getLeftMenu(PageData pd){
		return resourceMapper.selectLeftMenu(pd);
	}
	/**
	 * 根据主键ID查询对象
	 * @param rescId
	 * @return
	 * @author sushengli 2015年5月27日下午2:25:57
	 */
	public Resource getByRescId(Integer rescId){
		return resourceMapper.selectByRescId(rescId);
	}
	/**
	 * 根据角色查询对应资源列表
	 * @param roleId
	 * @return
	 * @author sushengli 2015年5月27日下午2:26:06
	 */
	public List<Resource> getByRoleId(Integer roleId){
		return resourceMapper.selectByRoleId(roleId);
	}
	/**
	 * 插入
	 * @param resource
	 * @return
	 * @author sushengli 2015年5月27日下午2:26:28
	 */
	public int insert(Resource resource){
		return resourceMapper.insert(resource);
	}
	/**
	 * 更新
	 * @param resource
	 * @return
	 * @author sushengli 2015年5月27日下午2:26:33
	 */
	public int update(Resource resource){
		return resourceMapper.update(resource);
	}
	/**
	 * 根据主键删除
	 * @param rescId
	 * @return
	 * @author sushengli 2015年5月27日下午2:26:38
	 */
	public int deleteByRescId(Integer rescId){
		return resourceMapper.deleteByRescId(rescId);
	}
	/**
	 * 批量删除
	 * @param rescList
	 * @return
	 * @author sushengli 2015年5月27日下午2:26:45
	 */
	public int batchDeleteResc(List<Resource> rescList){
		return resourceMapper.batchDeleteResc(rescList);
	}
	/**
	 * 级联删除
	 * @param rescId
	 * @return
	 * @author sushengli 2015年5月27日下午2:26:56
	 */
	public int cascadeDeleteResc(Integer rescId){
		List<Resource> subList = this.getAllSubResc(null, rescId);
		if(subList != null && subList.size() > 0){
			for(Resource resc : subList){
				roleService.deleteRoleRescByRescId(resc.getRescId());
			}
			this.batchDeleteResc(subList);
		}
		roleService.deleteRoleRescByRescId(rescId);
		return this.deleteByRescId(rescId);
	}
	/**
	 * 根据用户名查询权限集合
	 * @param userName
	 * @return
	 * @author sushengli 2015年5月27日下午2:27:34
	 */
	public Set<String> findPermissionsByUserName(String userName){
		PageData pd = new PageData();
		pd.put("userName", userName);
		return resourceMapper.findPermissions(pd);
	}
}

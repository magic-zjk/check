package com.jifeng.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.jifeng.controller.base.BaseController;
import com.jifeng.entity.Page;
import com.jifeng.entity.system.Permission;
import com.jifeng.entity.system.Resource;
import com.jifeng.entity.system.Role;
import com.jifeng.enums.StatusType;
import com.jifeng.service.PermissionService;
import com.jifeng.service.ResourceService;
import com.jifeng.service.RoleService;
import com.jifeng.util.PageData;
import com.jifeng.util.webUtil.ResponseUtils;
/**
 * 角色
 * @author sushengli
 * 2015年5月21日下午4:47:27
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 角色列表页面
	 */
	@RequiresPermissions("role:view")
	@RequestMapping(value="/listRoles")
	public String listRoles(Page page,ModelMap model)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Role> roleList = roleService.getRoleListPage(page);
		model.put("roleList", roleList);
		return "system/role/role_list";
	}
	
	/**
	 * 角色添加页面
	 * @return
	 * @author sushengli 2015年5月27日下午2:14:17
	 */
	@RequiresPermissions("role:create")
	@RequestMapping(value="/toAddRole")
	public String toAddRole(){
		return "system/role/role_add";
	}
	
	/**
	 * 角色添加保存
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @author sushengli 2015年5月27日下午2:14:24
	 */
	@RequiresPermissions("role:create")
	@RequestMapping(value = "/doAddRole",method = RequestMethod.POST)
	public String doAddRole(HttpServletRequest request,ModelMap model)throws Exception{
		try {
			PageData pd = this.getPageData();
			Role role = new Role();
			role.setRoleName(pd.getString("roleName"));
			role.setRoleKey(pd.getString("roleKey"));
			role.setStatus(StatusType.NORMAL.getCode());
			roleService.insert(role);
			model.put("msg","success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 角色编辑页面
	 * @param roleId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:14:32
	 */
	@RequiresPermissions("role:update")
	@RequestMapping(value="/toEditRole")
	public String toEditRole(
			@RequestParam(value="roleId",required=true) Integer roleId,
			ModelMap model){
		Role role = roleService.getByRoleId(roleId);
		model.put("role", role);
		return "system/role/role_edit";
	}
	
	/**
	 * 角色编辑保存
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @author sushengli 2015年5月27日下午2:14:40
	 */
	@RequiresPermissions("role:update")
	@RequestMapping(value = "/doEditRole",method = RequestMethod.POST)
	public String doEditRole(HttpServletRequest request,ModelMap model)throws Exception{
		try {
			PageData pd = this.getPageData();
			Role role = roleService.getByRoleId(pd.getInteger("roleId"));
			role.setRoleName(pd.getString("roleName"));
			role.setRoleKey(pd.getString("roleKey"));
			roleService.update(role);
			model.put("msg","success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 删除角色操作
	 * @param roleId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:14:47
	 */
	@RequiresPermissions("role:delete")
	@RequestMapping(value="/deleteRole")
	public String deleteRole(
			@RequestParam(value="roleId",required=true) Integer roleId,
			ModelMap model){
		
		try {
			roleService.deleteRoleRescByRoleId(roleId);
			roleService.deleteUserRoleByRoleId(roleId);
			roleService.deleteByRoleId(roleId);
			
			model.put("msg","success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 角色授权页面
	 * @param roleId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:14:55
	 */
	@RequiresPermissions(value={"role:create","role:update"},logical=Logical.OR)
	@RequestMapping(value="/auth")
	public String auth(
			@RequestParam(value="roleId",required=true) Integer roleId,
			ModelMap model){
		//所有资源列表
		List<Resource> allList = resourceService.getAllSubResc(null, 0);
		List<Permission> permList = permissionService.getPermList();
		//该角色的对应资源列表
		List<Resource> checkedList = resourceService.getByRoleId(roleId);
		List<Map<String,Object>> jsonList = new ArrayList<Map<String,Object>>();
		for(Resource resc : allList){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", resc.getRescId());
			map.put("pId", resc.getParentId());
			map.put("name", resc.getRescName());
			if(0 == resc.getParentId()){
				map.put("open", true);
			}
			Resource currCheckedResc = null;
			for(Resource checkedResc : checkedList){
				if(resc.getRescId().equals(checkedResc.getRescId())){
					map.put("checked", true);
					currCheckedResc = checkedResc;
				}
			}
			if(StringUtils.isNotBlank(resc.getIdentity())){
				for(Permission perm : permList){
					Map<String,Object> permMap = new HashMap<String, Object>();
					permMap.put("id", resc.getRescId()+"_perm_"+perm.getPermId());
					permMap.put("pId", resc.getRescId());
					permMap.put("name", perm.getPermName());
					if(currCheckedResc != null){
						permMap.put("checked", (","+currCheckedResc.getPermissions()+",").contains(","+perm.getPermId()+","));
					}
					jsonList.add(permMap);
				}
			}
			jsonList.add(map);
		}
		model.put("roleId", roleId);
		model.put("zTreeNodes", new Gson().toJson(jsonList));
		return "authorization";
	}
	
	/**
	 * 角色授权保存
	 * @param roleId
	 * @param rescIds
	 * @param response
	 * @param model
	 * @author sushengli 2015年5月27日下午2:15:08
	 */
	@RequiresPermissions(value={"role:create","role:update"},logical=Logical.OR)
	@RequestMapping(value="/auth/save")
	public void authSave(
			@RequestParam(value="roleId",required=true) Integer roleId,
			@RequestParam(value="rescIds",required=true) String rescIds,
			HttpServletResponse response, ModelMap model){
		try {
			
			PageData pd = new PageData();
			
			String[] checkedIds = rescIds.split(",");
			List<Map<String,Object>> checkedRescList = new ArrayList<Map<String,Object>>();
			for(String id : checkedIds){
				if(!id.contains("_perm_")){
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("rescId", id);
					StringBuffer permId = new StringBuffer();
					for(String pid : checkedIds){
						if(pid.contains(id+"_perm_")){
							permId.append(pid.substring(pid.indexOf("_perm_")+6, pid.length())+",");
						}
					}
					map.put("permId", permId.length()>0?permId.substring(0, permId.length()-1):null);
					checkedRescList.add(map);
				}
			}
			pd.put("roleId", roleId);
			pd.put("checkedRescList", checkedRescList);
			roleService.authSave(pd);
			
			ResponseUtils.renderText(response, "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ResponseUtils.renderText(response, "failed");
		}
	}
	
	/**
	 * 校验角色
	 * @param roleId
	 * @param roleName
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author sushengli 2015年5月27日下午2:15:16
	 */
	@RequiresPermissions(value={"role:create","role:update"},logical=Logical.OR)
	@RequestMapping(value="/validateRole")
	public void validateRole(
			@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "roleKey", required = true) String roleKey,
			HttpServletResponse response,ModelMap model)throws Exception{
		PageData pd = new PageData();
		pd.put("roleKey", roleKey);
		pd.put("roleId", roleId);
		Role role = roleService.validateRoleKey(pd);
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(role != null){
			result.put("result", "failed");
		}else{
			result.put("result", "success");
		}
		ResponseUtils.renderJson(response, new Gson().toJson(result));
	}

}

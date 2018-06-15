package com.jifeng.controller.system;

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
import com.jifeng.entity.system.Role;
import com.jifeng.entity.system.User;
import com.jifeng.enums.StatusType;
import com.jifeng.service.RoleService;
import com.jifeng.service.UserService;
import com.jifeng.util.JsonUtil;
import com.jifeng.util.PageData;
import com.jifeng.util.PasswordHelper;
import com.jifeng.util.webUtil.ResponseUtils;

/**
 * 用户
 * @author sushengli
 * 2015年5月21日下午4:42:08
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordHelper passwordHelper;
	
	/**
	 * 用户列表页
	 */
	@RequiresPermissions("user:view")
	@RequestMapping(value="/listUsers")
	public String listUsers(Page page,ModelMap model)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<User> userList = userService.getUserListPage(page);
		List<Role> roleList = roleService.getRoleList();
		model.put("userList", userList);
		model.put("pd", pd);
		model.put("roleList", roleList);
		return "system/user/user_list";
	}
	
	/**
	 * 用户添加页面
	 * @return
	 * @author sushengli 2015年5月27日下午2:11:08
	 */
	@RequiresPermissions("user:create")
	@RequestMapping(value="/toAddUser")
	public String toAddUser(ModelMap model){
		List<Role> roleList = roleService.getRoleList();
		model.put("roleList", roleList);
		return "system/user/user_add";
	}
	
	/**
	 * 用户添加保存
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @author sushengli 2015年5月27日下午2:11:28
	 */
	@RequiresPermissions("user:create")
	@RequestMapping(value = "/doAddUser",method = RequestMethod.POST)
	public String doAddUser(HttpServletRequest request,ModelMap model)throws Exception{
		try {
			PageData pd = this.getPageData();
			User user = new User();
			user.setUserName(pd.getString("userName"));
			user.setPassword(pd.getString("password"));
			user.setRealName(pd.getString("realName"));
			user.setSex(pd.getInteger("sex"));
			user.setAge(pd.getInteger("age"));
			user.setPhone(pd.getString("phone"));
			user.setStatus(StatusType.NORMAL.getCode());
			passwordHelper.encryptPassword(user);//生成密码
			//角色
			String[] roles=getRequest().getParameterValues("role");
			int[] roleids=null;
			userService.insert(user);
			if(roles!=null && roles.length>0){
				roleids = new int[roles.length];
				for(int i = 0;i<roles.length;i++){
					roleids[i]=Integer.valueOf(roles[i]);
				}
				userService.insertUserRoles(user.getUserId(), roleids);
			}
			model.put("msg","success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 用户编辑页面
	 * @param userId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:12:45
	 */
	@RequiresPermissions("user:update")
	@RequestMapping(value="/toEditUser")
	public String toEditUser(
			@RequestParam(value="userId",required=true) Integer userId,
			ModelMap model){
		User user = userService.getByUserId(userId);
		model.put("user", user);
		List<Role> roles = user.getRoles();
		model.put("roles", JsonUtil.convertObjectToJson(roles));
		List<Role> roleList = roleService.getRoleList();
		model.put("roleList", roleList);
		return "system/user/user_edit";
	}
	/**
	 * 个人资料修改
	 * 每个用户均有权限修改个人资料
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toEditUserSelf")
	public String toEditUserSelf(@RequestParam(value="userId",required=true) Integer userId,
			ModelMap model){
			User user = userService.getByUserId(userId);
			model.put("user", user);
			return "system/user/user_edit_userself";
	}
	/**
	 * 个人资料修改保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/doEditUserSelf",method = RequestMethod.POST)
	public String doEditUserSelf(HttpServletRequest request,ModelMap model){
		try {
			PageData pd = this.getPageData();
			int userId= pd.getInteger("userId");
			User user = userService.getByUserId(userId);
			user.setUserName(pd.getString("userName"));
			user.setRealName(pd.getString("realName"));
			user.setSex(pd.getInteger("sex"));
			user.setAge(pd.getInteger("age"));
			user.setPhone(pd.getString("phone"));
			if(StringUtils.isNotBlank(pd.getString("password"))){
				user.setPassword(pd.getString("password"));
				passwordHelper.encryptPassword(user);
			}
			//用户信息update
			userService.update(user);
			model.put("msg","success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	/**
	 * 用户编辑保存
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @author sushengli 2015年5月27日下午2:12:56
	 */
	@RequiresPermissions("user:update")
	@RequestMapping(value = "/doEditUser",method = RequestMethod.POST)
	public String doEditUser(HttpServletRequest request,ModelMap model)throws Exception{
		try {
			PageData pd = this.getPageData();
			int userId= pd.getInteger("userId");
			User user = userService.getByUserId(userId);
			user.setUserName(pd.getString("userName"));
			user.setRealName(pd.getString("realName"));
			user.setSex(pd.getInteger("sex"));
			user.setAge(pd.getInteger("age"));
			user.setPhone(pd.getString("phone"));
			if(StringUtils.isNotBlank(pd.getString("password"))){
				user.setPassword(pd.getString("password"));
				passwordHelper.encryptPassword(user);
			}
			
			//角色
			String[] roles=getRequest().getParameterValues("role");
			int[] roleids=null;
			userService.update(user);
			
			if(roles!=null && roles.length>0){
				roleids = new int[roles.length];
				for(int i = 0;i<roles.length;i++){
					roleids[i]=Integer.valueOf(roles[i]);
				}
				//先删除用户原有角色
				userService.deleteUserRoleByUserId(userId);
				//后添加用户选择的角色
				userService.insertUserRoles(userId, roleids);
			}else{
				//如果用户修改时没有赋予角色，那么需要把用户角色关联表中该用户对应的角色全部删除
				userService.deleteUserRoleByUserId(userId);
			}
			model.put("msg","success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 删除用户操作
	 * @param userId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:13:06
	 */
	@RequiresPermissions("user:delete")
	@RequestMapping(value="/deleteUser")
	public String deleteUser(
			@RequestParam(value="userId",required=true) Integer userId,
			ModelMap model){
		try {
			roleService.deleteUserRoleByUserId(userId);
			userService.deleteByUserId(userId);
			model.put("msg","success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 校验用户
	 * @param userId
	 * @param userName
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author sushengli 2015年5月27日下午2:13:19
	 */
	@RequiresPermissions(value={"user:create","user:update"},logical=Logical.OR)
	@RequestMapping(value="/validateUser")
	public void validateUser(
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "userName", required = true) String userName,
			HttpServletResponse response,ModelMap model)throws Exception{
		PageData pd = new PageData();
		pd.put("userName", userName);
		pd.put("userId", userId);
		User user = userService.validateUserName(pd);
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(user != null){
			result.put("result", "failed");
		}else{
			result.put("result", "success");
		}
		ResponseUtils.renderJson(response, new Gson().toJson(result));
	}
}

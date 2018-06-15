package com.jifeng.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.jifeng.entity.system.Resource;
import com.jifeng.service.ResourceService;
import com.jifeng.service.RoleService;
import com.jifeng.util.PageData;
import com.jifeng.util.webUtil.ResponseUtils;
/**
 * 资源
 * @author sushengli
 * 2015年5月21日下午4:48:43
 */
@Controller
@RequestMapping(value="/resc")
public class ResourceController extends BaseController {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 资源列表页面
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:15:28
	 */
	@RequiresPermissions("resc:view")
	@RequestMapping(value="/listRescs")
	public String listResource(ModelMap model){
		List<Resource> list = resourceService.getAllParentResc();
		//List<Resource> list = resourceService.getAllSubResc(null, 0);
		model.put("rescList", list);
		return "system/resc/resc_list";
	}
	
	/**
	 * 下级资源列表
	 * @param rescId
	 * @param response
	 * @param model
	 * @author sushengli 2015年5月27日下午2:15:35
	 */
	@RequiresPermissions("resc:view")
	@RequestMapping(value="/listSub")
	public void listSub(
			@RequestParam(value="rescId",required=true) Integer rescId,
			HttpServletResponse response, ModelMap model){
		List<Resource> list = resourceService.getSubResc(rescId);
		ResponseUtils.renderJson(response, new Gson().toJson(list));
	}
	
	/**
	 * 资源添加页面
	 * @param rescId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:16:05
	 */
	@RequiresPermissions("resc:create")
	@RequestMapping(value="/toAddResc")
	public String toAddResc(
			@RequestParam(value="rescId",required=false) Integer rescId,
			ModelMap model){
		if(rescId != null){
			Resource parentResc = resourceService.getByRescId(rescId);
			model.put("parentResc", parentResc);
		}
		return "system/resc/resc_add";
	}
	
	/**
	 * 资源添加保存
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:16:11
	 */
	@RequiresPermissions("resc:create")
	@RequestMapping(value="/doAddResc",method = RequestMethod.POST)
	public String doAddResc(ModelMap model){
		try {
			PageData pd = this.getPageData();
			Resource resc = new Resource();
			if(pd.getInteger("parentId") != null){
				resc.setParentId(pd.getInteger("parentId"));
			}else{
				resc.setParentId(0);
			}
			resc.setRescName(pd.getString("rescName"));
			resc.setIdentity(pd.getString("identity"));
			resc.setUrl(pd.getString("url"));
			resc.setType(1);
			resc.setSort(pd.getInteger("sort"));
			resc.setRescDesc(pd.getString("rescDesc"));
			resourceService.insert(resc);
			model.put("msg", "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 资源编辑页面
	 * @param rescId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:16:21
	 */
	@RequiresPermissions("resc:update")
	@RequestMapping(value="/toEditResc")
	public String toEditResc(
			@RequestParam(value="rescId",required=true) Integer rescId,
			ModelMap model){
		Resource resc = resourceService.getByRescId(rescId);
		if(resc.getParentId() != null){
			Resource parentResc = resourceService.getByRescId(resc.getParentId());
			model.put("parentResc", parentResc);
		}
		model.put("resc", resc);
		return "system/resc/resc_edit";
	}
	
	/**
	 * 资源编辑保存
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:16:27
	 */
	@RequiresPermissions("resc:update")
	@RequestMapping(value="/doEditResc",method = RequestMethod.POST)
	public String doEditResc(ModelMap model){
		try {
			PageData pd = this.getPageData();
			Resource resc = resourceService.getByRescId(pd.getInteger("rescId"));
			resc.setRescName(pd.getString("rescName"));
			resc.setIdentity(pd.getString("identity"));
			resc.setUrl(pd.getString("url"));
			resc.setType(1);
			resc.setSort(pd.getInteger("sort"));
			resc.setRescDesc(pd.getString("rescDesc"));
			resourceService.update(resc);
			model.put("msg", "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 资源图标页面
	 * @param rescId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:16:37
	 */
	@RequiresPermissions(value={"resc:create","resc:update"},logical=Logical.OR)
	@RequestMapping(value="/toEditIcon")
	public String toEditIcon(
			@RequestParam(value="rescId",required=true) Integer rescId,
			ModelMap model){
		model.put("rescId", rescId);
		return "system/resc/resc_icon";
	}
	
	/**
	 * 资源图标保存
	 * @param rescId
	 * @param rescIcon
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:16:47
	 */
	@RequiresPermissions(value={"resc:create","resc:update"},logical=Logical.OR)
	@RequestMapping(value="/doEditIcon")
	public String doEditIcon(
			@RequestParam(value="rescId",required=true) Integer rescId,
			@RequestParam(value="icon",required=true) String icon,
			ModelMap model){
		try{
			Resource resc = resourceService.getByRescId(rescId);
			resc.setIcon(icon);
			resourceService.update(resc);
			model.put("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			model.put("msg","failed");
		}
		return "save_result";
	}
	
	/**
	 * 删除资源
	 * @param rescId
	 * @param model
	 * @return
	 * @author sushengli 2015年5月27日下午2:16:55
	 */
	@RequiresPermissions("resc:delete")
	@RequestMapping(value="/deleteResc")
	public String deleteResc(
			@RequestParam(value="rescId",required=true) Integer rescId,
			ModelMap model){
		resourceService.cascadeDeleteResc(rescId);
		model.put("msg","success");
		return "save_result";
	}
	
	
}

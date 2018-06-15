package com.jifeng.controller.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fusioncharts.exporter.encoders.JPEGEncoder;
import com.google.gson.Gson;
import com.jifeng.controller.base.BaseController;
import com.jifeng.entity.system.Resource;
import com.jifeng.entity.system.Role;
import com.jifeng.entity.system.User;
import com.jifeng.service.ResourceService;
import com.jifeng.service.RoleService;
import com.jifeng.service.UserService;
import com.jifeng.util.Const;
import com.jifeng.util.PageData;
import com.jifeng.util.PropertyUtils;
import com.jifeng.util.webUtil.ResponseUtils;
/*
 * 总入口
 */
@Controller
public class LoginController extends BaseController {

	Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/login")
	public String toLogin(){
		return "main/login";
	}
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/doLogin")
	public void login(
			@RequestParam(value = "userName", required = true) final String userName,
			@RequestParam(value = "password", required = true) final String password,
			@RequestParam(value = "code", required = true) final String code,
			HttpServletRequest request,HttpServletResponse response, ModelMap modelMap)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		//验证码校验
		String sessionCode = (String) request.getSession().getAttribute(Const.SESSION_SECURITY_CODE);
		if(StringUtils.isBlank(sessionCode)){
			map.put("result", "codeexpired");
			ResponseUtils.renderJson(response, new Gson().toJson(map));
			return;
		}
		if(!code.toLowerCase().equals(sessionCode.toLowerCase())){
			map.put("result", "codeerror");
			ResponseUtils.renderJson(response, new Gson().toJson(map));
			return;
		}
		//用户名密码校验
		try {
			logger.info("login username:"+userName+"\t password:"+password);
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			SecurityUtils.getSubject().login(token);//Shiro登录验证
			User user = userService.getByUserName(userName);
			request.getSession().setAttribute(Const.CURR_USER, user);//记入session
			List<Role> roles = roleService.selectRoleByUserId(user.getUserId());
			request.getSession().setAttribute(Const.CURR_ROLES, roles);//记入session
			request.getSession().setAttribute("downUrl", PropertyUtils.getValue("downUrl"));
			map.put("result", "success");
			ResponseUtils.renderJson(response, new Gson().toJson(map));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			logger.debug("认证失败！");
			map.put("result", "usererror");
			ResponseUtils.renderJson(response, new Gson().toJson(map));
		}
	}
	
	/**
	 * 访问系统首页
	 */
	@RequestMapping(value="/index")
	public String toIndex(HttpServletRequest request,ModelMap modelMap){
			
		User user = (User)request.getSession().getAttribute(Const.CURR_USER);
		if(user == null){
			return "main/login";
		}
		PageData pd = new PageData();
		if(!"admin".equals(user.getUserName())){
			pd.put("userId", user.getUserId());
		}
		pd.put("parentId", 0);
		List<Resource> menuList = resourceService.getLeftMenu(pd);
		modelMap.put("user", user);
		modelMap.put("menuList", menuList);
		return "main/index";
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "main/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public String defaultPage(){
		return "main/default";
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(ModelMap model){
		
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		
		return this.toLogin();
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/noperms")
	public String noperms(){
		return "noperms";
	}
	
	@RequestMapping(value="/secucode")
	public void makeSecurityCode(HttpServletResponse response){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = drawImg(output);
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		session.setAttribute(Const.SESSION_SECURITY_CODE, code);
		
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String drawImg(ByteArrayOutputStream output){
		String code = "";
		for(int i=0; i<4; i++){
			code += randomChar();
		}
		int width = 70;
		int height = 25;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		Color color = new Color(66,2,82);
		g.setColor(color);
		g.setBackground(new Color(226,226,240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int)x, (int)baseY);
		g.dispose();
		try {
			JPEGEncoder encoder = new JPEGEncoder();
			encoder.encode(bi, output);
//			ImageIO.write(bi, "jpg", output);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return code;
	}
	
	private char randomChar(){
		Random r = new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(r.nextInt(s.length()));
	}
	
}

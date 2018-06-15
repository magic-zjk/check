<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <!-- <a class="brand"><small><i class="icon-leaf"></i>管理系统后台</small> </a> -->
			  <a class="brand">
			  <small>
			  	<img style="height: 20px;width:auto;" src="static/img/jingfeng_logo.png">管理系统后台</small>
			  </a>
			  
			  <ul class="nav ace-nav pull-right">
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="javascript:alert('预留功能,待开发');" data-toggle="dropdown">
							<img alt="机锋" src="static/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info">
								<small>Welcome</small>${user.realName}
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
						<shiro:lacksRole name="customer">
							<li><a onclick="editUserH('${user.userId}');" style="cursor:pointer;"><i class="icon-user"></i>修改资料</a></li>
							<li class="divider"></li>
						</shiro:lacksRole>
							<li><a href="logout"><i class="icon-off"></i>退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
	
	
		
		<script type="text/javascript" src="static/js/myjs/head.js"></script>
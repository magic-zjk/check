﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../main/top.jsp"%> 	
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="user/listUsers" method="post" name="userForm" id="userForm">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="userName" value="${param.userName }" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="roleId" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;width: 120px;">
						<option value="">全部</option>
						<c:choose>
							<c:when test="${not empty roleList}">
							<c:forEach items="${roleList}" var="role" varStatus="vs">
								<option value="${role.roleId}" <c:if test="${param.roleId == role.roleId}">selected</c:if>>${role.roleName}</option>
							</c:forEach>
							</c:when>
						</c:choose>
						
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /></label>
						</th>
						<th>序号</th>
						<th>用户名</th>
						<th>姓名</th>
						<th>性别</th>
						<th>年龄</th>
						<th>电话</th>
						<th>角色</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty userList}">
						<c:forEach items="${userList}" var="user" varStatus="vs">
									
							<tr>
								<td class='center' style="width: 30px;">
									<c:if test="${user.userName != 'admin'}"><label><input type='checkbox' name='ids' value="${user.userId }" alt="${user.phone }"/></label></c:if>
									<c:if test="${user.userName == 'admin'}"><label><input type='checkbox' disabled="disabled" /></label></c:if>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td><a>${user.userName}</a></td>
								<td>${user.realName}</td>
								<td>
									<c:if test="${user.sex == 0 }">女</c:if>
									<c:if test="${user.sex == 1 }">男</c:if>
								</td>
								<td>${user.age}</td>
								<td>${user.phone}</td>
								<td>
								<c:choose>
								<c:when test="${not empty user.roles}">
								<c:forEach items="${user.roles}" var="r" varStatus="st">
									<c:if test="${!st.last}">${r.roleName},</c:if>
									<c:if test="${st.last}">${r.roleName}</c:if>
								</c:forEach>
								</c:when>
								</c:choose>
								</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<shiro:hasPermission name="user:update">
										<c:choose>
											<c:when test="${user.userName != 'admin' or currUser.userName == 'admin'}">
												<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.userId }');"><i class='icon-edit'></i></a>
											</c:when>
											<c:otherwise>
												<a class='btn btn-mini btn-info' title="您不能编辑"><i class='icon-edit'></i></a>
											</c:otherwise>
										</c:choose>
										</shiro:hasPermission>
										<shiro:hasPermission name="user:delete">
										<c:choose>
											<c:when test="${user.userName=='admin'}">
												<a class='btn btn-mini btn-danger' title="不能删除"><i class='icon-trash'></i></a>
											</c:when>
											<c:otherwise>
												 <a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${user.userId }','${user.userName }');"><i class='icon-trash'></i></a>
											</c:otherwise>
										</c:choose>
										</shiro:hasPermission>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<shiro:hasPermission name="user:create">
						<a class="btn btn-small btn-success" onclick="add();">新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="user:delete">
					<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'></i></a>
					</shiro:hasPermission>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>user/toAddUser';
			 diag.Width = 222;
			 diag.Height = 380;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				 }
				 diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function editUser(user_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>user/toEditUser?userId='+user_id;
			 diag.Width = 222;
			 diag.Height = 380;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>user/deleteUser?userId="+userId;
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>user/deleteAllUser?tm='+new Date().getTime(),
						    	data: {userIds:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		</script>
		
	</body>
</html>


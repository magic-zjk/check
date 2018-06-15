<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>			
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	$(top.hangge());
	
	//保存
	function save(){
		
		if($("#userName").val()=="" || $("#userName").val()=="此用户名已存在!"){
			
			$("#userName").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#userName").focus();
			$("#userName").val('');
			$("#userName").css("background-color","white");
			return false;
		}else{
			$("#userName").val(jQuery.trim($('#userName').val()));
		}
		
		if($("#password").val()==""){
			
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			
			$("#chkpwd").focus();
			return false;
		}
		if($("#realName").val()==""){
			
			$("#realName").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#realName").focus();
			return false;
		}
		var ageReg = /120|((1[0-1]|\d)?\d)/
		if($("#age").val() != "" && !ageReg.test($("#age").val())){
			$("#age").tips({
				side:3,
	            msg:'年龄格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#age").focus();
			return false;
		}
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#phone").val()==""){
			
			/* $("#phone").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#phone").focus();
			return false; */
		}else if($("#phone").val().length != 11 && !myreg.test($("#phone").val())){
			$("#phone").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#phone").focus();
			return false;
		}
		
		hasU();
	}
	
	//判断用户名是否存在
	function hasU(){
		var userName = $.trim($("#userName").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/validateUser',
	    	data: {userName:userName},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					 $("#userName").tips({
							side:3,
				            msg:'此用户名已经存在',
				            bg:'#AE81FF',
				            time:2
				        });
						
						$("#userName").focus();
						//$("#roleName").val('');
						$("#userName").css("background-color","white");
				 }
			}
		});
	}
	
</script>
</head>
<body>
	<form action="user/doAddUser" name="userForm" id="userForm" method="post" >
		<div id="zhongxin">
		<table>
			
			<tr>
				<td><input type="text" name="userName" id="userName" autocomplete="off" value="" maxlength="32" placeholder="这里输入用户名" title="用户名"/></td>
			</tr>
			<tr>
				<td><input type="text" name="realName" id="realName"  autocomplete="off" value="" maxlength="32" placeholder="这里输入姓名" title="姓名"/></td>
			</tr>
			<tr>
				<td><input type="password" name="password" id="password" autocomplete="off" value=""  maxlength="32" placeholder="输入密码" title="密码"/></td>
			</tr>
			<tr>
				<td><input type="password" name="chkpwd" id="chkpwd"  maxlength="32" placeholder="确认密码" title="确认密码" /></td>
			</tr>
			<tr class="info">
				<td>
				<select class="chzn-select" name="sex" id="sex" style="vertical-align:top;">
				<option value="">--性别--</option>
				<option value="0">女</option>
				<option value="1">男</option>
				</select>
				</td>
			</tr>
			<!-- 角色 -->
			<tr class="info">
				<td>
				<select class="chzn-select" name="role" id="role" style="vertical-align:top;" >
				<c:choose>
					<c:when test="${not empty roleList}">
						<c:forEach items="${roleList}" var="role" varStatus="vs">
							<option value="${role.roleId}" <c:if test="${param.roleId == role.roleId}">selected</c:if>>${role.roleName}</option>
						</c:forEach>
						</c:when>
					</c:choose>
				</select>
				</td>
			</tr>
			<tr>
				<td><input type="text" name="age" id="age"  value="" maxlength="3" placeholder="这里输入年龄" title="年龄"/></td>
			</tr>
			<tr>
				<td><input type="text" name="phone" id="phone"  value=""  maxlength="11" placeholder="这里输入手机号" title="手机号"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
		});
		
		</script>
	
</body>
</html>
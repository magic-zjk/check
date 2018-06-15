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
		
		if($("#roleName").val()=="" || $("#roleName").val()=="此角色名称已存在!"){
			
			$("#roleName").tips({
				side:3,
	            msg:'输入角色名称',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#roleName").focus();
			$("#roleName").val('');
			$("#roleName").css("background-color","white");
			return false;
		}else{
			$("#roleName").val(jQuery.trim($('#roleName').val()));
		}
		
		if($("#roleKey").val()==""){
			
			$("#roleKey").tips({
				side:3,
	            msg:'输入角色标识',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#roleKey").focus();
			return false;
		}
		
		hasR();
	}
	
	//判断用户名是否存在
	function hasR(){
		var roleId = $.trim($("#roleId").val());
		var roleKey = $.trim($("#roleKey").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>role/validateRole',
	    	data: {roleId:roleId,roleKey:roleKey},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#roleForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					 $("#roleKey").tips({
							side:3,
				            msg:'该标识已经存在',
				            bg:'#AE81FF',
				            time:2
				        });
						
						$("#roleKey").focus();
						//$("#roleName").val('');
						$("#roleKey").css("background-color","white");
				 }
			}
		});
	}
	
</script>
	</head>
<body>
	<form action="role/doEditRole" name="roleForm" id="roleForm" method="post" >
		<input type="hidden" id="roleId" name="roleId" value="${role.roleId}"/>
		<div id="zhongxin">
		<table>
			
			<tr>
				<td><input type="text" name="roleName" id="roleName" autocomplete="off" value="${role.roleName}" maxlength="32" placeholder="这里输入角色名称" title="角色名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="roleKey" id="roleKey"  autocomplete="off" value="${role.roleKey}" maxlength="32" placeholder="这里输入角色标识" title="角色标识"/></td>
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
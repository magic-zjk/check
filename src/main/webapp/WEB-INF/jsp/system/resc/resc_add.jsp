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
		<title>菜单</title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</head>

<script type="text/javascript">
	$(top.hangge());
	//保存
	function save(){
		if($("#rescName").val()==""){
			
			$("#rescName").tips({
				side:3,
	            msg:'请输入资源名称',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#rescName").focus();
			return false;
		}
		if($("#parentId").val()!=""){
			if($("#identity").val()==""){
				
				$("#identity").tips({
					side:3,
		            msg:'请输入资源唯一标识',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#identity").focus();
				return false;
			}
			if($("#url").val()==""){
				
				$("#url").tips({
					side:3,
		            msg:'请输入资源链接',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#url").focus();
				return false;
			}
		}
		
		
		if($("#sort").val()==""){
			
			$("#sort").tips({
				side:1,
	            msg:'请输入序号',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#sort").focus();
			return false;
		}
		
		
		$("#rescForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>


<body>
	<form  action="resc/doAddResc" name="rescForm" id="rescForm" method="post" >
		<div id="zhongxin">
		<table>
			<tr>
				<td>
					<c:if test="${not empty parentResc}">
						<input type="hidden" name="parentId" id="parentId" value="${parentResc.rescId }"/>
						上级资源：${parentResc.rescName }
					</c:if>
					<c:if test="${empty parentResc}">
						<input type="hidden" name="parentId" id="parentId" value=""/>
						根资源
					</c:if>
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="rescName" id="rescName" placeholder="这里输入资源名称" value="" title="资源名称"/>
				</td>
			</tr>
			<c:if test="${not empty parentResc}">
				<tr>
					<td>
						<input type="text" name="identity" id="identity" placeholder="这里输入资源标识" value="" title="资源标识"/>
					</td>
				</tr>
				<tr>
					<td>
						<input type="text" name="url" id="url" placeholder="这里输入链接地址" value="" title="链接地址"/>
					</td>
				</tr>
			</c:if>
			<tr>
				<td>
					<input type="number" name="sort" id="sort" placeholder="这里输入排序" value="" title="排序"/></td>
			</tr>
			<tr>
				<td>
					<textarea name="rescDesc" id="rescDesc" rows="2" cols="1" maxlength="200" placeholder="这里输入描述" title="描述"></textarea>
				</td>
			</tr>
			<tr>
				<td style="text-align: center; padding-top: 10px;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	</form>
</body>
</html>
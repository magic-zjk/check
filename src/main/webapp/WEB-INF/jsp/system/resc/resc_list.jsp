<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="myshiro" tagdir="/WEB-INF/tags" %>
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
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>

<script type="text/javascript">
	$(top.hangge());	
	
	//新增
	function addResc(rescId){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="新增菜单";
		 diag.URL = '<%=basePath%>resc/toAddResc?rescId='+rescId;
		 diag.Width = 223;
		 diag.Height = 340;
		 diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				top.jzts(); 
				if(rescId == 0){
					
					setTimeout("location.reload()",100);
				}else{
					var txt = $("#zhan_"+rescId).text();
					if(txt!="展开"){
						openClose(rescId);
						openClose(rescId);
					}
				}
				top.hangge();
				//setTimeout("location.reload()",100);
			}
			diag.close();
		 };
		 diag.show();
	}
	
	//修改
	function editResc(rescId,parentId){
		 top.jzts();
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑菜单";
		 diag.URL = '<%=basePath%>resc/toEditResc?rescId='+rescId;
		 diag.Width = 223;
		 diag.Height = 340;
		 diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				top.jzts();
				if(parentId == 0){
					setTimeout("location.reload()",100);
				}else{
					var txt = $("#zhan_"+parentId).text();
					if(txt!="展开"){
						openClose(parentId);
						openClose(parentId);
					}
				}
				top.hangge();
			}
			diag.close();
		 };
		 diag.show();
	}
	
	//编辑顶部菜单图标
	function editTb(rescId){
		 top.jzts();
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑图标";
		 diag.URL = '<%=basePath%>resc/toEditIcon?rescId='+rescId;
		 diag.Width = 530;
		 diag.Height = 150;
		 diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				top.jzts(); 
				setTimeout("location.reload()",100);
			}
			diag.close();
		 };
		 diag.show();
	}
	
	function delResc(rescId,isParent,parentId){
		if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
			top.jzts();
			var url = "<%=basePath%>resc/deleteResc?rescId="+rescId;
			$.get(url,function(data){
				top.jzts();
				if(isParent){
					document.location.reload();
				}else{
					var txt = $("#zhan_"+parentId).text();
					if(txt!="展开"){
						openClose(parentId);
						openClose(parentId);
					}
				}
				top.hangge();
			});
		}
	}
	
	function openClose(rescId){
		var curObj = $("#zhan_"+rescId);
		var txt = curObj.text();
		var trIndex = curObj.attr("trIndex");
		var level = curObj.attr("level");
		if(txt=="展开"){
			curObj.text("折叠");
			$("#subTr"+rescId).after("<tr id='tempTr"+rescId+"'><td colspan='5'>数据载入中</td></tr>");
			if(trIndex%2==0){
				$("#tempTr"+rescId).addClass("main_table_even");
			}
			var url = "<%=basePath%>resc/listSub?rescId="+rescId;
			$.get(url,function(data){
				if(data.length>0){
					var html = "";
					level=level+1;
					$.each(data,function(i){
						html = "<tr style='height:24px;line-height:24px;' id='subTr"+this.rescId+"' name='subTr"+rescId+"'>";
						html += "<td></td>";
						html += "<td class='left'>";
						for(var j=0;j<level;j++){
							html += "<span style='width:40px;display:inline-block;'></span>"
						}
						if(i==data.length-1)
							html += "<img src='static/images/joinbottom.gif' style='vertical-align: middle;'/>";
						else
							html += "<img src='static/images/join.gif' style='vertical-align: middle;'/>";
						html += "<span style='text-align:left;display:inline-block;'>"+this.rescName+"</span>";
						html += "</td>";
						if(typeof(this.identity) == "undefined"){
							html += "<td></td>";
						}else{
							html += "<td>"+this.identity+"</td>";
						}
						if(typeof(this.url) == "undefined"){
							html += "<td></td>";
						}else{
							html += "<td class='left'>"+this.url+"</td>";
						}
						html += "<td class='center'>"+this.sort+"</td>";
						if(typeof(this.rescDesc) == "undefined"){
							html += "<td></td>";
						}else{
							html += "<td class='left'>"+this.rescDesc+"</td>";
						}
						html += "<td>";
						html += "<shiro:hasPermission name='resc:update'>";
						html += "<a class='btn btn-mini btn-info' title='编辑' onclick='editResc(\""+this.rescId+"\",\""+rescId+"\")'><i class='icon-edit'></i></a>";
						html += "</shiro:hasPermission>";
						html += "<shiro:hasPermission name='resc:delete'>";
						html += "<a class='btn btn-mini btn-danger' title='删除' onclick='delResc(\""+this.rescId+"\",false,\""+rescId+"\")'><i class='icon-trash'></i></a>";
						html += "</shiro:hasPermission>";
						html += "</td>"
						html += "</tr>";
						$("#tempTr"+rescId).before(html);
					});
					$("#tempTr"+rescId).remove();
					if(trIndex%2==0){
						$("tr[name='subTr"+rescId+"']").addClass("main_table_even");
					}
				}else{
					$("#tempTr"+rescId+" > td").html("没有相关数据");
				}
			},"json");
		}else{
			$("tr[name='subTr"+rescId+"']").each(function(){
				$("tr[name='"+$(this).attr("id")+"']").remove();
				$("#tempTr"+$(this).attr("id").substr(5,$(this).attr("id").length)).remove();
			});
			$("#tempTr"+rescId).remove();
			$("tr[name='subTr"+rescId+"']").remove();
			curObj.text("展开");
		}
	}
</script>
</head>

<body>
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center"  style="width: 50px;">序号</th>
			<th class='center'>名称</th>
			<th class='center'>标识</th>
			<th class='center'>资源路径</th>
			<th class='center'>排序</th>
			<th class='center'>描述</th>
			<th class='center' style="width: 180px;">操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty rescList}">
				<c:forEach items="${rescList}" var="resc" varStatus="vs">
				<tr id="subTr${resc.rescId }">
				<td class="center">${vs.index+1}</td>
				<td class='left'><i class="${resc.icon }">&nbsp;</i>${resc.rescName }</td>
				<td class='left'>${resc.identity }</td>
				<td>${resc.url == '#'? '': resc.url}</td>
				<td class='center'>${resc.sort }</td>
				<td class='left'>${resc.rescDesc }</td>
				<td class='center'>
				<shiro:hasPermission name="resc:view">
					<a class='btn btn-mini btn-warning' id="zhan_${resc.rescId}" trIndex="${vs.index }" level="0" onclick="openClose('${resc.rescId }')" >展开</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="resc:create">
					<a class='btn btn-mini btn-success' title='新增下级' onclick="addResc('${resc.rescId }',this,${vs.index },0)">新增</a> 
				</shiro:hasPermission>
				<myshiro:hasAnyPermissions name="resc:create,resc:update">
					<a class='btn btn-mini btn-purple' title="图标" onclick="editTb('${resc.rescId }')" ><i class='icon-picture'></i></a>
				</myshiro:hasAnyPermissions>
				<shiro:hasPermission name="resc:update">
					<a class='btn btn-mini btn-info' title="编辑" onclick="editResc('${resc.rescId }',this,${vs.index },0)" ><i class='icon-edit'></i></a>
				</shiro:hasPermission>
				<shiro:hasPermission name="resc:delete">
					<a class='btn btn-mini btn-danger' title="删除"  onclick="delResc('${resc.rescId }',true)"><i class='icon-trash'></i></a>
				</shiro:hasPermission>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	
	<div class="page_and_btn">
		<div>
			<shiro:hasPermission name="resc:create">
			&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="addResc(0);">新增根目录</a>
			</shiro:hasPermission>
		</div>
	</div>
	
</body>
</html>
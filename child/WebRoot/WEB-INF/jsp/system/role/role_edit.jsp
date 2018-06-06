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
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		
<script type="text/javascript">
	
	top.hangge();
	
	//保存
	function save(){
		if(!$("#roleName").val().match(/^[\u4e00-\u9fa5_a-zA-Z0-9_]{2,10}$/)){
			$("#roleName").tips({
				side:3,
	            msg:'请输入名称,2~10个字内',
	            bg:'#FF5080',
	            time:3
	        });
			$("#roleName").focus();
			return false;
		}  
		if($("#roleName").val().match(/^(＼d+)$|^([a-zA-Z]+)$|^([0-9a-zA-Z]+)$/)){
			$("#roleName").tips({
				side:3,
	            msg:'名称不得为数字或字母',
	            bg:'#FF5080',
	            time:3
	        });
			$("#roleName").focus();
			return false;
		}
		var ROLE_NAME = $.trim($("#roleName").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>role/hasR.do',
	    	data: {ROLE_NAME:ROLE_NAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#roleName").tips({
							side:3,
				            msg:'不得与原名称相同',
				            bg:'#FF5080',
				            time:3
				        });
					 $("#roleName").focus();
					setTimeout("$('#roleName').val('')",2000);
					return false;
				 }else{
					 $("#form1").submit();
					 $("#zhongxin").hide();
					 $("#zhongxin2").show();
				 }
			}
		});
	}	
</script>
	</head>
<body>
		<form action="role/edit.do" name="form1" id="form1"  method="post">
		<input type="hidden" name="ROLE_ID" id="id" value="${pd.ROLE_ID}"/>
			<div id="zhongxin">
			<table>
				<tr>
					<td><input type="text" name="ROLE_NAME" id="roleName" value="${pd.ROLE_NAME}" placeholder="这里输入名称" title="名称" /></td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
						<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
			</div>
		</form>
	
	<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif"  style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
		<!-- 引入 -->
		<script src="static/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
</body>
</html>

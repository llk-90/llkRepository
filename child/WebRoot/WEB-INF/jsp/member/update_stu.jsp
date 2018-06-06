<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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

<link rel="stylesheet" href="static/css/datepicker.css" />
<!-- 日期框 -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<script type="text/javascript">
	$(top.hangge());
	//保存
	function update(id) {
		if ($("#z_name").val() == "") {
			$("#z_name").tips({
				side : 3,
				msg : '请输入所属班级',
				bg : '#FF5080',
				time :2
			});
			$("#z_name").focus();
			return false;
		}
		var z_name = $("#z_name").val();
		var z_name_old = '${classInfo.className}';
		var z_id = '${classInfo.z_id}';
		$.ajax({
			type : "POST",
			url : 'member/selectClass.do',
			data : {
				z_name:z_name,
				z_name_old:z_name_old,
				z_id:z_id
			},
			dataType : 'json',
			cache : false,
			async : false,
			success : function(data) {
				if (data.msg == "fail") {
					 $("#z_name").tips({
						side : 3,
						msg : "班级已存在",
						bg : '#FF5080',
						time : 2
					 });
					 $("#z_name").focus();
					return false;
				}else if(data.msg == "ok") {
					$("#cForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				}
			}
			});
	
	}
</script>
</head>
<body>
	<form action="member/updateClass.do" name="cForm" id="cForm"  method="post">
		<input type="hidden" name="z_id" id="z_id"  value="${classInfo.z_id}"/>
		<div id="zhongxin">
			<table class="table ">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">入学年份：</td>
					<td><input type="text" name="gradeName" id="gradeName" value="${classInfo.gradeName}" maxlength="12"  title="入学年份" disabled="disabled"/></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">所属班级：</td>
					<td><input type="text" name="z_name" id="z_name" value="${classInfo.className}" maxlength="64" title="所属班级" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="10">
						<a class="btn btn-small btn-success" onclick="update('${classInfo.z_id}');">保存</a> 
						<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr> 
			</table>
		</div>
	</form>
	<div id="zhongxin2" class="center" style="display: none">
		<br />
		<br />
		<br />
		<br />
		<br />
		<img src="static/images/jiazai.gif" /><br />
		<h4 class="lighter block green">提交中...</h4>
	</div>
	
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>
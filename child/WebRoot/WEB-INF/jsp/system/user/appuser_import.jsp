<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
<script type="text/javascript">
	$(top.hangge());
</script>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div class="row-fluid" id="zhongxin">
			<div style="padding: 55px 90px;vertical-align: middle;">
				<button class="btn btn-primary">下载导入模板</button>
				<button class="btn btn-success">导入</button>
			</div>
		</div>
	</div>
</body>
</html>


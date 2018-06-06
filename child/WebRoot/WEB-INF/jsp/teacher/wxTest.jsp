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
<meta charset="utf-8" />
<title></title>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../system/admin/top.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div id="page-content" class="clearfix">
			<table>
				<tr>
					<td class="center">key</td>
					<td class="center">value</td>
				</tr>
				<tr>
					<td class="center">openid:</td>
					<td class="center">${pd.openid }</td>
				</tr>
				<tr>
					<td class="center">nickname:</td>
					<td class="center">${pd.nickname }</td>
				</tr>
				<tr>
					<td class="center">sex:</td>
					<td class="center">${pd.sex }</td>
				</tr>
				<tr>
					<td class="center">language:</td>
					<td class="center">${pd.language }</td>
				</tr>
				<tr>
					<td class="center">city:</td>
					<td class="center">${pd.city }</td>
				</tr>
				<tr>
					<td class="center">province:</td>
					<td class="center">${pd.province }</td>
				</tr>
				<tr>
					<td class="center">country:</td>
					<td class="center">${pd.country }</td>
				</tr>
				<tr>
					<td class="center">headimgurl:</td>
					<td class="center"><img height="50px" src="${pd.headimgurl }"></td>
				</tr>
				<tr>
					<td class="center">privilege:</td>
					<td class="center">${pd.privilege }</td>
				</tr>
			</table>
		</div>
	</div>
	</div>
	</div>
	</div>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->


	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->

	<script type="text/javascript">
		$(top.hangge());
		$(function() {

			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//复选框
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});

		});
	</script>
</body>
</html>
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

<link rel="stylesheet" href="/static/css/datepicker.css" />
<!-- 日期框 -->
<script type="text/javascript" src="/static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/static/js/jquery.tips.js"></script>
<link rel="stylesheet" href="/static/css/datepicker.css" />
<!-- 日期框 -->

<script type="text/javascript">
	//保存
	function save() {
		$("#Form").submit();
		$("#zhongxin").hide();
	}
</script>
</head>
<body>
	<form action="smsRecord/${msg }.do" name="Form" id="Form"
		method="post">
		<input type="hidden" name="entprise_id" id="entprise_id" value="${pd.entprise_id}" />
		<div id="zhongxin">
			<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center" nowrap="nowrap">序号</th>
									<th class="center" nowrap="nowrap">手机号码</th>
<!-- 									<th class="center" nowrap="nowrap">发送结果</th> -->
									<th class="center" nowrap="nowrap">发送时间</th>
									<th class="center" nowrap="nowrap">结束时间</th>
									<!-- <th class="center" nowrap="nowrap">操作</th> -->
								</tr>
							</thead>
							<tbody>
							<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">
									<tr>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class="center">${var.smsinfo_phone}</td>
<%-- 											<td class="center">${var.smsinfo_record}</td> --%>
											<td class="center">${var.smsinfo_time}</td>
											<td class="center">${var.smsinfo_endtime }</td>
											<!-- <td class="center">
											<a class="btn btn-small btn-danger" title="查看" onclick="checkInfo()"><i class='icon-search icon-on-right'></i></a>  
											</td> -->
										</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
			</table>
			<div class="page-header position-relative" style="height: 25px;">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
		</div>
	</form>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->


	<!-- 日期框 -->
	<script type="text/javascript">
		$(top.hangge());
		$(function() {

			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

		});

		//汉字或英文字符校验
		$('.gbkOrName').keyup(
				function() {
					var c = $(this);
					if (/[^a-zA-Z\u4E00-\u9FA5]/.test(c.val())) {//替换其他字符
						var temp_amount = c.val().replace(
								/[^a-zA-Z\u4E00-\u9FA5]/g, '');
						$(this).val(temp_amount);
					}
				});

		//字母数字或者下划线
		$('.gbkOrAbc').keyup(
				function() {
					var c = $(this);
					if (/[^a-zA-Z0-9! @ # $ % ?_]/.test(c.val())) {//替换其他字符
						var temp_amount = c.val().replace(
								/[^a-zA-Z0-9! @ # $ % ?_]/g, '');
						$(this).val(temp_amount);
					}
				});
	</script>

</body>
</html>
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
<%@ include file="../system/admin/top.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div id="page-content" class="clearfix">
		<form id="pageForm" name="pageForm"
				action="<%=basePath%>attController/list.do" method='post'>
			<table style="margin-bottom:10px">
				<tr>
					<td><span class="input-icon"><input autocomplete="off" maxlength="32"
							id="nav-search-input" type="text" name="keyword" value="${pd.keyword }"
							placeholder="这里输入姓名" /> <i id="nav-search-icon"
							class="icon-search"></i>
					</span></td>
					<td><input id="time_start" class="span10 date-picker"
						type="text" placeholder="考勤时间" data-date-format="yyyy-mm-dd"
						readonly="readonly" value="${pd.time_start }" name="time_start"
						style="width: 90%;" autocomplete="off"></td>
					<%-- <td><input id="time_end" class="span10 date-picker"
						type="text" placeholder="结束时间" data-date-format="yyyy-mm-dd"
						readonly="readonly" value="${pd.time_end }" name="time_end"
						style="width: 90%;" autocomplete="off"></td> --%>
					<td style="vertical-align: top;">
						<a class="btn btn-mini btn-light" onclick="pageForm.submit()" title="检索">
							<i id="nav-search-icon" class="icon-search"></i>
						</a>
						<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出">
							<i id="nav-search-icon" class="icon-download-alt"></i>
						</a>
					</td>
					</tr><tr>
					<td style="font-size:16px;">应到打卡人数：<font color="red" >${head.all }</font>人</td>
					<td style="font-size:16px;">实到打卡人数：<font color="red" style="font-size:20px;">${head.isSign }</font>人</td>
					<td style="font-size:16px;">打卡异常：<font color="red" style="font-size:20px;">${head.noSign }</font>人</td>
				</tr>
			</table>
			<div class="row-fluid">
				<div class="span12">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center">学校</th>
								<th class="center">班级</th>
								<th class="center">学号</th>
								<th class="center">学生姓名</th>
								<th class="center">学生性别</th>
								<th class="center">家长姓名</th>
								<th class="center">家长电话</th>
								<th class="center">考勤日期</th>
								<th class="center">入校时间</th>
								<th class="center">退校时间</th>
								<th class="center">状态</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty list}">
									<c:forEach items="${list}" var="att" varStatus="vs">
											<tr>
												<td class="center">${att.schoolNm }</td>
												<td class="center">${att.classNm }</td>
												<td class="center">${att.s_stu_no }</td>
												<td class="center">${att.s_name }</td>
												<td class="center"><c:if test="${att.s_sex == '0'}">女</c:if><c:if test="${att.s_sex == '1'}">男</c:if></td>
												<td class="center">${att.NAME }</td>
												<td class="center">${att.PHONE }</td>
												<td class="center">${att.ar_date }</td>
												<td class="center">${att.start_time }</td>
												<td class="center">${att.end_time }</td>
												<td class="center">
													<c:if test="${att.state == '1'}">正常</c:if>
													<c:if test="${att.state != '1'}">异常</c:if>
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
						<table style="width: 100%;">
							<tr>
								<!-- <td style="vertical-align: top;">
									<button class="btn btn-small btn-danger" style="margin-left: 1%">批量补卡</button>
								</td> -->
								<td style="vertical-align: top;"><div class="pagination"
										style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
					</div>

				</div>
			</div>
			</form>
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
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
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
		//导出EXcel
		function toExcel(){
			if($("#time_start").val() == null || $("#time_start").val() == ''){
				$("#time_start").tips({
					side : 3,
					msg : '请选择时间',
					bg : '#FF5080',
					time : 2
				});
				$("#time_start").focus();
				return false;	
			}
			window.location.href=encodeURI("<%=basePath%>attController/excel.do?time_start="+$("#time_start").val());	
		}
	</script>
</body>
</html>
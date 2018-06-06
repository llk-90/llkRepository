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
<!-- jsp文件头和头部 -->
<%@ include file="../system/admin/top.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="schoolOrder/list.do" method="post" name="schoolOrderForm"
						id="schoolOrderForm">				
						<table>
							<tr>
								<td><label class="control-label"
								style="padding-top: 1px; width: 190px; float: center;"
								for="reginoName">（测试使用）导出订单信息：</label></td>
								<td style="vertical-align: top;">
									<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出">
										<i id="nav-search-icon" class="icon-download-alt"></i>
									</a>
								</td>
								
							</tr>
						</table>
						<div style="margin-top:20px"></div>
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">用户帐号</th>
									<th class="center">姓名</th>
									<th class="center">卡物理号</th>
									<th class="center">消费额</th>
									<th class="center">流水号</th>
									<th class="center">消费时间</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty schoolOrderList}">
										<c:forEach items="${schoolOrderList}" var="schoolOrder" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class="center">${schoolOrder.AccountNo}</td>
												<td class="center">${schoolOrder.CustomerName}</td>
												<td class='center'>${schoolOrder.CardID}</td>
												<td class='center'>${schoolOrder.JE}</td>
												<td class='center'>${schoolOrder.LSH}</td>
												<td class='center'>${schoolOrder.PayTime}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="13" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

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
		//导出EXcel
		function toExcel(){
			window.location.href=encodeURI("<%=basePath%>schoolOrder/excel.do?");
		}

	</script>

</body>
</html>


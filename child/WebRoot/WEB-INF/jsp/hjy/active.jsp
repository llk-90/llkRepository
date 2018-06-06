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
					<form id="pageForm" name="pageForm"
						action="<%=basePath%>activeController/list.do" method='post'>
						<table>
							<tr>
								<td><span class="input-icon"> <input autocomplete="off" maxlength="32"
									id="nav-search-input" type="text" name="phone1" value="${pd.phone1}"
									placeholder="这里输入办理号码" /> <i id="nav-search-icon"
									class="icon-search"></i>
								</span></td>
								<td><input id="time_start" class="span10 date-picker"
									type="text" placeholder="开通时间" data-date-format="yyyy-mm-dd"
									readonly="readonly" value="${pd.time_start}" name="time_start"
									style="width: 90%;" autocomplete="off"></td>
								<td style="vertical-align: top;">
									<a class="btn btn-mini btn-light" onclick="pageForm.submit()" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</a>
								</td>
								</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">学校</th>
									<th class="center">班级</th>
									<th class="center">学生姓名</th>
									<th class="center">订单号</th>
									<th class="center">办理号码（清远移动号码）</th>
									<th class="center">收货地址</th>
									<th class="center">收货联系号码</th>
									<th class="center">开通时间</th>
									<th class="center">发送状态</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty list}">
										<c:forEach items="${list}" var="att" varStatus="vs">
												<tr>
													<td class="center">${att.school}</td>
													<td class="center">${att.classId}</td>
													<td class="center">${att.stuName}</td>
													<td class="center">${att.order_num}</td>
													<td class="center">${att.phone1}</td>
													<td class="center">${att.address}</td>
													<td class="center">${att.phone2}</td>
													<td class="center">${att.createTime}</td>
													<td class="center">
														<c:if test="${att.send_state == '0'}">未发送</c:if>
														<c:if test="${att.send_state != '0'}">已发送</c:if>
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
		//检索
		function search(){
			top.jzts();
			$("#equipmentForm").submit();
		}
		
	</script>

	<script type="text/javascript">
		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
	</script>
</body>
</html>


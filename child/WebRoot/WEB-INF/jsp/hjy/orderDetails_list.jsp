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
					<!-- 检索  -->
					<form action="orderDetails/list.do" method="post" name="orderDetailsForm"
						id="orderDetailsForm">
						<table style="margin-bottom:10px" >
							<tr>
								<td><span class="input-icon">
										<input autocomplete="off" maxlength="32"
										id="nav-search-input" type="text" name="keyword" value="${pd.keyword}"	
										placeholder="这里学生输入姓名" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td><input id="time_start" class="span10 date-picker"
									type="text" placeholder="开通时间" data-date-format="yyyy-mm-dd"
									readonly="readonly" value="${pd.time_start }" name="time_start"
									style="width: 90%;" autocomplete="off">
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索" onchange="tips();">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>								
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">学生ID</th>
									<th class="center">学生姓名</th>
									<th class="center">开通手机号</th>
									<th class="center">开通时间</th>
									<th class="center">绑定微信（是/否）</th>
									<th class="center">订阅业务</th>
									<th class="center">订阅状态</th>
									<th class="center">订阅方式</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty orderDetailsList}">
										<c:forEach items="${orderDetailsList}" var="orderDetails" varStatus="vs">
											<tr>
												<td class='center'>${orderDetails.hjy_s_id}</td>
												<td class="center">${orderDetails.hjy_s_name }</td>
												<td class="center">${orderDetails.hjy_create_phone_num }</td>
												<td class='center'>${orderDetails.hjy_create_time}</td>
												<td class='center'>${orderDetails.hjy_bound_weixin_account}</td>
												<td class='center'>${orderDetails.hjy_firm_name}</td>
												<td class='center'>${orderDetails.hjy_order_status}</td>
												<td class='center'>${orderDetails.hjy_order_way}</td>
                                                </td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="7" class="center">没有相关数据</td>
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
			$("#orderDetailsForm").submit();
		}
	
	</script>

	<script type="text/javascript">
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


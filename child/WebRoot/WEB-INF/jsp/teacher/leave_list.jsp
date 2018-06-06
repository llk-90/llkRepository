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
				action="<%=basePath%>leaveController/list.do" method='post'>
				<table>
					<tr>
						<td><span class="input-icon"> <input
								autocomplete="off" id="nav-search-input" type="text"
								name="keyword" value="${pd.keyword }" maxlength="5"
								placeholder="这里输入学生姓名" /> <i id="nav-search-icon"
								class="icon-search"></i>
						</span></td>
						<td style="vertical-align: top;"><select class="chzn-select"
							name="state" id="state" data-placeholder="请选择"
							style="vertical-align: middle; width: 120px;">
								<option value="">全部</option>
								<option value="1"
									<c:if test="${pd.state == '1'}">selected = "true"</c:if>>已审核
								<option>
								<option value="-1"
									<c:if test="${pd.state == '-1'}">selected = "true"</c:if>>已驳回</option>
								<option value="0"
									<c:if test="${pd.state == '0'}">selected = "true"</c:if>>待处理</option>
						</select></td>
						<td><input id="time_start" class="span10 date-picker"
							type="text" placeholder="开始时间" data-date-format="yyyy-mm-dd"
							readonly="readonly" value="${pd.time_start }" name="time_start"
							style="width: 90%;" autocomplete="off"></td>
						<td><input id="time_end" class="span10 date-picker"
							type="text" placeholder="结束时间" data-date-format="yyyy-mm-dd"
							readonly="readonly" value="${pd.time_end }" name="time_end"
							style="width: 90%;" autocomplete="off"></td>
						<td style="vertical-align: top;"><a
							class="btn btn-mini btn-light" onclick="check();" title="检索">
								<i id="nav-search-icon" class="icon-search"></i>
						</a></td>
					</tr>
				</table>
				<div class="row-fluid">
					<div class="span12">
						<table class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<!-- <th class="center"><label><input type="checkbox"
										id="zcheckbox" /><span class="lbl"></span></label></th> -->
									<th class="center">学号</th>
									<th class="center">学生姓名</th>
									<th class="center">学生性别</th>
									<th class="center">家长姓名</th>
									<td class="center">家长电话</td>
									<th class="center">请假时间</th>
									<th class="center">处理状态</th>
									<th class="center">请假理由</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty leaveList}">
										<c:forEach items="${leaveList}" var="leave" varStatus="vs">
											<tr>
												<!-- <td class="center"><label><input type='checkbox'
												name='ids' /><span class="lbl"></span></label></td> -->
												<td class="center">${leave.s_stu_no }</td>
												<td class="center">${leave.s_name }</td>
												<td class="center">${leave.s_sex }</td>
												<td class="center">${leave.name }</td>
												<td class="center">${leave.phone }</td>
												<c:if test="${leave.days == 1 }">
													<td class="center">${leave.l_start_time }${leave.l_leave_type }</td>
												</c:if>
												<c:if test="${leave.days >1 }">
													<td class="center">${leave.l_start_time }到${leave.l_end_time }共${leave.days}天</td>
												</c:if>
												<td class="center">${leave.l_state }</td>
												<td class="left">${leave.l_reason }</td>
												<td class="center"><c:choose>
														<c:when test="${leave.l_state == '未处理' }">
															<a class="btn btn-mini btn-danger"
																onclick="approval('${leave.l_id}','1')" title="通过"><i
																class='icon-ok'></i></a>
															<a class="btn btn-mini btn-danger"
																onclick="approval('${leave.l_id}','-1')" title="驳回"><i
																class='icon-remove'></i></a>
														</c:when>
														<c:otherwise>
													已处理
												</c:otherwise>
													</c:choose></td>
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

					</div>
				</div>
				<div class="page-header position-relative">
					<table style="width: 100%;">
						<tr>
							<td style="vertical-align: top;"><div class="pagination"
									style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
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
	<script src="static/js/alertify.min.js"></script>
	<link rel="stylesheet" href="static/js/css/alertify.core.css" />
	<link rel="stylesheet" href="static/js/css/alertify.default.css" />
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

	<style type="text/css">
		.alertify{
		    width:350px;
		    margin-left: -205px;
		    border:2px solid #4ba9e6;
		    background:#f3faff;
		    border-radius:0;
		}
	</style>
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
		
		//初始化操作
		function reset () {
			$("#toggleCSS").attr("href", "static/js/css/alertify.default.css");
			alertify.set({
				labels : {
					ok     : "继续",
					cancel : "取消"
				},
				delay : 5000,
				buttonReverse : false,
				buttonFocus   : "ok"
			});
		}
		function approval(id, flag) {
			//初始化操作
			reset();
			alertify.prompt("您可以在这里输入留言", function (e, replayReason) {
				//alert(replayReason);
				if (e) {
					//alert(replayReason);
					//alertify.success("You've clicked OK and typed: " + replayReason);
					$("#pageForm").attr("action","leaveController/approval.do?id="+id+"&flag="+flag+"&replayReason="+replayReason).submit();
				} else {
					//alertify.error("You've clicked Cancel");
				}
			}, "");	
		}

		function dateCheck() {
			var inputStart = $("#time_start").val();
			var arrStart = inputStart.split("-");
			var tmpIntStartYear = parseInt(arrStart[0], 10);
			var tmpIntStartMonth = parseInt(arrStart[1], 10);
			var tmpIntStartDay = parseInt(arrStart[2], 10);

			var inputEnd = $("#time_end").val();
			var arrEnd = inputEnd.split("-");
			var tmpIntEndYear = parseInt(arrEnd[0], 10);
			var tmpIntEndMonth = parseInt(arrEnd[1], 10);
			var tmpIntEndDay = parseInt(arrEnd[2], 10);
			if (tmpIntStartYear < tmpIntEndYear) {
				return true;
			} else if (tmpIntStartYear == tmpIntStartYear) {
				if (tmpIntStartMonth < tmpIntEndMonth) {
					return true;
				} else if (tmpIntStartMonth == tmpIntEndMonth) {
					if (tmpIntStartDay <= tmpIntEndDay) {
						return true;
					} else {
						$("#time_start").tips({
							side : 3,
							msg : '开始时间不能大于结束时间！',
							bg : '#FF5080',
							time : 2
						});
						$("#time_start").focus();
						return false;
					}
				} else {
					$("#time_start").tips({
						side : 3,
						msg : '开始时间不能大于结束时间！',
						bg : '#FF5080',
						time : 2
					});
					$("#time_start").focus();
					return false;
				}
			} else {
				$("#time_start").tips({
					side : 3,
					msg : '开始时间不能大于结束时间！',
					bg : '#FF5080',
					time : 2
				});
				$("#time_start").focus();
				return false;
			}
		}

		function check() {
			var inputStart = $("#time_start").val();
			var inputEnd = $("#time_end").val();
			if (inputStart == '' && inputEnd == '') {
				top.jzts();
				$("#pageForm").submit();
			} else {
				if (dateCheck()) {
					top.jzts();
					$("#pageForm").submit();
				} else {
					return false;
				}
			}
		}
	</script>
</body>
</html>
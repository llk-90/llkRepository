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
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="smsRecord/${msg }.do" name="Form" id="Form"
						method="post">
						<input type="hidden" name="entprise_id" id="entprise_id"
							value="${pd.entprise_id}" />
						<div id="zhongxin">
							<!-- <label> <span>余&nbsp;&nbsp;&nbsp;额:</span>58.64<span>元</span>
							</label>  -->
							<label> <span>可发送条数:</span>${pd.ye}<span>条</span>
							</label> 
							<!-- <!-- <label> <span>已发送条数:</span>20<span>条</span>
							</label>  --> 
							<!-- <table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center"><label> <input type="checkbox"
											id="zcheckbox" /><span class="lbl"></span></label></th>
									<th class="center" nowrap="nowrap">序号</th>
									<th class="center" nowrap="nowrap">余额</th>
									<th class="center" nowrap="nowrap">可发送数量</th>
									<th class="center" nowrap="nowrap">已发送数量</th>
									<th class="center" nowrap="nowrap">操作</th>
								</tr>
							</thead>
							<tbody>
									<tr>
											<td class='center' style="width: 30px;"><label><input
														type='checkbox' name='ids' value="$" /><span
														class="lbl"></span></label></td>
											<td class='center' style="width: 30px;">1</td>
											<td class="center">56.68</td>
											<td class="center">30</td>
											<td class="center">10</td>
											<td class="center">
											<a class="btn btn-small btn-danger" title="查看" onclick="checkInfo()"><i class='icon-search icon-on-right'></i></a>  
											</td>
										</tr>
							</tbody>
			</table> -->
							<div align="center">
								<table>
									<tr>
										<td style="text-align: center;" colspan="10">
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</form>
				</div>
				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->

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
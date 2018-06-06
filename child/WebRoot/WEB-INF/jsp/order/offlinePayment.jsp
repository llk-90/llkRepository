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
	 function offlinePayment(){	
		var co_pay_state = document.getElementById("co_pay_state").value;
		 if(co_pay_state != "1"){
				$("#co_pay_state").tips({
					side:4,
		            msg:'请更改支付状态',
		            bg:'#FF5080',
		            time:2
		        });
				$("#co_pay_state").focus();
				return false;
		}
		$("#orderForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();  
	} 
</script>
</head>
<body>
	<form action="<%=basePath%>order/offLinePay.do" name="orderForm" id="orderForm" method="post">
	<input type="hidden" name="co_id" id="co_id"  value="${order.co_id}"/>
	<div id="zhongxin">
			<table class="table ">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">会&#12288;员：</td>
					<td><input type="text" name="name" id="name" value="${order.name}" title="会员" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">联系电话：</td>
					<td><input type="text" name="phone" id="phone" value="${order.phone}" title="联系电话" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">商品详情：</td>
					<td><input type="text" name="c_name" id="c_name" value="${order.c_name}" title="商品详情" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">订单号：</td>
					<td><input type="text" name="co_order_num" id="co_order_num" value="${order.co_order_num}" title="订单号" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">总金额：</td>
					<td><input type="text" name="co_money" id="co_money" value="${order.co_money}" title="姓名" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">购买时间：</td>
					<td><input type="email" name="date" id="date" value="${order.date}" title="电子邮箱" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">支付状态：</td>
					<%-- <td><input type="text" name="state" id="state"value="${order.state}" title="支付状态" readonly="readonly"/></td> --%>					
					<td style="vertical-align: top;">
								<select class="chzn-select" name="co_pay_state" id="co_pay_state" data-placeholder="请选择支付状态" 
									    style="vertical-align: top; width: 220px;">
									<c:if test="${order.co_pay_state  == '2' }">
										<option value=""></option>								    
									    <option value="1" <c:if test="${order.co_pay_state  == '1' }"></c:if> >支付</option>
									    <option value="2" <c:if test="${order.co_pay_state  == '2' }">selected = "true"</c:if> >未支付</option>
									</c:if>
									<c:if test="${order.co_pay_state  == '-1' }">	
										<option value=""></option>									
										<option value="1" <c:if test="${order.co_pay_state  == '1' }"></c:if> >支付</option>
										<option value="2" <c:if test="${order.co_pay_state  == '-1' }">selected = "true"</c:if> >支付失败</option>
									</c:if>									
								</select>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="10">
					<a class="btn btn-small btn-success" onclick="offlinePayment('${order.co_id}');">提交</a> 
					<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">返回</a>
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
		$(function() {
			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

		});
	</script>
</body>
</html>
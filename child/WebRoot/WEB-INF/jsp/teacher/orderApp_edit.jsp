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
<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>css/zTreeStyle/zTreeStyle.css" type="text/css" />
<style type="text/css">
div.zTreeDemoBackground {
	width: 250px;
	height: 362px;
	text-align: left;
}

ul.ztree {
	margin-top: 10px;
	border: 1px solid #617775;
	background: #ffffff;
	width: 220px;
	height: 360px;
	overflow-y: scroll;
	overflow-x: auto;
}

ul  li {
	list-style-type: none
}

.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="static/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
		$(top.hangge());
		//保存
		//检查输入框是否为空
		
		function save(){
			if($("#ibaby_order_id").val()==""){
				$("#ibaby_order_id").tips({
					side:3,
		            msg:'请输入订单号',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_order_id").focus();
				return false;
			}
			
			if($("#ibaby_userId").val()==""){
				$("#ibaby_userId").tips({
					side:3,
		            msg:'请输入买家名',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_userId").focus();
				return false;
			}
			
			if($("#ibaby_nameInfo").val()==""){
				$("#ibaby_nameInfo").tips({
					side:3,
		            msg:'请输入收货名',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_nameInfo").focus();
				return false;
			}
			if($("#ibaby_telInfo").val()==""){
				$("#ibaby_telInfo").tips({
					side:3,
		            msg:'请输入联系电话',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_telInfo").focus();
				return false;
			}
			if($("#ibaby_addressInfo").val()==""){
				$("#ibaby_addressInfo").tips({
					side:3,
		            msg:'请输入收货地址',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_addressInfo").focus();
				return false;
			}
			if($("#ibaby_postCode").val()==""){
				$("#ibaby_postCode").tips({
					side:3,
		            msg:'请输入邮编',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_postCode").focus();
				return false;
			}
			if($("#ibaby_commodityNam").val()==""){
				$("#ibaby_commodityNam").tips({
					side:3,
		            msg:'请输入商品名',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_commodityNam").focus();
				return false;
			}
			
			
			if($("#ibaby_commodityNum").val()==""){
				$("#ibaby_commodityNum").tips({
					side:3,
		            msg:'请输入购买数量',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_commodityNum").focus();
				return false;
			}
			
			
			if($("#ibaby_commoditySum").val()==""){
				$("#ibaby_commoditySum").tips({
					side:3,
		            msg:'请输入总价',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_postCode").focus();
				return false;
			}
			
			
			
			if($("#ibaby_commodityStat").val()==""){
				$("#ibaby_commodityStat").tips({
					side:3,
		            msg:'请输入订单状态',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_commodityStat").focus();
				return false;
			}
			
			
			 
			
			
			if($("#ibaby_commodityDatetime").val()==""){
				$("#ibaby_commodityDatetime").tips({
					side:3,
		            msg:'请输入更新时间',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_commodityDatetime").focus();
				return false;
			}
			
			
			//check完后调用控制层的edit方法
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		
	</script>

</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute; z-index: 111111;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 208px; height: 200px;"></ul>
	</div>
	<form action="<%=basePath%>orderApp/edit.do" name="userForm" id="userForm" method="post">
		<!-- 利用主键控制  -->
		<input type="hidden" name="ibaby_order_id" id="ibaby_order_id" value="${order.ibaby_order_id}" />  
		<div id="zhongxin">
			<table class="table">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">订单号：</td>
					<td>
						<input type="text"  name="ibaby_order_id" id="ibaby_order_id" value="${order.ibaby_order_id}" class="gbkOrAbc"  readonly="readonly"/>
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">买家名：</td>
					<td>
						<input type="text" name="ibaby_userId" id="ibaby_userId" value="${order.ibaby_userId}" />
					</td>			
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">收货名：</td>
					<td>
						<input type="text" name="ibaby_nameInfo" id="ibaby_nameInfo" value="${order.ibaby_nameInfo}"  />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">联系电话：</td>
					<td>
						<input type="text" name="ibaby_telInfo" id="ibaby_telInfo" value="${order.ibaby_telInfo}" class="gbkOrAbc"  />
					</td>
				</tr>
				
				
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">收货地址：</td>
					<td>
						<input type="text" name="ibaby_addressInfo" id="ibaby_addressInfo" value="${order.ibaby_addressInfo}"  />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">邮编：</td>
					<td>
						<input type="text" name="ibaby_postCode" id="ibaby_postCode" value="${order.ibaby_postCode}" class="gbkOrAbc"  />
					</td>
				</tr>
				
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">商品名：</td>
					<td>
						<input type="text" name="ibaby_commodityNam" id="ibaby_commodityNam" value="${order.ibaby_commodityNam}"  />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">购买数量：</td>
					<td>
						<input type="number" name="ibaby_commodityNum" id="ibaby_commodityNum" value="${order.ibaby_commodityNum}" class="gbkOrAbc"  />
					</td>
				</tr>
				
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">总价：</td>
					<td>
						<input type="number" step="0.01" name="ibaby_commoditySum" id="ibaby_commoditySum" value="${order.ibaby_commoditySum}" class="gbkOrAbc"  />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">订单状态：</td>
					<td>
						<input type="text" style="max-length:1 " name="ibaby_commodityStat" id="ibaby_commodityStat" value="${order.ibaby_commodityStat}"  />
					</td>
				</tr>
				
<%-- 				 	<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">更新时间：</td>
					<td>
						<input type="text" id="ibaby_commodityDatetime" value="${order.ibaby_commodityDatetime}" class="gbkOrAbc"  />
					</td>
					 
				</tr> --%>
				<tr>
					<td class="center" colspan="4">
						<a class="btn btn-small btn-success" onclick="save();">保存</a>
						<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>

	</form>


	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
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
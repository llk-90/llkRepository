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
<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" href="<%=basePath%>css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
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
<script type="text/javascript"
	src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="static/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#equ_name").val()==""){
				$("#equ_name").tips({
					side:3,
		            msg:'请输入设备名称',
		            bg:'#FF5080',
		            time:2
		        });
				$("#equ_name").focus();
				return false;
			}
			if($("#equ_number").val()==""){
				$("#equ_number").tips({
					side:3,
		            msg:'输入设备编号',
		            bg:'#FF5080',
		            time:2
		        });
				$("#equ_number").focus();
				return false;
			}
			if($("#equ_manufactor").val()==""){
				$("#equ_manufactor").tips({
					side:3,
		            msg:'请输入厂家',
		            bg:'#FF5080',
		            time:3
		        });
				$("#equ_manufactor").focus();
				return false;
			}
			if($("#citySel").val()==""){
				$("#citySel").tips({
					side:3,
		            msg:'请选择所属垮',
		            bg:'#FF5080',
		            time:3
		        });
				$("#citySel").focus();
				return false;
			}
				$("#userForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			
		}
		
	</script>
</head>
<body>
	<div id="menuContent" class="menuContent"
		style="display: none; position: absolute; z-index: 111111;">
		<ul id="treeDemo" class="ztree"
			style="margin-top: 0; width: 208px; height: 200px;"></ul>
	</div>
	<form action="configure/createFile.do" name="userForm" id="userForm"
		method="post">
		<div id="zhongxin">
			<table class="table ">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">应用ID：</td>
					<td><input type="text" name="applicationID" id="applicationID" value="${pd.applicationID}"
						placeholder="应用ID" maxlength="32"  title="应用ID" /></td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">商户号：</td>
					<td><input type="text" name="merchants" id="merchants" value="${pd.merchants}"
						class="gbkOrAbc"  placeholder="请输入商户号" maxlength="32" title="商户号" /></td>
				</tr>
				<tr>
				   <td style="width: 80px; text-align: left; padding-top: 13px;">回调通知：</td>
					<td><input type="text" name="informUrl" id="informUrl" value="${pd.informUrl}"
						placeholder="请输入回调通知url" maxlength="32"  title="回调通知url" /></td>
						<td style="width: 80px; text-align: left; padding-top: 13px;">统一下单：</td>
					<td><input type="text" name="orderUrl"
						id="orderUrl" placeholder="统一下单url" maxlength="32" value="${pd.orderUrl}"
						title="统一下单url" /></td>
				</tr>
				<tr>
						<td style="width: 80px; text-align: left; padding-top: 13px;">APIKey：</td>
					<td><input type="text" name="apiKey" id="apiKey" value="${pd.apiKey}" placeholder="APIKey" maxlength="32"
						title="APIKey" /></td>
						<td style="width: 80px; text-align: left; padding-top: 13px;">域名：</td>
					<td><input type="text" name="domainName" id="domainName" value="${pd.domainName}"
						placeholder="域名" maxlength="32"  title="域名" /></td>
				</tr>
				<tr>
					<td class="center" colspan="4"><a
						class="btn btn-small btn-success" onclick="save();">保存</a> <a
						class="btn btn-small btn-danger" onclick="top.Dialog.close();">刷新</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br />
			<br />
			<br />
			<br />
			<br />
			<img src="static/images/jiazai.gif" /><br />
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
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
<script type="text/javascript">
function imp(){
	top.jzts();
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="导入";
	diag.URL = '<%=basePath%>StudentM/environmentImp.do';
	diag.Width = 400;
	diag.Height = 200;
	diag.CancelEvent = function() { //关闭事件
		if (diag.innerFrame.contentWindow.document
				.getElementById('zhongxin').style.display == 'none') {
			nextPage('${page.currentPage}');
		}
		diag.close();
	};
	diag.show();
}	
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
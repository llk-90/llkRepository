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
		function save(){
			if($("#ibaby_equ_openBusiness").val()==""){
				$("#ibaby_equ_openBusiness").tips({
					side:3,
		            msg:'请选择是否开通业务',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_equ_openBusiness").focus();
				return false;
			}
			
			if($("#ibaby_equ_status").val()==""){
				$("#ibaby_equ_status").tips({
					side:3,
		            msg:'请选择设备状态',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_equ_status").focus();
				return false;
			}
			
			if($("#ibaby_imei_code").val()==""){
				$("#ibaby_imei_code").tips({
					side:3,
		            msg:'请输入设备号',
		            bg:'#FF5080',
		            time:3
		        });
				$("#ibaby_imei_code").focus();
				return false;
			}
			
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
	<form action="<%=basePath%>equipment/edit.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="s_id" id="s_id" value="${equipment.s_id}" /> <input type="hidden" name="s_zone_id" id="s_zone_id" value="${equipment.s_zone_id}" /> <input type="hidden" name="USER_ID" id="USER_ID" value="${equipment.USER_ID}" /> 
		<div id="zhongxin">
			<table class="table">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">所属学校：</td>
					<td>
						<input type="text" name="schoolname" id="schoolname" value="${equipment.schoolname}"disabled="disabled" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">所属班级：</td>
					<td>
						<input type="text" name="className" id="className" value="${equipment.className}"disabled="disabled" />
					</td>			
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生姓名：</td>
					<td>
						<input type="text" name="s_name" id="s_name" value="${equipment.s_name}" disabled="disabled" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">联系方式：</td>
					<td>
						<input type="text" name="phone" id="phone" value="${equipment.phone}" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">开通业务：</td>
					<td>
						<select class="chzn-select" name="ibaby_equ_openBusiness" id="ibaby_equ_openBusiness" data-placeholder="请选择是否开通业务" style="vertical-align: top;" onchange="tips();">
							<option value=""></option>
							<option value="0" <c:if test="${equipment.ibaby_equ_openBusiness  == '0' }">selected = "true"</c:if>>未开通</option>
							<option value="1" <c:if test="${equipment.ibaby_equ_openBusiness  == '1' }">selected = "true"</c:if>>已开通</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">设备状态：</td>
					<td>
						<select class="chzn-select" name="ibaby_equ_status" id="ibaby_equ_status" data-placeholder="请选择设备状态" style="vertical-align: top;" onchange="tips();">
							<option value=""></option>
							<option value="0" <c:if test="${equipment.ibaby_equ_status  == '0' }">selected = "true"</c:if>>正常</option>
							<option value="1" <c:if test="${equipment.ibaby_equ_status  == '1' }">selected = "true"</c:if>>维修</option>
							<option value="2" <c:if test="${equipment.ibaby_equ_status  == '2' }">selected = "true"</c:if>>关机</option>
							<option value="3" <c:if test="${equipment.ibaby_equ_status  == '3' }">selected = "true"</c:if>>-</option>
						</select>
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">设备号：</td>
					<td>
						<input type="text" name="ibaby_imei_code" id="ibaby_imei_code" value="${equipment.ibaby_imei_code}" onkeyup="this.value=this.value.replace(/\D/g,'')" class="gbkOrAbc"  placeholder="这里输入设备号" maxlength="32" title="设备号" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">IC卡号：</td>
					<td>
						<input type="text" name="ibaby_ic_code" id="ibaby_ic_code" value="${equipment.ibaby_ic_code}" onkeyup="this.value=this.value.replace(/\D/g,'')" class="gbkOrAbc" placeholder="这里输入IC卡号" maxlength="32" title="IC卡号" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">IC卡种类：</td>
					<td>
						<select class="chzn-select" name="ibaby_ic_code_type" id="ibaby_ic_code_type" data-placeholder="请选择IC卡种类" style="vertical-align: top;" onchange="tips();">
							<option value=""></option>
							<option value="0" <c:if test="${equipment.ibaby_ic_code_type  == '0' }">selected = "true"</c:if>>2.4G</option>
							<option value="1" <c:if test="${equipment.ibaby_ic_code_type  == '1' }">selected = "true"</c:if>>13.56G</option>
						</select>
					</td>
				</tr>
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
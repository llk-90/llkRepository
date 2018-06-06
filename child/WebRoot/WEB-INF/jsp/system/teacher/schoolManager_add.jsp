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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<style type="text/css">
input[type="text"] {
	/* margin-bottom: 0px; */
	
}
</style>
<script type="text/javascript">
	//保存
	function save() {
		if ($("#USERNAME").val() == "") {
			$("#USERNAME").tips({
				side : 3,
				msg : '请输入用户名',
				bg : '#FF5080',
				time : 2
			});
			$("#USERNAME").focus();
			return false;
		}
		if (!$("#USERNAME").val().match(/^[a-zA-Z0-9_]{6,16}$/)) {
			$("#USERNAME").tips({
				side : 3,
				msg : "用户名由6～16位数字、字母或下划线组成",
				bg : '#FF5080',
				time : 2
			});
			$("#USERNAME").focus();
			return false;
		}
		//用户名重复check
		var username = $("#USERNAME").val();
		var username_old = '${pd.USERNAME}';
		var username_exist = false;
		$.ajax({
			type : "POST",
			url : 'areaManager/checkUsername.do',
			data : {
				USERNAME : username,
				USERNAME_OLD : username_old
			},
			dataType : 'json',
			cache : false,
			async : false,
			success : function(data) {
				if (data.result == "exist") {
					username_exist = true;
					$("#USERNAME").tips({
						side : 3,
						msg : "用户名已存在",
						bg : '#FF5080',
						time : 2
					});
					$("#USERNAME").focus();
				}
			}
		});
		if (username_exist) {
			return false;
		}
		if ($("#PASSWORD").val() == "") {
			$("#PASSWORD").tips({
				side : 3,
				msg : '请输入密码',
				bg : '#FF5080',
				time : 2
			});
			$("#PASSWORD").focus();
			return false;
		}
		if ($("#PASSWORD").val() != ""
				&& !$("#PASSWORD").val().match(/^([a-zA-Z0-9!@#$%?_]){6,100}$/)) {
			$("#PASSWORD").tips({
				side : 3,
				msg : "密码由6～16位字母、数字或符号组成",
				bg : '#FF5080',
				time : 2
			});
			$("#PASSWORD").focus();
			return false;
		}
		if ($("#PASSWORD_CONFIRM").val() == "") {
			$("#PASSWORD_CONFIRM").tips({
				side : 3,
				msg : '请输入确认密码',
				bg : '#FF5080',
				time : 2
			});
			$("#PASSWORD_CONFIRM").focus();
			return false;
		}
		if ($("#PASSWORD_CONFIRM").val() != $("#PASSWORD").val()) {
			$("#PASSWORD_CONFIRM").tips({
				side : 3,
				msg : '两次输入密码不一致',
				bg : '#FF5080',
				time : 2
			});
			$("#PASSWORD_CONFIRM").focus();
			return false;
		}
		if ($("#SCHOOL_ID").val() == "") {
			$("#SCHOOL_ID").tips({
				side : 3,
				msg : '请选择学校',
				bg : '#FF5080',
				time : 2
			});
			$("#PASSWORD_CONFIRM").focus();
			return false;
		}
		
		
		if ($("#AREA").val() == "") {
			$("#AREA").tips({
				side : 3,
				msg : '请输入区域',
				bg : '#FF5080',
				time : 2
			});
			$("#AREA").focus();
			return false;
		}
		//区域重复
		var area = $("#AREA").val();
		var area_old = '${pd.AREA}';
		var area_exist = false;
		$.ajax({
			type : "POST",
			url : 'areaManager/checkArea.do',
			data : {
				area_old : area_old,
				area : area
			},
			dataType : 'json',
			cache : false,
			async : false,
			success : function(data) {
				if (data.result == "exist") {
					area_exist = true;
					$("#AREA").tips({
						side : 3,
						msg : "区域已存在",
						bg : '#FF5080',
						time : 2
					});
					$("#AREA").focus();
				}
			}
		});
		if (area_exist) {
			return false;
		}
		if ($("#NAME").val() == "") {
			$("#NAME").tips({
				side : 3,
				msg : '请输入姓名',
				bg : '#FF5080',
				time : 2
			});
			$("#NAME").focus();
			return false;
		}
		if (!(/^[\u4e00-\u9fa5]*$/.test($("#NAME").val()) || /^[a-zA-Z\?·]*$/
				.test($("#NAME").val()))) {
			$("#NAME").tips({
				side : 3,
				msg : '姓名由汉字或字母组成',
				bg : '#FF5080',
				time : 2
			});
			$("#NAME").focus();
			return false;
		}
		/* if ($("#ROLE_ID").val() == "") {
			$("#ROLE_ID").tips({
				side : 3,
				msg : '请输入角色',
				bg : '#FF5080',
				time : 2
			});
			$("#ROLE_ID").focus();
			return false;
		} */
		if ($("#PHONE").val() == "") {
			$("#PHONE").tips({
				side : 3,
				msg : '请输入联系电话',
				bg : '#FF5080',
				time : 2
			});
			$("#PHONE").focus();
			return false;
		}
		if (!$("#PHONE")
				.val()
				.match(
						/^(0[0-9]{2,3}-)([2-9][0-9]{6,7})$|(^(13[0-9]|147|15[0-9]|17[0|7|8]|18[0-9])[0-9]{8}$)/)) {
			$("#PHONE").tips({
				side : 3,
				msg : "请输入11位的手机号或座机号(格式：区号-座机号)",
				bg : '#FF5080',
				time : 2
			});
			$("#PHONE").focus();
			return false;
		} else {
			var phone = $("#PHONE").val();
			var phone_old = '';
			if ('${pd.PHONE }' != null) {
				phone_old = '${pd.PHONE }';
			}
			var phone_exist = false;
			$.ajax({
				type : "POST",
				url : 'teacher/checkPhone.do',
				data : {
					PHONE : phone,
					phone_old : phone_old
				},
				dataType : 'json',
				cache : false,
				async : false,
				success : function(data) {
					if (data.result == "exist") {
						$("#PHONE").tips({
							side : 3,
							msg : "手机号已存在",
							bg : '#FF5080',
							time : 2
						});
						$("#PHONE").focus();
						phone_exist = true;
					}
				}
			});
			if (phone_exist) {
				return false;
			}
		}
		if ($("#EMAIL").val() == "") {
			$("#EMAIL").tips({
				side : 3,
				msg : '请输入邮箱',
				bg : '#FF5080',
				time : 2
			});
			$("#EMAIL").focus();
			return false;
		}
		if (!$("#EMAIL")
				.val()
				.match(
						/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/)) {
			$("#EMAIL").tips({
				side : 3,
				msg : "邮箱格式不正确",
				bg : '#FF5080',
				time : 2
			});
			$("#EMAIL").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	//数字校验
	$('.onlyNum').keyup(function() {
		var c = $(this);
		if (/[^\d]/.test(c.val())) {//替换非数字字符
			var temp_amount = c.val().replace(/[^\d]/g, '');
			$(this).val(temp_amount);
		}
	});
	//汉字或英文字符校验
	$('.gbkOrAbc').keyup(
			function() {
				var c = $(this);
				if (/[^a-zA-Z\u4E00-\u9FA5\?·]/.test(c.val())) {//替换其他字符
					var temp_amount = c.val().replace(
							/[^a-zA-Z\u4E00-\u9FA5\?·]/g, '');
					$(this).val(temp_amount);
				}
			});
	//汉字或英文字符校验
	$('.numOrAbc').keyup(function() {
		var c = $(this);
		if (/[^a-zA-Z\d]/.test(c.val())) {//替换其他字符
			var temp_amount = c.val().replace(/[^a-zA-Z\d]/g, '');
			$(this).val(temp_amount);
		}
	});
</script>
</head>
<body>
	<form action="schoolManager/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" />
		<div id="zhongxin">
			<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: 30px">
				<tr>
					<td style="width: 120px; text-align: left; padding-top: 13px;">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</td>
					<td>
						<input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME}" maxlength="32" placeholder="这里输入用户名" title="用户名" />
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">密&#12288;&#12288;码：</td>
					<td>
						<input type="password" name="PASSWORD" id="PASSWORD" value="${pd.PASSWORD}" maxlength="16" placeholder="这里输入密码" title="密码" />
					</td>
				</tr>
				<tr <c:if test="${msg eq 'edit' }">style="display:none"</c:if>>
					<td style="width: 70px; text-align: left; padding-top: 13px;">确认密码：</td>
					<td>
						<input type="password" name="PASSWORD_CONFIRM" id="PASSWORD_CONFIRM" value="${pd.PASSWORD_CONFIRM}" maxlength="16" placeholder="再次输入密码" title="密码" />
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">学&#12288;&#12288;校：</td>
					<td>
						<select name="SCHOOL_ID" id="SCHOOL_ID" class="chzn-select" data-placeholder="请选择学校" style="vertical-align: top;" title="学校">
							<option value=""></option>
							<c:forEach items="${schoolList}" var="var" varStatus="vs">
								<option value="${var.school_id }">${var.school_name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">姓&#12288;&#12288;名：</td>
					<td>
						<input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="32" placeholder="这里输入姓名" title="姓名" class="gbkOrAbc" />
					</td>
				</tr>
				<c:if test="${msg eq 'save' }">
					<tr style="display: none">
						<td style="width: 70px; text-align: left; padding-top: 13px;">角&#12288;&#12288;色：</td>
						<td>
							<select name="ROLE_ID" id="ROLE_ID" class="chzn-select" data-placeholder="请选择角色" style="vertical-align: top;" title="角色">
								<option value=""></option>
								<c:forEach items="${roleList }" var="var" varStatus="vs">
									<option value="${var.ROLE_ID }" selected="selected">${var.ROLE_NAME }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">联系电话：</td>
					<td>
						<input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="13" placeholder="这里输入联系电话" title="联系电话" />
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">邮&#12288;&#12288;箱：</td>
					<td>
						<input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="32" placeholder="这里输入邮箱" title="邮箱" />
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">备&#12288;&#12288;注：</td>
					<td>
						<input type="text" name="BZ" id="BZ" value="${pd.BZ}" maxlength="32" placeholder="这里输入备注" title="备注" />
					</td>
				</tr>
			</table>
			<div style="text-align: center; width: 100%">

				<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
				<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
			</div>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>

	</form>


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
		function areaRadio(value) {
			switch (value) {
			case '1':
				$('#area_select').css('display', '');
				$('#area_input').css('display', 'none');
				break;

			case '2':
				$('#area_select').css('display', 'none');
				$('#area_input').css('display', '');
				break;
			}
		}
	</script>
</body>
</html>
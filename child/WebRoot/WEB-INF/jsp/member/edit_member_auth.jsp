<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	$(top.hangge());
	//保存
	function edit(id){
		if ($("#auth_name").val() == "") {
			$("#auth_name").tips({
				side : 3,
				msg : '请输入权限名',
				bg : '#FF5080',
				time : 3
			});
			$("#auth_name").focus();
			return false;
		}
		if ($("#yxq").val() == "") {
			$("#yxq").tips({
				side : 3,
				msg : '请输入有效期',
				bg : '#FF5080',
				time : 2
			});
			$("#yxq").focus();
			return false;
		}
		if(parseInt($("#yxq").val()) > 89478485){
			$("#yxq").tips({
				side : 3,
				msg : '有效期过长',
				bg : '#FF5080',
				time : 2
			});
			$("#yxq").focus();
			return false;
		}
		if ($("#money").val() == "") {
			$("#money").tips({
				side : 3,
				msg : '请输入金额',
				bg : '#FF5080',
				time : 3
			});
			$("#money").focus();
			return false;
		}
		var pattern =/^([1-9]\d{0,15}|0)+([.]\d{1,2})?$/;
		 if(!pattern.test($("#money").val())){
			 $("#money").tips({
				side : 3,
				msg : '请输入正确金额，小数点后最多两位',
				bg : '#FF5080',
				time : 3
			});
			$("#money").focus();
		    return false;
		 }
		$("#authForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show(); 
	}
	</script>
</head>
<body>
	<form action="<%=basePath %>member_auth/editAuth.do" name="authForm" id="authForm" method="post">
		<input type="hidden" name="c_id" id="c_id"  value="${auth.c_id}"/>
		<div id="zhongxin">
			<table class="table ">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">权限名：</td>
					<td><input type="text" maxlength="12" name="c_name" id="auth_name" class="gbkOrName" value="${auth.c_name}" placeholder="这里输入权限名" title="权限名" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">有效期：</td>
					<td><input type="text" maxlength="12" name="c_time" id="yxq" class="onlyNum" value="${auth.c_time }" placeholder="这里输入有效期"  title="有效期" />&#12288;<font>(天)</font></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">金&#12288;额：</td>
					<td><input type="text" maxlength="12" name="c_price" id="money" value="${auth.c_price}" placeholder="这里输入金额" title="金额" />&#12288;<font>(元)</font></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">备&#12288;注：</td>
					<td><input type="text" name="c_remark" id="bz"  value="${auth.c_remark }" maxlength="64" placeholder="这里输入备注" title="备注" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="10">
						<a class="btn btn-small btn-success" onclick="edit('${auth.c_id}');">保存</a>
						<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
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
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
	<script type="text/javascript">
		$(top.hangge());
		

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
		//数字校验
		$('.onlyNum').keyup(function () {
			var c=$(this);
            if(/[^\d]/.test(c.val())){//替换非数字字符
              var temp_amount=c.val().replace(/[^\d]/g,'');
              $(this).val(temp_amount);
            }
		});
	</script>
</body>
</html>
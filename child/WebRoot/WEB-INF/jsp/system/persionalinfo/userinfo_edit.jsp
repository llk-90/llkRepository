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
	function save(){
		var d = new Date();
		var sysTime = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		if($("#name").val()==""){
			
			$("#name").tips({
				side:3,
	            msg:'请输入姓名',
	            bg:'#FF5080',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		
		if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'请输入邮箱',
	            bg:'#FF5080',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!userForm.EMAIL.value.match(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/)){
			$("#EMAIL").tips({
				side:3,
	            msg:'请输入正确的有箱地址',
	            bg:'#FF5080',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		} 
		if($("#PHONE").val()==""){
			$("#PHONE").tips({
				side:3,
	            msg:'请输入电话号码',
	            bg:'#FF5080',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}
		
		 if(!userForm.PHONE.value.match(/^(0[0-9]{2,3}-)([2-9][0-9]{6,7})$|(^(13[0-9]|147|15[0-9]|17[0|7|8]|18[0-9])[0-9]{8}$)/)){
			 $("#PHONE").tips({
						side : 3,
						msg : "请输入11位的手机号",
						bg : '#FF5080',
						time : 2
					});
					$("#PHONE").focus();
	                return false;
        	}
		    top.jzts();
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
			
			var value=$("#save").html(); 
			if("保存"==value){
				alert("即将返回主页");
			window.parent.back(value);
			}
			
	}
    //编辑信息
	function userinfoEdit(){
		top.jzts();
		window.location.href="<%=basePath%>useredit/list.do";
	}
	//修改密码
	function resetPswd(){
		top.jzts();
		window.location.href="<%=basePath%>resetpswd/list.do";
	}
	//取消按钮押下
	function cancel(){
		top.jzts();
		document.getElementById("userForm").action="login_default.do";
		document.getElementById("userForm").submit();
	}
</script>
</head>
<body>
	<div id="zhongxin">
		<ul class="nav nav-tabs" id="myTab">
			<li class="active" style="cursor:pointer"><a onclick="userinfoEdit()">编辑信息</a></li>
			<li style="cursor:pointer"><a onclick="resetPswd()">修改密码</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="editUser">
				<form action="useredit/${msg }.do" name="userForm" id="userForm" method="post">
					<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID }" /> 
					<table class="table ">
						<tr>
							<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;"
								for="loginname">用户名：</label></td>
							<td><input type="text" name="USERNAME" id="loginname"
								value="${pd.USERNAME}" maxlength="16" class="gbkOrAbc"
								placeholder="用户名" title="用户名" readonly="readonly" /></td>

							<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;" for="name">姓名：</label></td>
							<td><input type="text" name="NAME" id="name"
								value="${pd.NAME}" class="gbkOrName" maxlength="12"
								placeholder="姓名" title="姓名" /></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;"
								for="EMAIL">电子邮箱：</label></td>
							<td><input type="email" name="EMAIL" id="EMAIL"
								value="${pd.EMAIL}" maxlength="32" placeholder="电子邮箱"
								title="电子邮箱" onblur="hasE('${pd.USERNAME }')" /></td>

							<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;"
								for="PHONE">电话号码：</label></td>
							<td><input type="text" name="PHONE" id="PHONE"
								value="${pd.PHONE}" maxlength="13" placeholder="电话号码"
								title="电话号码" /></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;" for="BZ">备注：</label></td>
							<td><input type="text" name="BZ" id="BZ" value="${pd.BZ}"
								placeholder="备注" maxlength="64" title="备注" /></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: center;" colspan="10">
							   <a class="btn btn-small btn-success" id="save" onclick="save();">保存</a>
							   <a title="取消" class="btn btn-small btn-danger" onclick="cancel();" data-rel="tooltip">取消</a>
						    </td>
						</tr>
					</table>
				</form>
			</div>
			<div id="zhongxin2" class="center" style="display: none">
				<br /> <br /> <br /> <br /> <br /> <img
					src="static/images/jiazai.gif" /><br />
				<h4 class="lighter block green">提交中...</h4>
			</div>
		</div>
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
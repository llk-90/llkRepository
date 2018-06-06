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
<script type="text/javascript">$(top.hangge());</script>
</head>
<body>
	<div class="tab-content">
		<div class="tab-pane active" id="addUser">
			<form action="appuser/${msg }.do" name="userForm" id="userForm"
				method="post">
				<input type="hidden" name="USER_ID" id="user_id"
					value="${pd.USER_ID }" />
				<div id="zhongxin">
					<table class="table ">

						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;"
								for="loginname">用&nbsp;户&nbsp;名：</label></td>
							<td valign="middle"><input type="text" name="USERNAME"
								id="loginname" value="${pd.USERNAME}" maxlength="16"
								class="gbkOrAbc" placeholder="用户名" title="用户名" onblur="hasU();"/></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">密&#12288;&#12288;码：</label></td>
							<td><input type="password" name="PASSWORD" id="password"
								maxlength="16" placeholder="输入密码" title="密码" /></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">确认密码：</label></td>
							<td><input type="password" name="chkpwd" id="chkpwd"
								maxlength="16" placeholder="确认密码" title="确认密码" /></td>
						</tr>
						<%-- <tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">所属区域：</label></td>
							<td><select class="chzn-select" name="zd_id" id="ZD_ID"
								data-placeholder="请选择区域" style="vertical-align: top;" title="区域">
									<option value=""></option>
									<option value="">请选择区域</option>
									<c:forEach items="${listfarm}" var="farm">
										<option value="${farm.zd_id }"
											<c:if test="${farm.zd_id== pd.area_id}">selected</c:if>>${farm.name }</option>
									</c:forEach>
							</select></td>
						</tr> --%>
						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">所属学校：</label></td>
							<td><select class="chzn-select" name="ROLE_ID" id="role_id"
								data-placeholder="请选择学校" style="vertical-align: top;"
								title="所属学校">
									<option value=""></option>
									<option value="qqe">请选择学校</option>
									<c:forEach items="${roleList}" var="role">
										<option value="${role.ROLE_ID }"
											<c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">所属年级：</label></td>
							<td><select class="chzn-select" data-placeholder="请选择年级" style="vertical-align: top;"
								title="所属年级">
									<option value=""></option>
									<option value="qqe">请选择年级</option>
									<option value="qqe">一年级</option>
									<option value="qqe">二年级</option>
									<option value="qqe">三年级</option>
							</select></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">所属班级：</label></td>
							<td><select class="chzn-select" data-placeholder="请选择班级" style="vertical-align: top;"
								title="所属班级">
									<option value=""></option>
									<option value="qqe">请选择班级</option>
									<option value="qqe">一班</option>
									<option value="qqe">二班</option>
									<option value="qqe">三班</option>
							</select></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">姓&#12288;&#12288;名：</label></td>
							<td><input type="text" name="NAME" id="name"
								class="gbkOrName" maxlength="12" value="${pd.NAME }"
								placeholder="这里输入姓名" title="姓名" /></td>
						</tr>
						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">联系电话：</label></td>
							<td><input type="tel" name="PHONE" id="PHONE"
								class="onlyNum" value="${pd.PHONE }" placeholder="这里输入手机号"
								title="手机号" onblur="phone();"/></td>
						</tr>

						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">邮&#12288;&#12288;箱：</label></td>
							<td><input type="email" name="EMAIL" id="EMAIL"
								value="${pd.EMAIL}" maxlength="32" placeholder="这里输入邮箱"
								title="邮箱" onblur="emails();" /></td>
						</tr>

						<tr>
							<td><label class="control-label"
								style="padding-top: 10px; width: 80px; float: right;">备&#12288;&#12288;注：</label></td>
							<td><input type="text" name="BZ" id="BZ" value="${pd.BZ }"
								maxlength="30" placeholder="这里输入备注" title="备注" /></td>
						</tr>
						<tr>
							<td style="text-align: center;" colspan="10"><a
								class="btn btn-small btn-success" onclick="save();">保存</a> <a
								class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
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
		$(function() {
			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});
		});
		function ismail(mail){
			return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
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
		// 字符check
		$('.onlyNum').keyup(function() {
			var c = $(this);
			if (/[^\d]/.test(c.val())) {//替换非数字字符
				var temp_amount = c.val().replace(/[^\d]/g, '');
				$(this).val(temp_amount);
			}
		});
		
		//判断用户名是否存在
		function hasU() {
			var USERNAME = $.trim($("#loginname").val());
			$.ajax({
				type : "POST",
				url : '<%=basePath%>appuser/hasU.do',
				data : {
					USERNAME : USERNAME,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					if ("success" != data.result) {
						$("#loginname").tips({
							side : 3,
							msg : '此用户名已存在,请更换用户名！',
							bg : '#FF5080',
							time : 2
						});
						$("#loginname").focus();
						setTimeout("$('#loginname').val('')", 2000);
						return false;
					}
				}
			});
		}
		//判断电话是否存在
		function phone(){
			var PHONE = $("#PHONE").val();
			 $.ajax({
		            type: "POST",
		            url: '<%=basePath%>appuser/hasPH.do',
					data : {
						PHONE : PHONE,
						tm : new Date().getTime()
					},
					dataType : 'json',
					cache : false,
					success : function(data) {
						if ("success" != data.result) {
							$("#PHONE").tips({
								side : 3,
								msg : '手机号已存在',
								bg : '#FF5080',
								time : 2
							});
							$("#PHONE").focus();
							setTimeout("$('#PHONE').val('')", 2000);
							return false;
							}
			            }
		         });
		    }
		//判断邮箱是否存在
		function emails(){
			 var EMAIL = $("#EMAIL").val();
			 $.ajax({
					type: "POST",
					url: '<%=basePath%>appuser/hasE.do',
			    	data: {EMAIL:EMAIL,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						 if("success" != data.result){
							 $("#EMAIL").tips({
									side:3,
						            msg:'邮箱已存在',
						            bg:'#FF5080',
						            time:2
						        });
							 $("#EMAIL").focus();
							setTimeout("$('#EMAIL').val('')",2000);
							return false;
						 }
				}
			});
		}
		//保存
		function save(){
			if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在,请更换用户名！"){
				$("#loginname").tips({
					side:3,
		            msg:'请输入用户名',
		            bg:'#FF5080',
		            time:2
		        });
				$("#loginname").focus();
				$("#loginname").val('');
				return false;
			}
			if(!userForm.loginname.value.match(/^[a-zA-Z0-9_]{6,16}$/)){
				   $("#loginname").tips({
						side : 3,
						msg : "用户名由6～16位数字、字母或下划线组成",
						bg : '#FF5080',
						time : 2
					});
					$("#loginname").focus();
	               return false;
			}
			if($("#password").val()==""){
				$("#password").tips({
					side:3,
		            msg:'请输入密码',
		            bg:'#FF5080',
		            time:2
		        });
				$("#password").focus();
				return false;
			}
			 if(!userForm.password.value.match(/^([a-zA-Z0-9!@#$%?_]){6,16}$/)){
	   		   $("#password").tips({
							side : 3,
							msg : "密码由6-16位以上字母、数字或符号组成",
							bg : '#FF5080',
							time : 2
						});
					$("#password").focus();
		            return false;
	      		}
			if($("#password").val()!=$("#chkpwd").val()){
				$("#chkpwd").tips({
					side:3,
		            msg:'两次密码不相同',
		            bg:'#FF5080',
		            time:3
		        });
				$("#chkpwd").focus();
				return false;
			}
	 	if($("#role_id").val()==""){
				$("#role_id").tips({
					side:3,
		            msg:'选择角色类型',
		            bg:'#FF5080',
		            time:2
		        });
				$("#role_id").focus();
				return false;
			}  
	 	
	 	if($("#ZD_ID").val()==""){
			$("#ZD_ID").tips({
				side:3,
	            msg:'选择所属区域',
	            bg:'#FF5080',
	            time:2
	        });
			$("#ZD_ID").focus();
			return false;
		}  
			
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
			}else if(!ismail($("#EMAIL").val())){
				$("#EMAIL").tips({
					side:3,
		            msg:'邮箱格式不正确',
		            bg:'#FF5080',
		            time:3
		        });
				$("#EMAIL").focus();
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
			 $("#userForm").submit();
			 $("#zhongxin").hide();
			 $("#zhongxin2").show();
		}
	</script>

</body>
</html>
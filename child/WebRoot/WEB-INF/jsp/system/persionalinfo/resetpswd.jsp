<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	</head>
<body>
	<div id="zhongxin">
		<ul class="nav nav-tabs" id="myTab">
			<li style="cursor:pointer"><a onclick="userinfoEdit()">编辑信息</a></li>
			<li class="active" style="cursor:pointer"><a onclick="resetPswd()">修改密码</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="editUser">
				<form action="" method="post" id="Form" name="Form">
				<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }" />
				<input type="hidden" name="USERNAME" id="USERNAME" value="${pd.USERNAME }" />
					<table class="table">
							<tr>
								<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;"
								for="password">旧密码：</label></td>
								<td><input type="password" id="old_password" name="old_password" maxlength="10" placeholder="请输入旧密码"></td>
								<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;"
								for="password">新密码：</label></td>
							<td><input type="password" id="new_password" name="new_password" maxlength="10" placeholder="请输入新密码" ></td>
							</tr>
							<tr>
							<td><label class="control-label"
								style="padding-top: 8px; width: 100px; float: right;"
								for="password">确认密码：</label></td>
		                    <td><input type="password" id="re_password" maxlength="10" placeholder="请再次输入密码" ></td>
		                    <td></td>
		                    <td></td>
						</tr>
						<tr>
							<td style="text-align: center;" colspan="10"><a class="btn btn-small btn-success" onclick="check();">保存</a>
							<a title="取消" class="btn btn-small btn-danger" onclick="cancel();" data-rel="tooltip">取消</a> 
							</td>
							<td></td>
						</tr>
					</table>
				</form>
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
		</div>
	</div>
</body>
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
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		
	</script>
	<script type="text/javascript">	
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
		document.getElementById("Form").action="login_default.do";
		document.getElementById("Form").submit();
	}
		
		function Content_new(){
			if(Form.old_password.value == ""){
				$("#old_password").tips({
					side : 2,
					msg : "请填写旧密码",
					bg : '#FF5080',
					time : 2
				});
 		   return false;
			}
			if(Form.new_password.value===Form.old_password.value){
		 		   $("#new_password").tips({
							side : 2,
							msg : "新密码不能与旧密码相同",
							bg : '#FF5080',
							time : 2
						});
		 		   return false;
		    		}
			 if(!Form.new_password.value.match(/^([a-zA-Z0-9!@#$%?_]){6,100}$/)){
		 		   $("#new_password").tips({
							side : 2,
							msg : "密码应由6位以上字母、数字或符号组成",
							bg : '#FF5080',
							time : 2
						});
		 		   return false;
		    		}
			 return true;
		}
	
		function password_same(){
			 if(Form.new_password.value!==Form.re_password.value){   
				   
		  	       $("#re_password").tips({
						side : 2,
						msg : "两次输入密码不一致",
						bg : '#FF5080',
						time : 2
					});
		  	       return false;
		    	} 
		}
		

function check(){	
	if(Content_new()){ 
		var old_password = $("#old_password").val();
		top.jzts();
		$.ajax({			
			type: "POST",
			url: "resetpswd/check.do",
			data: {old_password:old_password}, 
			dataType:'json',
			cache: false,			
			success: function(data){
				top.hangge();
				if (data.result === "false") {
					$("#old_password").tips({
						side : 2,
						msg : "旧密码错误",
						bg : '#FF5080',
						time : 2
					});
					$("#old_password").focus();
					
				} else{
					if(localcheck()){
						bootbox.alert("修改成功，请重新登录！",function(){
						top.jzts();
						document.getElementById("Form").action="resetpswd/edit.do";
						document.getElementById("Form").submit();	
						});
					}
					
				} 
				
			}	
		});	}	
	
}
function localcheck() {
	   if(Form.new_password.value!=Form.re_password.value){   	   
	  	       $("#re_password").tips({
					side : 2,
					msg : "两次输入密码不一致",
					bg : '#FF5080',
					time : 2
				});
	  	     $("#re_password").focus();
	  	  		return false;
	    	} 
   return true;
   }
</script>
</html>
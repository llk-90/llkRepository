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
	function dateCompare(date1,date2){
		date1 = date1.replace(/\-/gi,"/");
		date2 = date2.replace(/\-/gi,"/");
		var time1 = new Date(date1).getTime();
		var time2 = new Date(date2).getTime();
		if(time1 > time2){
			return 1;
		}else if(time1 == time2){
			return 2;
		}else{
			return 3;
		}
	}
	
	//保存
	function save(){
		var d = new Date();
		var sysTime = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
	
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
	if($("#role_id").val()==""){
		$("#role_id").tips({
			side:3,
	        msg:'请选择角色类别',
	        bg:'#FF5080',
	        time:3
	    });
		$("#role_id").focus();
		return false;
	}
      if($("#PASSWORD").val()==""){
			$("#PASSWORD").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#FF5080',
	            time:2
	        });
			$("#PASSWORD").focus();
			return false;
		}
      if(!$("#PASSWORD").val().match(/^([a-zA-Z0-9!@#$%?_]){6,100}$/)){
  		   $("#PASSWORD").tips({
						side : 3,
						msg : "密码由6位以上字母、数字或符号组成",
						bg : '#FF5080',
						time : 2
					});
					$("#PASSWORD").focus();
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
	            msg:'邮箱格式不正确',
	            bg:'#FF5080',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
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
		$("#userForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show(); 
	}
	
	function hasU(){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#loginname").tips({
							side:3,
				            msg:'此用户名已存在,请更换用户名！',
				            bg:'#FF5080',
				            time:3
				        });
					 $("#loginname").focus();
					 setTimeout("$('#EMAIL').val('')",2000);
					 return false;
				 }	
			}
		});
	}
	function hashE(){
		var EMAIL = $("#EMAIL").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱已存在',
				            bg:'#FF5080',
				            time:3
				        });
						$("#EMAIL").focus();
						setTimeout("$('#EMAIL').val('')",2000);
						return false;
				 }
			}
		});
	}
</script>
	</head>
<body>

<div id="zhongxin">
<div class="tab-content">
		<div class="tab-pane active" id="editUser">
<form action="user/${msg }.do" name="userForm" id="userForm" method="post">

		 <input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
		 <input type="hidden" name="time" id="time" value="${pd.Update_Time}">
		
		<table class="table ">
			<tr>
				<td><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"  for="loginname">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</label></td>
				<td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME}" maxlength="16" class="gbkOrAbc" placeholder="请输入用户名" title="用户名" onblur="hasU();"/></td>
			</tr>
			
			<tr>
			<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="password">密&#12288;&#12288;码：</label></td>
	        <td><input type="password" name="PASSWORD" id="PASSWORD"   maxlength="12" placeholder="请输入密码" title="密码"/></td>
			</tr>
			
			<tr>
			<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="password">确认密码：</label></td>
	        <td><input type="password" name="PASSWORD" id="PASSWORD"   maxlength="12" placeholder="请输入确认密码" title="密码"/></td>
			</tr>
			<tr>
				<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="name">区&#12288;&#12288;域：</label></td>
				<td><input type="text" class="gbkOrName"  maxlength="12" placeholder="请输入区域" title="区域"/></td>
			</tr>
			<tr>
				<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="name">经理姓名：</label></td>
				<td><input type="text" name="NAME" id="name" value="${pd.NAME}" class="gbkOrName"  maxlength="12" placeholder="请输入姓名" title="姓名"/></td>
			</tr>
			<tr style="display: none">
			<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="name">职&#12288;&#12288;位：</label></td>
			<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="ROLE_ID" id="role_id" style="vertical-align:top;width: 220px; ">
						<option value="QYJL" selected="selected">区域经理</option>
					  	</select>
					</td>
			</tr>
			<tr>	
				<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="PHONE">联系电话：</label></td>
				<td><input type="text" name="PHONE" id="PHONE"  value="${pd.PHONE}"  maxlength="13" placeholder="请输入电话号码" title="电话号码"/></td>
			</tr>
			<tr>
				<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="EMAIL">邮&#12288;&#12288;箱：</label></td>
				<td><input type="email" name="EMAIL" id="EMAIL"  value="${pd.EMAIL}" maxlength="32" placeholder="请输入电子邮箱" title="电子邮箱" onblur="hashE();"/></td>
			</tr>
			<tr>
				<td ><label class="control-label" style="padding-top: 8px; width: 100px; float:right;"   for="BZ">备&#12288;&#12288;注：</label></td>
				<td><input type="text" name="BZ" id="BZ" value="${pd.BZ}" placeholder="请输入备注" maxlength="64" title="备注"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-small btn-success" onclick="save();">保存</a>
					<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</form>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</div>
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
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		//汉字或英文字符校验
		$('.gbkOrName').keyup(function () {
			var c=$(this);
            if(/[^a-zA-Z\u4E00-\u9FA5]/.test(c.val())){//替换其他字符
              var temp_amount=c.val().replace(/[^a-zA-Z\u4E00-\u9FA5]/g,'');
              $(this).val(temp_amount);
            }
		});
		
		//字母数字或者下划线
		$('.gbkOrAbc').keyup(function () {
			var c=$(this);
            if(/[^a-zA-Z0-9! @ # $ % ?_]/.test(c.val())){//替换其他字符
              var temp_amount=c.val().replace(/[^a-zA-Z0-9! @ # $ % ?_]/g,'');
              $(this).val(temp_amount);
            }
		});
		</script>
</body>
</html>
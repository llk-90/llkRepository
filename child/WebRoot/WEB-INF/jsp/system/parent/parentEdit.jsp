<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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
			
			if($("#parName").val()==""){
				$("#parName").tips({
					side:3,
		            msg:'请输入家长姓名',
		            bg:'#FF5080',
		            time:3
		        });
				$("#parName").focus();
				return false;
			}
			
			
			if($("#phone").val()==""){
				
				$("#PHONE").tips({
					side:3,
		            msg:'请输入电话号码',
		            bg:'#FF5080',
		            time:3
		        });
				$("#PHONE").focus();
				return false;
			}
			
			if($("#stuName").val()==""){
				$("#stuName").tips({
					side:3,
		            msg:'请输入学生姓名',
		            bg:'#FF5080',
		            time:3
		        });
				$("#stuName").focus();
				return false;
			}
			
			var $stuName = $("#stuName");
            var stuNameVal = $stuName.val();
            var exist = false;
            $.ajax({
                type : "POST",
                url : 'parentManage/checkStuName.do',
                data : {
                	stuNameVal : stuNameVal,
                	stuName_old : ''
                },
                dataType : 'json',
                cache : false,
                async : false,
                success : function(data) {
                    if (data.result == "exist") {
                        $stuName.tips({
                            side : 3,
                            msg : "学生姓名不存在,请重新输入",
                            bg : '#FF5080',
                            time : 2
                        });
                        exist=true;
                    }
                }
            });
            if(exist){
                $stuName.focus();
                return;
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
	<form action="parentManage/${msg}.do" name="userForm" id="userForm"
		method="post">
		<input type="hidden" name="parId" id="parId" value="${pd.uw_parent_id}" />
		<div id="zhongxin">
			<table class="table ">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">家长姓名：</td>
					<td>
					   <input type="text" name="parName" id="parName"value="${pd.uw_parent_name }"
						 placeholder="这里输入家长姓名" maxlength="32" title="家长姓名"  readonly="readonly" style="width: 250px;"/>
					</td>
				</tr>
				
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">OPENID：</td>
					<td>
					<input type="text" name="openId" id="openId"
						value="${pd.uw_open_id}"  maxlength="50"
						title="OPENID" readonly="readonly" style="width: 250px;"/>
					</td>
				</tr>
				
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">绑定手机号：</td>
					<td><input type="text" name="phone" id="phone"
						value="${pd.phone_num}" placeholder="这里输入手机号" maxlength="32"
						title="手机号" style="width: 250px;"/>
					</td>
				</tr>
				
				<tr>
					<td style="width:80px; text-align: left; padding-top: 13px;">学生姓名：</td>
					<td><input type="text" name="stuName" id="stuName"
						value="${pd.UserName}" placeholder="这里输入学生姓名" maxlength="32"
						title="学生姓名" style="width: 250px;" />
					</td>
				</tr>
				
				<tr>
					<td class="center" colspan="4"><a
						class="btn btn-small btn-success" onclick="save();">保存</a> <a
						class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img
				src="static/images/jiazai.gif" /><br />
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
	<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});
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
		
		
	     //检查号码长度
	    $("#phone").blur(function(){
		    var	stuNo = $("#phone").val();
		    if (!$("#phone").val()
					.match(/^(0[0-9]{2,3}-)([2-9][0-9]{6,7})$|(^(13[0-9]|147|15[0-9]|17[0|7|8]|18[0-9])[0-9]{8}$)/)) {
				$("#phone").tips({
					side : 3,
					msg : "请输入11位的手机号或座机号(格式：区号-座机号)",
					bg : '#AE81FF',
					time : 2
				});
				$("#phone").focus();
				return false;
			  }
		   });
	     
		
		
	</script>
</body>
</html>
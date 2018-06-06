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
						
			var pattern =/^([0-1]{1}\d|2[0-3]):([0-5]\d)$/;
			 if(!pattern.test($("#ibaby_startTime_Sun").val())){
				 $("#ibaby_startTime_Sun").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_startTime_Sun").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_endTime_Sun").val())){
				 $("#ibaby_endTime_Sun").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_endTime_Sun").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_startTime_Mon").val())){
				 $("#ibaby_startTime_Mon").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_startTime_Mon").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_endTime_Mon").val())){
				 $("#ibaby_endTime_Mon").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_endTime_Mon").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_startTime_Tu").val())){
				 $("#ibaby_startTime_Tu").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_startTime_Tu").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_endTime_Tu").val())){
				 $("#ibaby_endTime_Tu").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_endTime_Tu").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_startTime_We").val())){
				 $("#ibaby_startTime_We").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_startTime_We").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_endTime_We").val())){
				 $("#ibaby_endTime_We").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_endTime_We").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_startTime_Th").val())){
				 $("#ibaby_startTime_Th").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_startTime_Th").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_endTime_Th").val())){
				 $("#ibaby_endTime_Th").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_endTime_Th").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_startTime_Fr").val())){
				 $("#ibaby_startTime_Fr").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_startTime_Fr").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_endTime_Fr").val())){
				 $("#ibaby_endTime_Fr").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_endTime_Fr").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_startTime_Sa").val())){
				 $("#ibaby_startTime_Sa").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_startTime_Sa").focus();
			    return false;
			 }
			 
			 if(!pattern.test($("#ibaby_endTime_Sa").val())){
				 $("#ibaby_endTime_Sa").tips({
					side : 3,
					msg : '请输入正确时间',
					bg : '#FF5080',
					time : 3
				});
				$("#ibaby_endTime_Sa").focus();
			    return false;
			 }
		
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		
	</script>

</head>
<body>
	<form action="<%=basePath%>course/edit.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="ibaby_courseNo" id="ibaby_courseNo" value="${course.ibaby_courseNo}" />
		<input type="hidden" name="reginoType" id="reginoType" value="${reginoType}" />
		<input type="hidden" name="schoolType" id="schoolType" value="${schoolType}" />
		<input type="hidden" name="jibuType" id="jibuType" value="${jibuType}" />
		<input type="hidden" name="classType" id="classType" value="${classType}" />
		<input type="hidden" name="donotcall" id="donotcall" value="${donotcall}" />
		<div id="zhongxin">
			<table class="table">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">第几节课：</td>
					<td>
						<input style="width: 80px;" type="text" name="ibaby_courseNo" id="ibaby_courseNo" value="${course.ibaby_courseNo}"/>
					</td>		
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">星期一：</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_startTime_Mon" id="ibaby_startTime_Mon" value="${course.ibaby_startTime_Mon}"/>
					</td>
					<td>-</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_endTime_Mon" id="ibaby_endTime_Mon" value="${course.ibaby_endTime_Mon}"/>
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">星期二：</td>	
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_startTime_Tu" id="ibaby_startTime_Tu" value="${course.ibaby_startTime_Tu}"/>
					</td>
					<td>-</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_endTime_Tu" id="ibaby_endTime_Tu" value="${course.ibaby_endTime_Tu}"/>
					</td>	
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">星期三：</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_startTime_We" id="ibaby_startTime_We" value="${course.ibaby_startTime_We}"/>
					</td>
					<td>-</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_endTime_We" id="ibaby_endTime_We" value="${course.ibaby_endTime_We}"/>
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">星期四：</td>	
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_startTime_Th" id="ibaby_startTime_Th" value="${course.ibaby_startTime_Th}"/>
					</td>
					<td>-</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_endTime_Th" id="ibaby_endTime_Th" value="${course.ibaby_endTime_Th}"/>
					</td>	
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">星期五：</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_startTime_Fr" id="ibaby_startTime_Fr" value="${course.ibaby_startTime_Fr}"/>
					</td>
					<td>-</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_endTime_Fr" id="ibaby_endTime_Fr" value="${course.ibaby_endTime_Fr}"/>
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">星期六：</td>	
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_startTime_Sa" id="ibaby_startTime_Sa" value="${course.ibaby_startTime_Sa}"/>
					</td>
					<td>-</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_endTime_Sa" id="ibaby_endTime_Sa" value="${course.ibaby_endTime_Sa}"/>
					</td>	
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">星期日：</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_startTime_Sun" id="ibaby_startTime_Sun" value="${course.ibaby_startTime_Sun}"/>
					</td>
					<td>-</td>
					<td>
						<input style="width: 90px" type="text" maxlength="5" name="ibaby_endTime_Sun" id="ibaby_endTime_Sun" value="${course.ibaby_endTime_Sun}"/>
					</td>	
				</tr>
				<tr>
					<td class="center" colspan="8">
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
	</script>
</body>
</html>
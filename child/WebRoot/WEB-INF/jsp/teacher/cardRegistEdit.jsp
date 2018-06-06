<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<base href="<%=basePath%>">

<meta charset="utf-8" />
<title></title>

<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />

<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />

<link rel="stylesheet" href="static/css/datepicker.css" />

<style type="text/css">

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

</head>
<body>
	<form action="<%=basePath%>cardRegistManage/editSubmit.do" name="cardRegistForm" id="cardRegistForm" method="post">
		<input type="hidden" name="id" id="id" value="${cardRegist.id}" />
		<div id="zhongxin">
			<table class="table">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学&#12288;&#12288;校：</td>
					<td>
						<input type="text" name="SchoolName" id="SchoolName" value="${cardRegist.SchoolName}" disabled="disabled"/>
					</td>			
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">班&#12288;&#12288;级：</td>
					<td>
						<input type="text" name="ClassName" id="ClassName" value="${cardRegist.ClassName}" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">联&nbsp;&nbsp;系&nbsp;&nbsp;人：</td>
					<td>
						<input type="text" name="contactPerson" id="contactPerson" value="${cardRegist.contact_person}" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">联系电话：</td>
					<td>
						<input type="text" name="phoneNum" id="phoneNum" value="${cardRegist.phone_num}" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">联系地址：</td>
					<td>
						<input type="text" name="Address" id="Address" value="${cardRegist.address}" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">补卡数量：</td>
					<td>
						<input type="text" name="cardNum" id="cardNum" value="${cardRegist.card_num}" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: left; padding-top: 13px;">补卡状态：</td>
					<td>
						<select name="replaceStatus" id="replaceStatus" style="width: 80px;">
							<option value="0" <c:if test="${cardRegist.replace_status  == '0' }">selected = "true"</c:if>>已提交</option>
							<option value="1" <c:if test="${cardRegist.replace_status  == '1' }">selected = "true"</c:if>>制卡中</option>									
							<option value="2" <c:if test="${cardRegist.replace_status  == '2' }">selected = "true"</c:if>>已发卡</option>	
							<option value="99" <c:if test="${cardRegist.replace_status  == '99' }">selected = "true"</c:if>>无效</option>	
						</select>
					</td>
				</tr>
				<tr>
					<td class="center" colspan="4">
						<a id="savetest" class="btn btn-small btn-success">保存</a>
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
	<script type="text/javascript">
	
   		 document.getElementById('savetest').addEventListener('click', function () {
			$("#cardRegistForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
    	}, false)
    </script>
	<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//单选框
			$(".chzn-select").chosen();
		});
		
	</script>
</body>
</html>
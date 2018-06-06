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
	//保存
	function save(){	
			top.jzts();
			$('#microCampusInfoForm').attr('action', '/microCampusManage/editSubmit.do');
			$('#microCampusInfoForm').submit();
	}
</script>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="" name="microCampusInfoForm" id="microCampusInfoForm" method="post">
					<input type="hidden" name="id" id="id" value="${microCampusInfo.id}" />
					<input type="hidden" name="content" id="content" />
						<div id="zhongxin">
							<div class="row-fuild form-inline" style="margin-top: 10px;">
								<label class="lbl" style="margin-bottom: 0px;"
									for="repair_title">封面：</label> 
									<img src="{microCampusInfo.icon}" onclick="changeIcon();">
									<input type="file" title="请选择图片" id="file" multiple accept="image/png,image/jpg,image/gif,image/JPEG" style="display:none;"/>
							</div>
							<br>
							<div class="row-fuild form-inline" style="margin-top: 10px;">
								<label class="lbl" style="margin-bottom: 0px;"
									for="repair_title">标题：</label> 
								<input type="text" name="title" id="title" value="${microCampusInfo.title}" />

							</div>
							<br>
							<br>
							<div class="row-fuild" id="msg" style="margin-top: 20px;">
								<label class="lbl" style="margin-bottom: 0px;">内容：&#12288;&#12288;</label>
								<textarea id="context" name="content"
									style="min-height: 250px; margin: 0 auto;" title="详细描述">${microCampusInfo.content}</textarea>
							</div>
						    <div class="row-fuild" id="msg" style="margin-top: 20px;">
						    <label class="lbl" style="margin-bottom: 0px;">是否置顶：&#12288;&#12288;</label>
								<select name="StateTop" id="StateTop" style="width: 60px;">
								<option value="1" <c:if test="${microCampusInfo.is_top  == '1' }">selected = "true"</c:if>>是</option>
								<option value="0" <c:if test="${microCampusInfo.is_top  == '0' }">selected = "true"</c:if>>否</option>									
								</select>
							</div>
							<div class="row-fuild" id="msg" style="margin-top: 20px;">
							<label class="lbl" style="margin-bottom: 0px;">是否启用：&#12288;&#12288;</label>
								<select name="InfoState" id="InfoState" style="width: 80px;">
								<option value="1" <c:if test="${microCampusInfo.info_state  == '1' }">selected = "true"</c:if>>正常</option>
								<option value="0" <c:if test="${microCampusInfo.info_state  == '0' }">selected = "true"</c:if>>停用</option>									
								</select>
							</div>
						</div>
						<br>
						<div align="center" class="row row-height">
							<table>
								<tr>
									<td style="text-align: center;" colspan="10"><a id="savetest" 
										class="btn btn-small btn-success" onclick="save();">提交</a> <a
										class="btn btn-small btn-danger" onclick="top.Dialog.close();">返回</a>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<div id="zhongxin2" class="center" style="display: none">
					<br /> <br /> <br /> <br /> <br /> <img
						src="static/images/jiazai.gif" /><br />
					<h4 class="lighter block green">提交中...</h4>
				</div>
			</div>
			<!-- PAGE CONTENT ENDS HERE -->
		</div>
		<!--/row-->
	</div>
	<!--/#page-content-->

	<!-- 富文本编辑器start -->
	<!-- 配置文件 -->
	<script type="text/javascript" src="plugins/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 实例化编辑器 -->
	<script type="text/javascript">
		var ue = UE.getEditor("context");
		//ueditor有标签文本

		function getContent() {
			var arr = [];
			arr.push(UE.getEditor('context').getContent());
			return arr.join("");
		}

		function clean() {
			ue.setContent("");
		}

		function setFocus() {
			UE.getEditor('editor').focus();
		}
	</script>
	<!-- 富文本编辑器end -->
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
			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});
			var ue = UE.getEditor("context");
			ue.addListener("keyup", function(type, event) {
				var count = ue.getContentLength(true);
				if(count>10000){
				var contentText = ue.getContentTxt();
				ue.setContent(contentText.substring(0, 10000));
			 }
			})
		});
		
		function changeIcon(){ 
			$("#file").show();
		} 
	</script>
		<script type="text/javascript">       
        document.getElementById('savetest').addEventListener('click', function () {
			var fd = new FormData();
			var ajax = new XMLHttpRequest();
			var id=$("#id").val();
			fd.append("id",id);
			fd.append("picture",document.getElementById("file").files[0]);
			ajax.open("POST","/microCampusManage/editIcon",true);
			ajax.send(fd);
			$("#content").val(editor.txt.html());
			$("#microCampusInfoForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
    	}, false)
    </script>
</body>
</html>
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
<%@ include file="../system/admin/top.jsp"%>
</head>

<body>
	<div id="zhongxin">
		<div class="container-fluid">
			<form name="Form" id="Form" method="post" action="<%=basePath %>schoolAnnouncementController/saveEdit.do" enctype="multipart/form-data">
				<h3 class="center">发布公告信息</h3>
				<input name="id" type="hidden" value="${pd.n_id }" />
				<table>
					<tr>
					<td>
						标题：
						<input type="text" id="title" name="title" value="${pd.n_title }" maxLength="20"/> </td>
						<td style="vertical-align: top;">
						<%-- 类型：
						<select class="chzn-select"
							name="type" id="type" data-placeholder="请选择"
							style="vertical-align: top; width: 120px;">
								<option value="1" <c:if test="${pd.n_type == '1'}">selected="true"</c:if>>老师通知</option>
								<option value="2" <c:if test="${pd.n_type == '2'}">selected="true"</c:if>>学校通知</option>
						</select></td> --%>
						<td style="vertical-align: top;">
						图片：
						<input type="file" id="input-file-french-1" name="uploadfile" class="dropify-fr" data-default-file="" 
						onchange="checkFile(this);" value="${pd.n_pic_url }"/>
						</td>
					</tr>
				</table>
				<textarea id="content" name="content" 
				onpropertychange="checkLength(this,100);" oninput="checkLength(this,100);"
				 style="width: 100%; height: 300px; margin: 0 auto;">${pd.n_content }</textarea>
				<div class="center">
					<a class="btn btn-mini btn-success" onclick="saveEdit('${pd.n_id }');">修改</a>
					<a class="btn btn-mini btn-danger"
						onclick="top.Dialog.close();" style="margin-left: 1%">取消</a>
				</div>
			</form>
		</div>
	</div>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery|| document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->


	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->

	<script type="text/javascript">
		$(top.hangge());
		$(function() {
			var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//复选框
			$('table th input:checkbox').on('click',function() {
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox').each(
						function() {
							this.checked = that.checked;
							$(this).closest('tr').toggleClass('selected');
						});

			});
		});
		
		function saveEdit(id){
			var title = $("#title").val();
			var content = $("#content").val();
			if(title == ''|| title == null){
				$("#title").tips({
					side : 3,
					msg : '标题不能为空。',
					bg : '#FF5080',
					time : 2
				});
				$("#title").focus();
				return false;	
			}else if(content == ''|| content == null){
				$("#content").tips({
					side : 3,
					msg : '标题不能为空。',
					bg : '#FF5080',
					time : 2
				});
				$("#content").focus();
				return false;
			}
			top.jzts();
			$("Form").submit();
		}
		function checkFile(target,id) {
			var fileSize = 0;
			var filetypes = [ ".jpg", ".png", ".gif", ".ps", ".jepg" ];
			var filepath = target.value;
			var filemaxsize = 1024 * 2;//2M 
			if (filepath) {
				var isnext = false;
				var fileend = filepath.substring(filepath.indexOf("."));
				if (filetypes && filetypes.length > 0) {
					for (var i = 0; i < filetypes.length; i++) {
						if (filetypes[i] == fileend) {
							isnext = true;
							break;
						}
					}
				}
				if (!isnext) {
					alert("不接受此文件类型。");
					target.value = "";
					return false;
				}
			} else {
				return false;
			}
			if (isIE && !target.files) {
				var filePath = target.value;
				var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
				if (!fileSystem.FileExists(filePath)) {
					alert("图片不存在，请重新输入。");
					return false;
				}
				var file = fileSystem.GetFile(filePath);
				fileSize = file.Size;
			} else {
				fileSize = target.files[0].size;
			}

			var size = fileSize / 1024;
			if (size > filemaxsize) {
				alert("图片大小不能大于" + filemaxsize / 1024 + "M。");
				target.value = "";
				return false;
			}
			if (size <= 0) {
				alert("附件大小不能为0M。");
				target.value = "";
				return false;
			}

		}
		function checkLength(obj,maxlength){
		    if(obj.value.length > maxlength){
		        obj.value = obj.value.substring(0,maxlength);
		    }
		}

	</script>
</body>
</html>
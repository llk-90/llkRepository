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
	  <!-- 配置文件 -->
    <script type="text/javascript" src="static/js/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="static/js/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="static/js/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="static/js/wangEditor/wangEditor.min.js"></script>
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
<body  onUnload="opener.location.reload()">
	<div id="menuContent" class="menuContent" style="display: none; position: absolute; z-index: 111111;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 208px; height: 200px;"></ul>
	</div>
	<form action="<%=basePath%>eduInfoManage/newInfoSubmit.do" name="educationInfoForm" id="educationInfoForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="content" id="content" />
		<div id="zhongxin">
			<table class="table">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">封面</td>
					<td>
						<input type="file" title="请选择图片" id="file" name="picture" multiple accept="image/png,image/jpg,image/gif,image/JPEG"/>
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">标题</td>
					<td>
						<input type="text" name="title" id="title" value="${educationInfo.title}" />
					</td>			
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">内容：</td>
					<td>
					<input type="file" id="btn_file" style="display:none">
						<%-- <input type="text" name="content" id="content" value="${educationInfo.content}" /> --%>
						 <!-- 加载编辑器的容器 -->
							<textarea id="context" name="content"
									style="min-height: 150px; margin: 0 auto;" title="详细描述"></textarea>
  
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: left; padding-top: 13px;">置顶</td>
					<td>
						<select name="StateTop" id="StateTop" style="width: 60px;">
							<option value="1" <c:if test="${educationInfo.is_top  == '1' }">selected = "true"</c:if>>是</option>
							<option value="0" <c:if test="${educationInfo.is_top  == '0' }">selected = "true"</c:if>>否</option>									
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 100px; text-align: left; padding-top: 13px;">状态</td>
					<td>
						<select name="InfoState" id="InfoState" style="width: 80px;">
							<option value="1" <c:if test="${educationInfo.info_state  == '1' }">selected = "true"</c:if>>正常</option>
							<option value="0" <c:if test="${educationInfo.info_state  == '0' }">selected = "true"</c:if>>停用</option>									
						</select>
					</td>
				</tr>
				<tr>
					<td class="center" colspan="4">
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
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<script type="text/javascript">
        var E = window.wangEditor
        var editor = new E('#editor')
        editor.customConfig.uploadFileName = 'uploadPic';
        editor.customConfig.uploadImgServer = 'eduInfoManage/upfile';
        editor.create();
        //editor.txt.html('${educationInfo.content}');

       function save() {
    	   if($("#title").val()==""){
				$("#title").tips({
					side:3,
		            msg:'请输入标题',
		            bg:'#FF5080',
		            time:3
		        });
				$("#title").focus();
				return false;
			}
			
			$("#content").val(editor.txt.html());
			var fd = new FormData();
			var ajax = new XMLHttpRequest();
			var id=$("#id").val();
			fd.append("picture",document.getElementById("file").files[0]);
			fd.append("title",document.getElementById("title").value);
			fd.append("content",document.getElementById("content").value);
			fd.append("StateTop",document.getElementById("StateTop").value);
			fd.append("InfoState",document.getElementById("InfoState").value);
			
			$("#educationInfoForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
    	}
    </script>
	<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//单选框
			$(".chzn-select").chosen();
		});
		
		function changeIcon(){ 
			$("#file").show();
		} 
	</script>
</body>
</html>
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
			if($("#commodity_name").val()==""){
				$("#commodity_name").tips({
					side:3,
		            msg:'商品名不能为空',
		            bg:'#FF5080',
		            time:3
		        });
				$("#commodity_name").focus();
				return false;
			}
			
			if($("#commodity_price").val()==""){
				$("#commodity_price").tips({
					side:3,
		            msg:'请输入商品价格',
		            bg:'#FF5080',
		            time:3
		        });
				$("#commodity_price").focus();
				return false;
			}
			
			if($("#commodity_detail").val()==""){
				$("#commodity_detail").tips({
					side:3,
		            msg:'请输入商品描述',
		            bg:'#FF5080',
		            time:3
		        });
				$("#commodity_detail").focus();
				return false;
			}
			
			
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		
		function checkFile(target) {
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
					alert("附件不存在，请重新输入。");
					return false;
				}
				var file = fileSystem.GetFile(filePath);
				fileSize = file.Size;
			} else {
				fileSize = target.files[0].size;
			}

			var size = fileSize / 1024;
			if (size > filemaxsize) {
				alert("附件大小不能大于" + filemaxsize / 1024 + "M。");
				target.value = "";
				return false;
			}
			if (size <= 0) {
				alert("附件大小不能为0M。");
				target.value = "";
				return false;
			}
		}
	</script>

</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute; z-index: 111111;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 208px; height: 200px;"></ul>
	</div>
	<form action="commodity/edit.do" name="userForm" id="userForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="commodity_id" id="commodity_id" value="${commodity.ibaby_commodity_id}" /> 
		<div id="zhongxin">
			<table class="table">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">商品名称：</td>
					<td>
						<input type="text" name="commodity_name" id="commodity_name" value="${commodity.ibaby_commodity_name}" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">商品价格：</td>
					<td>
						<input type="text" name="commodity_price" id="commodity_price" value="${commodity.ibaby_commodity_price}" />
					</td>			
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">详细描述：</td>
					<td>
						<input type="text" name="commodity_detail" id="commodity_detail" value="${commodity.ibaby_commodity_detail}"  />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">商品图片：</td>
					<td>
						<input
							style="vertical-align: middle;" type="file"
							id="input-file-french-1" name="uploadfile" class="dropify-fr"
							data-default-file="" onchange="checkFile(this);" value="上传图片"/>
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


	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
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
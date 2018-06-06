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
		var htmlText = getContent();
		if($("#c_type").val()==""){
            $("#c_type").tips({
                side:3,
                msg:'请选择课件类型',
                bg:'#AE81FF',
                time:2
            });
            $("#c_type").focus();
        } else if($("#c_subject").val()==""){
            $("#c_subject").tips({
                side:3,
                msg:'请选择科目类型',
                bg:'#AE81FF',
                time:2
            });
            $("#c_subject").focus();
        } else  if($("#c_grade").val()==""){
            $("#c_grade").tips({
                side:3,
                msg:'请选择所属年级',
                bg:'#AE81FF',
                time:2
            });
            $("#c_grade").focus();
        } else if($("#c_commodity_id").val()==""){
            $("#c_commodity_id").tips({
                side:3,
                msg:'请选择所属权限',
                bg:'#AE81FF',
                time:2
            });
            $("#c_commodity_id").focus();
        } else if($("#c_name").val()==""){
            $("#c_name").tips({
                side:3,
                msg:'请填写标题',
                bg:'#AE81FF',
                time:2
            });
            $("#c_name").focus();
        } else  if(htmlText==""){
        $("#msg").tips({
            side:1,
            msg:'请填写内容',
            bg:'#AE81FF',
            time:2
        });
        $("#msg").focus();
        }else{
        	top.jzts();
			$('#Form').attr('action', 'coursewareP/edits.do');
			$('#Form').submit();
		}
	}
	//返回
	function returnback() {
		top.jzts();
		window.location.href = "<%=basePath%>coursewareP/coursewareList.do";
	}
	function tips() {
		var value = $('#service_TYPE').val();
		if (value == '8' || value == '9') {
			$('#tips').css('display', '');
		} else {
			$('#tips').css('display', 'none');
		}
	}
</script>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="" name="Form" id="Form" method="post">
					<input type="hidden" name="c_id" value="${pd.c_id}"/>
						<div id="zhongxin">
							<div class="row-fuild form-inline" style="margin-top: 10px;">
								<label class="lbl" style="margin-bottom: 0px;"
									for="repair_title">课件类型：</label> <select class="chzn-select"
									name="c_type" id="c_type" data-placeholder="请选择课件类型"
									style="width: 250px; vertical-align: top;" onchange="tips();">
									<option value=""></option>
									<option value="1" <c:if test="${pd.c_type=='1'}">selected = "true"</c:if>>图文</option>
									<option value="2" <c:if test="${pd.c_type=='2'}">selected = "true"</c:if>>视频</option>
									<option value="3" <c:if test="${pd.c_type=='3'}">selected = "true"</c:if>>文档</option>
								</select> <label class="lbl" style="margin-bottom: 0px;"
									for="repair_title">科目类型：</label> <select class="chzn-select"
									name="c_subject" id="c_subject" data-placeholder="请选择科目类型"
									style="width: 250px; vertical-align: top;" onchange="tips();">
									<option value=""></option>
									<c:forEach var="item" items="${subjects}">
										<option value="${item.key}" <c:if test="${item.key == pd.c_subject}">selected = "true"</c:if>>${item.value}</option>
									</c:forEach>
								</select>
							</div>
							<br>
							<div class="row-fuild form-inline" style="margin-top: 10px;">
								<label class="lbl" style="margin-bottom: 0px;"
									for="repair_title">所属年级：</label> <select class="chzn-select"
									name="c_grade" id="c_grade" data-placeholder="请选择所属年级"
									style="width: 250px; vertical-align: top;" onchange="tips();">
									<option value=""></option>
									<c:forEach var="item" items="${grade}">
										<option value="${item.key}" <c:if test="${item.key == pd.c_grade}">selected = "true"</c:if>>${item.value}</option>
									</c:forEach>
								</select> <label class="lbl" style="margin-bottom: 0px;"
									for="repair_title">所属权限：</label> <select class="chzn-select"
									name="c_commodity_id" id="c_commodity_id"
									data-placeholder="请选择所属权限"
									style="width: 250px; vertical-align: top;" onchange="tips();">
									<option value=""></option>
									<c:forEach var="item" items="${list}">
										<option value="${item.c_id}" <c:if test="${item.c_id == pd.c_commodity_id}">selected = "true"</c:if>>${item.c_name}</option>
									</c:forEach>
								</select>
							</div>
							<br>
							<div class="row-fuild form-inline">
								<label class="lbl" for="">课件标题：</label> <input maxlength="32"
									type="text" id="c_name" name="c_name" style="width: 530px"
									title="课件标题" placeholder="这里输入课件标题" value="${pd.c_name}"/>
							</div>
							<br>
							<div class="row-fuild" id="msg" style="margin-top: 20px;">
								<label class="lbl" style="margin-bottom: 0px;">详细描述：&#12288;&#12288;</label>
								<textarea id="context" name="c_detail"
									style="min-height: 250px; margin: 0 auto;" title="详细描述">${pd.c_detail}</textarea>
							</div>
						</div>
						<br>
						<div align="center" class="row row-height">
							<table>
								<tr>
									<td style="text-align: center;" colspan="10"><a
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
	</div>

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
			tips();
			var ue = UE.getEditor("context");
			ue.addListener("keyup", function(type, event) {
				var count = ue.getContentLength(true);
				if(count>10000){
				var contentText = ue.getContentTxt();
				ue.setContent(contentText.substring(0, 10000));
			 }
			})
		});
	</script>
	<!-- <script type="text/javascript">
$(document).ready(function(){$("#edui12").remove();})
</script> -->
</body>
</html>
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
	
	
	//保存
	function save(){	
		var htmlText = getContent();
		if($("#service_NAME").val()==""){
            $("#service_NAME").tips({
                side:3,
                msg:'请填写标题',
                bg:'#AE81FF',
                time:2
            });
            $("#service_NAME").focus();
        }
		else if($("#service_TYPE").val()==""){
            $("#service_TYPE").tips({
                side:3,
                msg:'请选择类别',
                bg:'#AE81FF',
                time:2
            });
            $("#service_TYPE").focus();
        }else if(htmlText==""){
        $("#msg").tips({
            side:1,
            msg:'请填写内容',
            bg:'#AE81FF',
            time:2
        });
        $("#msg").focus();
        }else{
        	  
			$('#Form').attr('action', 'policy/save.do');
			$('#Form').submit();
		}
	}
	//修改
	function edit(service_ID) {
		var htmlText = getContent();
		if($("#service_NAME").val()==""){
            $("#service_NAME").tips({
                side:3,
                msg:'请填写标题',
                bg:'#AE81FF',
                time:2
            });
            $("#service_NAME").focus();
        }
		else if($("#service_TYPE").val()==""){
            $("#service_TYPE").tips({
                side:3,
                msg:'请选择类别',
                bg:'#AE81FF',
                time:2
            });
            $("#service_TYPE").focus();
        }else if(htmlText==""){
        $("#msg").tips({
            side:1,
            msg:'请填写内容',
            bg:'#AE81FF',
            time:2
        });
        $("#msg").focus();
        }else{
        $("#service_TYPE").removeAttr("disabled"); 
		$('#Form').attr('action', 'policy/update2.do?service_ID=' + service_ID);
		$('#Form').submit();
		}
	}
	//返回
	function returnback() {
		top.jzts();
		window.location.href = "<%=basePath%>policy/list.do";
	}
	function tips(){
		var value = $('#service_TYPE').val();
		if(value=='8'||value=='9'){
			$('#tips').css('display','');
		}else{
			$('#tips').css('display','none');
		}
	}
</script>
	</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="" name="Form" id="Form"
						method="post">
						<div id="zhongxin">
							<div class="row-fuild form-inline">
								<label class="lbl" for="" >标题：&nbsp;&nbsp;</label> 
								<input maxlength="32" type="text" id="service_NAME" name="service_NAME" value="${pd.service_NAME}" style="width: 518px"  title="标题" placeholder="请输入标题"/>
							</div>
							<br>
							<div class="row-fuild form-inline">
							    <label class="lbl" style="margin-bottom: 0px;">发布状态：&nbsp;&nbsp;</label>
				                <input type="radio" name="state" value="1" checked="checked" <c:if test="${pd.state  == '1' }">checked="checked" </c:if>/> <span style="font-size: 13px; font-family: '应用字体 Regular', '应用字体';" class="lbl">已发布</span>&nbsp;&nbsp;&nbsp; 
				                <input type="radio" name="state" value="0" <c:if test="${pd.state  == '0' || pd.service_ID == null }">checked="checked" </c:if>/> <span style="font-size: 13px; font-family: '应用字体 Regular', '应用字体';"class="lbl">未发布</span>
								<label class="lbl" style="margin-bottom: 0px;" for="repair_title" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类别：</label> 
								<select  class="chzn-select" name=service_TYPE id="service_TYPE"
								data-placeholder="请选择类别"  style="vertical-align: top;" onchange="tips();" <c:if test="${pd.service_ID != null }">disabled="disabled"</c:if>>
								<option value=""></option>
									<option value="0"<c:if test="${pd.service_TYPE  == '0' }">selected = "true"</c:if>>帮助</option>
									<option value="1"<c:if test="${pd.service_TYPE  == '1' }">selected = "true"</c:if>>肥料</option>
									<option value="2"<c:if test="${pd.service_TYPE  == '2' }">selected = "true"</c:if>>防治</option>
							    </select>		
								<label id="tips" class="lbl" style="margin-left:20px;margin-bottom: 0px;color: red;display:none;"><!-- <i class="icon  icon-certificate"></i> -->*  所选类别只能发布一条数据</label> 	
							</div>
							<br>

							<div class="row-fuild" id="msg">
								<textarea id="context" name="service_content" style="min-height: 250px; margin: 0 auto;"  title="内容" >${pd.service_content}</textarea>
							</div>
							<br>
							<div align="center" class="row row-height" style="width: 90%;">
								<table>
									<tr>
										<td style="text-align: center;" colspan="10">
										<c:choose>
										<c:when test="${pd.service_ID == null }">
										<a class="btn btn-small btn-success" onclick="save();">提交</a> 
										<a class="btn btn-small btn-danger" onclick="returnback()">返回</a>
										</c:when>
										<c:otherwise>
										<a class="btn btn-small btn-success" href="javascript:edit('${pd.service_ID}');">提交</a>
										<a class="btn btn-small btn-danger" onclick="returnback()">返回</a>
										</c:otherwise>
										</c:choose>
									</tr>
								</table>
							</div>
							<div class="form-inline" style=" margin-top: 20px;">
							</div>
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

					</form>
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
			tips();
		});
		
		</script>
</body>
</html>
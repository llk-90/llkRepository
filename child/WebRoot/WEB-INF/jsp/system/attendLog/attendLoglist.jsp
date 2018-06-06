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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="attendLog/list.do" method="post" name="Form" id="Form">
						<table border='0'>
							<tr height="35">
								<td>请选择考勤日志时间：</td>
									<td colspan="5" >
										<input id="sms_time_start" class="span10 date-picker" type="text" autocomplete="off" style="width: 100px;margin-bottom: 0px" name="sms_time_start" value="" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="日志时间">
									</td>
									<td style="vertical-align: top;">
									<a class="btn btn-mini btn-light" onclick="toTxt();" title="导出">
										<i id="nav-search-icon" class="icon-download-alt"></i>
									</a>
								</td>	
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
		<i class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">		
		$(top.hangge());
	</script>
	<script type="text/javascript">	
		$(function() {		
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});	
		
		//导出EXcel
		function toTxt(){
        	var time=$('#sms_time_start').val();
        	if(time ==""){
        		bootbox.alert("请选择考勤日期！", function() {
				});
        		return;
        	}
			window.location.href=encodeURI("<%=basePath%>attendLog/downtxt.do?time="+time);
		}
	</script>
</body>
</html>


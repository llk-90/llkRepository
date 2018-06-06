<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="false"%>
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
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<style type="text/css">
	.table th, .table td {
    vertical-align: middle;  
}
.t_area{ 
width:300px; 
overflow-y:visible 
} 
</style>
<script type="text/javascript">
	//保存
	function save() {
		if ($("#lm_content").val() == "") {
			$("#lm_content").tips({
				side : 4,
				msg : '请输入内容',
				bg : '#FF5080',
				time : 2
			});
			$("#lm_content").focus();
			return false;
		}
		if ($("#lm_content").val().length > 500) {
			$("#lm_content").tips({
				side : 3,
				msg : '留言内容不得超过500个字',
				bg : '#FF5080',
				time : 2
			});
			$("#lm_content").focus();
			return false;
		}
		$.getJSON("<%=basePath%>announcementController/check.do",{
            title:$("#lm_content").val(),
            content:$("#lm_content").val()
        },function(data){
            if(data&&data.isAlert){
                var str="检测到您发送的信息包含敏感词汇\n";
                if(data.linJie){
                    str+="总共"+data.linJie+"次\n";
                }
                if (data.count) {
                    str+="已用"+data.count+"次";
                }else{
                	str+="已用0次";
                }
                bootbox.confirm(str,function(result){
                    if(result){
                        top.jzts();
                        $("#Form").submit();
                    }
                });
            }else{
                top.jzts();
                $("#Form").submit();
            }
        });
		$("#zhongxin").hide();					
	}
</script>
</head>
<body>

	<form action="messageController/saveReply.do" name="Form" id="Form" method="post">
		
		<div id="zhongxin">
			<input type="hidden" name="lm_send_user_id" value="${pd.sendId}">
			<input type="hidden" name="lm_receive_user_id" value="${pd.receiveId}">
			<input type="hidden" name="lm_state_du" value="1">
			<input type="hidden" name="lm_state_weidu" value="0">
			<table id="table_report" class="table table-striped table-bordered1 table-hover">
						<thead>
						<tr>
							<c:choose>
								<c:when test="${not empty varListName}">
									<c:forEach items="${varListName}" var="var" varStatus="vs">
										<c:if test="${var.fenkai == 1}">
										   
											<th id="parents" style="float: left;">家长：${var.name}</th>
																					
										</c:if>
										<c:if test="${var.fenkai == 0}">
												<th style="float: right;">老师：${var.name}</th>		
										</c:if>
									</c:forEach>
								</c:when>	
							</c:choose>
						</tr>
							</thead>
						</table>
					<div style="height:320px; overflow-y:scroll;">
						<table id="table_report" class="table table-striped table-bordered table-hover"style="border-top: 0px;">				
							
							<tbody >
								<%-- <c:if test="${QX.cha == 1 }"> --%>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
											<c:forEach items="${varList}" var="var" varStatus="vs">
									<c:if test="${var.fenkai == 1}">
									<tr>
										<td style="text-align:left;border: 0px;">
												${var.create_time}：${var.lm_content}
																						
										</td>
									</tr>
									</c:if>
									<c:if test="${var.fenkai == 0}">
									<tr>
										<td style="text-align:right;border: 0px;">	
												${var.lm_content}：${var.create_time}
														
										</td>
									</tr>
									</c:if>
								</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
							
						</table>	
				</div>
			<table id="table_report" class="table">
				
				<tr >
					<td style="width:60px;border: 0px;">
						<label class="" style="width: 70px;">回复内容：</label>
					</td>
					<td style="border: 0px;">
						<textarea id="lm_content" rows="5" cols="" name="lm_content"   style="width: 95%;"></textarea>
					
					</td>

				</tr>

				<tr>
					<td style="text-align: center;border: 0px;" colspan="10">
						<a class="btn btn-mini btn-primary" onclick="save();">回复</a>
						<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>

	</form>
   <script type="text/javascript">
     function MaxMe(o) {
       o.style.height = o.scrollTop + o.scrollHeight + "px";
     }
   </script>
   
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
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
		// 字符check
		$('.onlyNum').keyup(function() {
			var c = $(this);
			if (/[^\d]/.test(c.val())) {//替换非数字字符
				var temp_amount = c.val().replace(/[^\d]/g, '');
				$(this).val(temp_amount);
			}
		});
	</script>
	<script type="text/javascript">
	     function MaxMe(o) {
	       o.style.height = o.scrollTop + o.scrollHeight + "px";
	     }
	</script>
</body>
</html>
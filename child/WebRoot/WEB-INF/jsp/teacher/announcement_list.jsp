<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<div class="container-fluid">
		<div id="page-content" class="clearfix">
			<form id="pageForm" name="pageForm"
				action="<%=basePath%>announcementController/list.do" method='post'>
				<table>
					<tr>
						<td><span class="input-icon"> <input
								autocomplete="off" id="nav-search-input" type="text"
								name="keyword" value="${pd.keyword }" maxlength="32" placeholder="这里输入标题" maxlength="32" /> <i
								id="nav-search-icon" class="icon-search"></i>
						</span></td>
						<td><input id="time_start" class="span10 date-picker"
							type="text" placeholder="开始时间" data-date-format="yyyy-mm-dd"
							readonly="readonly" value="${pd.time_start }" name="time_start"
							style="width: 90%;" autocomplete="off"></td>
						<td><input id="time_end" class="span10 date-picker"
							type="text" placeholder="结束时间" data-date-format="yyyy-mm-dd"
							readonly="readonly" value="${pd.time_end }" name="time_end"
							style="width: 90%;" autocomplete="off"></td>
						<td style="vertical-align: top;"><a
								class="btn btn-mini btn-light" onclick="check();" title="检索">
								<i id="nav-search-icon" class="icon-search"></i>
							</a></td>
					</tr>
				</table>
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">编号</th>
							<th class="center">发布日期</th>
							<th class="center">标题</th>
							<td class="center">类型</td>
							<th class="center">内容</th>
							<th class="center">图片附件</th>
							<th class="center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty list}">
								<c:forEach items="${list}" var="an" varStatus="vs">
									<tr>
										<td class="center" style="vertical-align: middle;">${vs.index+1 }</td>
										<td class="center" style="vertical-align: middle;">${an.create_time }</td>
										<td class="center" style="vertical-align: middle;">${an.n_title }</td>
										<td class="center" style="vertical-align: middle;">${an.n_type }</td>
										<td class="center" style="vertical-align: middle;"><c:if test="${fn:length(an.n_content)>'20'}">${fn:substring(an.n_content,0,20)}...  </c:if>
												<c:if test="${fn:length(an.n_content)<='20'}">${an.n_content }</c:if>
											</td>
										<td class="center" style="vertical-align: middle;">
											<c:if test="${an.n_pic_url != '' and an.n_pic_url !=  null }">
												<img src="/files${an.n_pic_url }" style="width:100px;"></img>
											</c:if>
										</td>
										<td class="center" style="vertical-align: middle;"><a class="btn btn-mini btn-info"
											title="编辑" onclick="editAnno('${an.n_id}')"><i class="icon-edit"></i></a> <a
											title="删除" class="btn btn-mini btn-danger" onclick="delAnno('${an.n_id}')"><i
												class="icon-trash"></i></a></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="main_info">
									<td colspan="10" class="center">没有相关数据</td>
								</tr>
							</c:otherwise>
						</c:choose>

					</tbody>
				</table>
				<div class="page-header position-relative">
					<table style="width: 100%;">
						<tr>
							<td style="vertical-align: top;">
								<a class="btn btn-small btn-success" onclick="addAnno();">发布</a>
							</td>
							<td style="vertical-align: top;"><div class="pagination"
									style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
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

	<!-- 富文本编辑器start -->
	<!-- 配置文件 -->
	<script type="text/javascript" src="plugins/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="plugins/ueditor/ueditor.all.js"></script>
	<!-- 实例化编辑器 -->
	<script type="text/javascript">
		var ue = UE.getEditor("context");
	</script>
	<!-- 富文本编辑器end -->

	<script type="text/javascript">
		$(top.hangge());
		$(function() {

			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});


		});
		
		function addAnno(){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="发布";
			diag.URL = '<%=basePath%>announcementController/addAnno.do';
			diag.Width = 800;
			diag.Height = 600;
			diag.CancelEvent = function() { //关闭事件
				if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
					if('${page.currentPage}' == '0'){
						 setTimeout("self.location=self.location",100);
					}else{
						nextPage('${page.currentPage}');
					}
				}
				diag.close();
			};
			diag.show();
		}
		
		function editAnno(id){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="编辑";
			diag.URL = '<%=basePath%>announcementController/editAnno.do?id='+id;
			diag.Width = 800;
			diag.Height = 600;
			diag.CancelEvent = function() { //关闭事件
				if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
					if('${page.currentPage}' == '0'){
						 setTimeout("self.location=self.location",100);
					}else{
						nextPage('${page.currentPage}');
					}
				}
				diag.close();
			};
			diag.show();
		}
		
		function delAnno(id){
			bootbox.confirm("确定删除该条公告么？",function(result){
				if(result){
					$("Form").attr("action","<%=basePath%>announcementController/delAnno.do?id="+id).submit();
				}
			});
		}
		
		function dateCheck(){
			var inputStart = $("#time_start").val();
			var arrStart = inputStart.split("-");
			var tmpIntStartYear = parseInt(arrStart[0],10);
			var tmpIntStartMonth = parseInt(arrStart[1],10);
			var tmpIntStartDay = parseInt(arrStart[2],10);

			var inputEnd = $("#time_end").val();
			var arrEnd = inputEnd.split("-");
			var tmpIntEndYear = parseInt(arrEnd[0],10);
			var tmpIntEndMonth = parseInt(arrEnd[1],10);
			var tmpIntEndDay = parseInt(arrEnd[2],10);
			if( tmpIntStartYear < tmpIntEndYear ){
				return true;
			}else if(tmpIntStartYear == tmpIntStartYear ){
				if( tmpIntStartMonth < tmpIntEndMonth ){
					return true;
				}else if(tmpIntStartMonth == tmpIntEndMonth){
					if(tmpIntStartDay <= tmpIntEndDay){
						return true;
					}else{
						$("#time_start").tips({
							side : 3,
							msg : '开始时间不能大于结束时间！',
							bg : '#FF5080',
							time : 2
						});
						$("#time_start").focus();
						return false;	
					}
				}else{
					$("#time_start").tips({
						side : 3,
						msg : '开始时间不能大于结束时间！',
						bg : '#FF5080',
						time : 2
					});
					$("#time_start").focus();
					return false;	
				}
			}else{
				$("#time_start").tips({
					side : 3,
					msg : '开始时间不能大于结束时间！',
					bg : '#FF5080',
					time : 2
				});
				$("#time_start").focus();
				return false;	
			} 
		}
		
		function checkEnd(){
			var inputStart = $("#time_end").val();
			var arrStart = inputStart.split("-");
			var tmpIntStartYear = parseInt(arrStart[0],10);
			var tmpIntStartMonth = parseInt(arrStart[1],10);
			var tmpIntStartDay = parseInt(arrStart[2],10);
			
			var myDate = new Date();
			var tmpIntEndYear = parseInt(myDate.getFullYear());
			var tmpIntEndMonth = parseInt(myDate.getMonth())+1;
			var tmpIntEndDay = parseInt(myDate.getDate());
			if( tmpIntStartYear < tmpIntEndYear ){
				return true;
			}else if(tmpIntStartYear == tmpIntStartYear ){
				if( tmpIntStartMonth < tmpIntEndMonth ){
					return true;
				}else if(tmpIntStartMonth == tmpIntEndMonth){
					if(tmpIntStartDay <= tmpIntEndDay){
						return true;
					}else{
						bootbox.alert("结束日期不能晚于当前日期！", function() {
						});
						return false;
					}
				}else{
					bootbox.alert("结束日期不能晚于当前日期！", function() {
					});
					return false;
				}
			}else{
				bootbox.alert("结束日期不能晚于当前日期！", function() {
				});
				return false;
			} 
		}
		
		function check(){
			var inputStart = $("#time_start").val();
			var inputEnd = $("#time_end").val();
			if(inputStart != '' && inputEnd != ''){
				if(dateCheck()){
					top.jzts();
					$("#pageForm").submit();
				}else{
					return false;
				}
			}else{
				top.jzts();
				$("#pageForm").submit();
			}
		}
	</script>
</body>
</html>
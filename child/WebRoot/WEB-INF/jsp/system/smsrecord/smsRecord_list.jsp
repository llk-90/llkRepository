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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>
</head>
<body>

	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="smsRecord/list.do" method="post" name="Form"
						id="Form">
						<!-- 检索 start -->
						<table>
							<tr>
								<td><span class="input-icon"> 
									<input autocomplete="off" id="sms_time_start" type="text" class="span10 date-picker" style="width: 220px;"
									name="sms_time_start" value="${pd.sms_time_start }" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="这里输入开始发送时间" />
									<i id="nav-search-icon" class="icon-calendar"></i>
									</span></td>
								<td style="vertical-align: top;"><span class="input-icon"> 
								<input autocomplete="off" id="sms_time_end" type="text" class="span10 date-picker" style="width: 220px;"
								name="sms_time_end" value="${pd.sms_time_end }" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="这里输入发送结束时间" />
								<i id="nav-search-icon" class="icon-calendar"></i>
									</span></td>
								<td style="vertical-align: top;"><button
										type="button"	class="btn btn-mini btn-light" onclick="searchs();" title="检索">
											<i id="nav-search-icon" class="icon-search"></i>
										</button></td>
							</tr>
						</table>
						<!-- 检索  end-->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center"><label> <input type="checkbox"
											id="zcheckbox" /><span class="lbl"></span></label></th>
									<th class="center" nowrap="nowrap">序号</th>
									<th class="center" nowrap="nowrap">发送时间</th>
									<th class="center" style="width: 70%;">发送内容</th>
									<th class="center" nowrap="nowrap">实发数量</th>
									<th class="center" nowrap="nowrap">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:choose>
							<c:when test="${not empty varList}">
										
											<c:forEach items="${varList}" var="var" varStatus="vs">
									<tr>
											<td class='center' style="width: 30px;vertical-align: middle;"><label><input
														type='checkbox' name='ids' value="${var.sms_id}" /><span
														class="lbl"></span></label></td>
												<td class='center' style="width: 30px;vertical-align: middle;">${vs.index+1}</td>
											<td class="center" style="vertical-align: middle;" nowrap="nowrap">${var.sms_time}</td>
											<td class="" style="word-wrap: break-word;word-break: normal;">${var.sms_content}</td>
											<td class="center" nowrap="nowrap" style="vertical-align: middle;">${var.sms_count}</td>
											<td class="center" nowrap="nowrap" style="vertical-align: middle;">
											<a class="btn btn-small btn-danger" style="vertical-align: middle;" title="查看" onclick="checkInfo('${var.sms_id}')"><i class='icon-search icon-on-right'></i></a>  
											</td>
										</tr>
										</c:forEach>
										
								
									<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative" style="height: 25px;">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;">
										<a class="btn btn-small btn-info" onclick="balCheck();">余额查询</a>
										<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
											class='icon-trash'></i></a>
									</td>
									<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
	</div>
	<!--/.fluid-container#main-container-->


	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
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
			
			//日期框
			$('.date-picker').datepicker();
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
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
		
		
		//检索
		function searchs(){
			var start_time=$("#sms_time_start").val();
			var end_time=$("#sms_time_end").val();
			if(start_time.length>0&&end_time.length>0){
				if(start_time>end_time)
				{
				$("#sms_time_start").tips({
					side : 3,
					msg : '开始时间不能大于结束时间！',
					bg : '#FF5080',
					time : 2
				});
				$("#create_time_start").focus();
				return false;		
				}
			}
			$("#Form").submit();
			nextPage('${page.currentPage}'); 
		}
		//余额查询
		function balCheck(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="余额查询";
			 diag.URL = '<%=basePath%>smsRecord/balCheck.do';
			 diag.Width =600;
			 diag.Height =200;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage('${page.currentPage}');
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		//详情
		function checkInfo(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="详情";
			 diag.URL = '<%=basePath%>smsRecord/checkInfo.do?sms_id='+Id;
			 diag.Width =800;
			 diag.Height = 530;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage('${page.currentPage}');
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>smsRecord/deleteAll.do?tm='+ new Date().getTime(),
								data : {
									DATA_IDS : str
									   },
								dataType : 'json',
								//beforeSend: validateData,
								cache : false,
								success : function(data) {
									$.each(
										data.list,
										function(i,list) {
											nextPage('${page.currentPage}');
											});
										}
									});
								}
							}
						}
					});
		}
	</script>
</body>
</html>


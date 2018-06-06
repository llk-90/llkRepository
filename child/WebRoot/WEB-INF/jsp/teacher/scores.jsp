<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="false" %>
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
<%@ include file="../system/admin/top.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="row-fluid">

					<!-- 检索  -->
					<form action="scoresImportController/list.do" method="post" name="Form" id="Form">
					 <input type="hidden" name="userId" id="userId" value="" />
						<table>
							<tr>						
								<td>	
									<span class="input-icon"> 
									<input type="text"  autocomplete="off" id="nav-search-input" name="rc_name" value="${pd.rc_name }" placeholder="这里输入学生姓名" /><i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td></td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="classType" style="width: 165px;" data-placeholder="请选择考试批次">
										<option value=""></option>
										<option value="">全部</option>
										<c:forEach items="${piciList }" var="bean">
											<option value="${bean.rc_batch}" <c:if test="${pd.classType == bean.rc_batch}">selected = "true"</c:if>>${bean.rc_batch}</option>
										</c:forEach>
									</select>
								</td>
								
		
								<td></td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>
								</td>
								
								<td style="vertical-align: top;">
									<a class="btn btn-mini btn-light" onclick="fromExcel();" title="导入成绩">
										<i id="nav-search-icon" class="icon-upload-alt"></i>
									</a>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<!-- <th class="center"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th> -->
									<th class="center">序号</th>
									<!-- <th class="center">学号</th> -->
									<th class="center">姓名</th>
									<th class="center">语文</th>
									<th class="center">数学</th>
									<th class="center">英语</th>									
									<th class="center">生物</th>
									<th class="center">化学</th>
									<th class="center">物理</th>
									<th class="center">地理</th>
									<th class="center">历史</th>
									<th class="center">政治</th>
									<th class="center">美术</th>
									<th class="center">音乐</th>									
									<th class="center">体育</th>
									<th class="center">德育考核</th>
									<th class="center">劳动技术</th>
									<th class="center">计算机</th>
									<th class="center">物理实验</th>
									<th class="center">化学实验</th>									
									<th class="center">生物实验</th>
									<th class="center">总成绩</th>
									<th class="center">排名</th>
									<th class="center">考试批次</th>									
								</tr>
							</thead>

							<tbody>						
								<c:choose>
									<c:when test="${not empty varList}">
									<c:set var="counts" value="0" />								
										<c:forEach items="${piciList}" var="var1" varStatus="vs1">
											<c:set var="count" value="0" />
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<c:if test="${var1.rc_batch eq var.rc_batch}">
													<c:set var="count" value="${count + 1 }" />
													<c:set var="counts" value="${counts + 1 }" />
													<tr>														
														<td class='center' style="width: 30px;">${counts}</td>
														<%-- <td class='center'>${var.rc_stu_no}</td> --%>
														<td class='center'>${var.rc_name}</td>
														<td class='center'><fmt:formatNumber value="${var.rc_chinese}"  groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_math}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_english}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_biology}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_chemistry}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_hysics}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_geography}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_history}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_politics}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_art}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_music}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_sport}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_moral}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_labor}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_computer}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_phytest}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_chetest}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.rc_biotest}" groupingUsed="false"/></td>
														<td class='center'><fmt:formatNumber value="${var.total}" groupingUsed="false"/></td>
														<td class='center'>${count}</td>
														<td class='center'>${var.rc_batch}</td>
													</tr>
												</c:if>


											</c:forEach>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
								<%-- </c:if> --%>
								<%-- <c:if test="${QX.cha == 0 }">
									<tr>
										<td colspan="100" class="center">您无权查看</td>
									</tr>
								</c:if> --%>
							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<!-- <td style="vertical-align: top;">
										<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');">批量删除
											<i class='icon-trash'></i>
										</a>
									</td> -->
									<td style="vertical-align: top;">
										<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
									</td>
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

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
		<i class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script> <!-- 显示文本框里的提示信息 -->
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
		
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="学生成绩导入到数据库";
			 diag.URL = '<%=basePath%>scoresImportController/scoresImp.do';
			 diag.Width = 700;
			 diag.Height = 180;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
	
			
		//批量操作
		function makeAll(msg){
			 var obj=document.getElementsByName('ids'); 	
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str=='') {
						$(obj[0]).tips({
							side : 2,
							msg : "请至少选择一条信息",
							bg : '#FF5080',
							time : 2
						});
						return;
					}else{
						bootbox.confirm(msg,function(result){	
						if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>scoresImportController/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
				});								
			}	
}
	</script>

	<script type="text/javascript">
		
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
		
		</script>

</body>
</html>


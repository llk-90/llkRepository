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
<!-- jsp文件头和头部 -->
<%@ include file="../system/admin/top.jsp"%>
</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="row-fluid">

					<!-- 检索  -->
					<form action="messageController/list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="lm_receive_user_id" id="lm_receive_user_id" value="${pd.lm_receive_user_id }" />
						<table>
							<tr>
								<td>	
									<span class="input-icon"> 									
									<input type="text"  autocomplete="off" id="nav-search-input" name="Name" value="${pd.Name }" placeholder="这里输入学生姓名" class="onlyName" /><i id="nav-search-icon" class="icon-search onlyName"></i>
									</span>
								</td>								
								<%-- <td>
									<span class="input-icon" > <input id="lm_time_start" class="span10 date-picker" type="text" placeholder="留言时间" data-date-format="yyyy-mm-dd" readonly="readonly" value="${pd.create_time }" name="lm_time_start" style="width: 80%;" autocomplete="off">
									</span>
								</td>		 --%>														
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<!-- <th class="center"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th> -->
									<th class="center">序号</th>								
									<th class="center">学生姓名</th>
									<th class="center">家长姓名</th>
									<th class="center">家长留言内容</th>						
									<th class="center">时间</th>
									<th class="center" style="width: 25%;">未读条数</th>		
									<th class="center" style="width: 100px;">操作</th>
								</tr>
							</thead>
							<tbody>
								<%-- <c:if test="${QX.cha == 1 }"> --%>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>										
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class='center'>${var.sname}</td>
												<td class='center'>${var.NAME}</td>
												<td class='center'>${var.lm_content}</td>	
												<td class='center'>${var.create_time}</td>
												<td class='center'>
												<c:if test="${var.ant != '0'}">
												${var.ant}
												</c:if>
												<c:if test="${var.ant == '0'}">
												0
												</c:if>
												
												</td>
												<td class="center">
													<a title="内容详情" class="btn btn-mini btn-success" data-toggle="dropdown" onClick="huifu('${var.lm_send_user_id }');">内容详情</a>
												</td>																						
											</tr>

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
										<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');">
											批量删除 <i class='icon-trash'></i>
										</a>
										 <a title="回复" class="btn btn-small btn-success" onclick="makeAll('确定要删除选中的数据吗?');">回复										
											<i class="icon-pencil"></i>
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
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<!-- 显示文本框里的提示信息 -->
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<script src="http://libs.baidu.com/jquery/1.8.3/jquery.min.js"></script>
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
		
		//回复
		function huifu(sid){
			var lm_receive_user_id = document.getElementById("lm_receive_user_id").value;
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="留言内容";
			 diag.URL = '<%=basePath%>messageController/goReply.do?sendId='+sid+'&receiveId='+lm_receive_user_id;
			 diag.Width = 600;
			 diag.Height = 620;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
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
								url: '<%=basePath%>messageController/deleteAll.do?tm='+new Date().getTime(),
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
		$('.onlyName').keyup(function () {
			var c=$(this);
            if(/[^a-zA-Z\u4E00-\u9FA5\?·]/.test(c.val())){//替换非汉字字母字符
            	var temp_amount=c.val().replace(/[^a-zA-Z\u4E00-\u9FA5\?·]/g,'');
            	$(this).val(temp_amount);
            }
		});
		
		</script>

</body>
</html>


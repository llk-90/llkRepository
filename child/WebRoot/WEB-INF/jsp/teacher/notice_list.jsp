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
<%@ include file="../system/admin/top.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="noticeManage/noticeList.do" method="post" name="noticeForm"
						id="noticeForm">		
						<table>
							<tr>
								<td style="vertical-align: top;">
									<span>标题:</span>&nbsp;
									<span><input autocomplete="off" style="width: 130px;" type="text" name="Title" value="${pd.Title }" placeholder="请输入要搜索的标题" />&nbsp;&nbsp;
									</span>
								</td>
								<td style="vertical-align: top;">
									<span>类型:</span>&nbsp;										
									<select name="Type" id="Type" style="width: 120px;">
										<option value="${pd.Type }" selected>请选择类型</option>
										<!-- <option selected>请选择类型</option> -->
									    <option value="0" <c:if test="${pd.Type=='0'}">selected = "true"</c:if>>学校通知</option>
										<option value="1" <c:if test="${pd.Type=='1'}">selected = "true"</c:if>>班级通知</option>
										<option value="2" <c:if test="${pd.Type=='2'}">selected = "true"</c:if>>作业通知</option>
									</select>&nbsp;&nbsp;
								</td>
								<td style="vertical-align: top;">
									<span>发布时间:</span>&nbsp;										
									<select name="Time" id="Time" style="width: 140px;">
										<option value="">请选择发布时间</option>
										<option value="week" <c:if test="${pd.Time =='week'}">selected = "true"</c:if>>一周之内</option>
										<option value="month" <c:if test="${pd.Time =='month'}">selected = "true"</c:if>>一个月之内</option>
										<option value="halfYear" <c:if test="${pd.Time =='halfYear'}">selected = "true"</c:if>>半年之内</option>
										<option value="year" <c:if test="${pd.Time =='year'}">selected = "true"</c:if>>一年之内</option>
										<option value="overYear" <c:if test="${pd.Time =='overYear'}">selected = "true"</c:if>>一年以上</option>
									</select>&nbsp;&nbsp;
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
								</td>
							</table>
           
						<div style="margin-top:20px"></div>
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
										<label>
											<input type="checkbox" id="zcheckbox" />
											<span class="lbl"></span>
										</label>
									</th>
									<th class="center">序号</th>
									<th class="center">标题</th>
									<th class="center">图片</th>
									<th class="center">内容</th>
									<th class="center">发布时间</th>
									<th class="center">类型</th>
									<th class="center">发布人</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty noticeList}">
										<c:forEach items="${noticeList}" var="noticeList" varStatus="notice">
											<tr>
												<td class='center' style="width: 30px;">
													<label>
														<input type='checkbox' name='ids' value="${noticeList.id}" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class='center' style="width: 30px;">${notice.index+1}</td>
												<td class='center'>${noticeList.title }</td>
												<td class='center' style="width: 60%;">
													<c:choose>
														<c:when test="${not empty noticeList.picList}">
															<c:forEach items="${noticeList.picList}" var="list" varStatus="picture">
															<img src="${list}"/>
															</c:forEach>
														</c:when>
													</c:choose>
												<br/>
												</td>
												<td class='center' style="width: 60%;">${noticeList.content }</td>
												<td class='center' >
													<fmt:parseDate var="create_time" value="${noticeList.create_time }" pattern="yyyy-MM-dd" />
													<fmt:formatDate value="${create_time }" pattern="yyyy-MM-dd" />
												</td>
												<td class='center'>${noticeList.infoType }</td>
												<td class='center'>${noticeList.author }</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="13" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;">
											<a class="btn btn-small btn-success" onclick="newInfo();">新增</a>
											<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除">
												<i class='icon-trash'></i>
											</a>
									</td>
									<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

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
		function search(){
			top.jzts();
			
			var url='noticeManage/noticeList.do';
			//alert(url);
			$("#noticeForm").attr('action',url);
			$("#noticeForm").submit();
			
		}		
		
		//批量操作
		function makeAll(msg){
			var str ='';
			var tags = document.getElementsByName('ids');
			for(var i=0;i < tags.length;i++)
			{
				  if(tags[i].checked){
					  var s = tags[i].value;
				  	if(str=='') str += s;
				  	else str += ',' + s;
				  }
			}
			if(str==''){
				$("#zcheckbox").tips({
					side:3,
		            msg:'请至少选择一条',
		            bg:'#FF5080',
		            time:2
		        });
				
				return;
			}else{
				bootbox.confirm(msg, function(result) {
					if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>noticeManage/deleteInfo.do?',
						    	data: {id:str},
								dataType:'json',
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage('${page.currentPage}');
									 });
								}
							});
					}
				});
			}
		}
		
		function newInfo(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>noticeManage/newInfo.do';
			 diag.Width = 1000;
			 diag.Height = 850;
			 
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
	</script>
</body>
</html>


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
<%@ include file="../admin/top.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="coursewareP/coursewareList.do" method="post" name="courForm" id="courForm">
						<table>
							<tr>
								<td>
									<span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="c_name" value="${pd.c_name}" placeholder="这里输入课件标题" /> <i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="c_type" id="c_type" data-placeholder="请选择课件类型" style="vertical-align: top; width: 150px;">
										<option value=""></option>
										<option value="" selected="selected">全部</option>
										<option value="1" <c:if test="${pd.c_type  == '1' }">selected = "true"</c:if>>图文</option>
										<option value="2" <c:if test="${pd.c_type  == '2' }">selected = "true"</c:if>>视频</option>
										<option value="3" <c:if test="${pd.c_type  == '3' }">selected = "true"</c:if>>文档</option>
									</select>
								</td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="c_grade" id="c_grade" data-placeholder="请选择所属年级" style="vertical-align: top; width: 150px;">
										<option value=""></option>
										<option value="" selected="selected">全部</option>
										<c:forEach var="item" items="${grade}">
											<option value="${item.key}" <c:if test="${item.key == pd.c_grade}">selected = "true"</c:if>>${item.value}</option>
										</c:forEach>
									</select>
								</td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="c_subject" id="c_subject" data-placeholder="请选择科目类型" style="width: 150px; vertical-align: top;">
										<option value=""></option>
										<option value="" selected="selected">全部</option>
										<c:forEach var="item" items="${subjects}">
											<option value="${item.key}" <c:if test="${item.key == pd.c_subject}">selected = "true"</c:if>>${item.value}</option>
										</c:forEach>
									</select>
								</td>
								
								
								 <td style="vertical-align: top;">
									<select class="chzn-select" name="powers" id="powers" data-placeholder="请选择所属权限" style="width: 150px; vertical-align: top;">
										<option value=""></option>
										<option value="" selected="selected">全部</option>
										<c:forEach var="item" items="${powerslist}">
										<option value="${item.c_name}" <c:if test="${item.c_name == pd.powers}">selected = "true"</c:if>>${item.c_name}</option>
									</c:forEach>
									</select>
								</td> 
								
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center"><label>
									<input type="checkbox"
											id="zcheckbox" /><span class="lbl"></span></label></th>
									<th class="center">序号</th>
									<th class="center">课件标题</th>
									<th class="center">所属年级</th>
									<th class="center">课件类型</th>
									<th class="center">科目类型</th>
									<th class="center">所属权限</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								 <c:choose>
									<c:when test="${not empty list}">
										<c:forEach items="${list}" var="courseware" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input
														type='checkbox' name='ids' value="${courseware.c_id }" /><span
														class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class='center'>${courseware.c_name}</td>
												<td class='center'>${courseware.c_grade}</td>
												<td class='center'>${courseware.c_type}</td>
												<td class='center'>${courseware.c_subject}</td>
												<td class='center'>${courseware.cdname}</td>
												<td style="width: 80px;" class="center">
                                                <a class='btn btn-mini btn-info' title="编辑" onclick="edits('${courseware.c_id}');"><i class='icon-edit'></i></a>
												<a class='btn btn-mini btn-danger' title="删除"  onclick="deletes('${courseware.c_id}','${courseware.c_name}');"><i class='icon-trash'></i></a>
												</td>

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
									<td style="vertical-align: top;"><a
										class="btn btn-small btn-success" onclick="add();">新增</a> <a
										class="btn btn-small btn-danger"
										onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
											class='icon-trash'></i></a></td>
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
		//检索
		function search(){
			top.jzts();
			$("#courForm").submit();
		}
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>coursewareP/goAddC.do';	
			 diag.Width = 780;
			 diag.Height = 780;
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
		
		//修改
		function edits(cid){
		
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>coursewareP/goEditC.do?c_id='+cid;
			 diag.Width = 780;
			 diag.Height = 780;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}
	
		
		//删除
		function deletes(cid,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>coursewareP/deletes.do?c_id="+cid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}
		
		</script>

	<script type="text/javascript">
		
		$(function() {
			
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
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') {
						  		str += document.getElementsByName('ids')[i].value;
						  	}else{ 
						  		str += ',' + document.getElementsByName('ids')[i].value;
						  	}
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
								url: '<%=basePath%>coursewareP/deleteAll.do?tm='
																+ new Date()
																		.getTime(),
														data : {
															C_IDS : str
														},
														dataType : 'json',
														//beforeSend: validateData,
														cache : false,
														success : function(data) {
															$
																	.each(
																			data.list,
																			function(
																					i,
																					list) {
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


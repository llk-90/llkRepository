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
					<form action="<%=basePath %>member_auth/list.do" method="post" name="authForm" id="authForm">
						<table>
							<tr>
								<td>
									<span class="input-icon"> 
										<input autocomplete="off" id="nav-search-input" type="text" name="keyword" value="${pd.keyword}" placeholder="这里输入权限名" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
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
									<th class="center"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
									<th class="center">序号</th>
									<th class="center">权限名</th>
									<th class="center">有效期(天)</th>
									<th class="center">金额</th>
									<th class="center">备注</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								 <!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty authList}">
										<%-- <c:if test="${QX.cha == 1 }"> --%>
											<c:forEach items="${authList}" var="auth" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;">
														<label>
															<input type='checkbox' name='ids' value="${auth.c_id }"  />
															<span class="lbl"></span>
														</label>
													</td>
													<td class='center' style="width: 30px;">${vs.index+1}<input type='hidden' name= 'c_name' value="${auth.c_name}" /></td>
													<td class='center'>${auth.c_name}</td>
													<td class='center'>${auth.c_time}</td>
													<td class='center'>${auth.c_price}</td>
													<td class='center'>${auth.c_remark}</td>
													<td style="width: 100px;" class="center">
													<a class='btn btn-mini btn-info' title="编辑" onclick="editAuth('${auth.c_id}')" ><i class='icon-edit'></i></a>
													<a class='btn btn-mini btn-danger' title="删除"  onclick="delAuth('${auth.c_id}','${auth.c_name}')"><i class='icon-trash'></i></a>
													</td>
												</tr>
											</c:forEach>
										<%-- </c:if> --%>
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
											<a class="btn btn-small btn-success" onclick="add();">新增</a>
											<a title="批量删除" class="btn btn-small btn-danger"
												onclick="makeAll('确定要删除选中的数据吗?');"><i
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
			$("#authForm").submit();
		}
			
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>member_auth/toAdd.do';
			 diag.Width = 390;
			 diag.Height =490;
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
		function editAuth(c_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>member_auth/toEdit.do?id='+c_id;
			 diag.Width = 390;
			 diag.Height = 490;
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
		
		//删除
		function delAuth(c_id,c_name){
			bootbox.confirm("确定要删除这条数据吗?", function(result) {
				if(result) {
					 top.jzts();
					var url = "<%=basePath%>member_auth/isDelete.do?id="+c_id
					$.get(url,function(data){
						if(data.msg == 'fail'){
							top.hangge();
							bootbox.alert(c_name+"无法删除！", function() {
							});
						}else{
							$.get("<%=basePath%>member_auth/delete.do?id="+c_id,function(data){
								 nextPage('${page.currentPage}');
							});
						}
					});
				}
			});
		}
		
		//批量操作
		function makeAll(msg){
					var str = '';
					var strName ='';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
						  if(document.getElementsByName('ids')[i].checked){
							  if(str==''){
								  str += document.getElementsByName('ids')[i].value;
								  strName += document.getElementsByName('c_name')[i].value;
							  }else{ 
								  str += ',' + document.getElementsByName('ids')[i].value;
								  strName += ',' + document.getElementsByName('c_name')[i].value;
							  }
						}
					}
					if(str==''){
						$("#zcheckbox").tips({
							side:3,
				            msg:'请至少选择一条数据',
				            bg:'#FF5080',
				            time:2
				        });
						
						return;
					}else{
						bootbox.confirm(msg, function(result) {
							if(result) {
							if(msg == '确定要删除选中的数据吗?'){
								top.jzts();
								$.ajax({
									type: "POST",
									url: '<%=basePath%>member_auth/deleteAll.do',
									data : {
										DATA_IDS : str,
										DATA_NAMES:strName
									},
									dataType : 'json',
									cache : false,
									success : function(data) {
										if(data.msg == 'fail'){
											top.hangge();
											var altMsg = '';
											$.each(data.nameList,function(i,list) {
												altMsg += list+'<br/>';
											});
											bootbox.alert("以下权限无法删除！<br>"+ altMsg , function() {});
											return;
										}
										$.each(data.list,function(i,list) {
												 nextPage('${page.currentPage}');
										});
									}
								});
							} 
						}
				});
			}
		}
	</script>
	<script type="text/javascript">
		$(function() { 
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


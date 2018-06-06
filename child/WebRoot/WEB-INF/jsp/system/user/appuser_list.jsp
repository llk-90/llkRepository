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
					<form action="appuser/listUsers.do" method="post" name="userForm"
						id="userForm">
						<table border='0'>
							<tr>
								<td style="vertical-align: top;">
									<select class="chzn-select" style="width: 120px;" data-placeholder="请选择学校">
										<option value=""></option>
										<option value="">实验小学</option>
										<option value="">蒙山小学</option>
									</select>
								</td>
								<td style="vertical-align: top;">
									<select class="chzn-select" style="width: 120px;" data-placeholder="请选择年级">
										<option value=""></option>
										<option value="">一年级</option>
										<option value="">二年级</option>
									</select>
								</td>
								<td style="vertical-align: top;">
									<select class="chzn-select" style="width: 120px;" data-placeholder="请选择班级">
										<option value=""></option>
										<option value="">一班</option>
										<option value="">二班</option>
									</select>
								</td>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text" placeholder="姓名" />
								</span></td>	
								
									<td style="vertical-align: top;"><button
											class="btn btn-mini btn-light" onclick="search();" title="检索">
											<i id="nav-search-icon" class="icon-search"></i>
										</button>
										<a class="btn btn-mini btn-light" onclick="toImport()" title="导入">
					    <i id="nav-search-icon" class="icon-upload-alt"></i></a>
										</td>
							</tr>
						</table>
						<!-- 检索  -->


						<table id="table_report"
							class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center"><label><input type="checkbox"
											id="zcheckbox" /><span class="lbl"></span></label></th>
									<th class="center">序号</th>
									<th class="center">所属学校</th>
									<th class="center">所属年级班级</th>
									<th class="center">姓名</th>
									<th class="center"><i class="icon-phone"></i>联系电话</th>
									<th class="center"><i class="icon-envelope"></i>邮箱</th>
									<th class="center" width='20%'>备注</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>
								<tr>
									<td class='center' style="width: 30px;"><label><input
											type='checkbox' name='ids' value="1"  alt="1" /><span
											class="lbl"></span></label></td>
									<td class='center' style="width: 30px;">1</td>
									<td class='center'>实验小学</td>
									<td class='center'>一年级二班</td>
									<td class='center'>李红</td>
									<td class='center'>18315911482</td>
									<td class='center'>lihong@163.com</td>
									<td class='center'>特级教师资格证</td>
									<td style="width: 50px;" class="center">
									<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.USER_ID }');"><i class='icon-edit'></i></a>
									</td>
								</tr>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty userList}">
											<c:forEach items="${userList}" var="user" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;"><label><input
															type='checkbox' name='ids' value="${user.USER_ID }"
															id="${user.EMAIL }" alt="${user.PHONE }" /><span
															class="lbl"></span></label></td>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td class='center'>${user.USERNAME }</td>
													<td class='center'>${user.NAME }</td>
													<td class='center'>${user.farm_name}</td>
													<td class='center'>${user.ROLE_NAME }</td>
													<td class='center'>${user.EMAIL}</td>
													<td class='center'>${user.PHONE}</td>
													<td class='center'>${user.BZ}</td>
													<td style="width: 50px;" class="center">
													<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.USER_ID }');"><i class='icon-edit'></i></a>
													</td>
													<%-- <td style="width: 30px;" class="center">
													
														<div class='hidden-phone visible-desktop btn-group'>s
															<div class="inline position-relative">
																<button class="btn btn-mini btn-info"
																	data-toggle="dropdown">
																	<i class="icon-cog icon-only"></i>
																</button>ss
																<ul
																	class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
																	
																		<li><a style="cursor: pointer;" title="发送电子邮件"
																			onclick="sendEmail('${user.EMAIL }');"
																			class="tooltip-success" data-rel="tooltip" title=""
																			data-placement="left"><span class="green"><i
																					class='icon-envelope-alt'></i></span></a></li>
																
																	
																		<li><a style="cursor: pointer;" title=""
																			onclick="sendSms('${user.PHONE }');"
																			class="tooltip-warning" data-rel="tooltip" title=""
																			data-placement="left"><span class="blue"><i
																					class='icon-envelope'></i></span></a></li>
																	
																	
																		<li><a style="cursor: pointer;" title="编辑"
																			onclick="editUser('${user.USER_ID }');"
																			class="tooltip-success" data-rel="tooltip" title=""
																			data-placement="left"><span class="green"><i
																					class="icon-edit"></i></span></a></li>
																	
																	<c:choose>
																		<c:when test="${user.USERNAME=='admin'}"></c:when>
																		<c:otherwise>
																		
																				<li><a style="cursor: pointer;" title="删除"
																					onclick="delUser('${user.USER_ID }','${user.USERNAME }');"
																					class="tooltip-error" data-rel="tooltip" title=""
																					data-placement="left"><span class="red"><i
																							class="icon-trash"></i></span> </a></li>
																			
																		</c:otherwise>
																	</c:choose>
																</ul>

															</div>

														</div>
													</td> --%>
												</tr>

											</c:forEach>
									</c:when>
									<%-- <c:otherwise>
									<!-- 	<tr class="main_info">
											<td colspan="10" class="center">没有相关数据</td>
										</tr> -->
									</c:otherwise> --%>
								</c:choose>


							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;">
											<a class="btn btn-small btn-success" onclick="add();">新增</a>
											<a class="btn btn-small btn-danger"
												onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
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
			$("#userForm").submit();
		}
		
		//去发送电子邮件页面
		function sendEmail(EMAIL){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送电子邮件";
			 diag.URL = '<%=basePath%>appuser/goSendEmail.do?EMAIL='+EMAIL+'&msg=appuser';
			 diag.Width = 660;
			 diag.Height = 550;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//去页面
		function sendSms(phone){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="";
			 diag.URL = '<%=basePath%>appuser/goSendSms.do?PHONE='+phone+'&msg=appuser';
			 diag.Width = 600;
			 diag.Height = 265;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//新增
		function add(){
			 top.jzts();
			
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增教师";
			 diag.URL = '<%=basePath%>appuser/goAddU.do';
			 diag.Width = 365;
			 diag.Height = 700;
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
		function editUser(user_id){
		
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="教师资料";
			 diag.URL = '<%=basePath%>appuser/goEditU.do?USER_ID='+user_id;
			 diag.Width = 380;
			 diag.Height = 520;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}
	
		
		//删除
		function delUser(userId,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>appuser/deleteU.do?USER_ID="+userId+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}
		
		//导入
		function toImport(){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="导入教师";
			 diag.URL = '<%=basePath%>appuser/toImport.do';
			 diag.Width = 365;
			 diag.Height = 200;
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
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
						  	else emstr += ';' + document.getElementsByName('ids')[i].id;
						  	
						  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
						  	else phones += ';' + document.getElementsByName('ids')[i].alt;
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
								url: '<%=basePath%>appuser/deleteAllU.do?tm='
																+ new Date()
																		.getTime(),
														data : {
															USER_IDS : str
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
										} else if (msg == '确定要给选中的用户发送邮件吗?') {
											sendEmail(emstr);
										} else if (msg == '确定要给选中的用户吗?') {
											sendSms(phones);
										}

									}
								}
							});
		}

		</script>

</body>
</html>


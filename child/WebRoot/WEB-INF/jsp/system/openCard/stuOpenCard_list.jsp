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
					<form action="stuOpenCard/list.do" method="post" name="Form" id="Form">
						<table border='0'>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" name="stuName" id="nav-search-input" type="text" placeholder="这里输入学生姓名" value="${pd.stuName}" />
									</span>
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>
								</td>								
								<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="importExcel();" title="导入开卡信息"><i id="nav-search-icon" class="icon-upload-alt"></i></a></td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
										<label>
											<input type="checkbox" id="zcheckbox" />
											<span class="lbl"></span>
										</label>
									</th>
									<th class="center">序号</th>
									<th class="center">学生姓名</th>
									<th class="center">性别</th>
									<th class="center">学校名称</th>
									<th class="center">年级</th>
									<th class="center">班级</th>
									<th class="center">学生卡号</th>
									<th class="center">同步状态</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;">
														<label>
															<input type='checkbox' name='ids' value="${var.IcNo}" />
															<span class="lbl"></span>
														</label>
													</td>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td class="center">${var.stuName}</td>
													<td class="center">${var.sex}</td>
													<td class="center">${var.schoolName}</td>
													<td class="center">${var.stuGrade}</td>
													<td class="center">${var.stuClass}</td>
													<td class="center">${var.IcNo}</td>
													<td class="center">${var.updUserFlg}</td>
													<td style="width: 50px;" class="center">
														<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.IcNo}');">
															<i class='icon-edit'></i>
														</a>
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
									<td style="vertical-align: top;">
											<a class="btn btn-small btn-success" onclick="add();">新增</a>
											<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除">
												<i class='icon-trash'></i>
											</a>
									</td>
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
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}		
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="开卡";
			 diag.URL = '<%=basePath%>stuOpenCard/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 505;
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
		function del(IcNo){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>stuOpenCard/delete.do?IcNo="+IcNo
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}
		
		//修改
		function edit(IcNo){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>stuOpenCard/goEdit.do?IcNo='+IcNo;
			 diag.Width = 460;
			 diag.Height =490;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage('${page.currentPage}');
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
								url: '<%=basePath%>stuOpenCard/deleteAll.do?',
						    	data: {IcNo:str},
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

		//打开上传excel页面 
        function importExcel(){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="开卡信息导入";
             diag.URL = '<%=basePath%>stuOpenCard/importStu.do'
			diag.Width = 700;
			diag.Height = 180;
			diag.CancelEvent = function() { //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                      if('${page.currentPage}' == '0'){
                          top.jzts();
                          setTimeout("self.location.reload()",100);
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


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
					<form action="member/list.do" method="post" name="memberForm"
						id="memberForm">
						<table>
							<tr>
								<td>
									<span class="input-icon"> <input autocomplete="off" style="width: 160px;" id="nav-search-input" type="text" name="Name" value="${pd.Name }" placeholder="这里输入学生或家长姓名" /> <i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>								
								</td>
								<td style="vertical-align: top;">
<!-- 								2016/8/31 yc  删除 -->
<!-- 									<a class="btn btn-mini btn-light" onclick="importExcel();" title="导入成绩"> -->
<!-- 										<i id="nav-search-icon" class="icon-upload-alt"></i> -->
<!-- 									</a> -->
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">所属学校</th>
									<th class="center">入学年度</th>
									<th class="center">所属班级</th>
									<th class="center">学号</th>
									<th class="center">学生姓名</th>
									<th class="center">学生性别</th>
									<th class="center">学生家长</th>
									<th class="center">联系电话</th>
									<th class="center">微信绑定</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty memberList}">
										<c:forEach items="${memberList}" var="member" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class='center'>${member.schoolname}</td>
												<td class='center'>${member.jibuname}</td>
												<td class='center'>${member.className}</td>
												<td class='center'>${member.s_stu_no}</td>
												<td class='center'>${member.s_name}</td>
												<td class='center'>${member.s_sex}</td>
												<td class='center'>${member.name}</td>
												<td class='center'>${member.phone}</td>
												<td class='center'>${member.weixin}</td>
												<td style="width: 80px;" class="center">
	                                                <a class='btn btn-mini btn-info' title="编辑" onclick="editE('${member.s_id }');"><i class='icon-edit'></i></a>
	                                                <a class='btn btn-mini btn-danger' title="删除"  onclick="del('${member.s_id }')"><i class='icon-trash'></i></a>
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
<!-- 								2016/8/31 yc  删除 -->
<!-- 									<td style="vertical-align: top;"> -->
<!-- 										<a class="btn btn-small btn-success" onclick="add();">新增</a> -->
<!-- 										<a class="btn btn-small btn-danger" onclick="update();">所属班级</a> -->
<!-- 									</td> -->
									<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
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
			$("#memberForm").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>member/toAdd.do';
			 diag.Width = 750;
			 diag.Height = 420;
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
		
		//编辑学生信息
		function editE(s_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>member/toEdit.do?s_id='+s_id;
			 diag.Width = 750;
			 diag.Height = 400;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除学生信息
		function del(s_id){
			bootbox.confirm("删除该数据后，用户绑定的信息和购买的课程也将被删除！", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>member/delete.do?s_id="+s_id
					$.get(url,function(data){
							 nextPage('${page.currentPage}');
					});
				}
			});
		}
		
		
		//更新班级
		function update(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="班级信息";
			 diag.URL = '<%=basePath%>member/toUpdate.do';
			 diag.Width = 390;
			 diag.Height =490;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}

		
		//打开上传excel页面
        function importExcel(){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="学生和家长信息导入";
             diag.URL = '<%=basePath%>member/importStu.do';
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

	<script type="text/javascript">
		$(function() {
			//日期框
			$('.date-picker').datepicker();
			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});
			//复选框
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});
					});
		});
	</script>
</body>
</html>


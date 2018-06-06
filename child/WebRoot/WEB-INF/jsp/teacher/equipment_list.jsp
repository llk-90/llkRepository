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
					<form action="equipment/list.do" method="post" name="equipmentForm"
						id="equipmentForm">
						<table>
							<tr>
								<td>
									<span class="input-icon"> <input autocomplete="off" style="width: 160px;" id="nav-search-input" type="text" name="Name" value="${pd.Name }" placeholder="这里输入姓名" /> <i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>								
								</td>
								<td>
									<span class="input-icon"> <input autocomplete="off" style="width: 160px;" id="nav-search-input" type="text" name="phone" value="${pd.phone }" placeholder="这里输入联系方式" /> <i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>								
								</td>
								<td style="vertical-align: top;">
									<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出">
										<i id="nav-search-icon" class="icon-download-alt"></i>
									</a>
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
									<th class="center">所属班级</th>
									<th class="center">学生姓名</th>
									<th class="center">联系方式</th>
									<th class="center">开通业务</th>
									<th class="center">设备状态</th>
									<th class="center">设备号</th>
									<th class="center">IC卡号</th>
									<th class="center">IC卡种类</th>
									<th class="center">支付状态</th> 
									<th class="center">操作</th>
									<th class="center">轨迹</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty equipmentList}">
										<c:forEach items="${equipmentList}" var="equipment" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td class="center">${equipment.schoolname }</td>
												<td class="center">${equipment.className }</td>
												<td class='center'>${equipment.s_name}</td>
												<td class='center'>${equipment.phone}</td>
												<td class='center'>${equipment.ibaby_equ_openBusiness}</td>
												<td class='center'>${equipment.ibaby_equ_status}</td>
												<td class="center">${equipment.ibaby_imei_code}</td>
												<td class="center">${equipment.ibaby_ic_code}</td>
												<td class="center">${equipment.ibaby_ic_code_type}</td>
												<td class="center">${equipment.ibaby_pay_state}</td>
												<td style="width: 35px;" class="center">
	                                                <a class='btn btn-mini btn-info' title="编辑" onclick="editE('${equipment.s_id }');"><i class='icon-edit'></i></a>
                                                </td>
                                                <td style="width: 50px;" class="center">
	                                                <a class='btn btn-mini btn-purple' title="轨迹" onclick="editTb('${equipment.s_id }')" ><i class='icon-picture'></i></a>
                                                </td>
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
		//检索
		function search(){
			top.jzts();
			$("#equipmentForm").submit();
		}
		
		//轨迹
		function editTb(s_id){
			 
			 window.open("wx/equipment_orbit.html?s_id="+s_id, 
					 "", 
					 'left=250,top=150,width=750,height=500,toolbar=no,menubar=no,status=no,location=no, scrollbars=yes,resizable=yes');			 
		}
		
		//编辑学生信息
		function editE(s_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>equipment/toEdit.do?s_id='+s_id;
			 diag.Width = 750;
			 diag.Height = 450;
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
		//导出EXcel
		function toExcel(){
			window.location.href=encodeURI("<%=basePath%>equipment/excel.do?");
		}
	</script>
</body>
</html>


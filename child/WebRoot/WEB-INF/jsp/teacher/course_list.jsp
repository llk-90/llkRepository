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
					<form action="course/list.do" method="post" name="courseForm"
						id="courseForm">				
						<table>
							<tr>
								<td><label class="control-label"
								style="padding-top: 1px; width: 50px; float: center;"
								for="reginoName">区域：</label></td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="reginoType" style="width: 140px;">
										<option value="">全部</option>										
										<c:forEach items="${reginoList}" var="bean">										
											<option value="${bean.reginoName}" <c:if test="${pd.reginoType == bean.reginoName}">selected = "true"</c:if>>${bean.reginoName}</option>
										</c:forEach>
									</select>
								</td>
								<td>&nbsp;&nbsp;</td>
								<td><label class="control-label"
								style="padding-top: 1px; width: 50px; float: center;"
								for="schoolName">学校：</label></td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="schoolType" style="width: 165px;">
										<option value="">全部</option>										
										<c:forEach items="${schoolList}" var="bean">											
											<option value="${bean.schoolName}" <c:if test="${pd.schoolType == bean.schoolName}">selected = "true"</c:if>>${bean.schoolName}</option>
										</c:forEach>
									</select>
								</td>
								<td>&nbsp;&nbsp;</td>
								<td><label class="control-label"
								style="padding-top: 1px; width: 50px; float: center;"
								for="jibuName">年级：</label></td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="jibuType" style="width: 140px;">
										<option value="">全部</option>										
										<c:forEach items="${jibuList}" var="bean">											
											<option value="${bean.jibuName}" <c:if test="${pd.jibuType == bean.jibuName}">selected = "true"</c:if>>${bean.jibuName}</option>
										</c:forEach>
									</select>
								</td>
								<td>&nbsp;&nbsp;</td>
								<td><label class="control-label"
								style="padding-top: 1px; width: 50px; float: center;"
								for="className">班级：</label></td>
								<td style="vertical-align: top;">									
									<select class="chzn-select" name="classType" style="width: 140px;">	
										<option value="">全部</option>									
										<c:forEach items="${classList}" var="bean">										
											<option value="${bean.className}" <c:if test="${pd.classType == bean.className}">selected = "true"</c:if>>${bean.className}</option>
										</c:forEach>
									</select>
								</td>
								<td>&nbsp;&nbsp;</td>
								<td><label class="control-label"
								style="padding-top: 1px; width: 100px; float: center;"
								for="className">拒绝所有来电：</label></td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="ibaby_donot_call" style="width: 80px;" data-placeholder="是或否">
										<option value=""></option>
										<option value="0" <c:if test="${pd.ibaby_donot_call  == '0' }">selected = "true"</c:if>>否</option>
										<option value="1" <c:if test="${pd.ibaby_donot_call  == '1' }">selected = "true"</c:if>>是</option>
									</select>
								</td>
								<td>&nbsp;</td>	
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>								
								</td>
							</tr>
						</table>
						<div style="margin-top:20px"></div>
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<td colspan="9" class="center">课程表</td>
								</tr>
								<tr>
									<th class="center">节次</th>
									<th class="center">星期一</th>
									<th class="center">星期二</th>
									<th class="center">星期三</th>
									<th class="center">星期四</th>
									<th class="center">星期五</th>
									<th class="center">星期六</th>
									<th class="center">星期日</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty courseList}">
										<c:forEach items="${courseList}" var="course" varStatus="vs" end="7">
											<tr>												
												<td class='center'>第${course.ibaby_courseNo}节课</td>
												<td class='center'>${course.ibaby_startTime_Mon}-${course.ibaby_endTime_Mon}</td>
												<td class='center'>${course.ibaby_startTime_Tu}-${course.ibaby_endTime_Tu}</td>
												<td class='center'>${course.ibaby_startTime_We}-${course.ibaby_endTime_We}</td>
												<td class='center'>${course.ibaby_startTime_Th}-${course.ibaby_endTime_Th}</td>
												<td class='center'>${course.ibaby_startTime_Fr}-${course.ibaby_endTime_Fr}</td>
												<td class='center'>${course.ibaby_startTime_Sa}-${course.ibaby_endTime_Sa}</td>
												<td class='center'>${course.ibaby_startTime_Sun}-${course.ibaby_endTime_Sun}</td>
												<td style="width: 80px;" class="center">
	                                                <a class='btn btn-mini btn-info' title="编辑" onclick="editE('${course.ibaby_courseNo}');"><i class='icon-edit'></i></a>
                                                </td>																							
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="9" class="center">没有课程表信息&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="add();">新增课程表</a></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
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
			$("#courseForm").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
		     var params = $("#courseForm").serializeArray();  
	         var values = {};  
	         for( x in params ){
	        	if(params[x].name=='reginoType'){reginoType=params[x].value};
	        	if(params[x].name=='schoolType'){schoolType=params[x].value};
	        	if(params[x].name=='jibuType'){jibuType=params[x].value};
	        	if(params[x].name=='classType'){classType=params[x].value};
	        	if(params[x].name=='ibaby_donot_call'){ibaby_donot_call=params[x].value};
	         }       
	         window.location.href='<%=basePath%>course/add.do?reginoType='+encodeURI(encodeURI(reginoType))+'&schoolType='+encodeURI(encodeURI(schoolType))+'&jibuType='+encodeURI(encodeURI(jibuType))+'&classType='+encodeURI(encodeURI(classType))+'&ibaby_donot_call='+ibaby_donot_call;

		}
		
		//编辑
		function editE(ibaby_courseNo){
			 top.jzts();
			 var diag = new top.Dialog();
		     var params = $("#courseForm").serializeArray();  
		         var values = {};  
		         for( x in params ){
		        	if(params[x].name=='reginoType'){reginoType=params[x].value};
		        	if(params[x].name=='schoolType'){schoolType=params[x].value};
		        	if(params[x].name=='jibuType'){jibuType=params[x].value};
		        	if(params[x].name=='classType'){classType=params[x].value};
		        	if(params[x].name=='ibaby_donot_call'){ibaby_donot_call=params[x].value};
		         }  
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>course/toEdit.do?ibaby_courseNo='+ibaby_courseNo+'&reginoType='+encodeURI(encodeURI(reginoType))+'&schoolType='+encodeURI(encodeURI(schoolType))+'&jibuType='+encodeURI(encodeURI(jibuType))+'&classType='+encodeURI(encodeURI(classType))+'&ibaby_donot_call='+ibaby_donot_call;	
			 diag.Width = 750;
			 diag.Height = 450;
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


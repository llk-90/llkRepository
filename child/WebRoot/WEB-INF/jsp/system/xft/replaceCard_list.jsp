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
					<form action="replaceCard/list.do" method="post" name="replaceCardForm"
						id="replaceCardForm">
						<!-- 检索  -->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">联系人</th>									
									<th class="center">电话号码</th>
									<th class="center">联系地址</th>
									<th class="center">补卡状态</th>
									<th class="center">补卡数量</th>
									<th class="center">提交时间</th>
									<th class="center">班级名称</th>
									<th class="center">卡的类型</th> 
									<th class="center">补卡照片地址</th>							
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty cardReplaceList}">
										<c:forEach items="${cardReplaceList}" var="cardReplace" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>									
												<td class='center'>${cardReplace.contact_person}</td>												
												<td class='center'>${cardReplace.phone_num}</td>
												<td class='center'>${cardReplace.address}</td>
												<td class="center">${cardReplace.replace_status}</td>
												<td class='center'>${cardReplace.card_num}</td>			
												<td class="center">${cardReplace.submit_time}</td>
												<td class="center">${cardReplace.className}</td>
												<td class="center">${cardReplace.cardType}</td>
												<td class="center">${cardReplace.url}</td>
												<td style="width: 35px;" class="center">
	                                                <a class='btn btn-mini btn-info' title="编辑" onclick="editE('${cardReplace.stuId}');"><i class='icon-edit'></i></a>
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


	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		$(top.hangge());
		//检索
		function search(){
			top.jzts();
			$("#replaceCardForm").submit();
		}
		
		//编辑补卡信息
		function editE(stu_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>replaceCard/toEdit.do?stu_id='+stu_id;
			 diag.Width = 850;
			 diag.Height = 550;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}
	</script>
</body>
</html>


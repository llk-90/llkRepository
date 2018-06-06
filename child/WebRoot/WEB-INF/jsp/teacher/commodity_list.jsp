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
					<form action="commodity/list.do" method="post" name="commodityForm"
						id="commodityForm">
						<!-- 检索  -->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">商品编号</th>
									<th class="center">图片</th>
									<th class="center">商品名</th>
									<th class="center">商品价格</th>
									<th class="center">商品详细</th>
									<th class="center">创建时间</th> 
									<th class="center">更新时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty commodityList}">
										<c:forEach items="${commodityList}" var="commodity" varStatus="vs">
											<tr>
												<td class='center'>${commodity.ibaby_commodity_id}</td>
												<td class="center"><img alt="" src="${commodity.ibaby_commodity_imgurl}"></td>
												<td class="center">${commodity.ibaby_commodity_name}</td>
												<td class="center">${commodity.ibaby_commodity_price}</td>
												<td class="center">${commodity.ibaby_commodity_detail}</td>
												<td class="center">${commodity.ibaby_create_time}</td>
												<td class="center">${commodity.ibaby_update_time}</td>
												<td style="width: 35px;" class="center">
	                                                <a class='btn btn-mini btn-info' title="编辑" onclick="editE('${commodity.ibaby_commodity_id }');"><i class='icon-edit'></i></a>
                                                </td>
											</tr>
										</c:forEach>
											<tr class="main_info">
												<td colspan="8" class="center"><a class="btn btn-small btn-success" onclick="add();">新增商品</a></td>
											</tr>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="8" class="center">没有商品信息&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="add();">新增商品</a></td>
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
		//编辑商品信息
		function editE(commodity_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>commodity/toEdit.do?commodity_id='+commodity_id;
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
		
		//新增
<<<<<<< .mine
	<%-- 	function add(){
||||||| .r2079
		function add(){
=======
		<%-- 	function add(){
>>>>>>> .r2095
			 top.jzts();
		     var params = $("#commodityForm").serializeArray();  
	         window.location.href='<%=basePath%>commodity/add.do?';
<<<<<<< .mine
		} --%>
		
		
		//新增
		function add(){
			$.ajax({
				type: "POST",
				url: 'schoolCreateOrder/createOrder.do',
				data : {},
				dataType : 'json',
				cache : false,
				success : function(data) {
					
				}
			});
||||||| .r2079
=======
		} --%>
		
		
			function add(){
			$.ajax({
				type: "POST",
				url: 'schoolCreateOrder/createOrder.do',
				data : {},
				dataType : 'json',
				cache : false,
				success : function(data) {
					
				}
			});
>>>>>>> .r2095
		}
	</script>

</body>
</html>


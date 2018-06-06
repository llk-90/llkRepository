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
					<form action="orderApp/list.do" method="post" name="orderAppForm"
						id="orderAppForm">					
						<!-- 检索  -->
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">订单号</th>
									<th class="center">买家名</th>
									<th class="center">收货名</th>
									<th class="center">联系电话</th>
									<th class="center">收货地址</th>
									<th class="center">邮编</th> 
									<th class="center">商品名</th> 
									<th class="center">购买数量</th>
									<th class="center">总价</th>
									<th class="center">订单状态</th>
									<th class="center">更新时间</th>
									<th class="center">发货</th>
									<th class="center">撤单</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty orderAppList}">
										<c:forEach items="${orderAppList}" var="orderApp" varStatus="vs">
											<tr>
												<td class='center'>${orderApp.ibaby_order_id}</td>
												<td class="center">${orderApp.ibaby_userId}</td>
												<td class="center">${orderApp.ibaby_nameInfo}</td>
												<td class="center">${orderApp.ibaby_telInfo}</td>
												<td class="center">${orderApp.ibaby_addressInfo}</td>
												<td class="center">${orderApp.ibaby_postCode}</td>
												<td class="center">${orderApp.ibaby_commodityNam}</td>
												<td class="center">${orderApp.ibaby_commodityNum}</td>
												<td class="center">${orderApp.ibaby_commoditySum}</td>
												<td class="center">${orderApp.ibaby_commodityStat}</td>
												<td class="center">${orderApp.ibaby_commodityDatetime}</td>
												<td style="width: 35px;" class="center">
	                                                <a class='btn btn-mini btn-info' title="发货" onclick="sends('${orderApp.ibaby_order_id }');"><i class='icon-edit'></i></a>
                                                </td>
                                                <td style="width: 35px;" class="center">
	                                                <a class='btn btn-mini btn-info' title="撤单" onclick="cancel('${orderApp.ibaby_order_id }');"><i class='icon-edit'></i></a>
                                                </td>
                                                <td style="width: 35px;" class="center">
	                                               <a class='btn btn-mini btn-info' title="编辑" onclick="editE('${orderApp.ibaby_order_id }');"><i class='icon-edit'></i></a>
                                                </td>
                                                
											</tr>
										</c:forEach>
										<tr class="main_info">
											<td colspan="14" class="center"><a class="btn btn-small btn-success" onclick="add();">新增订单</a></td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="14" class="center">没有订单信息&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="add();">新增订单</a></td>
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
		//发货
		function sends(orderId){
			top.jzts();
		     if(confirm("确定要发货吗？")){
	         	window.location.href='<%=basePath%>orderApp/send.do?ibaby_order_id=' + orderId;
			 }
		}
		//撤单
		function cancel(orderId){
			top.jzts();
		     if(confirm("确定要撤销订单吗？")){
	         	window.location.href='<%=basePath%>orderApp/cancel.do?ibaby_order_id=' + orderId;
		     }
		}
		
		
		function editE(orderId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 //调用控制层的toEdit方法
			 diag.URL = '<%=basePath%>orderApp/toEdit.do?ibaby_order_id='+orderId ;
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
		function add(){
			 top.jzts();
	         window.location.href='<%=basePath%>orderApp/add.do?';
		}
	</script>

</body>
</html>


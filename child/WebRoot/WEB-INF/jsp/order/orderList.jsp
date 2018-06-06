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
					<form action="order/list.do" method="post" name="orderForm" id="orderForm">
						<table>
							<tr>
								<td>
									<span class="input-icon"> 
										<input autocomplete="off" id="nav-search-input" type="text" name="name" value="${pd.name}" placeholder="这里输入会员姓名" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="co_pay_state" id="co_pay_state" data-placeholder="请选择支付状态" 
										    style="vertical-align: top; width: 150px;">
										<option value=""></option>
										<option value="">全部</option>
										<option value="-1" <c:if test="${pd.co_pay_state  == '-1' }">selected = "true"</c:if>>支付失败</option>
										<option value="0" <c:if test="${pd.co_pay_state  == '0' }">selected = "true"</c:if>>支付中</option>
										<option value="1" <c:if test="${pd.co_pay_state  == '1' }">selected = "true"</c:if> >支付成功</option>
										<option value="2" <c:if test="${pd.co_pay_state  == '2' }">selected = "true"</c:if> >未支付</option>
									</select>
								</td>
								<td style="vertical-align: top;">
									<select class="chzn-select" name="select" id="select" data-placeholder="请选择会员权限"
											style="vertical-align: top; width: 150px;">
									    <option value=""></option>
									    <option value="">全部</option>
										<c:forEach var="item" items="${allAuthName}">
										<option value="${item.c_id}" <c:if test="${item.c_id == pd.select}">selected = "true"</c:if>>${item.c_name}</option>   
										</c:forEach> 
									</select>
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
									<i id="nav-search-icon" class="icon-search"></i></button>
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
									<th class="center">会员</th>
									<th class="center">联系电话</th>
									<th class="center">商品详情</th>
									<th class="center">订单编号</th>
									<th class="center">购买时间</th>
									<th class="center">总金额</th>
									<th class="center">支付状态</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								 <c:choose>
									<c:when test="${not empty orderList}">
											<c:forEach items="${orderList}" var="order" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;">
														<label><input type='checkbox' name='ids' value="${order.co_id}" />
															<span class="lbl"></span>
														</label>
													</td>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td class='center'>${order.name}</td>
													<td class='center'>${order.phone}</td>
													<td class='center'>${order.c_name}</td>
													<td class='center'>${order.co_order_num}</td>
													<td class='center'>${order.date}</td>
													<td class='center'>${order.co_money}</td>
													<td class='center'>${order.state}</td>
												<td class="center" style="width: 100px;">
													<%-- <a class='btn btn-mini btn-info' title="查看详情" onclick="orderDetails('${order.co_id}')" ><i class=' icon-eye-open'></i></a> --%>
													<c:choose>
													<c:when test="${order.co_pay_state == 2 or order.co_pay_state == -1}">
														<a class='btn btn-mini btn-danger' title="线下支付" onclick="offlinePayment('${order.co_id}')"><i>线下支付</i></a>
													</c:when>
													<c:otherwise>
														<a class='btn btn-mini btn-grey' title="线下支付" disabled="disabled" ><i>线下支付</i></a>
													</c:otherwise>
													</c:choose>
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
			$("#orderForm").submit();
		}
		//详情
		function offlinePayment(co_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="线下支付";
			 diag.URL = '<%=basePath%>order/toSee.do?co_id='+co_id;
			 diag.Width = 420;
			 diag.Height = 550;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
			  if(document.getElementsByName('ids')[i].checked){
				  if(str==''){
					  str += document.getElementsByName('ids')[i].value;
				  }else{ 
					  str += ',' + document.getElementsByName('ids')[i].value;
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
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>order/deleteAll.do',
						data : {DATA_IDS : str},
						dataType : 'json',
						cache : false,
						success : function(data) {
							$.each(data.list,function(i,list) {
								nextPage('${page.currentPage}');
							});
						}
					});
					}
				});
			}
		}
	</script>

	<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
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
		
		</script>
</body>
</html>


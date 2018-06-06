<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%>
	</head>
	<script type="text/javascript">
	$(top.hangge());
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
	//新增
	 function add(){ 
		 top.jzts();
		 window.location.href="<%=basePath%>policy/goAdd.do";
	}
	//新增
	 function edit(service_ID){ 
		 top.jzts();
		 window.location.href='<%=basePath%>policy/update.do?URLTO=policy_add&service_ID='+service_ID;
	}
	//检索
		function search() {
			top.jzts();
			$("#Form").submit();
		}
		//批量操作
        function makeAll(msg){
            bootbox.confirm(msg, function(result) {
                if(result) {
                    var str = '';
                    for(var i=0;i < document.getElementsByName('ids').length;i++)
                    {
                          if(document.getElementsByName('ids')[i].checked){
                            if(str=='') str += document.getElementsByName('ids')[i].value;
                            else str += ',' + document.getElementsByName('ids')[i].value;
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
                                url: '<%=basePath%>policy/deleteAll.do?tm='	+ new Date().getTime(),
														data : {
															DATA_IDS : str
														},
														dataType : 'json',
														//beforeSend: validateData,
														cache : false,
														success : function(data) {$.each(data.list,
																function(i,list) {
															nextPage('${page.currentPage}');
															});
														}
													});
										}
									}
								}
							});
		}
	</script>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="policy/list.do" method="post" name="Form" id="Form">
					
						<!-- 检索  -->
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text"
										name="service_NAME" value="${pd.service_NAME }" placeholder="这里输入标题" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="vertical-align: top;"><select  class="chzn-select" name="service_TYPE" id="service_TYPE"
									data-placeholder="请选择类别"  style="vertical-align: top; width: 120px;">
									<option value=""></option>
										<option value="" >请选择</option>
										<option value="0"<c:if test="${pd.service_TYPE  == '0' }">selected = "true"</c:if>>帮助</option>
										<option value="1"<c:if test="${pd.service_TYPE  == '1' }">selected = "true"</c:if>>肥料</option>
										<option value="2"<c:if test="${pd.service_TYPE  == '2' }">selected = "true"</c:if>>防治</option>
								    </select>	
								</td>
								<td style="vertical-align: top;"><button
											class="btn btn-mini btn-light" onclick="search();" title="检索">
											<i id="nav-search-icon" class="icon-search"></i>
										</button></td>
							</tr>
						</table>
						
						<!-- 检索  -->
						<table id="table_report"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center" style="width:130px"><label> <input type="checkbox"
												id="zcheckbox" /><span class="lbl"></span></label></th>
										<th class="center" style="width:130px">序号</th>
										<th class="center" >标题</th>
										<th class="center" >类别</th>
										<th class="center" style="width:150px" >时间</th>
										<th class="center" style="width:150px" >发布状态</th>
										<th class="center" style="width:150px" >操作</th>
									</tr>
								</thead>

								<tbody>

									<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td class="center" style="vertical-align: middle" style="width: 30px;"><label><input
																type='checkbox' name='ids' value="${var.service_ID}" /><span
																class="lbl"></span></label></td>
														<td class="center" style="vertical-align: middle" style="vertical-align: middle" style="width: 30px;">${vs.index+1}</td>
														<td class="center" style="vertical-align: middle">${var.service_NAME}</td>
														<td class="center" style="vertical-align: middle">
														<c:if test="${var.service_TYPE =='0'}">帮助</c:if>
														<c:if test="${var.service_TYPE =='1'}">肥料</c:if>
														<c:if test="${var.service_TYPE =='2'}">防治</c:if> 
														</td>
														<td class="center" style="vertical-align: middle">${var.Create_TIME}</td>
														<td class="center" style="vertical-align: middle">
														<c:if test="${var.state =='1'}">已发布</c:if>
														<c:if test="${var.state == '0'}">未发布</c:if></td>
														<td  class="center" style="vertical-align: middle" style="width: 150px;">
														<a class="btn btn-small btn-info" title="发布" onclick="edit('${var.service_ID}');"><i class="icon-edit"></i></a>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</tbody>
							</table>
							<div class="page-header position-relative">
								<table style="width: 100%;">
								<tr>
								<td style="vertical-align: top;">
									<a class="btn btn-small btn-success" onclick="add();">新增</a> 
									<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i class='icon-trash'></i></a>
								</td>
								<td></td>
								<td style="vertical-align: top;">
								<div class="pagination"
									style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
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
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->

	</body>
</html>


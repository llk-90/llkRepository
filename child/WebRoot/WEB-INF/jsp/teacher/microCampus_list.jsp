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
					<form action="microCampusManage/list.do" method="post" name="microCampusForm"
						id="microCampusForm">		
						<table>
							<tr>
								<td style="vertical-align: top;">
									<span>标题:</span>&nbsp;
									<span><input autocomplete="off" style="width: 130px;" type="text" name="Title" value="${pd.Title }" placeholder="请输入要搜索的标题" />&nbsp;&nbsp;
									</span>
								</td>
								<td style="vertical-align: top;">
									<span>是否置顶:</span>&nbsp;										
									<select name="StateTop" id="StateTop" style="width: 90px;">
										<option value="" selected>请选择</option>
									    <option value="1" <c:if test="${pd.StateTop=='1'}">selected = "true"</c:if>>是</option>
									    <option value="0" <c:if test="${pd.StateTop=='0'}">selected = "true"</c:if>>否</option>
									</select>&nbsp;&nbsp;
								</td>
								<td style="vertical-align: top;">
									<span>类型:</span>&nbsp;										
									<select name="Type" id="Type" style="width: 120px;">
										<option value="${pd.Type }" selected>请选择类型</option>
									    <option value="0" <c:if test="${pd.Type=='0'}">selected = "true"</c:if>>学校介绍</option>
										<option value="1" <c:if test="${pd.Type=='1'}">selected = "true"</c:if>>校园资讯</option>
										<option value="2" <c:if test="${pd.Type=='2'}">selected = "true"</c:if>>校园风采</option>
										<option value="3" <c:if test="${pd.Type=='3'}">selected = "true"</c:if>>学生活动</option>
										<option value="4" <c:if test="${pd.Type=='4'}">selected = "true"</c:if>>教师风采</option>
										<option value="5" <c:if test="${pd.Type=='5'}">selected = "true"</c:if>>招生信息</option>
										<option value="6" <c:if test="${pd.Type=='6'}">selected = "true"</c:if>>家庭教育</option>
									</select>&nbsp;&nbsp;
								</td>
								<td style="vertical-align: top;">
									<span>发布时间:</span>&nbsp;										
									<select name="Time" id="Time" style="width: 140px;">
										<option value="">请选择发布时间</option>
										<option value="week" <c:if test="${pd.Time =='week'}">selected = "true"</c:if>>一周之内</option>
										<option value="month" <c:if test="${pd.Time =='month'}">selected = "true"</c:if>>一个月之内</option>
										<option value="halfYear" <c:if test="${pd.Time =='halfYear'}">selected = "true"</c:if>>半年之内</option>
										<option value="year" <c:if test="${pd.Time =='year'}">selected = "true"</c:if>>一年之内</option>
										<option value="overYear" <c:if test="${pd.Time =='overYear'}">selected = "true"</c:if>>一年以上</option>
									</select>&nbsp;&nbsp;
								</td>
								<td style="vertical-align: top;">
									<span>状态:</span>&nbsp;										
									<select name="InfoState" id="InfoState" style="width: 90px;">
										<option value="">请选择</option>
										<option value="1" <c:if test="${pd.InfoState=='1'}">selected = "true"</c:if>>正常</option>
										<option value="0" <c:if test="${pd.InfoState=='0'}">selected = "true"</c:if>>停用</option>
									</select>&nbsp;&nbsp;
								</td>
								<td style="vertical-align: top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
								</td>
							<td style="vertical-align: top;">
								<button onclick="applyUrl();">url申请</button>	</td>						
							</tr>
							</table>
           
						<div style="margin-top:20px"></div>
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
										<label>
											<input type="checkbox" id="zcheckbox" />
											<span class="lbl"></span>
										</label>
									</th>
									<th class="center">序号</th>
									<th class="center">标题</th>
									<th class="center">内容</th>
									<th class="center">发布时间</th>
									<th class="center">类型</th>
									<th class="center">置顶</th>
									<th class="center">状态</th>
									<th class="center">编辑</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty microCampusList}">
										<c:forEach items="${microCampusList}" var="microCampusList" varStatus="microCampus">
											<tr>
												<td class='center' style="width: 30px;">
													<label>
														<input type='checkbox' name='ids' value="${microCampusList.id}" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class='center' style="width: 30px;">${microCampus.index+1}</td>
												<td class='center'>${microCampusList.title }</td>
												<td class='center' style="width: 60%;">${microCampusList.content }</td>
												<td class='center' ><fmt:formatDate value="${microCampusList.create_time }" pattern="yyyy-MM-dd" /></td>
												<td class='center'>${microCampusList.infoType }</td>
												<td class='center'>${microCampusList.isTop }</td>
												<td class='center'>${microCampusList.infoState }</td>
												<td style="width: 30px;" class="center">
                                                		<a class='btn btn-mini btn-info' title="修改" onclick="editE('${microCampusList.id }');"><i class='icon-edit'></i></a>
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
									<td style="vertical-align: top;">
											<a class="btn btn-small btn-success" onclick="newInfo();">新增</a>
											<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除">
												<i class='icon-trash'></i>
											</a>
									</td>
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
		function search(){
			top.jzts();
			
			var url='microCampusManage/list.do';
			//alert(url);
			$("#microCampusForm").attr('action',url);
			$("#microCampusForm").submit();
			
		}		
		
		function applyUrl(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="生成";
			 diag.URL = '<%=basePath%>weixinplusmicrocampus/applyPath.do';
			 diag.Width = 350;
			 diag.Height = 150;
			 
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
								url: '<%=basePath%>microCampusManage/deleteInfo.do?',
						    	data: {id:str},
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
		
		function newInfo(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>microCampusManage/newInfo.do';
			 diag.Width = 1000;
			 diag.Height = 850;
			 
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

		
		//编辑资讯信息
		function editE(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>microCampusManage/toEdit.do?id='+id;
			 diag.Width = 1000;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage('${page.currentPage}');
				}
				diag.close();
				location.reload(true);
			 };
			 diag.show();
		}
		
	</script>


</body>
</html>


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
					<form action="cardRegistManage/list.do" method="post" name="cardRegistForm"
						id="cardRegistForm">				
						<table>
							<tr>
								<td style="vertical-align: top;">
									<span>学校:</span>&nbsp;
									<span><input autocomplete="off" style="width: 130px;" type="text" name="SchoolName" value="${pd.SchoolName }" placeholder="请输入要搜索的标题" />&nbsp;&nbsp;
									</span>
								</td>
								<td style="vertical-align: top;">
									<span>联系人:</span>&nbsp;
									<span><input autocomplete="off" style="width: 150px;" type="text" name="ContactPerson" value="${pd.ContactPerson }" placeholder="请输入要搜索的联系人" />&nbsp;&nbsp;
									</span>
								</td>
								<td style="vertical-align: top;">
									<span>联系电话:</span>&nbsp;
									<span><input autocomplete="off" style="width: 155px;" type="text" name="phoneNum" value="${pd.phoneNum }" placeholder="请输入要搜索的联系方式" />&nbsp;&nbsp;
									</span>
								</td>
								<td style="vertical-align: top;">
									<span>补卡状态:</span>&nbsp;										
									<select name="replaceStatus" id="replaceStatus" style="width: 90px;">
									    <option value="">请选择</option>
										<option value="0" <c:if test="${pd.replaceStatus=='0'}">selected = "true"</c:if>>已提交</option>
										<option value="1" <c:if test="${pd.replaceStatus=='1'}">selected = "true"</c:if>>制卡中</option>
										<option value="2" <c:if test="${pd.replaceStatus=='2'}">selected = "true"</c:if>>已发卡</option>
										<option value="99" <c:if test="${pd.replaceStatus=='99'}">selected = "true"</c:if>>无效</option>
									</select>&nbsp;&nbsp;
								</td>
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
									<th class="center">序号</th>
									<th class="center">学校</th>
									<th class="center">班级</th>
									<th class="center">联系人</th>
									<th class="center">联系电话</th>
									<th class="center">联系地址</th>
									<th class="center">补卡数量</th>
									<th class="center">补卡状态</th>
									<th class="center">提交时间</th>
									<th class="center">编辑</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty cardRegistlist}">
										<c:forEach items="${cardRegistlist}" var="cardRegistlist" varStatus="cardRegist">
											<tr>
												<td class='center' style="width: 30px;">${cardRegist.index+1}</td>
												<td class='center'>${cardRegistlist.SchoolName }</td>
												<td class='center'>${cardRegistlist.ClassName }</td>
												<td class='center'>${cardRegistlist.contact_person }</td>
												<td class='center'>${cardRegistlist.phone_num }</td>
												<td class='center'>${cardRegistlist.address }</td>
												<td class='center'>${cardRegistlist.card_num }</td>
												<td class='center'>${cardRegistlist.replaceStatus }</td>
												<td class='center' ><fmt:formatDate value="${cardRegistlist.submit_time }" pattern="yyyy-MM-dd" /></td>
												<td style="width: 30px;" class="center">
                                                		<a class='btn btn-mini btn-info' title="修改" onclick="editE('${cardRegistlist.id }');"><i class='icon-edit'></i></a>
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
										<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
									</td>
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
			
			var url='cardRegistManage/list.do';
			//alert(url);
			$("#cardRegistForm").attr('action',url);
			$("#cardRegistForm").submit();
			
		}
		
		//编辑资讯信息
		function editE(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>cardRegistManage/toEdit.do?id='+id;
			 diag.Width = 400;
			 diag.Height = 500;
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


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
					<form action="microCampusBanner/list.do" method="post" name="bannerForm"
						id="bannerForm">				
						<table>
							<tr>
								<td>图片大小最好为1200*350，不易过大&nbsp;&nbsp;<input type="file" title="请选择图片" id="file" multiple accept="image/png,image/jpg,image/gif,image/JPEG"/></td>
								<td>
									<input type="button" id="upJS" value="确定上传" >
								</td>
								
							</tr>
						</table>
						<div style="margin-top:20px"></div>
						<table id="table_report"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">序号</th>
									<th class="center">图片</th>
									<th class="center">迁移地址</th>
									<th class="center">上传时间</th>
									<th class="center">删除</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty bannerList}">
										<c:forEach items="${bannerList}" var="bannerList" varStatus="banner">
											<tr>
												<td class='center' style="width: 30px;">${banner.index+1}</td>
												<td class='center' width="50%"><img src="${bannerList.picture_url}">
												</td>
												<td class='center' ><input type="text" id="moveUrl${bannerList.banner_id}" value="${bannerList.move_url}" disabled />&nbsp;&nbsp;
													<a onclick="Edit(${bannerList.banner_id});"><input type="button" id="edit${bannerList.banner_id}" value="编辑"></input></a>
													<a onclick="Submit(${bannerList.banner_id});"><input type="button" id="submit${bannerList.banner_id}" value="提交" style="display:none;"></input></a></td>
												<td class='center' ><fmt:formatDate value="${bannerList.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td style="width: 30px;" class="center">
                                                	<a onclick="deletebanner(${bannerList.banner_id});"><input type="button" value="删除"></input></a>		
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
		function deletebanner(banner_id){
			window.location.href='<%=basePath%>microCampusBanner/deleteBanner.do?banner_id='+banner_id;
		}

		document.getElementById("upJS").onclick = function() {
			var image = document.getElementById("file").files[0];
			var bb = image.size/1024/1024;
			if (bb > 5) {
				alert ("请上传小于5M的图片");
			} else {
				var fd = new FormData();
				var ajax = new XMLHttpRequest();
				fd.append("picture",document.getElementById("file").files[0]);
				fd.append("moveUrl","http://www.baidu.com");
				ajax.open("POST","/microCampusBanner/addBanner",true);
				ajax.onload = function () {
					window.location.reload(true);
				};
				
				ajax.send(fd);
			}

		}
		
		function Edit(banner_id){
			 $("#moveUrl"+banner_id).attr("disabled",false);
			 $("#edit"+banner_id).hide();
			 $("#submit"+banner_id).show();
		}
		
		function Submit(banner_id){
			var moveurl=$("#moveUrl"+banner_id).val();
			window.location.href='<%=basePath%>microCampusBanner/editMoveUrl.do?banner_id='+banner_id+'&moveUrl='+moveurl;
		}
		
	</script>


</body>
</html>


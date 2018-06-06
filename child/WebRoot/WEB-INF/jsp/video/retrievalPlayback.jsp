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

<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<style type="text/css">
.divcss5 {
	width: 600px;
	height: 200px;
	border: 1px solid #D3D3D3
}
</style>
</head>
<body>

	<div class="container-fluid" id="main-container">
		<table style="width: 100%;">
			<tr>
				<td class="divcss5" style="width: 13%;" valign="top"
					bgcolor="#FFFFFF">
					<div class="accordion-heading">
						<a class="accordion-toggle" style="text-decoration: none;">农场列表</a>
					</div>
					<div style="width: 13%;">
						<ul id="leftTree" class="ztree"></ul>
					</div>
				</td>
				<td style="width: 13%;" valign="top">
				<div style="width: 220px"class="accordion-heading">
						<a class="accordion-toggle" style="text-decoration: none;">设备</a>
					</div>
					<DIV STYLE="width: 13%;">
						 <SELECT
							ID="FORM-FIELD-SELECT-2" MULTIPLE="MULTIPLE"
							style="height: 500px;">
							<OPTION VALUE="1">1号摄像头</OPTION>
							<OPTION VALUE="2">2号摄像头</OPTION>
							<OPTION VALUE="3">3号摄像头</OPTION>
							<OPTION VALUE="4">4号摄像头</OPTION>
						</SELECT>
					</DIV>
				</td>
				<td style="width: 74%;" valign="top">
					<div class="row-fluid">
						<!-- 检索  -->
						<form action="#" method="post" name="userForm" id="userForm">
							<table>
								<tr>
									<td><input class="span10 date-picker"
										name="lastLoginStart" id="lastLoginStart" value=""
										data-date-format="yyyy-mm-dd" readonly="readonly"
										style="width: 88px;" placeholder="开始日期" type="text"></td>
									<td><input class="span10 date-picker" name="lastLoginEnd"
										id="lastLoginEnd" value="" data-date-format="yyyy-mm-dd"
										readonly="readonly" style="width: 88px;" placeholder="到期日期"
										type="text"></td>
									<td style="vertical-align: top;">
										<button class="btn btn-mini btn-light" title="检索">
											<i id="nav-search-icon" class="icon-search"></i>
										</button>
									</td>
								</tr>
							</table>
							<div class="row-fluid" style="border: 1px solid #ddd;">
								<div class="xbox" style="overflow: hidden;" id="videoarea">
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
									<video id="video_control" controls="" height="185" width="300">
										<source type="video/mp4"
											src="http://www.w3school.com.cn/example/html5/mov_bbb.mp4">
									</video>
								</div>
							</div>
						
						</form>
					</div>

				</td>
			</tr>
		</table>
	</div>
	<!--/.fluid-container#main-container-->
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>

	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<!-- 引入 -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
	<SCRIPT type="text/javascript">
		var setting = {
			view : {
				showLine : true
			},
			data : {
				simpleData : {
					enable : true
				}
			}
		};

		function showIconForTree(treeId, treeNode) {
			return !treeNode.isParent;
		};
		var treeObj;
		$(document).ready(function(){
		    var zNodes=[];
		    $.ajax({  
		        async : false,  
		        cache:false,  
		        type: 'POST',  
		        dataType : "json",  
		        url: '<%=basePath%>regioncontroller/listdata.do',
		        error: function () {
		            alert('加载失败');  
		        },  
		        success:function(data){ 
		        	if(data){
		        	for(var i=0;i<data.length;i++){
		        	 zNodes.push ({id:data[i].id, pId:data[i].pId,name:data[i].name,bianma:data[i].bianma});
		        	}
		        	$.fn.zTree.init($("#leftTree"), setting,zNodes);
		            treeObj = $.fn.zTree.getZTreeObj("leftTree"); 
	                treeObj.expandAll(true); 
		        	}
		        }  
			});    
			});
		function treeFrameT() {
			var hmainT = document.getElementById("treeFrame");
			var bheightT = document.documentElement.clientHeight;
			hmainT.style.width = '100%';
			hmainT.style.height = (bheightT - 7) + 'px';
		}
		treeFrameT();
		window.onresize = function() {
			treeFrameT();
		};
	</SCRIPT>
	<script type="text/javascript">
		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
	</script>
</body>
</html>


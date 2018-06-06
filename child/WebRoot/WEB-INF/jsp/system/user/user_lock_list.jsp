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
	<div class="tabbable">
		<ul class="nav nav-tabs">
			<li role="presentation" class="active"><a href="#1"data-toggle="tab">已锁定</a></li>
			<li role="presentation"><a href="#2" data-toggle="tab">被举报</a></li>
			<li role="presentation"><a href="#3" data-toggle="tab">配置</a></li>
		</ul>
		<div class="tab-content">
			<!--产品介绍-->
			<div role="presentation" class="tab-pane active" id="1">
			     <div class="container-fluid" id="main-container">
                    <div id="page-content" class="clearfix">
                        <div class="row-fluid">
                            <form action="user/listUsers.do" method="post" name="userForm"
                                id="userForm">
                                <table id="table_report"
                                    class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th class="center">序号</th>
                                            <th class="center">姓名</th>
                                            <th class="center">联系电话</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty lockUser}">
                                                <c:forEach items="${lockUser}" var="user" varStatus="vs">

                                                    <tr>
                                                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                        <td class='center'>${user.NAME }</td>
                                                        <td class='center'>${user.PHONE}</td>
                                                        <td style="width: 100px;" class="center"><a
                                                            class='btn btn-mini btn-info' title="解除锁定"
                                                            onclick="unlockUser('${user.USER_ID }');">解除锁定
                                                        </a></td>
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
                                            </td>
                                            <td style="vertical-align: top;">
                                                <div class="pagination"
                                                    style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
			</div>
			<div role="presentation" class="tab-pane" id="2">
				<div class="container-fluid" id="main-container">
                    <div id="page-content" class="clearfix">
                        <div class="row-fluid">
                            <form action="user/listUsers.do" method="post" name="userForm"
                                id="userForm">
                                <table id="table_report"
                                    class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th class="center">序号</th>
                                            <th class="center">姓名</th>
                                            <th class="center">联系电话</th>
                                            <th class="center">被举报次数</th>
                                            <th class="center">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty reportedUser}">
                                                <c:forEach items="${reportedUser}" var="user" varStatus="vs">

                                                    <tr>
                                                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                        <td class='center'>${user.NAME }</td>
                                                        <td class='center'>${user.PHONE}</td>
                                                        <td class='center'>${user.reportCount}</td>
                                                        <td style="width: 100px;" class="center"><a
                                                            class='btn btn-mini btn-info' title="锁定"
                                                            onclick="lockUser('${user.USER_ID }');">锁定
                                                        </a></td>
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
                                            </td>
                                            <td style="vertical-align: top;">
                                                <div class="pagination"
                                                    style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
			</div>
			<!--产品评价-->
			<div role="presentation" class="tab-pane" id="3">
				<form class="form-horizontal" action="" method="post">
					<fieldset>
						<div class="control-group">

							<!-- Text input-->
							<label class="control-label" for="input01">临界值</label>
							<div class="controls">
								<input type="text" id="linJie"
								        maxlength="10"
                                        onkeyup="if(this.value.length==0){this.value=1;}else if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
                                        onafterpaste="if(this.value.length==0){this.value=1;}else if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
								placeholder="请输入封号上限" class="input-xlarge" value="${linJie.Value}">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"></label>

							<!-- Button -->
							<div class="controls">
								<button onclick="saveLinJie();" class="btn btn-default btn-small" >保存</button>
							</div>
						</div>

					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<!--             分割线 -->

</body>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<!--提示框-->
<script type="text/javascript">
		$(top.hangge());
		var basepath="<%=basePath%>";

	//检索
// 	function search() {
// 		top.jzts();
// 		$("#userForm").submit();
// 	}
	
	function unlockUser(userId){
		top.jzts();
		$.getJSON("user/unlockUser.do",{
			userId:userId
		},function(result){
			top.jzts();
			location.reload();
// 			nextPage('${page.currentPage}');
		});
	}
	
	function lockUser(userId){
		top.jzts();
		$.getJSON("user/lockUser.do",{
			userId:userId
		},function(result){
			top.jzts();
			location.reload();
// 			nextPage('${page.currentPage}');
		});
	}
	
	function saveLinJie(){
		var linJie = $("#linJie").val();
		top.jzts();
        $.getJSON("user/updateLinJie.do",{
        	value:linJie
        },function(result){
            top.jzts();
            location.reload();
        });
	}
	
	//新增
// 	function add() {
// 		top.jzts();
// 		var diag = new top.Dialog();
// 		diag.Drag = true;
// 		diag.Title = "新增";
// 		diag.URL = basepath + 'user/goAddU.do';
// 		diag.Width = 390;
// 		diag.Height = 620;
// 		diag.CancelEvent = function() { //关闭事件
// 			if (diag.innerFrame.contentWindow.document
// 					.getElementById('zhongxin').style.display == 'none') {
// 				if ('${page.currentPage}' == '0') {
// 					top.jzts();
// 					setTimeout("self.location=self.location", 100);
// 				} else {
// 					nextPage('${page.currentPage}');
// 				}
// 			}
// 			diag.close();
// 		};
// 		diag.show();
// 	}

	//修改
// 	function editUser(user_id) {
// 		top.jzts();
// 		var diag = new top.Dialog();
// 		diag.Drag = true;
// 		diag.Title = "编辑";
// 		diag.URL = basepath + 'user/goEditU.do?USER_ID=' + user_id;
// 		diag.Width = 390;
// 		diag.Height = 400;
// 		diag.CancelEvent = function() { //关闭事件
// 			if (diag.innerFrame.contentWindow.document
// 					.getElementById('zhongxin').style.display == 'none') {
// 				nextPage('${page.currentPage}');
// 			}
// 			diag.close();
// 		};
// 		diag.show();
// 	}

	//删除
// 	function delUser(userId, msg) {
// 		bootbox.confirm("确定要删除[" + msg + "]吗?", function(result) {
// 			if (result) {
// 				top.jzts();
// 				var url = basepath + "user/deleteU.do?USER_ID=" + userId
// 						+ "&tm=" + new Date().getTime();
// 				$.get(url, function(data) {
// 					nextPage('${page.currentPage}');
// 				});
// 			}
// 		});
// 	}

	//批量操作
// 	function makeAll(msg) {

// 		bootbox
// 				.confirm(
// 						msg,
// 						function(result) {
// 							if (result) {
// 								var str = '';
// 								var emstr = '';
// 								var phones = '';
// 								for (var i = 0; i < document
// 										.getElementsByName('ids').length; i++) {
// 									if (document.getElementsByName('ids')[i].checked) {
// 										if (str == '')
// 											str += document
// 													.getElementsByName('ids')[i].value;
// 										else
// 											str += ','
// 													+ document
// 															.getElementsByName('ids')[i].value;

// 										if (emstr == '')
// 											emstr += document
// 													.getElementsByName('ids')[i].id;
// 										else
// 											emstr += ';'
// 													+ document
// 															.getElementsByName('ids')[i].id;

// 										if (phones == '')
// 											phones += document
// 													.getElementsByName('ids')[i].alt;
// 										else
// 											phones += ';'
// 													+ document
// 															.getElementsByName('ids')[i].alt;
// 									}
// 								}
// 								if (str == '') {
// 									bootbox.dialog("您没有选择任何内容!", [ {
// 										"label" : "关闭",
// 										"class" : "btn-small btn-success",
// 										"callback" : function() {
// 											//Example.show("great success");
// 										}
// 									} ]);

// 									$("#zcheckbox").tips({
// 										side : 3,
// 										msg : '点这里全选',
// 										bg : '#AE81FF',
// 										time : 8
// 									});

// 									return;
// 								} else {
// 									top.jzts();
// 									$
// 											.ajax({
// 												type : "POST",
// 												url : basepath
// 														+ 'user/deleteAllU.do?tm='
// 														+ new Date().getTime(),
// 												data : {
// 													USER_IDS : str
// 												},
// 												dataType : 'json',
// 												//beforeSend: validateData,
// 												cache : false,
// 												success : function(data) {
// 													$
// 															.each(
// 																	data.list,
// 																	function(i,
// 																			list) {
// 																		nextPage('${page.currentPage}');
// 																	});
// 												}
// 											});
// 								}
// 							}
// 						});
// 	}
</script>

<script type="text/javascript">
	$(function() {

		//日期框
// 		$('.date-picker').datepicker();

		//下拉框
// 		$(".chzn-select").chosen();
// 		$(".chzn-select-deselect").chosen({
// 			allow_single_deselect : true
// 		});

		//复选框
// 		$('table th input:checkbox').on(
// 				'click',
// 				function() {
// 					var that = this;
// 					$(this).closest('table').find(
// 							'tr > td:first-child input:checkbox').each(
// 							function() {
// 								this.checked = that.checked;
// 								$(this).closest('tr').toggleClass('selected');
// 							});
// 				});
	});
</script>
</html>


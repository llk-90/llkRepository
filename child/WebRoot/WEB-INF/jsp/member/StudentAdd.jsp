<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<meta charset="utf-8" />
<title></title>

<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />

<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />

<link rel="stylesheet" href="static/css/datepicker.css" />
<!-- 日期框 -->
<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>css/zTreeStyle/zTreeStyle.css" type="text/css" />
<style type="text/css">
div.zTreeDemoBackground {
	width: 250px;
	height: 362px;
	text-align: left;
}

ul.ztree {
	margin-top: 10px;
	border: 1px solid #617775;
	background: #ffffff;
	width: 220px;
	height: 360px;
	overflow-y: scroll;
	overflow-x: auto;
}

ul  li {
	list-style-type: none
}

.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="static/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#s_stu_no").val()==""){
				$("#s_stu_no").tips({
					side:3,
		            msg:'请输入学生学号',
		            bg:'#FF5080',
		            time:2
		        });
				$("#s_stu_no").focus();
				return false;
			}
			var $stuNo = $("#s_stu_no");
            var stuNoVal = $stuNo.val();
            var hasStuNo = false;
            $.ajax({
                type : "POST",
                url : 'StudentM/hasStuNo.do',
                data : {
                    s_stu_no : stuNoVal
                },
                dataType : 'json',
                cache : false,
                async : false,
                success : function(data) {
                    if (data&&data.hasStuNo == true) {
                        $stuNo.tips({
                            side : 3,
                            msg : "学生号已存在",
                            bg : '#FF5080',
                            time : 2
                        });
                        hasStuNo=true;
                    }
                }
            });
            if(hasStuNo){
                $stuNo.focus();
                return;
            }
			
			if($("#s_name").val()==""){
				$("#s_name").tips({
					side:3,
		            msg:'请输入学生姓名',
		            bg:'#FF5080',
		            time:3
		        });
				$("#s_name").focus();
				return false;
			}
			
			if($("#s_sex").val()==""){
				$("#s_sex").tips({
					side:3,
		            msg:'请选择学生性别',
		            bg:'#FF5080',
		            time:3
		        });
				$("#s_sex").focus();
				return false;
			}
			
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入家长姓名',
		            bg:'#FF5080',
		            time:3
		        });
				$("#NAME").focus();
				return false;
			}
			
			var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
			if($("#PHONE").val()==""){
				
				$("#PHONE").tips({
					side:3,
		            msg:'请输入电话号码',
		            bg:'#FF5080',
		            time:3
		        });
				$("#PHONE").focus();
				return false;
			}
			
			if (!$("#PHONE").val()
					.match(/^(0[0-9]{2,3}-)([2-9][0-9]{6,7})$|(^(13[0-9]|147|15[0-9]|17[0|7|8]|18[0-9])[0-9]{8}$)/)) {
				$("#PHONE").tips({
					side : 3,
					msg : "请输入11位的手机号或座机号(格式：区号-座机号)",
					bg : '#FF5080',
					time : 2
				});
				$("#PHONE").focus();
				return false;
			}
			var $phone = $("#PHONE");
            var phoneVal = $phone.val();
            var exist = false;
            $.ajax({
                type : "POST",
                url : 'teacher/checkPhone.do',
                data : {
                    PHONE : phoneVal,
                    phone_old : ''
                },
                dataType : 'json',
                cache : false,
                async : false,
                success : function(data) {
                    if (data.result == "exist") {
                        $phone.tips({
                            side : 3,
                            msg : "手机号已存在",
                            bg : '#FF5080',
                            time : 2
                        });
                        exist=true;
                    }
                }
            });
            if(exist){
                $phone.focus();
                return;
            }
			 if ($("#PASSWORD").val() == "") {
					$("#PASSWORD").tips({
						side : 3,
						msg : '请输入密码',
						bg : '#FF5080',
						time : 2
					});
					$("#PASSWORD").focus();
					return false;
				}
				if ($("#PASSWORD").val() != ""
						&& !$("#PASSWORD").val().match(/^([a-zA-Z0-9!@#$%?_]){6,100}$/)) {
					$("#PASSWORD").tips({
						side : 3,
						msg : "密码由6位以上字母、数字或符号组成",
						bg : '#FF5080',
						time : 2
					});
					$("#PASSWORD").focus();
					return false;
				}
				if ($("#PASSWORD_CONFIRM").val() == "") {
					$("#PASSWORD_CONFIRM").tips({
						side : 3,
						msg : '请输入确认密码',
						bg : '#FF5080',
						time : 2
					});
					$("#PASSWORD_CONFIRM").focus();
					return false;
				}
				if ($("#PASSWORD_CONFIRM").val() != $("#PASSWORD").val()) {
					$("#PASSWORD_CONFIRM").tips({
						side : 3,
						msg : '两次输入密码不一致',
						bg : '#FF5080',
						time : 2
					});
					$("#PASSWORD_CONFIRM").focus();
					return false;
				}
			
			if($("#EMAIL").val()==""){
				
				$("#EMAIL").tips({
					side:3,
		            msg:'请输入邮箱',
		            bg:'#FF5080',
		            time:3
		        });
				$("#EMAIL").focus();
				return false;
			}else if(!userForm.EMAIL.value.match(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/)){
				$("#EMAIL").tips({
					side:3,
		            msg:'邮箱格式不正确',
		            bg:'#FF5080',
		            time:3
		        });
				$("#EMAIL").focus();
				return false;
			} 
			
			if($("#s_addr").val()==""){
				$("#s_addr").tips({
					side:3,
		            msg:'请输入家庭住址',
		            bg:'#FF5080',
		            time:3
		        });
				$("#s_addr").focus();
				return false;
			}
// 			device_no
			if($("#device_no").val()==""){
				$("#device_no").tips({
					side:3,
		            msg:'请输入学生卡号',
		            bg:'#FF5080',
		            time:3
		        });
				$("#device_no").focus();
				return false;
			}else if(!$("#device_no").val().match(/^[0-9a-zA-Z]*$/)){
				$("#device_no").tips({
                    side:3,
                    msg:'学生卡号格式不正确',
                    bg:'#FF5080',
                    time:3
                });
                $("#device_no").focus();
                return false;
			}
			
			
				$("#userForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			
		}
		
	</script>

</head>
<body>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute; z-index: 111111;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 208px; height: 200px;"></ul>
	</div>
	<form action="StudentM/${msg}.do" name="userForm" id="userForm" method="post">
		<div id="zhongxin">
			<table class="table ">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生学号：</td>
					<td>
						<input type="text" name="s_stu_no" id="s_stu_no" onkeyup="this.value=this.value.replace(/[^\w]/ig,'')" placeholder="这里输入学生学号" maxlength="32" title="学生学号" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">所属班级：</td>
					<td>
						<input type="text" id="citySel" style="width: 208px;" value="${pd.className}" placeholder="所属班级" readonly class="form-control  onlyNum"> <input type="hidden" name="s_zone_id" id="s_zone_id" value="${pd.classId}" />
					</td>

				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生姓名：</td>
					<td>
						<input type="text" name="s_name" id="s_name" placeholder="这里输入学生姓名" maxlength="32" title="学生姓名" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生性别：</td>
					<td>
						<select class="chzn-select" name="s_sex" id="s_sex" data-placeholder="请选择学生性别" style="vertical-align: top;" onchange="tips();" <c:if test="${pd.service_ID != null }">disabled="disabled"</c:if>>
							<option value=""></option>
							<option value="0" <c:if test="${pd.s_sex  == '0' }">selected = "true"</c:if>>男</option>
							<option value="1" <c:if test="${pd.s_sex  == '1' }">selected = "true"</c:if>>女</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生家长：</td>
					<td>
						<input type="text" name="NAME" id="NAME" placeholder="这里输入学生家长" maxlength="32" title="学生家长" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">家长电话：</td>
					<td>
						<input type="text" name="PHONE" id="PHONE" onkeyup="this.value=this.value.replace(/\D/g,'')" class="gbkOrAbc" placeholder="这里输入家长电话" maxlength="32" title="家长电话" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">密&#12288;&#12288;码：</td>
					<td>
						<input type="password" name="PASSWORD" id="PASSWORD" value="${pd.PASSWORD}" maxlength="32" placeholder="这里输入密码" title="密码" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">确认密码：</td>
					<td>
						<input type="password" name="PASSWORD_CONFIRM" id="PASSWORD_CONFIRM" value="${pd.PASSWORD_CONFIRM}" maxlength="32" placeholder="再次输入密码" title="密码" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">家长邮箱：</td>
					<td>
						<input type="text" name="EMAIL" id="EMAIL" placeholder="这里输入家长邮箱" maxlength="32" title="家长邮箱" />
					</td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">家庭住址：</td>
					<td>
						<input type="text" name="s_addr" id="s_addr" placeholder="这里输入家庭住址" maxlength="32" title="家庭住址" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">卡&#12288;&#12288;号：</td>
					<td>
						<input type="text" name="device_no" id="device_no" onkeyup="this.value=this.value.replace(/[^\w]/ig,'')" placeholder="这里输入卡号" maxlength="100" title="卡号" />
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="center" colspan="4">
						<a class="btn btn-small btn-success" onclick="save();">保存</a>
						<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>

	</form>


	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript">
		$(function() {
			//日期框
			$(".farmtree").click(function() {
				showMenu();
			});
			var setting = {
					check : {
						enable : true,	
						chkStyle: "radio",
						radioType: "all"
					},
					view : {
						dblClickExpand : false
					},
					data : {
						simpleData : {
							enable : true
						}
					},
					callback : {
						beforeClick : beforeClick,
						onCheck : onCheck,
						beforeExpand : beforeExpand,
						onExpand : onExpand,
						onClick : onClickT
					}
				};
				var zNodes=[];
				$.ajax({  
			        async : false,  
			        cache:false,  
			        type: 'POST',  
			        dataType : "json",  
			        url: '<%=basePath%>regioncontroller/listdata.do',
				error : function() {
					alert('加载失败');
				},
				success : function(data) {
					if (data) {
						for (var i = 0; i < data.length; i++) {
							if (data[i].zType != 4) {
								zNodes.push({
									id : data[i].id,
									pId : data[i].pId,
									name : data[i].name,
									bianma : data[i].bianma,
									nocheck : true
								});
							} else {
								zNodes.push({
									id : data[i].id,
									pId : data[i].pId,
									name : data[i].name,
									bianma : data[i].bianma
								});
							}
						}
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
						$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
					}
				}
			});
			function beforeClick(treeId, treeNode) {
				if (treeNode.level == 0 || treeNode.level == 1) {
					return false;
				}
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}

			function onCheck(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
						.getCheckedNodes(true), v = "", kid = "";
				for (var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].name + ",";
					kid += nodes[i].id + ",";
				}
				if (v.length > 0)
					v = v.substring(0, v.length - 1);
				if (kid.length > 0)
					kid = kid.substring(0, kid.length - 1);
				var cityObj = $("#citySel");
				$("#s_zone_id").val(kid);
				cityObj.attr("value", v);
			}
			var curExpandNode = null;
			function beforeExpand(treeId, treeNode) {
				var pNode = curExpandNode ? curExpandNode.getParentNode()
						: null;
				var treeNodeP = treeNode.parentTId ? treeNode.getParentNode()
						: null;
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
					if (treeNode !== treeNodeP.children[i]) {
						zTree.expandNode(treeNodeP.children[i], false);
					}
				}
				while (pNode) {
					if (pNode === treeNode) {
						break;
					}
					pNode = pNode.getParentNode();
				}
				if (!pNode) {
					singlePath(treeNode);
				}
			}
			function singlePath(newNode) {
				if (newNode === curExpandNode)
					return;
				if (curExpandNode && curExpandNode.open == true) {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					if (newNode.parentTId === curExpandNode.parentTId) {
						zTree.expandNode(curExpandNode, false);
					} else {
						var newParents = [];
						while (newNode) {
							newNode = newNode.getParentNode();
							if (newNode === curExpandNode) {
								newParents = null;
								break;
							} else if (newNode) {
								newParents.push(newNode);
							}
						}
						if (newParents != null) {
							var oldNode = curExpandNode;
							var oldParents = [];
							while (oldNode) {
								oldNode = oldNode.getParentNode();
								if (oldNode) {
									oldParents.push(oldNode);
								}
							}
							if (newParents.length > 0) {
								zTree.expandNode(oldParents[Math
										.abs(oldParents.length
												- newParents.length) - 1],
										false);
							} else {
								zTree.expandNode(
										oldParents[oldParents.length - 1],
										false);
							}
						}
					}
				}
				curExpandNode = newNode;
			}

			function onExpand(event, treeId, treeNode) {
				curExpandNode = treeNode;
			}

			function onClickT(e, treeId, treeNode) {
				if (treeNode.level == 0 || treeNode.level == 1) {
					return false;
				}
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.expandNode(treeNode, null, null, null, true);
			}
			function showMenu() {
				var cityObj = $("#citySel");
				var cityOffset = $("#citySel").offset();
				$("#menuContent").css({
					left : cityOffset.left + "px",
					top : cityOffset.top + cityObj.outerHeight() + "px"
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDown);
			}
			function hideMenu() {
				$("#menuContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function onBodyDown(event) {
				if (!(event.target.id == "menuBtn"
						|| event.target.id == "citySel"
						|| event.target.id == "menuContent" || $(event.target)
						.parents("#menuContent").length > 0)) {
					hideMenu();
				}
			}
		});
	</script>
	<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();
			$('.date-picker1').datepicker({
				language : 'zh-CN',
				format : 'yyyy-mm-dd',
				weekStart : 1,
				todayBtn : 'linked',
				autoclose : 1,
				todayHighlight : 1,
			});
		});
		//汉字或英文字符校验
		$('.gbkOrName').keyup(
				function() {
					var c = $(this);
					if (/[^a-zA-Z\u4E00-\u9FA5]/.test(c.val())) {//替换其他字符
						var temp_amount = c.val().replace(
								/[^a-zA-Z\u4E00-\u9FA5]/g, '');
						$(this).val(temp_amount);
					}
				});

		//字母数字或者下划线
		$('.gbkOrAbc').keyup(
				function() {
					var c = $(this);
					if (/[^a-zA-Z0-9! @ # $ % ?_]/.test(c.val())) {//替换其他字符
						var temp_amount = c.val().replace(
								/[^a-zA-Z0-9! @ # $ % ?_]/g, '');
						$(this).val(temp_amount);
					}
				});
	</script>
</body>
</html>
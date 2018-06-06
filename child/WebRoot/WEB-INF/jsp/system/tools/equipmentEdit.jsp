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
<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" href="<%=basePath%>css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
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
<script type="text/javascript"
	src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="static/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#equ_name").val()==""){
				$("#equ_name").tips({
					side:3,
		            msg:'请输入设备名称',
		            bg:'#FF5080',
		            time:2
		        });
				$("#equ_name").focus();
				return false;
			}
			if($("#equ_number").val()==""){
				$("#equ_number").tips({
					side:3,
		            msg:'输入设备编号',
		            bg:'#FF5080',
		            time:2
		        });
				
				$("#equ_number").focus();
				return false;
			}
			/* if($("#citySel").val()==""){
				$("#citySel").tips({
					side:3,
		            msg:'请选择所属垮',
		            bg:'#FF5080',
		            time:3
		        });
				$("#citySel").focus();
				return false;
			} */
			if($("#equ_manufactor").val()==""){
				$("#equ_manufactor").tips({
					side:3,
		            msg:'请输入厂家',
		            bg:'#FF5080',
		            time:3
		        });
				$("#equ_manufactor").focus();
				return false;
			}	
				$("#userForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
		}
		
	</script>

</head>
<body>
	<!-- <div id="menuContent" class="menuContent"
		style="display: none; position: absolute; z-index: 111111;">
		<ul id="treeDemo" class="ztree"
			style="margin-top: 0; width: 208px; height: 200px;"></ul>
	</div> -->
	<form action="environmentM/${msg}.do" name="userForm" id="userForm"
		method="post">
		<input type="hidden" name="equ_id" id="equ_id" value="${pd.equ_id}" />
		<input type="hidden" name="treeid" id="treeid" value="${pd.area_kid}"/>
		<div id="zhongxin">
			<table class="table ">
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生姓名：</td>
					<td><input type="text" name="equ_name" id="equ_name"
						value="${pd.equ_name}" placeholder="请输入设备名称" maxlength="32"
						title="设备名称" /></td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生年龄：</td>
					<td><input type="text" name="equ_number" id="equ_number"
						value="${pd.equ_number}"  placeholder="请输入设备编号" maxlength="32"
						class="gbkOrAbc" title="设备编号" /></td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">家庭住址：</td>
					<td><input type="text" name="equ_manufactor"
						id="equ_manufactor" value="${pd.equ_manufactor}" 
						placeholder="请输入生产厂家" maxlength="32" title="生产厂家" /></td>
					<td style="width: 80px; text-align: left; padding-top: 13px;">班级信息：</td>
					<td><input type="text" id="citySel" style="width: 208px;"
						value="${pd.kname}" placeholder="所属垮" readonly
						class="form-control farmtree onlyNum"></td>
				</tr>
				<tr>
					<td class="center" colspan="4"><a
						class="btn btn-small btn-success" onclick="save();">保存</a> <a
						class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br />
			<br />
			<br />
			<br />
			<br />
			<img src="static/images/jiazai.gif" /><br />
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
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript">
		<%-- $(function() {
			//日期框
			$(".farmtree").click(function() {
				showMenu();
			});
			var setting = {
					check : {
						enable : true,
						chkboxType : {
							"Y" : "",
							"N" : ""
						}
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
							if (data[i].jb == "0" || data[i].jb == "1") {
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
				$("#equ_id").val(kid);
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
			var treeDemos=$.fn.zTree.getZTreeObj("treeDemo");
			var treeids=$("#treeid").val();
			var node = treeDemos.getNodeByParam("id",treeids);
			node.checked = true; 
			treeDemos.updateNode(node); 
		}); --%>
	</script>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
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
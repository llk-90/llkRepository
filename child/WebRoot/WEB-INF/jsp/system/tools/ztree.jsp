<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>

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
<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript">document.oncontextmenu=function(e){return false;}</script>
<style type="text/css">
div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #DDD;
	text-align: left;
}

div#rMenu ul li {
	width: 100%;
	cursor: pointer;
	list-style: none outside none;
	border: 1px solid #D3D3D3;
	background-color: #FFFFFF;
}
</style>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<table style="width: 100%;" border="0">
			<tr>
				<td style="width: 15%;" valign="top" bgcolor="#F9F9F9">
					<div style="width: 15%;">
						<ul id="leftTree" class="ztree"></ul>
					</div>
				</td>
				<td style="width: 85%;" valign="top"><iframe name="treeFrame"
						id="treeFrame" frameborder="0"
						src="<%=basePath%>/StudentM/stlist.do"
						style="margin: 0 auto; width: 100%; height: 100%;"></iframe></td>
			</tr>
		</table>

		<div id="rMenu">
			<ul style="width: 100%; height: 100%; margin: 1px 1px 1px 1px;">
				<li id="m_add" onclick="addTreeNode();">增加节点</li>
				<li id="m_edit" onclick="editTreeNode();">编辑节点</li>
				<li id="m_del" onclick="removeTreeNode();">删除节点</li>
			</ul>
		</div>
		<form action="" id="forms"
			method="post" style="display: none;">
			<input type="hidden" name="banji" id="banji" /> <input type="hidden"
				name="schoolid" id="schoolid" /> <input type="hidden" name="areaid"
				id="areaid" /> <input type="hidden" name="nianji" id="nianji" />
		</form>
		<!--/.fluid-container#main-container-->
	</div>

	<script type="text/javascript" src="static/js/jquery.tips.js"></script>

	<script type="text/javascript">
	var setting = {
			data: {
				simpleData: {
					enable: true,
					idKey:"id",
				    pIdKey:"pId",
				    rootPId:null
				}
			},
			view: {
				showLine: true
				},
			callback: {
					onRightClick: OnRightClick,
					onClick: OnClick
				}
	};
	function showIconForTree(treeId, treeNode) {
		return !treeNode.isParent;
	};
	function OnRightClick(event, treeId, treeNode) {
		 if(treeNode==null){
			return false;
		 } 
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			treeObj.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY,treeNode.level);
		} else if (treeNode && !treeNode.noR) {
			treeObj.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY,treeNode.level);
		} 
	}
	function OnClick(event, treeId, treeNode){
		if(treeNode.level==0){
		var hmainT = document.getElementById("treeFrame");
		hmainT.src="<%=basePath%>/StudentM/stlist.do?fid="+treeNode.id;
		}
		if(treeNode.level==1){
			var hmainT = document.getElementById("treeFrame");
			hmainT.src="<%=basePath%>/StudentM/stlist.do?pid="+treeNode.id;
		}
		if(treeNode.level==2){
			var hmainT = document.getElementById("treeFrame");
			hmainT.src="<%=basePath%>/StudentM/stlist.do?kid="+treeNode.id;
		}
		if(treeNode.level==3){
			var hmainT = document.getElementById("treeFrame");
			hmainT.src="<%=basePath%>/StudentM/stlist.do?bid="+treeNode.id;
		}
	}
	function showRMenu(type, x, y,levels) {
		$("#rMenu ul").show();
		if (type=="root") {
			$("#m_del").hide();
			$("#m_edit").hide();
		} else {
			if(levels==3){
				$("#m_add").hide();
				$("#m_del").show();
				$("#m_edit").show();	
			}
			if(levels==1){
				$("#m_add").show();
				$("#m_del").show();
				$("#m_edit").show();	
			}
			if(levels==2){
				$("#m_add").show();
				$("#m_del").show();
				$("#m_edit").show();	
			}
			if(levels==0){
				$("#m_add").show();
				$("#m_del").hide();
				$("#m_edit").hide();	
			}
		}
		rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	var addCount = 1;
	function addTreeNode() {
		hideRMenu();
		var nodes = treeObj.getSelectedNodes()[0];
		if(nodes.level==3){
			return;
		}else{
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="增加节点";
			 diag.URL = '<%=basePath%>StudentM/toAdd.do?parentid='+nodes.id+'&level='+(nodes.level+1);
			diag.Width = 350;
			diag.Height = 260;
			diag.CancelEvent = function() { //关闭事件
				window.location.href = window.location.href;
				top.jzts();
				diag.close();
			};
			diag.show();
		}
	}
	function editTreeNode(){
		hideRMenu();
		var nodes = treeObj.getSelectedNodes()[0];
		top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑节点";
		 diag.URL = '<%=basePath%>StudentM/toEdit.do?id='+nodes.id+'&name='+encodeURI(nodes.name)+'&z_order='+nodes.zOrder+'&z_type='+nodes.zType;
		diag.Width = 350;
		diag.Height = 260;
		diag.CancelEvent = function() { //关闭事件
			window.location.href = window.location.href;
			top.jzts();
			diag.close();
		};
		diag.show();
	}
		//使用了递归，得到叶子节点的数据
	 function getAllChildrenNodes(treeNode,result){ 
		    if (treeNode.isParent) { 
		      var childrenNodes = treeNode.children; 
		      if (childrenNodes) { 
		          for (var i = 0; i < childrenNodes.length; i++) {
		               if(childrenNodes[i].isParent){
		            result = getAllChildrenNodes(childrenNodes[i], result); 
		            }else{
		            	  if(childrenNodes[i].level==3) {
		            result += ',' + childrenNodes[i].id; 
		            	  }
		        	  }
		          } 
		      } 
		  } 
		  return result; 
		} 
		

		function deleteZone(node){
			var zoneId = node.id;
			var nodeName = node.name;
			console.info({id:zoneId,name:nodeName});
			bootbox.confirm("确定要删除["+nodeName+"]节点吗?", function(result) {
                if(result) {
                    var url = "<%=basePath%>StudentM/deleteZone.do";
                    $.getJSON(url,{
                    	z_id:zoneId
                    },function(data){
                        if(data.success==false){
                            bootbox.confirm("请先删除节点下相应的信息。", function() {});
                        }else if(data.success==true){
                        	treeObj.removeNode(node);
                        }
                    });
                }
            });
		}
		
	function removeTreeNode() {
		     hideRMenu();
		     var nodes= treeObj.getSelectedNodes();//获取选中的节点
		     if (nodes && nodes.length>0){
		    	 deleteZone(nodes[0]);
		     }
	}
	var treeObj,rMenu;
	$(document).ready(function(){
	    var zNodes=[];
	    $.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',  
	        dataType : "json",  
	        url: '<%=basePath%>StudentM/listdata.do',
	        error: function () {
	            alert('加载失败');  
	        },  
	        success:function(data){ 
	        	if(data){
	        	for(var i=0;i<data.length;i++){
	        	 zNodes.push ({id:data[i].id, pId:data[i].pId,name:data[i].name,zOrder:data[i].zOrder,zType:data[i].zType});
	        	}
	        	$.fn.zTree.init($("#leftTree"), setting,zNodes);
	            treeObj = $.fn.zTree.getZTreeObj("leftTree"); 
                treeObj.expandAll(true); 
                rMenu = $("#rMenu");
	        	}
	        }  
		});    
		});
			function treeFrameT(){
				var hmainT = document.getElementById("treeFrame");
				var bheightT = document.documentElement.clientHeight;
				hmainT .style.width = '100%';
				hmainT .style.height = (bheightT-7) + 'px';
			}
			treeFrameT();
			window.onresize=function(){  
				treeFrameT();
			};
	function adds(){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="增加顶级节点";
		 diag.URL = '<%=basePath%>StudentM/toAdd.do?parentid=0&level=0';
			diag.Width = 350;
			diag.Height = 260;
			diag.CancelEvent = function() { //关闭事件
				window.location.href = window.location.href;
				top.jzts();
				diag.close();
			};
			diag.show();
		}
	</script>
	<script type="text/javascript">
		$(top.hangge());
		function treeFrameT() {
			var hmainT = document.getElementById("treeFrame");
			var bheightT = document.documentElement.clientHeight;
			hmainT.style.width = '100%';
			hmainT.style.height = (bheightT - 7) + 'px';
		}
		treeFrameT();
		window.onresize = function() {
			treeFrameT();
		}
	</script>
</body>
</html>


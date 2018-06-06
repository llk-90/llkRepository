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
		<table style="width: 100%;">
			<tr>
				<td class="divcss5" style="width: 15%;" valign="top"
					bgcolor="#FFFFFF">
					<div class="accordion-heading">
						<a class="accordion-toggle" style="text-decoration: none;">区域列表<i
							class="icon-plus-sign icon-on-right" style="float: right;"
							title="增加顶级节点" onclick="adds();"></i></a>

					</div>
					<div style="width: 15%;">
						<ul id="leftTree" class="ztree"></ul>
					</div>
				</td>
				<td style="width: 85%;" valign="top">
					<div class="row-fluid">
						<!-- 检索  -->
						<form action="ZoneM/list.do" method="post" name="userForm"
							id="userForm">
							<input type="hidden" name="kid" class="classk" /> <input
								type="hidden" name="pid" class="classp" /> <input type="hidden"
								name="fid" class="classf" /><input type="hidden" name="bid" class="classb" />
							<table>	
								<tr>
									<td><span class="input-icon"> <input
											autocomplete="off" id="nav-search-input" type="text"
											name="equ_name" value="${pd.equ_name }"
											placeholder="这里输入名称" /> <i id="nav-search-icon"
											class="icon-search"></i>
									</span></td>
									<td style="vertical-align: top;"><button
											class="btn btn-mini btn-light" onclick="search();" title="检索">
											<i id="nav-search-icon" class="icon-search"></i>
										</button></td>
										<!-- <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="imp();" title="导入信息"><i id="nav-search-icon" class="icon-upload-alt"></i></a></td> -->
								</tr>
							</table>
							<!-- 检索  -->
							<table id="table_report"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center"><label> 
										<input type="checkbox"  id="zcheckbox"  onclick="$('[name=ids]').attr('checked',this.checked)" />
										<span class="lbl"></span></label></th>
										<th>序号</th>
										<th>名称</th>
										<th>归属</th>
										<th>编码</th>
										<th>备注</th>
										<th class="center">操作</th>
									</tr>
								</thead>
								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:forEach items="${varList}" var="varList" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;vertical-align: middle;"><label><input
														type='checkbox' name='ids' value="${varList.equ_id}" /><span
														class="lbl"></span></label></td>		
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td>齐鲁软件园</td>
													<td>高新区</td>
													<td>1200000021</td>
													<td>comehere</td>
													<td style="width: 30px;" class="center">
                                                <a class='btn btn-mini btn-info' title="编辑" onclick="editE('${varList.equ_id }');"><i class='icon-edit'></i></a></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="main_info">
												<td colspan="100" class="center">没有相关数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>

							<div class="page-header position-relative">
								<table style="width: 100%;">
									<tr>
										<td style="vertical-align: top;"><a
											class="btn btn-small btn-success" onclick="add();">新增</a> <a
											class="btn btn-small btn-danger"
											onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
												class='icon-trash'></i></a></td>
										<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</td>
			</tr>
		</table>


		<div id="rMenu">
			<ul style="width: 100%; height: 100%; margin: 1px 1px 1px 1px;">
				<li id="m_add" onclick="addTreeNode();">增加节点</li>
				<li id="m_edit" onclick="editTreeNode();">编辑节点</li>
				<li id="m_del" onclick="removeTreeNode();">删除节点</li>
			</ul>
		</div>
		<form action="<%=basePath%>regioncontroller/delete.do" id="forms"
			method="post" style="display: none;">
			<input type="hidden" name="zd_id" id="zd_ids" /> 
			<input type="hidden" name="level" id="levels" />
			<input type="hidden" name="strid" id="strids" />
		</form>
	</div>
	<!--/.fluid-container#main-container-->


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
			$(".classf").val(treeNode.id);
			}
			if(treeNode.level==1){
				$(".classp").val(treeNode.id);
			}
			if(treeNode.level==2){
				$(".classk").val(treeNode.id);
			}
			if(treeNode.level==3){
				$(".classb").val(treeNode.id);
			}
			$("#userForm").submit();
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
				}else{
					$("#m_add").show();
					$("#m_del").show();
					$("#m_edit").show();
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
				 diag.URL = '<%=basePath%>regioncontroller/toAdd.do?parentid='+nodes.id+'&level='+(nodes.level+1);
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
			 diag.URL = '<%=basePath%>regioncontroller/toEdit.do?id='+nodes.id+'&name='+nodes.name+'&bianma='+nodes.bianma+'&z_type='+nodes.z_type;
			diag.Width = 350;
			diag.Height = 260;
			diag.CancelEvent = function() { //关闭事件
				window.location.href = window.location.href;
				top.jzts();
				diag.close();
			};
			diag.show();
		}
		 function delte(rowData,strid){
             	$("#zd_ids").val(rowData.id);
             	 $("#levels").val(rowData.level);
             	$("#strids").val(strid);
             	 $("#forms").submit();
           }
		function removeTreeNode() {
			hideRMenu();
			var nodes = treeObj.getSelectedNodes();
			if (nodes && nodes.length>0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					var strid="";
					for(var i=0;i<nodes[0].children.length;i++){
						if(nodes[0].level==1){
						if(nodes[0].children[i].level==3){
							strid+=nodes[0].children[i].id+",";
						 }
						}else{
							for(var j=0;j<nodes[0].children[i].children.length;j++){
								if(nodes[0].children[i].children[j].level==3){
									strid+=nodes[0].children[i].children[j].id+",";
								 }	
							}
						}
					}
					var msg = "要删除的节点是父节点，如果删除将连同子节点及其相关设备信息一起删掉。\n\n请确认！";
					if (confirm(msg)==true){
						treeObj.removeNode(nodes[0]);
						delte(nodes[0],strid);
					} 
				} else {
					var strid="";
					if(nodes[0].level==3){
						strid=nodes[0].id;
					}
					 var msg = "该节点可能存在相关设备信息,是否删除该节点。\n\n请确认！";
					if (confirm(msg)==true){
						treeObj.removeNode(nodes[0]);
						delte(nodes[0],strid);
					} 
				}
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
			 diag.URL = '<%=basePath%>regioncontroller/toAdd.do?parentid=0&level=0';
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
			//检索
			function search(){
				top.jzts();
				$("#userForm").submit();
			}
			//编辑
			function editE(equ_id){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="编辑";
				 diag.URL = '<%=basePath%>ZoneM/equipmentEdit.do?equ_id='+equ_id;
				 diag.Width = 700;
				 diag.Height = 290;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 nextPage('${page.currentPage}');
					}
					diag.close();
				 };
				 diag.show();
			}
			
			
			function imp(){
				top.jzts();
				var diag = new top.Dialog();
				diag.Drag=true;
				diag.Title ="导入";
				diag.URL = '<%=basePath%>ZoneM/environmentImp.do';
				diag.Width = 400;
				diag.Height = 200;
				diag.CancelEvent = function() { //关闭事件
					if (diag.innerFrame.contentWindow.document
							.getElementById('zhongxin').style.display == 'none') {
						nextPage('${page.currentPage}');
					}
					diag.close();
				};
				diag.show();
			}
			
			//新增
			function add(){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="新增";
				 diag.URL = '<%=basePath%>ZoneM/equipmentAdd.do';
				 diag.Width = 750;
				 diag.Height = 350;
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
				bootbox.confirm(msg, function(result) {
					if(result) {
						var str = '';
						for(var i=0;i < document.getElementsByName('ids').length;i++)
						{
							  if(document.getElementsByName('ids')[i].checked){
							  	if(str==''){ 
							  		str += document.getElementsByName('ids')[i].value;
							  		}else{
							  		str += ',' + document.getElementsByName('ids')[i].value;
							  		}
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
									url: '<%=basePath%>ZoneM/deleteAll.do?tm='+ new Date().getTime(),
									data : {
										equ_id : str
										},
									dataType : 'json',
									//beforeSend: validateData,
									cache : false,
									success: function(data){
										top.hangge();
										 $.each(data.list, function(i, list){
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
</body>
</html>


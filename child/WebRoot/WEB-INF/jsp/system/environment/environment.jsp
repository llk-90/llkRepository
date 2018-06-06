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

<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"
	type="text/css">
<!-- <script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script> -->
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<!-- 引入echarts-all.js -->
<script type="text/javascript" src="static/chart/echarts-all.js"></script>

 <style>
  #echarts {
  width:85%;
  height:400px;
}
.divcss5 {
	width: 600px;
	height: 100px;
	border: 1px solid #D3D3D3
}
  </style>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<table style="width: 100%;">
			<tr>
				<td class="divcss5" style="width: 15%;" valign="top" bgcolor="#FFFFFF">
					<div class="accordion-heading">
						<a class="accordion-toggle" style="text-decoration: none;">农场列表</a>
					</div>
					<div style="width: 15%;">
						<ul id="leftTree" class="ztree"></ul>
					</div>
				</td>
				<td style="width: 85%;" valign="top">
				<input type="hidden" id="type" value="0" />
				<input type="hidden" id="unit" value="day" />
				<input type="hidden" id="zd_id" value="" />
					<div class="row-fluid">
						<!-- 检索  -->
						<form action="#" method="post" name="userForm" id="userForm">
							<table>
								<tr>
								<td>环境参数：</td>
									<td>
									<label>
								<input name="type" type="radio" checked="checked" value="0" onclick="changeType(this)"><span class="lbl">空气温度</span>
							       </label>
									</td>
									<td style="width:20px;"></td>
									<td>
									<label>
								<input name="type" type="radio" value="1" onclick="changeType(this)"><span class="lbl">空气湿度</span>
							       </label>
									</td>
									<td style="width:20px;"></td>
									<td><label>
								<input name="type" type="radio" value="2" onclick="changeType(this)"><span class="lbl">土壤水分</span>
							       </label></td>
							       <td style="width:20px;"></td>
									<td><label>
								<input name="type" type="radio" value="3" onclick="changeType(this)"><span class="lbl">光照度</span>
							       </label></td>
							       <td style="width:20px;"></td>
									<td><label>
								<input name="type" type="radio" value="4" onclick="changeType(this)"><span class="lbl">土壤温度</span>
							       </label></td>
							       <td style="width:20px;"></td>
									<td><label>
								<input name="type" type="radio" value="5" onclick="changeType(this)"><span class="lbl">CO2浓度</span>
							       </label></td>
							       <td style="width:574px;"></td>
							       <td><label>
							       <a class="btn btn-small btn-info" onclick="SetUp();"><i class="icon-cog icon-only" ></i>设置</a>
							       </label></td>
								</tr>
								</table>
								<table style="margin-left: 1000px">
								<tr style="height: 10px;"></tr>
								<tr>
							       <td><label>
								<a class="btn btn-small btn-info" type="submit" onclick="dayData();">天分析</a>
							       </label></td>
							       <td style="width:20px;"></td>
								<td><label>
								<a class="btn btn-small btn-info" type="submit" onclick="monthData();">月分析</a>
							       </label></td>
							       <td style="width:20px;"></td>
								<td><label>
								<a class="btn btn-small btn-info" type="submit" onclick="yearData();">年分析</a>
							       </label></td>
								</tr>
								</table>
							<!-- 检索  -->
							</form>
					</div>
					<div class="row-fluid" >
							<div class="xbox" style="overflow: hidden;" id="videoarea">
									<div id="echarts" class="ax_image" style="border: 1px solid #ddd;"></div>
							</div>
					</div>
				<div class="row-fluid" >
				<div class="span5" style="width:85%; ">
				<div class="widget-box">
				  <div class="widget-header">
					<h4>处理预案</h4>
					<span class="widget-toolbar">
							<a data-action="settings" onclick="able();" style="cursor:pointer"><i class="icon-cog"></i>编辑</a>
						</span>
					<input type="hidden" id="but" name="but" value="0">
				  </div>
				  <div class="widget-body">
				   <div class="widget-main no-padding">
							<textarea class="span12" id="plan" placeholder="处理预案" disabled="disabled" style="margin: 0px; width:100%; height: 150px;color:black"></textarea>
							<!-- 检索  -->
							<div class="form-actions center">
							<a class="btn btn-small btn-info" onclick="editPlan()"><i class="icon-ok"></i> 提交</a>
					  </div>
				   </div>
				  </div>
				</div>
			</div>
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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
	<!-- 日期框 -->
	<!-- 引入 -->
	<script type="text/javascript">
		$(top.hangge());
	
	</script>
	<script type="text/javascript">
    
	var titles = [];
	var datas = [];
	var maxtitles = [];
	var maxdatas = [];
	var mintitles = [];
	var mindatas = [];
	var worntitles = [];
	var worndatas = [];
	var parameter;
	function getData(type,unit,zd_id){
		if(type == '' || type == null){
			var type = $("#type").val();
		}
		if(unit == '' || unit == null){
			var cycle = $("#unit").val();
		}
		if(zd_id == '' || zd_id == null){
			var zd_id = $("#zd_id").val();
		}
		titles = [];
		datas = [];
		maxtitles = [];
		maxdatas = [];
		mintitles = [];
		mindatas = [];
		worntitles = [];
		worndatas = [];
		$.ajax({  
		        async : false,  
		        cache:false,  
		        type: 'POST',
		        dataType : "json",  
		        url: '<%=basePath %>environment/listData.do?type='+type+'&unit='+unit+'&zd_id='+zd_id,
		        error: function () {
		        	bootbox.alert("加载失败！", function() {
					});
		        },  
		        success:function(data){
		        	if(data == null){
		        		bootbox.alert("无数据！", function() {
						});
		        	}
		        	var num = data.length;
		        	var max = data[0].equ_threshold;
		        	var min = data[0].low_threshold;
		        	var plan = data[0].equ_plan;
		        	var cycle = data[0].cycle;
		        	var par = data[0].equ_parameter;
		        	$("#plan").val(plan);
		        	$("#zd_id").val(data[0].area_kid);
		        	$("#type").val(par);
					$("#unit").val(unit);
		        	if(par == '0'){
		        		parameter = '空气温度';
		        	}else if(par == '1'){
		        		parameter = '空气湿度';
		        	}else if(par == '2'){
		        		parameter = '土壤水分';
		        	}else if(par == '3'){
		        		parameter = '光照度';
		        	}else if(par == '4'){
		        		parameter = '土壤温度';
		        	}else if(par == '5'){
		        		parameter = 'CO2浓度';
		        	}
		        	for (var i=0;i<num;i++){
		        		titles.push(data[i].create_date);
		        		datas.push(data[i].equ_data);
		        		if(data[i].equ_data >= max){
		        			maxdatas.push(data[i].equ_data);
		        			maxtitles.push(data[i].create_date);
		        		}
		        		if(data[i].equ_data <= min){
		        			mindatas.push(data[i].equ_data);
		        			mintitles.push(data[i].create_date);
		        		}
		        	}
		        worntitles = maxtitles.concat(mintitles);
		        worndatas = maxdatas.concat(mindatas);
		        var myChart = echarts.init(document.getElementById('echarts'));
		   		var option = {
		   			title : {
		   				text : parameter
		   			},
		   			tooltip : {
		   				trigger : 'axis'
		   			},
		   			toolbox : {
		   				show : true,
		   				feature : {
		   					mark : {
		   						show : false
		   					},
		   					dataView : {
		   						show : false,
		   						readOnly : false
		   					},
		   					magicType : {
		   						show : true,
		   						type : [ 'line', 'bar' ]
		   					},
		   					restore : {
		   						show : false
		   					},
		   					saveAsImage : {
		   						show : false
		   					}
		   				}
		   			},
		   			dataZoom : {
		   				show : true,
		   				realtime : true,
		   				start : 0,
		   				end : 100
		   			},
		   			calculable : false,
		   			xAxis : [ {
		   				type : 'category',
		   				boundaryGap : true,
		   				data : titles
		   			} ],
		   			yAxis : [ {
		   				type : 'value',
		   				axisLabel : {
		   					formatter : '{value} °C'
		   				}
		   			} ],
		   			series : [
		   					{
		   						name : parameter,
		   						type : 'line',
		   						data : datas, 
		   						markPoint : {
		   							data : (function(){
		   	                            var data = [];
		   	                            var len = worndatas.length;
		   	                            for (var i=0 ;i<len;i++){
		   	                             	data.push({
		   	                                    name : '预警',
		   	                                    value : worndatas[i],
		   	                                    xAxis : worntitles[i],
		   	                                    yAxis : worndatas[i]
		   	                                })
		   	                            }
		   	                            return data;
		   	                        })(),
		   							effect : {
		   								show : true,
		   								type : 'scale',
		   								loop : true,
		   								period : 15,
		   								scaleSize : 2,
		   								bounceDistance : 10,
		   								color : '#f0f',
		   								shadowColor : null,
		   								shadowBlur : 0
		   							}
		   						},
		   						 markLine : {
		   							data : [  
		   							[ {
		   								name : '最高'+parameter,
		   								value : max,
		   								xAxis : -1,
		   								yAxis : max
		   							}, {
		   								xAxis : 100,
		   								yAxis : max
		   							} ],
		   							[ {
		   								name : '最低'+parameter,
		   								value : min,
		   								xAxis : -1,
		   								yAxis : min
		   							}, {
		   								xAxis : 100,
		   								yAxis : min
		   							} ]
		   							]
		   						}
		   					} ]
		   		};
		   		// 为echarts对象加载数据 
		   		myChart.setOption(option);
		        } 
			});
	}
	/* $(document).ready(function(){
		getData('0','day','');
	}) */
	function changeType(obj){
		getData(obj.value,$("#unit").val(),$("#zd_id").val());
	}
	function able(){
		/* $("#plan").attr("disabled","false"); */
		document.getElementById("plan").disabled = false;
		$("#but").val('1');
	}
	function dayData(){
		getData($("#type").val(),'day',$("#zd_id").val());
	}
	function monthData(){
		getData($("#type").val(),'month',$("#zd_id").val());
	}
	function yearData(){
		getData($("#type").val(),'year',$("#zd_id").val());
	}
	
	//设置参数
	function SetUp(){
		 top.jzts();
		 var id = $("#zd_id").val();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="设置临界值";
		 diag.URL = '<%=basePath%>environment/toSetUp.do?id='+id;
		 diag.Width = 1000;
		 diag.Height = 380;
		 diag.CancelEvent = function(){ //关闭事件
			 top.hangge();
			diag.close();
		 };
		 diag.show();
		 top.hangge();
	}		
	
	function editPlan(){
		var flag = $("#but").val();
		if(flag == '0'){
			bootbox.alert("当前处于不可编辑状态下！", function() {
			});
			return false;
		}
		var id = $("#zd_id").val();
		var type = $("#type").val();
		var plan = $("#plan").val();
		$.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',
	        dataType : "text",  
	        data : {id:id,type:type,plan:plan},
	        url: '<%=basePath %>environment/savePlan.do',
	        error: function () {
	        	bootbox.alert("加载失败！", function() {
				});
	        },  
	        success:function(data){
	        	if(data == 'error'){
	        		bootbox.alert("修改失败，请确保已经添加采集周期和临界值。", function() {
					});
	        	}else if(data == 'success'){
	        		bootbox.alert("修改成功！", function() {
					});
	        	}
	        }
		})
	}
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
			},
			callback: {
				onClick: OnClick
			}
		};
		function OnClick(event, treeId, treeNode){
			if(treeNode==null){
				return false;
			} 
			if(treeNode.level !=2){
				return false;
			}
			$("#zd_id").val(treeNode.id);
			getData('','',treeNode.id);
		}
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
		        	bootbox.alert("加载失败！", function() {
					}); 
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
		    getData('0','day','');
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


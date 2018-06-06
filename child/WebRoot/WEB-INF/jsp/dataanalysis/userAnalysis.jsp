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
<!-- jsp文件头和头部 -->
<%@ include file="../system/admin/top.jsp"%>

<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<!-- <script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script> -->
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<!-- 引入echarts-all.js -->
<script type="text/javascript" src="static/chart/echarts-all.js"></script>

<style>
#echarts {
	width: 85%;
	height: 400px;
}

.divcss5 {
	width: 600px;
	height: 100px;
	border: 1px solid #D3D3D3
}
</style>
</head>
<body>
	<div class="container" id="main-container">
		<table style="width: 100%;">
			<tr>
				<td style="width: 85%;" valign="top">
					<div class="row-fluid">
						<!-- 检索  -->
						<form action="#" method="post" name="userForm" id="userForm">
							<input type="hidden" id="typeValue" value="">
							<input type="hidden" id="timeUnitValue" value="">
							<input type="hidden" class="dates" value="${pd.time_end}">
							<table>
								<tr height="50px"></tr>
								<tr height="35">
									<td>时间：</td>
									<td colspan="5">
										<input id="time_start" class="span10 date-picker" type="text" autocomplete="off" style="width: 100px;margin-bottom: 0px" name="sms_time_start" value="${pd.time_start}" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="开始时间">
										-
										<input id="time_end" class="span10 date-picker" type="text" autocomplete="off" style="width: 100px;margin-bottom: 0px" name="sms_time_start" value="${pd.time_end}" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="结束时间">
										<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出">
										<i id="nav-search-icon" class="icon-download-alt"></i>
										</a>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr height="35">
									<td>周期	：</td>
									<td style="width: 100px; cursor: pointer" id="day" class="center btn-primary" onclick="changeParam('timeUnitValue', 'day')">
										<font color="#FFFFFF">日分析</font>
									</td>
									<td width="5px"></td>
									<td style="width: 100px; cursor: pointer" id="week" class="center btn-inverse" onclick="changeParam('timeUnitValue', 'week')">
										<font color="#FFFFFF">周分析</font>
									</td>
									<td width="5px"></td>
									<td style="width: 100px; cursor: pointer;" id="month" class="center btn-inverse" onclick="changeParam('timeUnitValue', 'month')">
										<font color="#FFFFFF">月分析</font>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div class="row-fluid">
						<div class="xbox" style="overflow: hidden;" id="videoarea">
							<div id="echarts" class="ax_image" style="border: 1px solid #ddd;"></div>
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
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>

	<script type="text/javascript">
		$(top.hangge());
		$(document).ready(function() {
			getData('day');
			$('#timeUnitValue').val('day');
		});
		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
		function changeParam(paramName,paramValue){
			var timeUnits = new Array('day','week','month');
			if(paramName=='timeUnitValue'){
				for(var i=0;i<timeUnits.length;i++){
					$('#'+timeUnits[i]).removeClass('btn-primary');
					$('#'+timeUnits[i]).addClass('btn-inverse');
				}
				$('#'+paramValue).removeClass('btn-inverse');
				$('#'+paramValue).addClass('btn-primary');
			}
			$('#'+paramName).val(paramValue);
			var start_time=$('#time_start').val();
			var end_time=$('#time_end').val();
			if(start_time.length>0&&end_time.length>0){
				if(start_time>end_time)
				{
				$("#time_start").tips({
					side : 3,
					msg : '开始时间不能大于结束时间！',
					bg : '#FF5080',
					time : 2
				});
				$("#time_start").focus();
				return false;		
				}
			}
			var tds=$('.dates').val();
			if(tds.length>0&&end_time.length>0){
				if(end_time>tds)
				{
				$("#time_end").tips({
					side : 3,
					msg : '结束时间不能大于当前日期！',
					bg : '#FF5080',
					time : 2
				});
				$("#time_end").focus();
				return false;		
				}
			} 
			getData($('#timeUnitValue').val());
		}
	</script>
	<script type="text/javascript">
    
	var titles = [];
	var datas = [];
	var bw;
	function getData(timeUnit){
			titles = [];
			datas = [];
			$.ajax({  
			        async : false,  
			        cache:false,  
			        type: 'POST',
			        dataType : "json",  
			        url: '<%=basePath%>userAnalysisController/listData.do?timeUnit='+timeUnit+ '&time_start=' + $('#time_start').val() + '&time_end=' + $('#time_end').val(),
					error : function() {
						bootbox.alert("加载失败！", function() {
						});
					},
					success : function(map) {
						var data = map.list;
						var before = map.before;
						var num = data.length;
						//日
						if (timeUnit == 'day') {
							bw = 30;
							timeUnitValue = '日';
							for (var j = 0; j < num; j++){
								if(j == 0){
									data[0].count = parseInt(data[0].count) + parseInt(before);
								}else{
									data[j].count = parseInt(data[j].count) + parseInt(data[j-1].count);
								}
							}
							for (var i = 0; i < num; i++){
								titles.push(data[i].dt);
								datas.push(data[i].count);
							}
						}
						//周
						if (timeUnit == 'week') {
							bw = 60;
							timeUnitValue = '周';
							for (var j = 0; j < num; j++){
								if(j == 0){
									data[0].count = parseInt(data[0].count) + parseInt(before);
								}else{
									data[j].count = parseInt(data[j].count) + parseInt(data[j-1].count);
								}
							}
							for (var i = 0;i < num;i++){
								titles.push(data[i].dt);
								datas.push(data[i].count);
							}
						}
						//月
						if (timeUnit == 'month') {
							bw = 90;
							timeUnitValue = '月';
							for (var j = 0; j < num; j++){
								if(j == 0){
									data[0].count = parseInt(data[0].count) + parseInt(before);
								}else{
									data[j].count = parseInt(data[j].count) + parseInt(data[j-1].count);
								}
							}
							for (var i = 0;i < num;i++){
								titles.push(data[i].dt);
								datas.push(data[i].count);
							}
						}
						//新增数据统计
						var datas_add = [];
						datas_add.push(parseInt(datas[0])-parseInt(before));
						for(var i = 1; i < datas.length; i++){
							if(parseInt(datas[i])>=parseInt(datas[i-1])){
								datas_add.push(parseInt(datas[i])-parseInt(datas[i-1]));
							}else{
								datas_add.push(0);
							}
						}

						var datas_base = [];
						datas_base.push(parseInt(datas[0])-parseInt(datas_add[0]));
						for(var i = 1;i < datas.length; i++){
							datas_base.push(parseInt(datas[i])-parseInt(datas_add[i]));
						}
						
						if (data == null || num == 0) {
							titles = [];
							datas_add = [];
							datas = [];
							datas_base = [];
						}	
						var myChart = echarts.init(document.getElementById('echarts'));
						var itemStyle = {
							    normal: {
							    },
							    emphasis: {
							        barBorderWidth: 1,
							        shadowBlur: 10,
							        shadowOffsetX: 0,
							        shadowOffsetY: 0,
							        shadowColor: 'rgba(0,0,0,0.5)'
							    }
							};
						 var option = {
			           	    title : {
			           	        text: '微信绑定用户数据'+timeUnitValue+'分析',
			           	        subtext: '数量'
			           	    },
			           	    tooltip : {
			           	        trigger: 'axis'
			           	    },
			           	    legend: {
			           	        data:['新增用户','基础用户']
			           	    },
			           	    toolbox: {
			           	        show : true,
			           	        feature : {          	           
			           	            dataView : {show: false, readOnly: false},
			           	            magicType : {show: true, type: ['line', 'bar']},
			           	         	saveAsImage : {show: true}
			           	        }
			           	    },
			           	    calculable : false,
			           		grid: {y: 70, y2:30, x2:20},
			           	    xAxis : [
			           	        {
			           	            type : 'category',
			           	        	boundaryGap : true,			           	        	
			           	            data : titles	
			           	        },
						        {
						            type : 'category',
						            axisLine: {show:false},
						            axisTick: {show:false},
						            axisLabel: {show:false},
						            splitArea: {show:false},
						            splitLine: {show:false},
						            data : titles
						        }
			           	    ],
			           	    yAxis : [
			           	        {
			           	            type : 'value',
			           	         	axisLabel : {
					                	formatter: '{value} 人'
					           		 }
			           	        }
			           	    ],
			           	    series : [
			           	        {
			           	        	name: '基础用户',
						            type:'bar',
						            data:datas_base
			           	            
			           	        },
			           	        {
			           	        	name: '新增用户',
						            type:'bar',
						            data: datas_add
			           	        }
			           	    ]
			           	};
						// 为echarts对象加载数据 
						myChart.setOption(option);
					}
				});
		}
	//导出EXcel
	function toExcel(){
		var timeUnitVlue=$('#timeUnitValue').val();
		var start_time=$('#time_start').val();
		var end_time=$('#time_end').val();
		if(start_time.length>0&&end_time.length>0){
			if(start_time>end_time)
			{
			$("#time_start").tips({
				side : 3,
				msg : '开始时间不能大于结束时间！',
				bg : '#FF5080',
				time : 2
			});
			$("#time_start").focus();
			return false;		
			}
		}
		window.location.href=encodeURI("<%=basePath%>userAnalysisController/excel.do?time_start="+start_time+"&time_end="+end_time+"&timeUnit="+timeUnitVlue);	
	}
	</script>
</body>
</html>


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
<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<!-- 引入echarts-all.js -->
<script type="text/javascript" src="static/chart/echarts-all.js"></script>

	<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
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
										<input id="time_start" class="span10 date-picker" type="text" autocomplete="off" style="width: 100px;margin-bottom: 0px" name="time_start" value="${pd.time_start}" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="开始时间">
										-
										<input id="time_end" class="span10 date-picker" type="text" autocomplete="off" style="width: 100px;margin-bottom: 0px" name="time_end" value="${pd.time_end}" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="结束时间">
										<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出">
										<i id="nav-search-icon" class="icon-download-alt"></i>
										</a>
									</td>
								</tr>
								<tr height="5px"></tr>
								<tr height="35">
									<td>类型：</td>
									<td style="width: 100px; cursor: pointer" id="school" class="center btn-success" onclick="changeParam('typeValue', 'school')">
										<font color="#FFFFFF">学校</font>
									</td>
									<td width="5px"></td>
									<td style="width: 100px; cursor: pointer" id="class" class="center btn-inverse" onclick="changeParam('typeValue', 'class')">
										<font color="#FFFFFF">班级</font>
									</td>
									<!-- <td width="5px"></td>
									<td style="width: 100px; cursor: pointer" id="card" class="center btn-inverse" onclick="changeParam('typeValue', 'card')">
										<font color="#FFFFFF">打卡</font>
									</td> -->
								</tr>
								<tr height="5px"></tr>
								<tr height="35">
									<td>单位：</td>
									<td style="width: 100px; cursor: pointer" id="day" class="center btn-primary" onclick="changeParam('timeUnitValue', 'day')">
										<font color="#FFFFFF">日</font>
									</td>
									<td width="5px"></td>
									<td style="width: 100px; cursor: pointer" id="week" class="center btn-inverse" onclick="changeParam('timeUnitValue', 'week')">
										<font color="#FFFFFF">周</font>
									</td>
									<td width="5px"></td>
									<td style="width: 100px; cursor: pointer;" id="month" class="center btn-inverse" onclick="changeParam('timeUnitValue', 'month')">
										<font color="#FFFFFF">月</font>
									</td>
									<td width="5px"></td>
									<td>
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
			getData('school', 'day',$('#time_start').val(),$('#time_end').val());
			$('#typeValue').val('school');
			$('#timeUnitValue').val('day');
		});
		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
		function changeParam(paramName,paramValue){
			var types = new Array('school','class','card');
			var timeUnits = new Array('day','week','month');
			if(paramName=='typeValue'){
				for(var i=0;i<types.length;i++){
					$('#'+types[i]).removeClass('btn-success');
					$('#'+types[i]).addClass('btn-inverse');
				}
				$('#'+paramValue).removeClass('btn-inverse');
				$('#'+paramValue).addClass('btn-success');
			}else if(paramName=='timeUnitValue'){
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
			getData($('#typeValue').val(),$('#timeUnitValue').val());
		}
	</script>
	<script type="text/javascript">
    
	var titles = [];
	var datas = [];
	var bw;
	function getData(type,timeUnit){
		
			titles = [];
			datas = [];
			$.ajax({  
			        async : false,  
			        cache:false,  
			        type: 'POST',
			        dataType : "json",  
			        url: '<%=basePath%>newData/listData.do?type=' + type
							+ '&timeUnit=' + timeUnit + '&time_start=' + $('#time_start').val() + '&time_end=' + $('#time_end').val(),
					error : function() {
						bootbox.alert("加载失败！", function() {
						});
					},
					success : function(map) {
						var data = map.list;
						var before = map.before;
						var num = data.length;
						var timeUnitValue;
						if (type == 'school') {
							typeValue = '学校';
						} else if (type == 'class') {
							typeValue = '班级';
						} else if (type == 'card') {
							typeValue = '打卡';
						}
						if(timeUnit == 'day'){
							timeUnitValue = '日';
						}
						if(timeUnit == 'week'){
							timeUnitValue = '周';
						}
						if(timeUnit == 'month'){
							timeUnitValue = '月';
						}
						if(data != null && num != 0){
							
							//日
							if (timeUnit == 'day') {
								bw = 30;
								timeUnitValue = '日';
								data[0].count = parseInt(data[0].count) + parseInt(before);
								for (var j = 1; j < num; j++){
									data[j].count = parseInt(data[j].count) + parseInt(data[j-1].count);
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
								data[0].count = parseInt(data[0].count) + parseInt(before);
								for (var j = 1; j < num; j++){
									data[j].count = parseInt(data[j].count) + parseInt(data[j-1].count);
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
						}else if (data == null || num == 0) {
							titles = [];
							datas = [];
							datas_add = [];
							datas_base = [];
						}
						var myChart = echarts.init(document.getElementById('echarts'));
						var option = {
								noDataLoadingOption:{
									text: '暂无数据',
				                    effect:'bubble',
				                    effectOption : {
			                        	effect: {
				                            n: 20 //气泡个数为0 
				                        }
				                    }
							    },
							    title : {
							        text: typeValue + '（' + timeUnitValue + '）'
							    },
							    tooltip : {
							        trigger: 'axis'
							    },
							    legend: {
							        data:[
										'每' + timeUnitValue + '新增' + typeValue + '数据统计',
										'每' + timeUnitValue + '基础' + typeValue + '数据统计'
							        ]
							    },
							    toolbox: {
							        show : true,
							        feature : {
							        	feature : {          	           
					           	            dataView : {show: false, readOnly: false},
					           	            magicType : {show: true, type: ['line', 'bar']},
					           	         	saveAsImage : {show: true}
					           	        }
							        }
							    },
							    calculable : false,
							    grid: {y: 70, y2:30, x2:20},
							    visualMap: {
							        type: 'continuous',
							        dimension: 1,
							        text: ['High', 'Low'],
							        inverse: true,
							        itemHeight: 200,
							        calculable: true,
							        min: -2,
							        max: 6,
							        top: 60,
							        left: 10,
							        inRange: {
							            colorLightness: [0.4, 0.8]
							        },
							        outOfRange: {
							            color: '#bbb'
							        },
							        controller: {
							            inRange: {
							                color: '#2f4554'
							            }
							        }
							    },
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
							            axisLabel:{formatter:'{value} 个'}
							        }
							    ],
							    series : [
							        {
							            name: '每' + timeUnitValue + '基础'+typeValue + '数据统计',
							            type:'bar',
							            /* itemStyle: itemStyle, */
							            data: datas_base
							        },
							        {
							            name: '每' + timeUnitValue + '新增'+typeValue + '数据统计',
							            type:'bar',
							            /* xAxisIndex:1, */
							            /* itemStyle: itemStyle, */
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
		var typeValue=$("#typeValue").val();
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
		window.location.href=encodeURI("<%=basePath%>newData/excel.do?time_start="+start_time+"&time_end="+end_time+"&timeUnit="+timeUnitVlue+"&type="+typeValue);	
	}
	</script>
</body>
</html>


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
							<input type="hidden" id="excel_type" value="">
							<input type="hidden" id="excel_typename" value="">
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
					<!-- <div class="row-fluid">
						<div class="span5" style="width: 85%;">
							<div class="widget-box">
								<div class="widget-header">
									<h4>数据分析</h4>
									<input type="hidden" id="but" name="but" value="0">
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding">
										<textarea class="span12" id="plan" disabled="disabled" style="margin: 0px; width: 100%; height: 150px; color: black">
											每日图文类浏览量平均值12.14。
											每日视频类浏览量平均值5。
											每日文档类浏览量平均值4。
										</textarea>
									</div>
								</div>
							</div>
						</div>
					</div> -->
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
			getData('day',$('#time_start').val(),$('#time_end').val());
			$('#timeUnitValue').val('day');
			$('#excel_typename').val('day');
			$('#excel_type').val('1');
		});
		function unique(arr) {
		    var result = [], hash = {};
		    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
		        if (!hash[elem]) {
		            result.push(elem);
		            hash[elem] = true;
		        }
		    }
		    return result;
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
		function changeParam(paramName,paramValue){
			if(paramValue=="day"){
				$("#excel_type").val(1);
				$('#excel_typename').val('day');
			}else if(paramValue=="week"){
				$("#excel_type").val(2);
				$('#excel_typename').val('week');
			}else if(paramValue=="month"){
				$("#excel_type").val(3);
				$('#excel_typename').val('month');
			}
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
			getData($('#timeUnitValue').val(),start_time,end_time);
		}
		
		
	</script>
	<script type="text/javascript">
	function getData(timeUnit,time_start,time_end){
		var xdata = [];
		var pictures = [];
		var videos = [];
		var txts = [];
		$.ajax({  
		        async : false,  
		        cache:false,  
		        type: 'POST',
		        dataType : "json",  
		        url: '<%=basePath%>coursewareAnalysisController/listData.do?timeUnit='+timeUnit+'&time_start='+time_start+'&time_end='+time_end,
				error : function() {
					
					bootbox.alert("加载失败！", function() {
					});
				},
				success : function(data) {
					if (data == null) {
						bootbox.alert("无数据！", function() {
						});
					}
					var num = data.length;
					for(var i=0;i<num;i++){
						xdata.push(data[i].dt);
						if(data[i].c_type==1){
							pictures.push(data[i].count);
						}
						if(data[i].c_type==2){
							videos.push(data[i].count);
						}
						if(data[i].c_type==3){
						    txts.push(data[i].count);
						}
					}
					xdata=unique(xdata);
					xdata.sort();
					var timeUnitValue;					
					//日
					if (timeUnit == 'day') {
						timeUnitValue = '日';						
					}else if(timeUnit == 'week'){
						timeUnitValue = '周';
					}else if(timeUnit == 'month'){
						timeUnitValue = '月';
					}
					var myChart = echarts.init(document.getElementById('echarts'));
					var option = {
		    			    title : {
		    			        text: '课件内容浏览量'+timeUnitValue+'分析',
		    			        subtext: '浏览量'
		    			    },
		    			    tooltip : {
		    			        trigger: 'axis'
		    			    },
		    			    legend: {
		    			        data:['图文类','视频类','文档类']
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
		    			    xAxis : [
		    			        {
		    			            type : 'category',
		    			            boundaryGap : false,
		    			            data :xdata	
		    			        }
		    			    ],
		    			    yAxis : [
		    			        {
		    			            type : 'value',
		    			            axisLabel : {
		    			                formatter: '{value} 次'
		    			            }
		    			        }
		    			    ],
		    			    series : [
		    			        {
		    			            name:'图文类',
		    			            type:'line',
		    			            data : pictures
		    			        },
		    			        {
		    			            name:'视频类',
		    			            type:'line',
		    			            data : videos
		    			        },
		    			        {
		    			            name:'文档类',
		    			            type:'line',
		    			            data :txts
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
		var excel_type=$("#excel_type").val();
		var excel_typename=$('#excel_typename').val();
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
		window.location.href=encodeURI("<%=basePath%>coursewareAnalysisController/excel.do?time_start="+start_time+"&time_end="+end_time+"&timeUnit="+excel_typename+"&excel_type="+excel_type);	
	} 
	</script>
</body>
</html>


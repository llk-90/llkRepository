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
<%@ include file="../system/admin/top.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div id="" class="row-fluid">
			
			<table>
					<tr height="25px"></tr>
					<tr height="35">
						<td>时间：</td>
						<td colspan="5" >
							<input id="sms_time_start" class="span10 date-picker" type="text" autocomplete="off" style="width: 100px;margin-bottom: 0px" name="sms_time_start" value="" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="开始时间">
							-
							<input id="sms_time_start" class="span10 date-picker" type="text" autocomplete="off" style="width: 100px;margin-bottom: 0px" name="sms_time_start" value="" readonly="readonly" data-date-format="yyyy-mm-dd" placeholder="结束时间">
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
					<tr height="20px"></tr>
			</table>
			
			<div class="row-fluid">
				<div class="span12">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>								
								<th class="center" colspan='9'>考勤数据分析表</th>														
							</tr>
						</thead>
						<thead>
							<tr class="success">					
								<td class="center" >日期</td>
								<td class="center" >学校</td>
								<td class="center" >班级</td>
								<td class="center">应出勤人数</td>
								<td class="center">实际出勤人数</td>
								<td class="center">迟到</td>
								<td class="center">早退</td>
								<td class="center">缺卡</td>
								<td class="center">出勤率</td>							
							</tr>
						</thead>	
						<tbody>	
							<tr class="info">								
								<td class="center">2016-06-11</td>
								<td class="center">希望幼儿园</td>
								<td class="center">大班</td>
								<td class="center">120人</td>
								<td class="center">113人</td>
								<td class="center">2人</td>
								<td class="center">4人</td>
								<td class="center">1人</td>
								<td class="center">94.17%</td>								
							</tr>
							<tr class="error">						
								<td class="center">2016-06-10</td>
								<td class="center">希望幼儿园</td>
								<td class="center">大班</td>
								<td class="center">150人</td>
								<td class="center">125人</td>
								<td class="center">5人</td>
								<td class="center">10人</td>
								<td class="center">10人</td>
								<td class="center">83.33%</td>																	
							</tr>
							<tr class="info">							
								<td class="center">2016-06-09</td>
								<td class="center">希望幼儿园</td>
								<td class="center">小班</td>
								<td class="center">120人</td>
								<td class="center">113人</td>
								<td class="center">2人</td>
								<td class="center">4人</td>
								<td class="center">1人</td>
								<td class="center">94.17%</td>									
							</tr>
							<tr class="error">						
								<td class="center">2016-06-08</td>
								<td class="center">希望幼儿园</td>
								<td class="center">小班</td>
								<td class="center">150人</td>
								<td class="center">125人</td>
								<td class="center">5人</td>
								<td class="center">10人</td>
								<td class="center">10人</td>
								<td class="center">83.33%</td>																	
							</tr>
							<tr class="error">						
								<td class="center">2016-06-07</td>
								<td class="center">希望幼儿园</td>
								<td class="center">大班</td>
								<td class="center">150人</td>
								<td class="center">125人</td>
								<td class="center">5人</td>
								<td class="center">10人</td>
								<td class="center">10人</td>
								<td class="center">83.33%</td>																	
							</tr>
							<tr class="error">						
								<td class="center">2016-06-06</td>
								<td class="center">希望幼儿园</td>
								<td class="center">中班</td>
								<td class="center">150人</td>
								<td class="center">125人</td>
								<td class="center">5人</td>
								<td class="center">10人</td>
								<td class="center">10人</td>
								<td class="center">83.33%</td>																	
							</tr>
							<tr class="error">						
								<td class="center">2016-06-05</td>
								<td class="center">希望幼儿园</td>
								<td class="center">大班</td>
								<td class="center">150人</td>
								<td class="center">125人</td>
								<td class="center">5人</td>
								<td class="center">10人</td>
								<td class="center">10人</td>
								<td class="center">83.33%</td>																	
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->


	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->

	<script type="text/javascript">
		$(top.hangge());
		
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
			getData($('#timeUnitValue').val());
		}
		
		$(function() {

			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//复选框
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});

		});
	</script>
</body>
</html>
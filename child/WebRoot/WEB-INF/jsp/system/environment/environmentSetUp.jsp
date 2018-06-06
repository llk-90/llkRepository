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
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
	$(top.hangge());
	</script>
</head>
<body>
	<form action="environment/saveSetUp.do" name="Form" id="Form"
		method="post">
		<input type="hidden" name="area_kid" id="area_kid" value="${area_kid}" />
		<input type="hidden" name="action" id="action" value="${msg }">
		<div id="zhongxin">
			<table class="table ">
				
					<tr>
						<td style="width: 80px; text-align: left; padding-top: 13px;">&#12288;所属垮：</td>
						<td><input type="text" maxlength="32" value="${name }" readonly="readonly" /></td>
						<td style="width: 80px; text-align: left; padding-top: 13px;">采集周期：</td>
						<td><input type="text" name="cycle" id="cycle"
						onkeyup="value=value.replace(/[^?\d.]/g,'')"
						maxlength="5" placeholder="请输入采集周期" title="采集周期" value="${kqwd.cycle }" /></td>
					</tr>
					<tr>
						<td style="width: 80px; text-align: left; padding-top: 13px;">空气温度：</td>
						<td>
							<input type="text" style="width:35%" name="min_kqwd" id="min_kqwd" maxlength="10"
							placeholder="°C" title="最低空气温度" value="${kqwd.low_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>~
							<input type="text" style="width:35%" name="max_kqwd" id="max_kqwd" maxlength="10"
							placeholder="°C" title="最高空气温度" value="${kqwd.equ_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>
						</td>
						<td style="width: 80px; text-align: left; padding-top: 13px;">空气湿度：</td>
						<td>
							<input type="text" style="width:35%" name="min_kqsd" id="min_kqsd"
							maxlength="10" placeholder="RH" title="最低空气湿度" value="${kqsd.low_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>~
							<input type="text" style="width:35%" name="max_kqsd" id="max_kqsd"
							maxlength="10" placeholder="RH" title="最高空气湿度" value="${kqsd.equ_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>
						</td>
					</tr>
					<tr>
						<td style="width: 80px; text-align: left; padding-top: 13px;">土壤水分：</td>
						<td>
							<input type="text" style="width:35%" name="min_trsf" id="min_trsf"
							maxlength="10" placeholder="%" title="最低土壤水分" value="${trsf.low_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>~
							<input type="text" style="width:35%" name="max_trsf" id="max_trsf"
							maxlength="10" placeholder="%" title="最高土壤水分" value="${trsf.equ_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>
						</td>
						<td style="width: 80px; text-align: left; padding-top: 13px;">&#12288;光照度：</td>
						<td>
							<input type="text" style="width:35%" name="min_gzd" id="min_gzd"
							maxlength="10" placeholder="lx" title="最低光照度" value="${gzd.low_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>~
							<input type="text" style="width:35%" name="max_gzd" id="max_gzd"
							maxlength="10" placeholder="lx" title="最高光照度" value="${gzd.equ_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>
						</td>
					</tr>
					<tr>
						<td style="width: 80px; text-align: left; padding-top: 13px;">土壤温度：</td>
						<td>
							<input type="text" style="width:35%" name="min_trwd" id="min_trwd"
							maxlength="10" placeholder="°C" title="最低土壤温度" value="${trwd.low_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>~
							<input type="text" style="width:35%" name="max_trwd" id="max_trwd"
							maxlength="10" placeholder="°C" title="最高土壤温度" value="${trwd.equ_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>
						</td>
						<td style="width: 80px; text-align: left; padding-top: 13px;">CO2浓度：</td>
						<td>
							<input type="text" style="width:35%" name="min_co2nd" id="min_co2nd" maxlength="10"
							placeholder="ppm" title="最低CO2浓度" value="${co2nd.low_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>~
							<input type="text" style="width:35%" name="max_co2nd" id="max_co2nd" maxlength="10"
							placeholder="ppm" title="最高CO2浓度" value="${co2nd.equ_threshold }" onkeyup="value=value.replace(/[^?\d.]/g,'')"/>
						</td>
					</tr>	
				<tr>
					<td class="center" colspan="4"><a
						class="btn btn-mini btn-primary" onclick="check();">保存</a> <a
						class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
			   </tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		function check(){
			if($("#cycle").val() == ''){
				$("#cycle").tips({
					side : 2,
					msg : '周期不得为空',
					bg : '#AE81FF',
					time : 3
				});
				$("#cycle").focus();
				return false;
			}
			if($("#min_kqwd").val() == '' || $("#max_kqwd").val() == ''){
				$("#max_kqwd").tips({
					side : 2,
					msg : '空气温度不得为空',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_kqwd").val()>=$("#max_kqwd").val()){
				$("#max_kqwd").tips({
					side : 2,
					msg : '最小空气温度必须小于最大空气温度',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_kqsd").val() == '' || $("#max_kqsd").val() == ''){
				$("#max_kqsd").tips({
					side : 2,
					msg : '空气湿度不得为空',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_kqsd").val()>=$("#max_kqsd").val()){
				$("#max_kqsd").tips({
					side : 2,
					msg : '最小空气湿度必须小于最大空气湿度',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_trsf").val() == '' || $("#max_trsf").val() == ''){
				$("#max_trsf").tips({
					side : 2,
					msg : '土壤水分不得为空',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_trsf").val()>=$("#max_trsf").val()){
				$("#max_trsf").tips({
					side : 2,
					msg : '最小土壤水分必须小于最大土壤水分',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_gzd").val() == '' || $("#max_gzd").val() == ''){
				$("#max_gzd").tips({
					side : 2,
					msg : '光照度不得为空',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_gzd").val()>=$("#max_gzd").val()){
				$("#max_gzd").tips({
					side : 2,
					msg : '最小光照度必须小于最大光照度',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_trwd").val() == '' || $("#max_trwd").val() == ''){
				$("#max_trwd").tips({
					side : 2,
					msg : '土壤温度不得为空',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_trwd").val()>=$("#max_trwd").val()){
				$("#max_trwd").tips({
					side : 2,
					msg : '最小土壤温度必须小于最大土壤温度',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_co2nd").val() == '' || $("#max_co2nd").val() == ''){
				$("#max_co2nd").tips({
					side : 2,
					msg : 'CO2浓度不得为空',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			if($("#min_co2nd").val()>=$("#max_co2nd").val()){
				$("#max_co2nd").tips({
					side : 2,
					msg : '最小CO2浓度必须小于最大CO2浓度',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}
			top.jzts();
			$("#Form").submit();
		}
	</script>
</body>
</html>
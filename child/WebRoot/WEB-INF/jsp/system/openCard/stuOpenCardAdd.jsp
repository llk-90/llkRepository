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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<style type="text/css">
input[type="text"] {
	/* margin-bottom: 0px; */
	
}
</style>
<script type="text/javascript">
	//保存
	function save() {
		if($("#gradeParam").val()==""){
			$("#gradeParam").tips({
				side:3,
	            msg:'请选择年级',
	            bg:'#FF5080',
	            time:2
	        });
			$("#gradeParam").focus();
			return false;
		}
		
		if($("#classParam").val()==""){
			$("#classParam").tips({
				side:3,
	            msg:'请选择班级',
	            bg:'#FF5080',
	            time:2
	        });
			$("#classParam").focus();
			return false;
		}
		
		if($("#stuName").val()==""){
			$("#stuName").tips({
				side:3,
	            msg:'请输入学生姓名',
	            bg:'#FF5080',
	            time:2
	        });
			$("#stuName").focus();
			return false;
		}
		
		if($("#sex").val()==""){
			$("#sex").tips({
				side:3,
	            msg:'请选择性别',
	            bg:'#FF5080',
	            time:2
	        });
			$("#sex").focus();
			return false;
		}
		
		if($("#IcNo").val()==""){
			$("#IcNo").tips({
				side:3,
	            msg:'请输入卡号',
	            bg:'#FF5080',
	            time:2
	        });
			$("#IcNo").focus();
			return false;
		}
		var $IcNo = $("#IcNo");
		var IcNoVal = $IcNo.val();
		var hasIcNo = false;
		$.ajax({
            type : "POST",
            url : 'stuOpenCard/hasIcNo.do',
            data : {
            	IcNo : IcNoVal
            },
            dataType : 'json',
            cache : false,
            async : false,
            success : function(data) {
                if (data&&data.hasIcNo == true) {
                    $IcNo.tips({
                        side : 3,
                        msg : "卡号已存在",
                        bg : '#AE81FF',
                        time : 2
                    });
                    hasIcNo=true;
                }
            }
        });
		if(hasIcNo){
			$IcNo.focus();
			return;
		}
		
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	//数字校验
	$('.onlyNum').keyup(function() {
		var c = $(this);
		if (/[^\d]/.test(c.val())) {//替换非数字字符
			var temp_amount = c.val().replace(/[^\d]/g, '');
			$(this).val(temp_amount);
		}
	});
	//汉字或英文字符校验
	$('.gbkOrAbc').keyup(	
			function() {
				var c = $(this);
				if (/[^a-zA-Z\u4E00-\u9FA5\?·]/.test(c.val())) {//替换其他字符
					var temp_amount = c.val().replace(
							/[^a-zA-Z\u4E00-\u9FA5\?·]/g, '');
					$(this).val(temp_amount);
				}
			});
</script>
</head>
<body>
	<form action="stuOpenCard/save.do" name="Form" id="Form" method="post">
		<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.userId}" />
		<input type="hidden" name="school_id" id="school_id" value="${pd.school_id}" />	
		<input type="hidden" name="AccNoMax" id="AccNoMax" value="${pd.AccNoMax}" />
		<div id="zhongxin">
			<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: 30px">
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">学&#12288;&#12288;校：</td>
					<td>
						<input type="text" name="SchoolName" id="SchoolName" value="${pd.SchoolName}" maxlength="32" title="学校" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td style="width: 120px; text-align: left; padding-top: 13px;">年&#12288;&#12288;级：</td>
					<td style="vertical-align: top;">
						<select name="gradeParam" id="gradeParam" class="chzn-select" style="width: 220px;" data-placeholder="请选择年级" onchange="getStuClass();">
							<option value=""></option>
							<option value="QB" <c:if test="${'QB' eq pd.gradeParam}">selected="selected"</c:if>>全部</option>
							<c:forEach items="${gradeList}" var="var" varStatus="vs">
								<option value="${var.GradeId}" <c:if test="${var.GradeId.toString() eq pd.gradeParam.toString()}">selected="true"</c:if>>${var.GradeName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				    <td style="width: 70px; text-align: left; padding-top: 13px;">班&#12288;&#12288;级：</td>
					<td style="vertical-align: top;">
						<select name="classParam" id="classParam" class="chzn-select" style="width: 220px;" data-placeholder="请选择班级">
							<option value=""></option>						
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">姓&#12288;&#12288;名：</td>
					<td>
						<input type="text" name="stuName" id="stuName" value="${pd.stuName}" maxlength="32" placeholder="这里输入姓名" title="姓名" class="gbkOrAbc" />
					</td>
				</tr>
				<tr>
					<td style="width: 80px; text-align: left; padding-top: 13px;">学生性别：</td>
					<td>
						<select class="chzn-select" name="sex" id="sex" onkeyup="this.value=this.value.replace(/\D/g,'')" data-placeholder="请选择学生性别" style="vertical-align: top;" onchange="tips();">
							<option value=""></option>
							<option value="女">女</option>
							<option value="男">男</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: left; padding-top: 13px;">卡&#12288;&#12288;号：</td>
					<td>
						<input type="text" name="IcNo" id="IcNo" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${pd.IcNo}" maxlength="32" placeholder="这里输入卡号" title="卡号" class="onlyNum"/>
					</td>
				</tr>
			</table>
			<div style="text-align: center; width: 100%">

				<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
				<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
			</div>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>

	</form>


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
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
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
			
			if('${pd.gradeParam}' != ''){
				getStuClass();
			}

		});
		function areaRadio(value) {
			switch (value) {
			case '1':
				$('#area_select').css('display', '');
				$('#area_input').css('display', 'none');
				break;

			case '2':
				$('#area_select').css('display', 'none');
				$('#area_input').css('display', '');
				break;
			}
		}
		
		//获取班级
		function getStuClass(){
			var GradeId = $('#gradeParam').val();
			var school_id = $('#school_id').val();		
			$.ajax({
				type:'POST',
				url:'<%=basePath%>stuOpenCard/classList.do',
				data:{
					GradeId:GradeId,
					school_id:school_id
				},
				dataType:'json',
				cache:false,
				success:function(data){
					var html='';
					if(data.classList.length>0){
						if('' == '${pd.classParam}'){
							html+='<option value="" selected="selected">全部</option>';
						}else{
							html+='<option value="">全部</option>';
						}
					}
					$.each(data.classList,function(i,list){
						if(list.GradeId == '${pd.classParam}'){
							html+='<option value="'+list.ClassId+'" selected="selected">'+list.ClassName+'</option>';
						}else{
							html+='<option value="'+list.ClassId+'">'+list.ClassName+'</option>';
						}
					});
					$("#classParam").empty();
					$('#classParam').append(html);
					$("#classParam").trigger("liszt:updated");				}
			})
		}
	</script>
</body>
</html>
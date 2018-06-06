<%@page import="org.apache.ibatis.annotations.Case"%>
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
<%@ include file="../../system/admin/top2.jsp"%>
</head>
<body>
<form action="StudentM/${msg}.do" name="Form" id="Form" method="post">
<input type="hidden" name="id" value="${pd.id}"/>
		<div id="zhongxin">
		<table style="margin-left:5px;">
			<tbody>
			<tr>
				<td>&#12288;</td>
			</tr>
			<tr> 
			    <td><label>名&#12288;&#12288;称：</label></td>
				<td><input name="z_name" id="z_name" placeholder="请输入名称" value="${pd.name}" maxlength="8" title="名称" type="text"></td>
			</tr>
			<tr>
			    <td><label>类&#12288;&#12288;型：</label></td>
				    <td>
				        <input name="z_type" id="z_type"  type="hidden" value="${pd.zone_type}" />
                        <input title="节点类型" type="text" value="${pd.zone_type_name}" readonly />
				    </td>
			</tr>
			<tr>
			    <td><label>排&#12288;&#12288;序：</label></td>
				<td><input style="margin-top: 6px" name="z_order" id="z_order"  placeholder="请输入排序" value="${pd.z_order}" maxlength="20" title="排序"  type="text"></td>
			</tr>
			<tr>
			 <td></td>
				<td class="center;">
					<a class="btn btn-mini btn-primary" onclick="save();" style="margin-left: 40px;">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</tbody></table>
		</div>
	</form>
	

	<div id="zhongxin2" class="center" style="display: none;">
		<img src="static/images/jzx.gif" style="width: 50px;" /><br />
		<h4 class="lighter block green"></h4>
	</div>
	
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script> <!-- 显示文本框里的提示信息 -->
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
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
	</script>
	<script type="text/javascript">
	//保存

	function change(id){
			$("#unit_number").empty();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>regioncontroller/listQuYu.do',
										data : {
											z_parent_id : id
										},
										dataType : 'json',
										cache : false,
										success : function(data) {
											var str="";
											for(var i=0;i<data.length;i++){
												  str+="<option value="+data[i].z_id+">"+data[i].z_name+"</option>";		
											}
											$("#unit_number").append(str);
											$("#unit_number").trigger("liszt:updated");
											$("#unit_number").change();
										}
									});
		}

	var canSave = true;
	function save(){
		if(canSave){
			canSave = false;
			if ($("#z_name").val() == "") {
				$("#z_name").tips({
					side : 3,
					msg : '请输入名称',
					bg : '#FF5080',
					time : 2
				});
				$("#z_name").focus();
				return false;
			}
			
			
			var $zName = $("#z_name");
			var zNameVal = $zName.val();
			var hasStuNo = false;
			$.ajax({
                type : "POST",
                url : 'regioncontroller/hasZname.do',
                data : {
                	z_name : zNameVal
                },
                dataType : 'json',
                cache : false,
                async : false,
                success : function(data) {
                    if (data&&data.hasStuNo == true) {
                        $zName.tips({
                            side : 3,
                            msg : "节点名已存在",
                            bg : '#AE81FF',
                            time : 2
                        });
                        hasStuNo=true;
                    }
                }
            });
			if(hasStuNo){
				$zName.focus();
				return;
			}
			
			if ($("#z_type").val() == "") {
				$("#z_type").tips({
					side : 3,
					msg : '请选择类型',
					bg : '#FF5080',
					time : 2
				});
				$("#z_type").focus();
				return false;
			}
			if ($("#z_order").val() == "") {
				$("#z_order").tips({
					side : 3,
					msg : '请输入排序',
					bg : '#FF5080',
					time : 2
				});
				$("#z_order").focus();
				return false;
			}
			
			$("#Form").submit();
	// 		$("#zhongxin").hide();
	// 		top.Dialog.close();
		}
	}
	</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${pd.SYSNAME}</title>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">

<link rel="shortcut icon" href="static/login/login_home/image/logo.png">
<meta name="description" content="">
<meta name="keywords" content="">

<script src="static/login/login_home/jquery-1.9.1.min.js"></script>
<link href="static/login/login_home/jquery.fancybox.css">
<script src="static/login/login_home/jquery.fancybox.pack.js"></script>

<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script src="static/login/login_home/parallax.js"></script>
<link media="all" href="static/login/login_home/index.css" type="text/css" rel="stylesheet">
</head>

<body style="height: 6080px;" onpageshow="changeCode();">
	<div id="jzts" style="display: none; width: 100%; position: fixed; z-index: 99999999;" align="center">
		<div class="commitopacity" id="bkbgjz" style="display: none"></div>
		<div style="padding: 15% 45%" align="center">
			<div style="margin-top: 3px;">
				<img src="static/images/jiazai.gif" />
			</div>
		</div>
	</div>
	<div class="wrapHeader">
		<div class="header">
			<h1 class="logo" align="right">
				<a href="">
					<img src="static/login/login_home/image/logo.png" alt="掌上关爱" title="掌上关爱" height="30" style="margin: 15px 0px;">
				</a>
			</h1>
			<ul class="nav">
				<li>
					<a href="javascript:void(0)" class="cur">首页</a>
				</li>
				<li>
					<a href="javascript:void(0)">考勤通知</a>
				</li>
				<li>
					<a href="javascript:void(0)">在线请假</a>
				</li>
				<li>
					<a href="javascript:void(0)">成绩查询</a>
				</li>
				<li>
					<a href="javascript:void(0)">教子良方</a>
				</li>
				<li>
					<a href="javascript:void(0)">班级公告</a>
				</li>
				<li>
					<a href="javascript:void(0)">联系老师</a>
				</li>
				<li>
					<a href="javascript:void(0)">加入我们</a>
				</li>
				<li id="more">
					<a href="javascript:void(0)">更多功能</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="wrapBox" id="wrapBox">
		<div class="box b1_bg" style="background: url('static/login/login_home/image/11111_0003.png') no-repeat center center;  background-size: 100% auto;">
			<div class="load" style="display: none;">
				<p>
					<img src="loading.gif" alt="">
				</p>
				<div class="load_bg"></div>
			</div>
			<div class="box_fixed zIndex40" align="center">
				<h2 style="color: #73af44; font-size: 80px; font-weight: 100; margin-top: 50px">一路相伴，与您同行</h2>
				<h3 style="color: #43698f; font-size: 30px; font-weight: 100;margin-top:120px">校园微信，掌上关爱</h3>
				<div class="login">
					<div class="login_box" style="display: block;">
						<div class="login_main">
							<div class="loginsel">
								<span id="span_dl" onclick="formChange('dl');" class="one cur" style="cursor: pointer;">账号密码登录</span>
								<span id="span_ewm" onclick="formChange('ewm');" class="" style="cursor: pointer;">微信扫一扫登录</span>
								<div class="clear"></div>
							</div>
							<div class="login_c user login_c_top" id="login_dl" style="display: block;">
								<form action="">
									<input placeholder="账号" value="" type="user" id="loginname" name="loginname">
									<input placeholder="密码" type="password" id="password" name="password">
									<div style="border-radius: 4px; height: 36px; line-height: 36px; margin-bottom: 16px; width: 80%;">
										<input placeholder="验证码" name="code" id="code" value="" type="text" style="width: 50%; float: left; margin-left: 1px;">
										<input type=button style="height: 40px; width: 94px;padding-left:2px; margin-bottom: 0px; border-radius: 5px;border-width: 0px;background-color:#999999;color:white;font-size:20px;text-align:center" id="codeImg" onclick="changeCode()"></input>
										<!--  <img style="height: 40px; width: 104px; margin-bottom: 0px; border-radius: 5px;background-color:#e4841c" id="codeImg" alt="点击更换" title="点击更换"  />  -->
									</div>
									<div style="border-radius: 4px; height: 36px; line-height: 26px; width: 80%; display: inline-block;">
										<a class="forgot" style="float: right">记住密码</a>
										<input name="form-field-checkbox" id="saveid" type="checkbox" onclick="savePaw();" style="padding-top: 0px; width: 15px; height: 15px; border-radius: 15px; float: right" />
									</div>
									<label onclick="severCheck();">登录</label>
								</form>
							</div>
							<div id="login_ewm" style="display: none;">
								<img width="200px" src="static/login/login_home/image/login_ewm.png" style="border: solid 1px #979796; margin-top: 27px" />
							</div>
						</div>
					</div>
				</div>
				<!-- <div style="position: absolute; right: 450px;top:193px; z-index: 100px; width: 400px; height: 400px; margin-top: 20px; background-color: #FFF">
					<table style="width: 100%; height: 100%;">
						<tr>
							<td width="33%" id="txt_dl" onclick="formChange('dl');" style="line-height: 50px; height: 50px; text-align: right;padding-right:20px; color: #5f6064; font-size: 20px; cursor: pointer; font-family: '黑体';">帐号密码登录</td>
							<td width="34%" height="50px" style="line-height: 50px; text-align: center; font-size: 30px; color: #dbdbdb; font-family: '黑体';">|</td>
							<td width="33%" id="txt_ewm" onclick="formChange('ewm');" height="50px" style="line-height: 50px; text-align: left;padding-left:20px; color: #c7c5c5; font-size: 20px; cursor: pointer; font-family: '黑体';">微信扫码登录</td>
						</tr>
						<tr id="form-dl1">
							<td colspan="3" align="center" style="height: 60px;">
								<input id="loginname" name="loginname" class="form-input" style="" type="text" placeholder="用户名" />
							</td>
						</tr>
						<tr id="form-dl2">
							<td colspan="3" align="center" style="height: 60px;">
								<input id="password" name="password" class="form-input" type="password" placeholder="密码" />
							</td>
						</tr>
						<tr id="form-dl3">
							<td colspan="3" align="center" style="height: 60px;">
								<input type="text" name="code" id="code" class="form-input" style="position: absolute; left: 55px; width: 100px; height: 40px; font-size: 14px;" placeholder="验证码" />
								<img style="height: 50px; width: 104px; position: absolute; top: 238px; left: 210px" id="codeImg" alt="点击更换" title="点击更换" src="" />
								<input name="form-field-checkbox" id="saveid" type="checkbox" onclick="savePaw();" style="padding-top: 0px; position: absolute; left: 323px; top: 270px" />

							</td>
						</tr>
						<tr id="form-dl4">
							<td colspan="3" align="center" style="height: 60px;">
								<button onclick="severCheck();"
									style="width: 290px; height: 50px; font-size: 18px; padding: 5px 20px; border-radius: 5px; border: solid 1px #dbdbdb; background-color: #ff0000; color: #FFF; cursor: pointer; font-family: '黑体';">登陆</button>
							</td>
						</tr>
						<tr id="form-ewm" style="display: none;">
							<td colspan="3" style="height: 250px; width: 100%;" align="center">
								<img width="300px" src="static/login/login_home/image/login_ewm.png" />
							</td>
						</tr>
						</tbody>
					</table>
				</div> -->

				<!--<div style=" width:100%;position: static;margin-top: 80px; "><img src="static/login/login_home/image/11111_0001_组-1.png " alt=" "></div>
					<div style="width:100%;position:static;margin-top: 50px; "><img src="static/login/login_home/image/11111_0000_或关注微信公众号.png " alt=" "></div>-->
			</div>
		</div>
		<div class="box b2_bg " style="height: 660px; background: url('static/login/login_home/image/22222_0000.png') no-repeat center center;  background-size: 100% auto;">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div class="box_fixed zIndex40 " align="center ">
				<h2 class="b2_11 " style="position: absolute; left: 400px; top: 100px; opacity: 1; color: #09655a; font-size: 50px;">时刻掌握孩子进出校园动态</h2>
				<h3 class="b2_12 " style="position: absolute; left: 400px; top: 180px; opacity: 1; color: #09655a; font-size: 25px;">线上操作方便，无需电话奔波</h3>
				<div class="b2_13 zIndex40 " style="left: 0px; top: 360px; opacity: 0; transform: rotate(10deg);">
					<img src="static/login/login_home/image/22222_0002.png " alt=" ">
				</div>
				<div class="b2_14 zIndex40 " style="left: 0px; top: 300px; opacity: 0; transform: rotate(-5deg);">
					<img src="static/login/login_home/image/22222_0003.png " alt=" ">
				</div>
				<div class="b2_15 zIndex40 " style="left: 479px; top: 450px; opacity: 0; transform: rotate(-6deg);">
					<img src="static/login/login_home/image/22222_0004.png " alt=" ">
				</div>
				<div class="b2_16 zIndex40 " style="left: 489px; top: 554px; opacity: 0; transform: rotate(-6deg);">
					<img src="static/login/login_home/image/22222_0005.png " alt=" ">
				</div>
			</div>
		</div>
		<div class="box b3_bg " style="height: 660px; background: url('static/login/login_home/image/33333_0004.png') no-repeat center center;  background-size: 100% auto;">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div class="box_fixed zIndex40 ">
				<h2 class="b3_11 " style="position: absolute; z-index: 100; left: 680px; top: 200px; opacity: 0; color: #FFF; font-size: 35px;">家校互动10秒钟完成在线请假</h2>
				<p class="b3_12 " style="position: absolute; z-index: 100; left: 680px; top: 200px; opacity: 0; color: #FFF; font-size: 20px;">随手请假，线上互动，老师家长随时了解详情</p>
				<div class="b3_13 zIndex40 " style="left: 400px; top: 250px; opacity: 0">
					<img src="static/login/login_home/image/33333_0003.png " alt=" ">
				</div>
				<div class="b3_14 zIndex40 " style="right: 400px; top: 250px; opacity: 0">
					<img src="static/login/login_home/image/33333_0002.png " alt=" ">
				</div>
			</div>
		</div>
		<div class="box b4_bg " style="height: 660px; background: url('static/login/login_home/image/44444_0000s_0004.png') no-repeat bottom center;  background-size: 100% auto;">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div style="position: static; height: 100%; margin: 0px 400px;">
				<div class="box_fixed zIndex40 ">
					<h2 class="b4_11 " style="position: absolute; z-index: 100; top: 150px; opacity: 0; color: #09655a; font-size: 40px;">
						孩子成绩班级排名随时 <br />可查
					</h2>
					<p class="b4_12 " style="position: absolute; z-index: 100; left: 560px; top: 270px; opacity: 0; color: #09655a; font-size: 15px;">成绩一出，即时可知，操作方便。</p>
					<div class="b4_13 zIndex40 " style="left: 0px; top: 250px; opacity: 0;">
						<img src="static/login/login_home/image/44444_0000s_0003.png " alt=" ">
					</div>
					<div class="b4_14 zIndex40 " style="left: 435px; top: 440px; opacity: 0;">
						<img src="static/login/login_home/image/44444_0000s_0000.png" style="box-shadow: 5px 5px 5px 5px #ccc;border-radius: 5px;" alt=" ">
					</div>
				</div>
			</div>
		</div>
		<div class="box b5_bg " style="height: 660px; background: url('static/login/login_home/image/55555_0004.png') no-repeat center center;  background-size: 100% auto;">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div class="box_fixed zIndex40 ">
				<h2 class="b5_11 " style="position: absolute; z-index: 100; left: 500px; top: 80px; opacity: 0; color: #feefe3; font-size: 35px;">教子良方在线阅读</h2>
				<div class="b5_13 zIndex40 " style="left: 500px; top: 150px; opacity: 0">
					<img src="static/login/login_home/image/55555_0002.png " style="" alt=" ">
				</div>
			</div>
		</div>
		<div class="box b6_bg ">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div style="position: static; background: #FFF; height: 100%; margin: 0px 400px;">
				<div class="box_fixed zIndex40 ">
					<h2 class="b6_11 " style="position: absolute; z-index: 100; left: 30px; top: 150px; opacity: 0; color: #09655a; font-size: 45px;">班级公告一手掌握</h2>
					<p class="b6_12 " style="position: absolute; z-index: 100; left: 430px; top: 175px; opacity: 0; color: #09655a; font-size: 20px;">学校通知，随时随地，及时可知。</p>
					<div class="b6_13 zIndex40 " style="left: 0px; top: 250px; opacity: 0;">
						<img src="static/login/login_home/image/66666_0000.png " width="254px" height="488" alt=" ">
					</div>
					<div class="b6_14 zIndex40 " style="right: 0px; top: 250px; opacity: 0;">
						<img src="static/login/login_home/image/66666_0001.png " alt=" ">
					</div>
				</div>
			</div>
		</div>
		<div class="box b7_bg " style="height: 660px; background: #fb787d">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div class="box_fixed zIndex40 ">
				<img src="static/login/login_home/image/77777_0000.png " style="position: absolute; left: 0px; top: 100px;" /> <img class="b7_11 " src="static/login/login_home/image/77777_0005.png "
					style="position: absolute; left: 513px; top: 320px; opacity: 0" /> <img class="b7_12 " src="static/login/login_home/image/77777_0008.png "
					style="position: absolute; left: 525px; top: 400px; opacity: 0" /> <img class="b7_13 " src="static/login/login_home/image/77777_0006.png "
					style="position: absolute; left: 513px; top: 480px; opacity: 0" /> <img class="b7_14 " src="static/login/login_home/image/77777_0007.png "
					style="position: absolute; left: 541px; top: 560px; opacity: 0" /> <img class="b7_15 " src="static/login/login_home/image/77777_0001.png "
					style="position: absolute; left: 1204px; top: 320px; opacity: 0" /> <img class="b7_16 " src="static/login/login_home/image/77777_0004.png "
					style="position: absolute; left: 1201px; top: 400px; opacity: 0" /> <img class="b7_17 " src="static/login/login_home/image/77777_0002.png "
					style="position: absolute; left: 1247px; top: 480px; opacity: 0" /> <img class="b7_18 " src="static/login/login_home/image/77777_0003.png "
					style="position: absolute; left: 1201px; top: 560px; opacity: 0" />
			</div>
		</div>
		<div class="box b8_bg " style="height: 660px; background: url('static/login/login_home/image/88888_0001.png') no-repeat center center;  background-size: 100% auto;">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div class="b8_11 zIndex40 " style="left: 900px; bottom: 100px;">
				<a onclick="$( '#more').click(); " style="cursor: pointer;">
					<img src="static/login/login_home/image/88888_0000.png " alt=" ">
				</a>
			</div>
		</div>
		<div class="box b9_bg " align="center">
			<div class="load " style="display: none;">
				<p>
					<img src="loading.gif " alt=" ">
				</p>
				<div class="load_bg "></div>
			</div>
			<div class="zIndex40 " style="width: 1082px; position: relative; height: 85%;">
				<h2 style="width: 1082px; height: 100px; background-image: url(static/login/login_home/image/bbb_0002.png);  background-size: 100% auto; position: absolute; left: 0px; bottom: 500px;"></h2>
				<table class="ComebackTable " style="position: absolute; left: 0px; bottom: 0px;">
					<tr>
						<td rowspan="2 ">
							<div class="wrap" style="width: 248px; height: 290px;">
								<div class="Comebackbox" style="width: 248px; height: 290px;">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0000.png) no-repeat 100% 100%;  background-size: 100% 100%;" style="width: 248px; height: 290px;"></a>
									<a style="background-color: #1aa0ff;width: 248px; height: 290px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:100px 20px">
									<span style="margin-top: 90px">全方位定制你的学校官网，打开微信既可以浏览学校官方信息。</span></a>
								</div>
							</div>
						</td>
						<td colspan="3 ">
							<div class="wrap " style="width: 385px; height: 134px; margin-left: 0px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0001.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #ff863a;width: 385px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:30px 20px">
									通知推送稳定，不怕被各种手机助手拦截。业务完全免费，无需担心忘记续费而遗漏学校重要通知。</a>
								</div>
							</div>
							<div class="wrap " style="width: 385px; height: 134px; margin-left: 405px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0002.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #7d5dc7;width: 385px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:30px 20px">
									每日作业，直接送达到家长微信中，让家长全面了解孩子作业情况。</a>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="wrap " style="width: 248px; height: 134px; top: 168px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0003.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #67af00;width: 248px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:35px 20px">
									捕捉学校里的美好班级时光，分享给家长这份美好，多多点赞互动，关系更亲密！</a>
								</div>
							</div>
						</td>
						<td>
							<div class="wrap " style="width: 248px; height: 134px; top: 168px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0004.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #e86c6b;width: 248px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:35px 20px">
									随时查看通讯录，从此沟通不再是烦恼。</a>
								</div>
							</div>
						</td>
						<td>
							<div class="wrap " style="width: 248px; height: 134px; top: 168px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0005.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #1aa0ff;width: 248px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:35px 20px">
									不再麻烦，拿起手机几步搞定，一切有迹可循。</a>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="wrap " style="width: 248px; height: 134px; top: 324px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0006.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #e76b6a;width: 248px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:25px 20px">
									即填即提交，结果马上知道。针对统计情况，查看具体参与人员名单。</a>
								</div>
							</div>
						</td>
						<td>
							<div class="wrap " style="width: 248px; height: 134px; top: 324px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0007.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #7d5cc6;width: 248px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:25px 20px">
									特聘各类专家，为大家分享丰富的教育资讯，学习材料以及报考知道使用的图文资讯。
									</a>
								</div>
							</div>
						</td>
						<td>
							<div class="wrap " style="width: 248px; height: 134px; top: 324px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0008.png) no-repeat 100% 100%;  background-size: 100% 100%;"></a>
									<a style="background-color: #ff873a;width: 248px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:35px 20px">
									专门针对教师使用场景，内建日程安排功能，规划每一天。
									</a>
								</div>
							</div>
						</td>
						<td>
							<div class="wrap " style="width: 248px; height: 134px; top: 324px;">
								<div class="Comebackbox ">
									<a style="background: url(static/login/login_home/image/aaaaaaaaaa_0009.png) no-repeat;  background-size: 100% 100%;"></a>
									<a style="background-color: #67b000;width: 248px; height: 134px;color:#FFF;text-align: center;vertical-align: middle;box-sizing: border-box;padding:35px 20px">
									所有校园缴费直接通知到家长，清楚分明。
									</a>
								</div>
							</div>
						</td>
					</tr>
				</table>

			</div>
		</div>
	</div>	
<div style="width: 100%;z-index:9999; position: fixed; bottom: 20px; height: 60px; background:#48c375 url(static/login/login_home/image/99999_0000_3.png) no-repeat center center;  background-size: auto 100%;"></div>
<div style="text-align: center;width: 100%;z-index:9999; position: fixed; bottom: 0px; height: 20px;background:#48c375 url(static/login/login_home/image/99999_0000_2.png) no-repeat center center;  background-size: auto 100%;"><font style="color: white;"><a style="color:rgba(255,255,255,1)" href="http://www.miitbeian.gov.cn">备案号：粤ICP备16047692号-1</a>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 工信部链接： <a style="color:rgba(255,255,255,1)" href="http://www.miitbeian.gov.cn">http://www.miitbeian.gov.cn</a></font></div>
</body>




<script type="text/javascript">
var ie6 = !-[1, ] && !window.XMLHttpRequest;
ie6 || loadImg.init();
	function formChange(t) {
		if (t == 'dl') {
			$('#span_dl').addClass('one cur');
			$('#span_ewm').removeClass('one cur');
			$('#login_dl').css('display', '');
			$('#login_ewm').css('display', 'none');
		} else if (t == 'ewm') {
			$('#span_ewm').addClass('one cur');
			$('#span_dl').removeClass('one cur');
			$('#login_ewm').css('display', '');
			$('#login_dl').css('display', 'none');
		}
	}

	//清除加载进度
	function hangge() {
		$("#jzts").hide();
	}

	//显示加载进度
	function jzts() {
		$("#jzts").show();
	}

	var keyStr = "ABCDEFGHIJKLMNOP" + 
				"QRSTUVWXYZabcdef" + 
				"ghijklmnopqrstuv" + 
				"wxyz0123456789+/" + 
				"=";

	function encode64(input) { 
		input = unicodetoBytes(input); 
		var output = ""; 
		var chr1, chr2, chr3 = ""; 
		var enc1, enc2, enc3, enc4 = ""; 
		var i = 0;

		do { 
			chr1 = input[i++]; 
			chr2 = input[i++]; 
			chr3 = input[i++];

			enc1 = chr1 >> 2; 
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4); 
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6); 
			enc4 = chr3 & 63;

			if (isNaN(chr2)) { 
				enc3 = enc4 = 64; 
			} else if (isNaN(chr3)) { 
				enc4 = 64; 
			}

			output = output + 
					keyStr.charAt(enc1) + 
					keyStr.charAt(enc2) + 
					keyStr.charAt(enc3) + 
					keyStr.charAt(enc4); 
			chr1 = chr2 = chr3 = ""; 
			enc1 = enc2 = enc3 = enc4 = ""; 
		} while (i < input.length);

		return output; 
	}

	function unicodetoBytes(s) { 
		var result=new Array(); 
		if(s==null || s=="") return result; 
			result.push(255); // add "FE" to head 
			result.push(254); 
		for(var i=0;i<s.length;i++) { 
			var c=s.charCodeAt(i).toString(16); 
			if(c.length==1) i="000"+c; 
			else if(c.length==2) c="00"+c; 
			else if(c.length==3) c="0"+c; 
			var var1=parseInt( c.substring(2),16); 
			var var2=parseInt( c.substring(0,2),16); 
			result.push( var1); 
			result.push(var2) ; 
		} 
		return result; 
	}
	
	function bytesToUnicode(bs) { 
		var result=""; 
		var offset=0; 
		if(bs.length>=2 && bs[0]==255 && bs[1]==254) offset=2; // delete "FE" 
		for(var i=offset;i<bs.length;i+=2) { 
			var code=bs[i]+(bs[i+1]<<8); 
			result+=String.fromCharCode(code); 
		} 
		return result; 
	} 

	//服务器校验
	function severCheck() {
		if (check()) {

			var loginname = $("#loginname").val();
			var password = $("#password").val();
			var code =encode64(loginname)+ "," + encode64(password)+"," +encode64($("#code").val());
			jzts();
			$.ajax({
				type : "POST",
				url : 'login_login',
				data : {
					KEYDATA : code,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					if ("success" == data.result) {
						saveCookie();
						window.location.href = "main/index";
					} else if ("usererror" == data.result) {
						hangge();
						$("#loginname").tips({
							side : 1,
							msg : "用户名或密码有误",
							bg : '#FF5080',
							time : 2
						});
						$("#loginname").focus();
					} 
					/*else if ("codeerror" == data.result) {
						hangge();
						changeCode();
						$("#code").tips({
							side : 1,
							msg : "验证码输入有误",
							bg : '#FF5080',
							time : 2
						});
						$("#code").focus();
					} */
					else if ("locked" == data.result) {
						hangge();
						changeCode();
						$("#loginname").tips({
							side : 1,
							msg : "该账号已锁定",
							bg : '#FF5080',
							time : 2
						});
						$("#code").focus();
					} else {
						hangge();
						$("#loginname").tips({
							side : 1,
							msg : "缺少参数",
							bg : '#FF5080',
							time : 2
						});
						$("#loginname").focus();
					}
				}
			});
		}
	}

	$(document).ready(function() {
		//changeCode();
		//$("#codeImg").bind("click", changeCode);
	});

	function changeCode() {
		//$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		//$("#codeImg").attr("src",generateMixed(4));
		var code = generateMixed(4);
		
		if(document.getElementById("codeImg").innerText){
			document.getElementById("codeImg").innerText = code;
		} else {
			document.getElementById("codeImg").textContent  = code;
		}
		document.getElementById("codeImg").value = code.replace(/\s+/g, "");
	}


	function generateMixed(n) {
		var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
		var res = "";
		for(var i = 0; i < n ; i ++) {
			var id = Math.ceil(Math.random()*61);
			res += " "+chars[id];
		}
		return res;
	}
	
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#to-recover").trigger("click");
			severCheck();
			
		}
	});

	function genTimestamp() {
		var time = new Date();
		return time.getTime();
	}

	//客户端校验
	function check() {

		if ($("#loginname").val() == "") {

			$("#loginname").tips({
				side : 2,
				msg : '用户名不得为空',
				bg : '#FF5080',
				time : 2
			});

			$("#loginname").focus();
			return false;
		} else {
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}

		if ($("#password").val() == "") {

			$("#password").tips({
				side : 2,
				msg : '密码不得为空',
				bg : '#FF5080',
				time : 2
			});

			$("#password").focus();
			return false;
		}
		if ($("#code").val() == "") {

			$("#code").tips({
				side : 1,
				msg : '验证码不得为空',
				bg : '#FF5080',
				time : 2
			});

			$("#code").focus();
			return false;
		}
		var codeImg = $("#codeImg").val();
		if($("#code").val() !=codeImg){
			
			$("#code").tips({
				side : 1,
				msg : '验证码输入有误',
				bg : '#FF5080',
				time : 2
			});

			$("#code").focus();
			return false;
		}

		return true;
	}

	function savePaw() {
		if (!$("#saveid").is(':checked')) {
			$.cookie('loginname', '', {
				expires : -1
			});
			$.cookie('password', '', {
				expires : -1
			});
			$("#loginname").val('');
			$("#password").val('');
		}
	}

	function saveCookie() {
		if ($("#saveid").is(':checked')) {
			$.cookie('loginname', $("#loginname").val(), {
				expires : 7
			});
			$.cookie('password', $("#password").val(), {
				expires : 7
			});
		}
	}
	function quxiao() {
		$("#loginname").val('');
		$("#password").val('');
	}

	jQuery(function() {
		var loginname = $.cookie('loginname');
		var password = $.cookie('password');
		if (typeof (loginname) != "undefined"
				&& typeof (password) != "undefined") {
			$("#loginname").val(loginname);
			$("#password").val(password);
			$("#saveid").attr("checked", true);
			$("#code").focus();
		}
	});
	//TOCMAT重启之后 点击左侧列表跳转登录首页 
	if (window != top) {
		top.location.href = location.href;
	}
	
</script>
<style type="text/css">
.form-input {
	width: 250px;
	height: 40px;
	font-size: 18px;
	padding: 5px 20px;
	border-radius: 5px;
	border: solid 1px #dbdbdb;
	color: #5f6064;
}

.form-input:focus {
	width: 250px;
	height: 40px;
	font-size: 18px;
	padding: 5px 20px;
	border-radius: 5px;
	border: solid 1px rgb(255, 46, 24);
	margin: 0px;
	outline: none;
	color: #5f6064;
}

.ComebackTable td {
	border: solid 10px #FFF;
	width: 248px;
	height: 134px;
	margin: 0px;
}

.wrap {
	position: relative;
	perspective: 800px;
}

.Comebackbox {
	width: 100%;
	height: 100%;
	position: absolute;
	transform-style: preserve-3d;
	transition: transform 1s;
	transform-origin: right center;
}

.Comebackbox a {
	display: block;
	position: absolute;
	width: 100%;
	height: 100%;
	backface-visibility: hidden;
	cursor: pointer;
}

.Comebackbox a:nth-of-type(1) {
	transition: .8s transform
}

.Comebackbox a:nth-of-type(2) {
	transform: rotateY(180deg);
	transition: .8s transform
}

.Comebackbox:hover {
	transform: translateX(-100%) rotateY(-180deg);
}

.clear {
	clear: both;
}

.login {
	position: absolute;
	right: 443px;
	top: 157px;
}

.login div {
	position: static;
}

.login span {
	display: block;
	width: 100%;
}

.login_box {
	position: absolute;
	width: 320px;
	display: none;
	top: 100%;
	left: -120px;
}

.login_arrw {
	width: 100%;
}

.login_arrw img {
	width: 19px;
	display: block;
	margin: 0px auto;
}

.login_main {
	width: 100%;
	background: rgba(255, 255, 255, 0.9) none repeat scroll 0% 0%;
	border-radius: 6px;
	padding: 6px 0px 26px;
	margin-top: -1px;
	height: 300px;
}

.login_box input {
	width: 239px;
	padding-left: 15px;
	border: 1px solid rgb(221, 221, 221);
	color: rgb(51, 51, 51);
	background: rgb(255, 255, 255) none repeat scroll 0% 0%;
	border-radius: 4px;
	height: 36px;
	line-height: 36px;
	margin-bottom: 16px;
}

.login_box:focus {
	outline: none;
}

.login_box label {
	width: 40%;
	margin: 0px auto;
	display: block;
	border-radius: 4px;
	height: 36px;
	line-height: 36px;
	text-align: center;
	background: rgb(62, 154, 226) none repeat scroll 0% 0%;
	color: rgb(255, 255, 255);
	cursor: pointer;
}

.loginsel {
	width: 100%;
	padding: 0px 2%;
	border-bottom: 1px solid rgb(241, 241, 241);
	box-sizing: border-box;
	margin: 0px auto 16px;
}

.loginsel span {
	width: 50%;
	box-sizing: border-box;
	display: block;
	text-align: center;
	padding: 5px 0px;
	border-bottom: 2px solid rgb(255, 255, 255);
	float: left;
	color: rgb(51, 51, 51);
	margin-right: 0%;
	margin-left: 0px;
}

.loginsel span.one {
	margin-left: 0%;
	margin-right: 0px;
}

.loginsel span:hover, .loginsel span.cur {
	border-color: rgb(35, 131, 205);
	color: rgb(35, 131, 205);
}

.login_box a.forgot {
	color: rgb(153, 153, 153);
	font-size: 12px;
	line-height: 20px;
}

.login_box a.forgot:hover {
	background: transparent none repeat scroll 0px 0px;
}
</style>

</html>
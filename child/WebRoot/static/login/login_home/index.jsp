<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${pd.SYSNAME}</title>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">

		<link rel="shortcut icon" href="image/logo.png">
		<meta name="description" content="">
		<meta name="keywords" content="">

		<script src="jquery-1.9.1.min.js"></script>
		<script src="parallax.js"></script>
		<link href="jquery.fancybox.css">
		<script src="jquery.fancybox.pack.js"></script>

		<link media="all" href="index.css" type="text/css" rel="stylesheet">
	</head>

	<body style="height: 6080px;">
		<div class="wrapHeader">
			<div class="header">
				<h1 class="logo" align="right"><a href=""><img src="image/logo.png" alt="掌上关爱" title="掌上关爱" height="30"style="margin: 15px 0px;"></a></h1>
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
			<div class="box b1_bg" style="background-image: url(image/11111_0003_页头建筑.png);background-size: 100% 100%;">
				<div class="load" style="display: none;">
					<p><img src="loading.gif" alt=""></p>
					<div class="load_bg"></div>
				</div>
				<div class="box_fixed b1_fixed b1-flash zIndex40" align="center">
					<h2 style="color:#73af44;font-size: 80px;font-weight: 100 ;margin-top: 0px;">一路相伴，与您同行</h2>
					<h3 style="color:#43698f;font-size: 30px;font-weight: 100 ;margin-top: 20px;">校园微信，掌上关爱</h3>
					<div style="position: absolute;left: 760px;z-index: 100px;width:400px;height: 400px;margin-top: 20px;background-color: #FFF">
						<table style="width: 100%;height: 100%;">
							<tr>
								<td width="33%" id="txt_dl" onclick="formChange('dl');" style="line-height:50px;height: 50px;text-align: right;color: #5f6064;font-size: 20px;cursor: pointer;">登录</td>
								<td width="34%" height="50px" style="line-height: 50px;text-align: center;font-size: 30px;color: #dbdbdb;">|</td>
								<td width="33%" id="txt_ewm" onclick="formChange('ewm');" height="50px" style="line-height: 50px;text-align: left;color: #c7c5c5;font-size: 20px;cursor: pointer;">二维码</td>
							</tr>
							<tbody id="form-dl">
								<tr>
									<td colspan="3" align="center" style="height: 60px;"><input id="" class="form-input" style="" type="text" placeholder="用户名" /></td>
								</tr>
								<tr>
									<td colspan="3" align="center" style="height: 60px;"><input id="" class="form-input" type="text" placeholder="密码" /></td>
								</tr>
								<tr>
									<td colspan="3" align="center" style="height: 60px;"></td>
								</tr>
								<tr>
									<td colspan="3" align="center" style="height: 60px;"><button style="width: 290px;height: 50px;font-size: 18px;padding: 5px 20px;border-radius: 5px;border: solid 1px #dbdbdb;background-color: #ff0000;color: #FFF;cursor: pointer;font-family:'黑体';">登陆</button></td>
								</tr>
							</tbody>
							<tbody style="display: none;" id="form-ewm">
								<tr>
									<td colspan="3" style="height: 250px;width: 100%;" align="center">
										<img width="300px" src="image/login_ewm.png" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<style type="text/css">
						.form-input {
							width: 250px;
							height: 50px;
							font-size: 18px;
							padding: 5px 20px;
							border-radius: 5px;
							border: solid 1px #dbdbdb;
							color: #5f6064;
						}
						
						.form-input:hover {
							width: 250px;
							height: 50px;
							font-size: 18px;
							padding: 5px 20px;
							border-radius: 5px;
							border: solid 1px #ff2e18;
							color: #5f6064;
						}
					</style>
					<script type="text/javascript">
						function formChange(t) {
							if(t == 'dl') {
								$('#txt_dl').css('color', '#5f6064');
								$('#txt_ewm').css('color', '#c7c5c5');
								$('#form-dl').css('display', '');
								$('#form-ewm').css('display', 'none');
							} else if(t == 'ewm') {
								$('#txt_dl').css('color', '#c7c5c5');
								$('#txt_ewm').css('color', '#5f6064');
								$('#form-dl').css('display', 'none');
								$('#form-ewm').css('display', '');
							}
						}
					</script>
					<!--<div style=" width:100%;position: static;margin-top: 80px; "><img src="image/11111_0001_组-1.png " alt=" "></div>
					<div style="width:100%;position:static;margin-top: 50px; "><img src="image/11111_0000_或关注微信公众号.png " alt=" "></div>-->
				</div>
			</div>
			<div class="box b2_bg " style="height: 660px;background-image: url(image/22222_0000_矢量智能对象.png);background-size: 100% 100%; ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div class="box_fixed zIndex40 " align="center ">
					<h2 class="b2_11 " style="position: absolute;left: 400px;top: 100px; opacity: 1;color: #09655a;font-size: 50px; ">时刻掌握孩子进出校园动态</h2>
					<h3 class="b2_12 " style="position: absolute;left: 400px;top: 180px; opacity: 1;color: #09655a;font-size: 25px; ">线上操作方便，无需电话奔波</h3>
					<div class="b2_13 zIndex40 " style="left: 0px;top: 360px;opacity: 0;transform: rotate(10deg); "><img src="image/22222_0002_考勤查看-拷贝.png " alt=" "></div>
					<div class="b2_14 zIndex40 " style="left: 0px;top: 300px;opacity: 0;transform: rotate(-5deg); "><img src="image/22222_0003_通知截图.png " alt=" "></div>
				</div>
			</div>
			<div class="box b3_bg " style="height: 660px;background:#FFF url(image/33333_0004_图层-7-副本-7.png);;background-size: 100% 100%; ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div class="box_fixed zIndex40 ">
					<h2 class="b3_11 " style="position: absolute;z-index: 100;left: 680px;top: 200px; opacity: 0;color: #FFF;font-size: 35px; ">家校互动10秒钟完成在线请假</h2>
					<p class="b3_12 " style="position: absolute;z-index: 100;left: 680px;top: 200px; opacity: 0;color: #FFF;font-size: 20px; ">随手请假，线上互动，老师家长随时了解详情</p>
					<div class="b3_13 zIndex40 " style="left: 400px;top: 250px;opacity: 0 "><img src="image/33333_0003_请假申请-拷贝-时间段.png " alt=" "></div>
					<div class="b3_14 zIndex40 " style="right: 400px;top: 250px;opacity: 0 "><img src="image/33333_0002_请假处理-拷贝.png " alt=" "></div>
				</div>
			</div>
			<div class="box b4_bg " style="height: 660px; ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div style="position: static;background:#FFF url(image/44444_0000s_0004_图层-327.png) no-repeat right bottom;height: 100%;margin:0px 400px; ">
					<div class="box_fixed zIndex40 ">
						<h2 class="b4_11 " style="position: absolute;z-index: 100;top: 150px; opacity: 0;color: #09655a;font-size: 40px; ">孩子成绩班级排名随时
					<br />可查</h2>
						<p class="b4_12 " style="position: absolute;z-index: 100;left: 560px;top: 270px; opacity: 0;color: #09655a;font-size: 15px; ">成绩一出，即时可知，操作方便。</p>
						<div class="b4_13 zIndex40 " style="left: 0px;top: 250px;opacity: 0; "><img src="image/44444_0000s_0003_iMac-Flat.png " alt=" "></div>
						<div class="b4_14 zIndex40 " style="left: 435px;top: 440px;opacity: 0; "><img src="image/44444_0000s_0000_成绩查询.png " alt=" "></div>
					</div>
				</div>
			</div>
			<div class="box b5_bg " style="height: 660px;background:#FFF url(image/55555_0003_图层-3261.png);background-size: 100% 100%; ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div class="box_fixed zIndex40 ">
					<h2 class="b5_11 " style="position: absolute;z-index: 100;left: 500px;top: 80px; opacity: 0;color: #feefe3;font-size: 35px; ">教子良方在线阅读</h2>
					<div class="b5_13 zIndex40 " style="left: 500px;top: 150px;opacity: 0 "><img src="image/55555_0002_矢量智能对象.png " alt=" "></div>
				</div>
			</div>
			<div class="box b6_bg ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div style="position: static;background:#FFF;height: 100%;margin:0px 400px; ">
					<div class="box_fixed zIndex40 ">
						<h2 class="b6_11 " style="position: absolute;z-index: 100;left: 30px;top: 150px; opacity: 0;color: #09655a;font-size: 45px; ">班级公告一手掌握</h2>
						<p class="b6_12 " style="position: absolute;z-index: 100;left: 430px;top: 175px; opacity: 0;color: #09655a;font-size: 20px; ">学校通知，随时随地，及时可知。</p>
						<div class="b6_13 zIndex40 " style="left: 0px;top: 250px;opacity: 0; "><img src="image/66666_0000_请假处理通知-拷贝.png " alt=" "></div>
						<div class="b6_14 zIndex40 " style="right: 0px;top: 250px;opacity: 0; "><img src="image/66666_0001_矢量智能对象.png " alt=" "></div>
					</div>
				</div>
			</div>
			<div class="box b7_bg " style="height: 660px;background:#fb787d ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div class="box_fixed zIndex40 ">
					<img src="image/77777_0000_矢量智能对象.png " style="position: absolute;left: 0px;top: 100px; " />
					<img class="b7_11 " src="image/77777_0005___-chat-1.png " style="position: absolute;left: 513px;top: 320px;opacity: 0 " />
					<img class="b7_12 " src="image/77777_0008___-chat-3.png " style="position: absolute;left: 525px;top: 400px;opacity: 0 " />
					<img class="b7_13 " src="image/77777_0006___-chat-2.png " style="position: absolute;left: 513px;top: 480px;opacity: 0 " />
					<img class="b7_14 " src="image/77777_0007___-chat-3-拷贝.png " style="position: absolute;left: 541px;top: 560px;opacity: 0 " />
					<img class="b7_15 " src="image/77777_0001___-chat-1.png " style="position: absolute;left: 1204px;top: 320px;opacity: 0 " />
					<img class="b7_16 " src="image/77777_0004___-chat-3.png " style="position: absolute;left: 1201px;top: 400px;opacity: 0 " />
					<img class="b7_17 " src="image/77777_0002___-chat-2.png " style="position: absolute;left: 1247px;top: 480px;opacity: 0 " />
					<img class="b7_18 " src="image/77777_0003___-chat-3-拷贝.png " style="position: absolute;left: 1201px;top: 560px;opacity: 0 " />
				</div>
			</div>
			<div class="box b8_bg " style="height: 660px;background-image: url(image/88888_0001_更方便、更高效、更便捷.png);background-size: 100% 100%; ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div class="b8_11 zIndex40 " style="left: 900px;bottom: 100px; ">
					<a onclick="$( '#more').click(); " style="cursor: pointer; "><img src="image/88888_0000_箭头.png " alt=" "></a>
				</div>
			</div>
			<div class="box b9_bg " align="center ">
				<div class="load " style="display: none; ">
					<p><img src="loading.gif " alt=" "></p>
					<div class="load_bg "></div>
				</div>
				<div class="zIndex40 " style="width: 1082px;position:relative;height: 85%; ">
					<h2 style="width: 1082px;height: 100px;background-image: url(image/bbb_0002_图层-3.png);background-size: 100% 100%;position:absolute;left:0px;bottom: 500px; "></h2>
					<table class="ComebackTable " style="position:absolute;left:0px;bottom: 0px; ">
						<tr>
							<td rowspan="2 ">
								<div class="wrap " style="width:248px;height: 290px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0000_图层-1.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #1aa0ff; "></a>
									</div>
								</div>
							</td>
							<td colspan="3 ">
								<div class="wrap " style="width:385px;height: 134px;margin-left: 0px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0001_图层-2.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #ff863a; "></a>
									</div>
								</div>
								<div class="wrap " style="width:385px;height: 134px;margin-left: 405px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0002_图层-3.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #7d5dc7; "></a>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="wrap " style="width:248px;height: 134px;top: 168px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0003_图层-4.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #67af00; "></a>
									</div>
								</div>
							</td>
							<td>
								<div class="wrap " style="width:248px;height: 134px;top: 168px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0004_图层-5.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #e86c6b; "></a>
									</div>
								</div>
							</td>
							<td>
								<div class="wrap " style="width:248px;height: 134px;top: 168px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0005_图层-6.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #1aa0ff; "></a>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="wrap " style="width:248px;height: 134px;top: 324px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0006_图层-7.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #e76b6a; "></a>
									</div>
								</div>
							</td>
							<td>
								<div class="wrap " style="width:248px;height: 134px;top: 324px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0007_图层-8.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #7d5cc6; "></a>
									</div>
								</div>
							</td>
							<td>
								<div class="wrap " style="width:248px;height: 134px;top: 324px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0008_图层-9.png) no-repeat 100% 100%;background-size: 100% 100%; "></a>
										<a style="background-color: #ff873a; "></a>
									</div>
								</div>
							</td>
							<td>
								<div class="wrap " style="width:248px;height: 134px;top: 324px; ">
									<div class="Comebackbox ">
										<a style="background: url(image/aaaaaaaaaa_0009_图层-10.png) no-repeat; background-size: 100% 100%; "></a>
										<a style="background-color: #67b000; "></a>
									</div>
								</div>
							</td>
						</tr>
					</table>

				</div>
				<div class="zIndex40 " style="width: 100%;position: absolute;height: 80px;background: url(image/99999_0000_邮箱.png);background-size: 100% 100%;bottom: 0px; ">
				</div>
			</div>

		</div>
	</body>

	<style type="text/css ">
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
			transform: rotateY( 180deg);
			transition: .8s transform
		}
		
		.Comebackbox:hover {
			transform: translateX( -100%) rotateY( -180deg);
		}
	</style>

	<script>
		//document.body.style.overflow = 'hidden';
		var ie6 = !-[1, ] && !window.XMLHttpRequest;
		ie6 || loadImg.init();

		function tologin() {
			$(".nav a ").removeClass("cur ").eq(9).addClass("cur ");
			$(".fixedNav li ").removeClass("cur ").eq(9).addClass("cur ");
			/*$.fancybox({
        'type': 'ajax',
        'href': 'login.html' 
    });*/
		}
	</script>

</html>
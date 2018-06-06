
function getByClass(oParent, sClass) {//通过className获取对象
	var aChild = oParent.getElementsByTagName('*');
	var regex = new RegExp('\\b' + sClass + '\\b', 'i');
	var result = [];

	for (var i=0; i<aChild.length; i++) {
		if (regex.test(aChild[i].className)) {//判断对象className是否符合条件
			result.push(aChild[i]);
		}
	}

	return result;
}

//视察滚动
function parallaxAnimate(sDom, region, scrollTop) {
/*
sDom：运动对象class名称或者id名称
 region, scrollTop
*/
	var elm; //要运动的对象

	//判断是.还是#，使用不同的方法获取对象
	if (sDom.charAt(0) == ".") {
		var oWrap = document.getElementById("wrapBox");
		elm = getByClass(oWrap, sDom.substring(1))[0];
	} else if (sDom.charAt(0) == "#") {
		elm = document.getElementById(sDom.substring(1));
	}

	//console.log(elm);
	var area = (scrollTop - region.startTop)/(region.endTop - region.startTop);

	for (var i in region.startCss) {
			var target = (region.endCss[i] - region.startCss[i])*area;
			var json = {};
			json[i] = region.startCss[i] + target;
			//$(elm).css(json);
		if (i == 'backgroundPosition') {
			$(elm).stop().animate({'background-position-x': '50%', 'background-position-y': target}, 1100, 'linear');
		} else {
			$(elm).stop().animate(json, 1100, 'linear');
		}
	}
}

function fnLoad() {
	var $wrapBox = $("#wrapBox");
	var windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
	var boxHeight = (windowHeight-60)>570 ? (windowHeight-60) : 570;

	$(".box").css('height', boxHeight);//初始化.box的高度
	$(".b1_fixed").css('top', (boxHeight-570)/2);//初始化.box的高度
	
	var line = {
		lineHeight: boxHeight*$(".box").length,//虚拟轴高度
		pageIndex: 0,//当前page索引
		scrollRatio: 1,//滚动条和虚拟轴的比率，越大则滚动得越慢（滚动条滚几倍，虚拟轴才滚1）
		isAnimRuning: false,//自动滚动
		obj:[]//虚拟轴上的运动对象
	};//虚拟轴
	document.getElementsByTagName("body")[0].style.height = (line.lineHeight + 60) * line.scrollRatio + "px";

	var page_1 = 0,
		page_2 = line.scrollRatio*boxHeight,
		page_3 = line.scrollRatio*boxHeight*2,
		page_4 = line.scrollRatio*boxHeight*3,
		page_5 = line.scrollRatio*boxHeight*4,
		page_6 = line.scrollRatio*boxHeight*5,
		page_7 = line.scrollRatio*boxHeight*6;

	var beforePage = line.scrollRatio*200,
		afterPage = line.scrollRatio*200;

	var speedTop = 800;
	var scale = (line.lineHeight + 60 - windowHeight)/((line.lineHeight + 60) * line.scrollRatio - windowHeight);

	//fixed
	line.obj.push({
		dom: ".b1_fixed",
		regions: [{
			startTop: 0,
			endTop: (boxHeight-510)/scale,
			startCss: {
				top: (boxHeight-570)/2
			},
			endCss: {
				top: boxHeight-570
			}
		}]
	}, {
		dom: ".b2_fixed",
		regions: [{
			startTop: 510/scale,
			endTop: (boxHeight*2-510)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight-570
			}
		}]
	}, {
		dom: ".b3_fixed",
		regions: [{
			startTop: (boxHeight+510)/scale,
			endTop: (boxHeight*3-510)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight-570
			}
		}]
	}, {
		dom: ".b4_fixed",
		regions: [{
			startTop: (boxHeight*2+510)/scale,
			endTop: (boxHeight*4-510)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight-570
			}
		}]
	}, {
		dom: ".b5_fixed",
		regions: [{
			startTop: (boxHeight*3+510)/scale,
			endTop: (boxHeight*5-510)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight-570
			}
		}]
	}, {
		dom: ".b6_fixed",
		regions: [{
			startTop: (boxHeight*4+510)/scale,
			endTop: (boxHeight*6-510)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight-570
			}
		}]
	}, {
		dom: ".b7_fixed",
		regions: [{
			startTop: (boxHeight*5+510)/scale,
			endTop: (boxHeight*7-510)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight-570
			}
		}]
	});

	//box bg
	line.obj.push({
		dom: ".box1_bg",
		regions: [{
			startTop: 0,
			endTop: (boxHeight-60)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight - 1100
			}
		}]
	}, {
		dom: ".box2_bg",
		regions: [{
			startTop: 0,
			endTop: (boxHeight*2-60)/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight - 1100
			}
		}]
	}, {
		dom: ".box3_bg",
		regions: [{
			startTop: boxHeight/scale,
			endTop: boxHeight*3/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight - 1100
			}
		}]
	}, {
		dom: ".box4_bg",
		regions: [{
			startTop: boxHeight*2/scale,
			endTop: boxHeight*4/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight - 1100
			}
		}]
	}, {
		dom: ".box5_bg",
		regions: [{
			startTop: boxHeight*3/scale,
			endTop: boxHeight*5/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight - 1100
			}
		}]
	}, {
		dom: ".box6_bg",
		regions: [{
			startTop: boxHeight*4/scale,
			endTop: boxHeight*6/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight - 1100
			}
		}]
	}, {
		dom: ".box7_bg",
		regions: [{
			startTop: boxHeight*5/scale,
			endTop: boxHeight*7/scale,
			startCss: {
				top: 0
			},
			endCss: {
				top: boxHeight - 1100
			}
		}]
	});

	//box1
	line.obj.push({
		dom: ".b1_04",
		regions: [{
			startTop: page_1,
			endTop: page_2,
			startCss: {
				top: 470
			},
			endCss: {
				top: 400 + 470
			}
		}]
	});

	//box2 --> box3
	line.obj.push({
		dom: ".b2_fixed",
		regions: [{
			startTop: page_1,
			endTop: page_3,
			startCss: {
				top: -400
			},
			endCss: {
				top: 600 + 30
			}
		}]
	}, {
		dom: ".b2_04",
		regions: [{
			startTop: page_1,
			endTop: page_3,
			startCss: {
				top: -100
			},
			endCss: {
				top: 200+ 350
			}
		}]
	}, {
		dom: ".b2_06",
		regions: [{
			startTop: page_1,
			endTop: page_3,
			startCss: {
				top: -200
			},
			endCss: {
				top: speedTop/2 + 300
			}
		}]
	});

	// line.obj.push({
	// 	dom: ".b4_fixed",
	// 	regions: [{
	// 		startTop: (page_3 + 800)/(line.lineHeight + 60 - windowHeight)*((line.lineHeight + 60) * line.scrollRatio - windowHeight),
	// 		endTop: page_5-800,
	// 		startCss: {
	// 			top: -100
	// 		},
	// 		endCss: {
	// 			top: boxHeight-750
	// 		}
	// 	}]
	// });
	// line.obj.push({
	// 	dom: ".b6_fixed",
	// 	regions: [{
	// 		startTop: (page_5 + 800)/(line.lineHeight + 60 - windowHeight)*((line.lineHeight + 60) * line.scrollRatio - windowHeight),
	// 		endTop: page_7-800,
	// 		startCss: {
	// 			top: -50
	// 		},
	// 		endCss: {
	// 			top: boxHeight-750
	// 		}
	// 	}]
	// });
	// line.obj.push({
	// 	dom: ".b7_fixed",
	// 	regions: [{
	// 		startTop: (page_6 + 800)/(line.lineHeight + 60 - windowHeight)*((line.lineHeight + 60) * line.scrollRatio - windowHeight),
	// 		endTop: page_7,
	// 		startCss: {
	// 			top: 0
	// 		},
	// 		endCss: {
	// 			top: boxHeight-800
	// 		}
	// 	}]
	// });

	var oBody = document.getElementsByTagName('body')[0];
	var scrollNum = 1;

	if(oBody.addEventListener) {
		oBody.addEventListener('DOMMouseScroll', function(e){
			scrollFunc(e);
		}, false);
	}
	oBody.onmousewheel = function(e){
		scrollFunc(e);
	};
	function scrollFunc(e) {
		var e = e || event;
		var wrapTop = 60 - document.getElementById("wrapBox").offsetTop;
		var direct;
		var endWrapTop;

		e.preventDefault ? e.preventDefault() : (e.returnValue = false);

		direct = e.wheelDelta ? e.wheelDelta : e.detail*-1;


		if (direct > 0) {
			if ((scrollNum++)%2) {
				return;
			}

			endWrapTop = (Math.round(wrapTop/boxHeight*1)-1) * boxHeight/1;
		} else {
			if ((scrollNum--)%2) {
				return;
			}
			endWrapTop = (Math.round(wrapTop/boxHeight*1)+1) * boxHeight/1;
		}

		window.scroll(0, endWrapTop/(line.lineHeight + 60 - windowHeight)*((line.lineHeight + 60) * line.scrollRatio - windowHeight));
	}

	var endTop = 0;
	window.onscroll = function() {
		var top = document.documentElement.scrollTop || document.body.scrollTop;//获取滚动条位置
		// if (endTop > top) {
		// 	clearInterval(bodyTimer);
		// }
		endTop = top;
		//$wrapBox.css({"top": -top2+60});
		var wrapTop = top*(line.lineHeight + 60 - windowHeight)/((line.lineHeight + 60) * line.scrollRatio - windowHeight);//内容改变top值

		//$wrapBox.css({"top": -wrapTop+60});
		$wrapBox.stop().animate({"top": -wrapTop+60}, 1300);//#wrap 滚动
		//$wrapBox.stop().animate({"top": -wrapTop+60},{queue:false, duration: 1100});

		var topIndex = 0;
		if (wrapTop <= boxHeight*(topIndex + 0.6)) {
			if (box1In) {
				boxGoIn_1();
			}
			$(".nav a").removeClass("cur").eq(0).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(0).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex + 0.8)) {
			boxGoOut_1();
		}
		topIndex++;

		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			if (box2In) {
				boxGoIn_2();
			}
			$(".nav a").removeClass("cur").eq(1).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(1).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			boxGoOut_2();
		}
		topIndex++;

		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			if (box3In) {
				boxGoIn_3();
			}
			$(".nav a").removeClass("cur").eq(2).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(2).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			boxGoOut_3();
		}
		topIndex++;

		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			if (box4In) {
				boxGoIn_4();
			}
			$(".nav a").removeClass("cur").eq(3).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(3).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			boxGoOut_4();
		}
		topIndex++;

		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			if (box5In) {
				boxGoIn_5();
			}
			$(".nav a").removeClass("cur").eq(4).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(4).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			boxGoOut_5();
		}
		topIndex++;

		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			if (box6In) {
				boxGoIn_6();
			}
			$(".nav a").removeClass("cur").eq(5).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(5).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			boxGoOut_6();
		}
		topIndex++;
		
		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			if (box7In) {
				boxGoIn_7();
			}
			$(".nav a").removeClass("cur").eq(6).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(6).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			boxGoOut_7();
		}
		topIndex++;
		
		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			if (box8In) {
				boxGoIn_8();
			}
			$(".nav a").removeClass("cur").eq(7).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(7).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			boxGoOut_8();
		}
		topIndex++;
		
		if (boxHeight*(topIndex-0.4) < wrapTop && wrapTop < boxHeight*(topIndex+0.6)) {
			$(".nav a").removeClass("cur").eq(8).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(8).addClass("cur");
		} else if (wrapTop > boxHeight*(topIndex+0.8) || wrapTop < boxHeight*(topIndex-0.4)) {
			
		}
		topIndex++;

		if (boxHeight*(topIndex-0.4) < wrapTop) {
			$(".nav a").removeClass("cur").eq(5).addClass("cur");
			$(".fixedNav li").removeClass("cur").eq(5).addClass("cur");
		}

		for (var i=0, len=line.obj.length; i<len; i++) {
			var curEle = line.obj[i];
			for (var j=0, len2=curEle.regions.length; j<len2; j++) {
				if (curEle.regions[j].startTop <= top && curEle.regions[j].endTop >= top) {
					parallaxAnimate(curEle.dom, curEle.regions[j], top);
				} else if (curEle.regions[j].endTop <= top) {
					for (var n in curEle.regions[j].endCss) {
						var json = {};
						
						json[n] = curEle.regions[j].endCss[n];
						//$(curEle.dom).css(json);
						$(curEle.dom).stop().animate(json, 1300);
					}
				} else if (curEle.regions[j].startTop >= top) {
					for (var n in curEle.regions[j].startCss) {
						var json = {};

						json[n] = curEle.regions[j].startCss[n];
						//$(curEle.dom).css(json);
						$(curEle.dom).stop().animate(json, 1300);
					}
				}
			}
		}
	};

	for (var i in elementInit) {//初始化对象
		var $i = $wrapBox.find(i);
		for (var j in elementInit[i]) {
			$i.css(j, elementInit[i][j]);
		}
	}

	boxGoIn_1();//默认第一个

	$(".nav li").click(function() {
		var index = $(this).index();
		//index = index==0 ? 0 : index+1;
		if(index!=9){
			window.scroll(0, index * line.scrollRatio * boxHeight);
		}
		//document.getElementsByTagName('body')[0].scrollTop = index * line.scrollRatio * boxHeight;
	});

	$(".fixedNav").css({'margin-left': $(window).width()/2-40, 'display': 'block'});
	$(".fixedNav li").click(function() {
		var index = $(this).index();
		index = index==0 ? 0 : index+1;
		//document.getElementsByTagName('body')[0].scrollTop = index * line.scrollRatio * boxHeight;
		window.scroll(0, index * line.scrollRatio * boxHeight);
	});
	$(".fixedNav li").hover(function(){
		$(this).find('.txt').show();
	}, function() {
		$(this).find('.txt').hide();
	})

	var lazyload;
	window.onresize = function() {
		clearTimeout(lazyload);
		lazyload = setTimeout(function() {
			window.location.reload(); 
			document.documentElement.scrollTop = document.body.scrollTop = 0;
		}, 500);
		//$(".fixedNav").css('margin-left', $(window).width()/2-50);
	};

	//var bodyTimer;
	$(".b1_03 a").click(function() {
		//clearInterval(bodyTimer);
		var now = 1;
		//$(".nav a").eq(1).click();
		document.getElementsByTagName('body')[0].scrollTop = document.documentElement.scrollTop = now * line.scrollRatio * boxHeight;
		// document.body.scrollTop = now * line.scrollRatio * boxHeight;
		// bodyTimer = setInterval(function() {
		// 	now++;
		// 	document.documentElement.scrollTop  = document.body.scrollTop = now * line.scrollRatio * boxHeight;

		// 	if (now >= 7) {
		// 		clearInterval(bodyTimer);
		// 	}
		// }, 5000);
	});
};

var elementInit = {//初始化对象属性
	".b1_01": {
		opacity: 0
	},
	".b1_02": {
		//left: (1920 - windowWidth)/2 - 709,
		left: 300,
		opacity: 0
	},
	".b1_03": {
		top: 185,
		opacity: 0
	},
	".b1_03_btn": {
		//opacity: 0
	},
	".b1_04": {
		//top: 800
	},
	".b2_01": {
		top: 800,
		left: -200,
		opacity: 0
	},
	".b2_02": {
		left: 1050,
		opacity: 0
	},
	".b2_07": {
		left: 810,
		opacity: 0
	},
	".b2_03": {
		//top: 70,
		//opacity: 0
	},
	".b3_tl": {
		opacity: 0
	},
	".b3_01": {
		left: 670,
		top: 275,
		opacity: 0
	},
	".b3_02": {
		left: 963,
		top: 293,
		opacity: 0
	},
	".b3_03": {
		left: 963,
		opacity: 0
	},
	".b3_04": {
		left: 963,
		top: 353,
		opacity: 0
	},
	".b3_05": {
		left: 670,
		top: 353,
		opacity: 0
	},
	".b3_06": {
		left: 670,
		opacity: 0
	},
	".b4_tl": {
		top: 810,
		opacity: 0
	},
	".b4_txt": {
		opacity: 0
	},
	".b4_txt01": {
		top: 930
	},
	".b4_txt02": {
		top: 1012
	},
	".b4_txt03": {
		top: 1094
	},
	".b4_txt04": {
		top: 1176
	},
	".b4_txt05": {
		top: 1258
	},
	".b4_txt06": {
		top: 1340
	},
	".b4_01": {
		left: 1000,
		opacity: 0
	},
	".b4_02": {
		left: 1235,
		opacity: 0
	},
	".b5_tl": {
		opacity: 0
	},
	".b5_txt": {
		opacity: 0
	},
	".b5_01": {
		//left: 500,
		opacity: 0
	},
	".b5_ico01": {
		left: 500,
		opacity: 0
	},
	".b5_ico02": {
		left: 625,
		top: -34,
		opacity: 0
	},
	".b5_ico03": {
		left: 625,
		top: 304,
		opacity: 0
	},
	".b5_ico04": {
		left: 780,
		top: -145,
		opacity: 0
	},
	".b5_ico05": {
		left: 780,
		opacity: 0
	},
	".b5_ico06": {
		left: 780,
		top: 395,
		opacity: 0
	},
	".b6_tl": {
		opacity: 0
	},
	".b6_img": {
		opacity: 0
	},
	".b6_arrow01": {
		top: 34
	},
	".b6_arrow02": {
		top: -34
	},
	".b6_txt": {
		opacity: 0
	},
	".b6_01": {
		left: 400,
		opacity: 0
	}
}

var box1In = true,
	box2In = true,
	box3In = true,
	box4In = true,
	box5In = true,
	box6In = true;
	box7In = true;
	box8In = true;
	box9In = true;

function boxGoIn_1() {
	 box1In = false;
	$(".b1_01").stop().animate({top: 285, opacity: 1}, 1200);
	$(".b1_02").stop().animate({left: 365, opacity: 1}, 1200);
	$(".b1_03").stop().animate({top: 235, opacity: 1}, 1200, function() {
		$(".b1_03_btn").addClass('anima');
	});
}
function boxGoOut_1() {
	 box1In = true;
	$(".b1_02").stop().animate({left: 300, opacity: 0}, 500);
	$(".b1_03").stop().animate({top: 185, opacity: 0}, 500, function() {
		$(".b1_03_btn").removeClass('anima');
	});
	$(".b1_01").stop().animate({top: 415, opacity: 0}, 800);
}

var box3Timer;
var box3Now;
function boxGoIn_2() {
	box3In = false;
	$(".b2_13").stop().animate({left: 650, opacity: 1}, 1000);
	$(".b2_14").stop().animate({left: 450, opacity: 1}, 1000);
	setTimeout(function() {
		$(".b2_15").stop().animate({opacity: 1}, 1000);
		setTimeout(function() {
			$(".b2_16").stop().animate({opacity: 1}, 1000);
		},1000);
	},1000);
}
function boxGoOut_2() {
	box3In = true;
	$(".b2_16").stop().animate({opacity: 0}, 150);
	$(".b2_15").stop().animate({opacity: 0}, 150);
	$(".b2_13").stop().animate({left: 0, opacity: 0}, 500);
	$(".b2_14").stop().animate({left: 0, opacity: 0}, 500);
}
function boxGoIn_3() {
	box3In = false;
	$(".b3_11").stop().animate({top: 150,opacity: 1}, 2000);
	$(".b3_12").stop().animate({top: 200,opacity: 1}, 2000);
	$(".b3_13").stop().animate({left: 400,opacity: 1}, 2000);
	$(".b3_14").stop().animate({right: 400,opacity: 1}, 2000);
}
function boxGoOut_3() {
	box3In = true;
	$(".b3_11").stop().animate({top: 300,opacity: 0}, 1000);
	$(".b3_12").stop().animate({top: 350,opacity: 0}, 1000);
	$(".b3_13").stop().animate({left: 600,opacity: 0}, 1000);
	$(".b3_14").stop().animate({right: 600,opacity: 0}, 1000);
}

function boxGoIn_4() {
	box4In = false;
	$(".b4_11").stop().animate({left: 560,opacity: 1}, 2000);
	$(".b4_12").stop().animate({left: 560,opacity: 1}, 2000);
	$(".b4_13").stop().animate({top: 250,opacity: 1}, 2000);
	$(".b4_14").stop().animate({top: 440,opacity: 1}, 2000);
}
function boxGoOut_4() {
	box4In = true;
	$(".b4_11").stop().animate({left: 760,opacity: 0}, 1000);
	$(".b4_12").stop().animate({left: 360,opacity: 0}, 1000);
	$(".b4_13").stop().animate({top: 50,opacity: 0}, 1000);
	$(".b4_14").stop().animate({top: 550,opacity: 0}, 1000);
}

function boxGoIn_5() {
	var b5qx = -90;
	$(".b5_11").stop().animate({top: 80, opacity: 1}, 1500);
	$(".b5_13").stop().animate({top:150,opacity: 1}, 800);
	var timer1 = setInterval(function(){
		if(b5qx<=45){
			b5qx+=1;
		}else{
			b5qx+=1;
		}
		$(".b5_13").css('transform','rotate('+b5qx+'deg)');
		if(b5qx>=10){
			clearInterval(timer1);
			boxGoIn_5_2();
		}
	},10);
}
function boxGoIn_5_2(){
	var b5qx = 10;
	var timer2 = setInterval(function(){
		b5qx-=1;
		$(".b5_13").css('transform','rotate('+b5qx+'deg)');
		if(b5qx<=0){
			clearInterval(timer2);
		}
	},10);
}
function boxGoOut_5() {
	box5In = true;
	$(".b5_11").stop().animate({top: 0, opacity:0}, 500);
	$(".b5_13").stop().animate({top:0,opacity:0}, 800);
	/*var b5qx = 0;
	var timer1 = setInterval(function(){
		b5qx-=3;
		$(".b5_13").css('transform','rotate('+b5qx+'deg)');
		if(b5qx<=-80){
			clearInterval(timer1);
		}
	},10);*/
	
}

function boxGoIn_6() {
	box6In = false;
	$(".b6_11").stop().animate({left: 30,opacity: 1}, 1000);
	$(".b6_12").stop().animate({left: 430,opacity: 1}, 1000);
	$(".b6_13").stop().animate({left:0,opacity: 1}, 1000);
	$(".b6_14").stop().animate({right:0,opacity: 1}, 1000);
}
function boxGoOut_6() {
	box6In = true;
	$(".b6_11").stop().animate({left:-300,opacity:0}, 800);
	$(".b6_12").stop().animate({left:-300,opacity:0}, 800);
	$(".b6_13").stop().animate({left:-300,opacity:0}, 800);
	$(".b6_14").stop().animate({right:-300,opacity:0}, 800);
}

var box7Timer;
function boxGoIn_7() {
	box7In = false;
	clearTimeout(box7Timer);
	box7Timer = setTimeout(function() {
		$(".b7_11").stop().animate({opacity: 1}, 500);
		box7Timer = setTimeout(function() {
			$(".b7_12").stop().animate({opacity: 1}, 500);
			box7Timer = setTimeout(function() {
				$(".b7_13").stop().animate({ opacity: 1}, 500);
				box7Timer = setTimeout(function() {
					$(".b7_14").stop().animate({ opacity: 1}, 500);
				},1000);
			}, 1000);
		}, 1000);
	}, 1000);
	box7Timer = setTimeout(function() {
		$(".b7_15").stop().animate({opacity: 1}, 500);
		box7Timer = setTimeout(function() {
			$(".b7_16").stop().animate({opacity: 1}, 500);
			box7Timer = setTimeout(function() {
				$(".b7_17").stop().animate({ opacity: 1}, 500);
				box7Timer = setTimeout(function() {
					$(".b7_18").stop().animate({ opacity: 1}, 500);
				},1000);
			}, 1000);
		}, 1000);
	}, 1000);
}
function boxGoOut_7(){
	box7In = true;
	clearTimeout(box7Timer);
	box7Timer = setTimeout(function() {
		$(".b7_14").stop().animate({opacity: 0}, 150);
		box7Timer = setTimeout(function() {
			$(".b7_13").stop().animate({opacity: 0}, 150);
			box7Timer = setTimeout(function() {
				$(".b7_12").stop().animate({ opacity: 0}, 150);
				box7Timer = setTimeout(function() {
					$(".b7_11").stop().animate({ opacity: 0}, 150);
				},150);
			}, 150);
		}, 150);
	}, 150);
	box7Timer = setTimeout(function() {
		$(".b7_18").stop().animate({opacity: 0}, 150);
		box7Timer = setTimeout(function() {
			$(".b7_17").stop().animate({opacity: 0}, 150);
			box7Timer = setTimeout(function() {
				$(".b7_16").stop().animate({ opacity: 0}, 150);
				box7Timer = setTimeout(function() {
					$(".b7_15").stop().animate({ opacity: 0}, 150);
				},150);
			}, 150);
		}, 150);
	}, 150);
	
}
var box8Timer;
function boxGoIn_8() {
	box8In = false;
	$(".b8_11").stop().animate({bottom: 50,opacity: 1}, 1000);
		setTimeout(function() {$(".b8_11").stop().animate({bottom: 100,opacity: 1}, 1000);},1000);
	box8Timer = setInterval(function() {
		$(".b8_11").stop().animate({bottom: 50,opacity: 1}, 1000);
		setTimeout(function() {$(".b8_11").stop().animate({bottom: 100,opacity: 1}, 1000);},1000);
	},2000);
}

function boxGoOut_8() {
	box8In = true;
	clearInterval(box8Timer);
	$(".b8_11").stop().animate({bottom: 0,opacity:0}, 800);
}


var loadImg = {
	path: './img/',
	imgs: {
		'.b1_bg': ['image/11111_0003_页头建筑.png'],
		'.b2_bg': ['image/11111_0003_页头建筑.png'],
		'.b3_bg': ['b3_01.png', 'b3_bg.jpg'],
		'.b4_bg': ['b4_01.png',	'b4_02.png', 'b4_bg.jpg'],
		'.b5_bg': ['b5_01.png', 'b5_bg.jpg', 'b5_ico.png'],
		'.b6_bg': ['b6_01.png', 'b6_arrow.png', 'b6_bg.jpg', 'b6_ico.png', 'b6_img.png'],
		'.b7_bg': ['b7_bg.jpg', 'b7_sprite.png', 'map.jpg']
	},
	boxLoad: function(ele, aImgs, callback) {
		var len = aImgs.length,
			i=0, j=0, imgLoad = [], _self = this;

		for (; i<len; i++) {
			(function(index) {
				imgLoad[index] = new Image();

				imgLoad[index].onload = imgLoad[index].onerror = function(index) {
					j++;
					
					if (j == len) {
						$(ele).find('img').each(function() {
							var src = $(this).attr('data-src');

							src && $(this).attr('src', src);
						});

						if (ele == '.b1_bg') {
							fnLoad();
							$('.b2_bg').find('img').each(function() {
								var src = $(this).attr('data-src');

								src && $(this).attr('src', src);
							});
							$('.b2_bg').find('.load').hide();
						}
						$(ele).find('.load').hide();
						if (callback) {
							callback();
						}
					}
				};

				imgLoad[index].src = _self.path + aImgs[index];
			})(i);
		}
	},
	init: function() {
		var index = 1;
		var self = this;
		var imgLoad = function() {
			self.boxLoad('.b'+ index + '_bg', self.imgs['.b'+ index + '_bg'], function() {
				index = index+1 == 2 ? 3 : index+1;

				if (index <= 7) {
					imgLoad();
				}
			});
		};

		imgLoad();
	}
}


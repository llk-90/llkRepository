﻿var city;
var userInfo;
var address;
var phone;
var name;
var lng;
var lat;
var visits_count;
function mapInit(){
	 // 加入高的地图
    map = new AMap.Map('container', {
           resizeEnable: true,
           zoom:15
   });
    //
   AMap.plugin(['AMap.ToolBar','AMap.Scale'],
       function(){
           map.addControl(new AMap.ToolBar());

           map.addControl(new AMap.Scale());

   });
   
   //信息框
   infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});//信息窗口
}

//信息框
function createContent(name,phone) {  //信息窗体内容
    var s = [];
    s.push("<div style='color:graytext; margin-top: 10px; font-size:15px;'>客户姓名：<input type='text' class='input' id='name' value='"+name+"'/>"+"</div>");
    s.push("<div style='color:graytext; font-size:15px;'>联系方式：<input type='tel'  id='phone' class='input' value='"+phone+"'/>"+"</div>");
    s.push("<div style='color:graytext; font-size:15px;'>备注：<textarea rows='3' cols='30'></textarea></div>");
    return s.join("<br>");
}


//創建點標記
function addMarker(userInfo) {
	 if(map.getAllOverlays()!=''){
	 map.remove(marker);
	 map.remove(markers);
	 }
	 for(var i=0;i<userInfo.length;i++){
		 marker = new AMap.Marker({
			 icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
			 position: [userInfo[i].lng,userInfo[i].lat]
		 });
		 marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
	         offset: new AMap.Pixel(3, 0),//修改label相对于maker的位置
	         content: userInfo[i].visitsCount
            });
		 marker.content = userInfo[i].cusName+","+userInfo[i].phone+","+userInfo[i].visitsCount;
		 marker.on('click', markerClick);
		 marker.setMap(map);
		 map.setFitView();
	 }
}




function markerClick(e) {
	var str = e.target.content;
	var info = str.split(",");
	name =info[0];
	phone =info[1];
	visits_count = info[2];
	lng =e.target.getPosition().lng;
	lat =e.target.getPosition().lat;
    infoWindow.setContent(createContent(name,phone))
    infoWindow.open(map, e.target.getPosition());
}

//获取拜访记录
function getHistoryPosition() {
	//openId= GetQueryString("openId")
	openId = "oCBdZw35rkQVQi3ZBMZ8WsDrR628";
	$.ajax({
		url : '/position/userPosition/queryPosition',
		data : {
			keyId : openId
		},
		type : 'POST',
		timeout : 20000,
		/*contentType : 'application/json',*/
		/*dataType : 'json',*/
		success : function(data) {
			if (data.code == "200") {
			 if(data.positions !=null || data.positions.length>0){
				 userInfo = data.positions;
				 addMarker(userInfo);
				 }
			}
			if(data.code =="201"){
				mui.toast("请求失败,请稍后重试。");
			}
		},
		error : function(data) {
			console.log(data);
		}
	});

}

//修改备注
function updateInfo(){
  var notes = $("textarea").val();
  if(notes ==null || notes.trim() ==""){
	  mui.toast("请输入备注信息");
	  return;
  }else{
	  visits_count++;
	  $.ajax({
		  url:'/position/userPosition/updateInfo',
		  data:JSON.stringify({
			  notes:notes,
			  lng:lng,
			  lat:lat,
			  visitsCount:visits_count
		  }),
		  type:'POST',		
		  timeout:20000,
		  contentType: 'application/json',
	      dataType: 'json',
		  success :function(data){
			  if(data.code =="200"){
				  window.location.href="../html/yuyuebaifang.html";
			  }
			  if(data.code =="201"){
				 mui.toast("请求失败,数据未发生变动,请稍后重试。"); 
				 setTimeout(
						 window.location.href="../html/yuyuebaifang.html",5000
				 );
			  }
		  },
		  error :function(data){
			  console.log(data);
		  }
	  })
  }
}














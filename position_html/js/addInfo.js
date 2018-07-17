 var map,addMarker;
  var geocoder;
  var placeSearch;
  var infoWindow ;
  var markers = [];
  var city;
  var lng;
  var lat;
  var poiName;
  var address;
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
     //定位
     map.plugin('AMap.Geolocation', function() {
         geolocation = new AMap.Geolocation({
             enableHighAccuracy: true,//是否使用高精度定位，默认:true
             timeout: 10000,          //超过10秒后停止定位，默认：无穷大
             buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
             zoomToAccuracy: false,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
             buttonPosition:'RB',       //定位按钮停靠位置，默认：'LB'，左下角
             showCircle: false,        //定位成功后用圆圈表示定位精度范围，默认：true
             showButton: false,       //显示定位按钮，默认：true
             showMarker: false        //定位成功后在定位到的位置显示点标记，默认：true
         });
         map.addControl(geolocation);
         geolocation.getCurrentPosition();
         AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
         AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
     });
     
   //解析定位结果
     function onComplete(data) {
     	console.log(data.addressComponent.city);
     	city = data.addressComponent.city;
     }
     //解析定位错误信息
     function onError(data) {
         //document.getElementById('tip').innerHTML = '定位失败';
     } 
     
     AMap.service('AMap.Geocoder',function(){//回调函数
         //实例化Geocoder
         geocoder = new AMap.Geocoder({
             city: city//"全国"//城市，默认：“全国”
         });
     });
     // 初始化加载
     AMap.service(["AMap.PlaceSearch"], function() {
             placeSearch = new AMap.PlaceSearch();
     });
     infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});//信息窗口
     //为地图注册click事件获取鼠标点击出的经纬度坐标
//     var clickEventListener = map.on('click', function(e) {
//         $("input[name=longitude]").val(e.lnglat.lng);
//         $("input[name=latitude]").val(e.lnglat.lat);
//         // 填写地址
//         writeAddress([e.lnglat.lng,e.lnglat.lat]);
//     });
     //键盘点击搜索
     //placeSearch.search("北京");
     $("#tip").on("keydown",function(event){
         if(event = event || window.event){
             if(event.keyCode==13){
                 mapsearch();
             }
         }
     });  
  }
  
 //地图搜索
 function mapsearch(){
	//查询前先移除所有标注
	 if(map.getAllOverlays()!=''){
		 map.remove(marker);
		 map.remove(markers);
		 }
     var searchVal = $("#tip").val();
     placeSearch.search(searchVal,function(status, result){
    	 if (status === 'complete' && result.info === 'OK') {
             var poiArr = result.poiList.pois;
             //var str = "<ul>";
             for(var i=0;i<poiArr.length;i++){
                  //在地图上创建标注点
            	 if(poiArr[i].name === searchVal){
            		 placeSearch.getDetails(poiArr[i].id, function(status, result) {
     			        if (status === 'complete' && result.info === 'OK') {
     			            placeSearch_CallBack(result);
     			        }
     			    });
            	}
               /*   marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
                      offset: new AMap.Pixel(3, 0),//修改label相对于maker的位置
                      content: String.fromCharCode(65+i)
                  });*/
              /*    marker.content = poiArr[i].name+"<br/>"+poiArr[i].address;
                  marker.on('click', markerClick);
                  markers.push(marker);
                  map.setFitView();*/
    	    }
    	 }else{
    		 mui.toast("未查询到相关地址");
    		 }
   });
 }
 
/* // 填写地址
 function writeAddress(lnglatXY){
     geocoder.getAddress(lnglatXY, function(status, result) {
         if (status === 'complete' && result.info === 'OK') {
             geocoder_CallBack(result);
         }
     }); 
 }*/
 /* // 地址回调
 function geocoder_CallBack(data) {
     var address = data.regeocode.formattedAddress; //返回地址描述
     $("input[name=address_detail]").val(address);
 }*/
 
/* //点击标注  显示信息窗口及内容
 function markerClick(e) {
     infoWindow.setContent(e.target.content);
     infoWindow.open(map, e.target.getPosition());
 }*/
 

 function placeSearch_CallBack(data) {
     var poiArr = data.poiList.pois;
     for(var i=0;i<poiArr.length;i++){
     //添加marker
     var marker = new AMap.Marker({
         map: map,
         position: poiArr[i].location
     });
     poiName = poiArr[i].name;
     address = poiArr[i].address;
     lng = poiArr[i].location.lng;
     lat = poiArr[i].location.lat;
     map.setCenter(marker.getPosition());  
   /*  marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
         offset: new AMap.Pixel(3, 0),//修改label相对于maker的位置
         content: iString.fromCharCode(65+i)
     });*/
      infoWindow.setContent(createContent(poiArr[i]));
	  infoWindow.open(map, marker.getPosition());
     }
 }
 
 //信息框
 function createContent(poi) {  //信息窗体内容
     var s = [];
     s.push("<b style='color:graytext; font-size:15px; font-family:'>名称：" + poi.name+"</b>");
     s.push("<b style='color:graytext; font-size:15px;'>地址：" + poi.address+"</b>");
     s.push("<div style='color:graytext; margin-top: 10px; font-size:15px;'>客户姓名：<input type='text' class='input' id='name' value=''/>"+"</div>");
     s.push("<div style='color:graytext; font-size:15px;'>联系方式：<input type='tel'  id='phone' class='input' value=''/>"+"</div>");
     s.push("<div style='color:graytext; font-size:15px;'>预约时间：<input type='datetime-local'  id='date' class='input' value=''/>"+"</div>");
     return s.join("<br>");
 }
 
//上传当前坐标
function upload(){
	 openId= 'oCBdZw35rkQVQi3ZBMZ8WsDrR628';
	 var name = $("#name").val();
	 if(name ==null || name.trim() == ""){
		 mui.toast("姓名不能为空");
		 return;
	 }
	 var phone = $("#phone").val();
	 if(phone == null || phone.trim() ==""){
		 mui.toast("联系方式不能为空");
		 return;
	 }
	 var date = $("#date").val();
	 if(date ==null || date.trim() ==""){
		 mui.toast("日期不能为空");
		 return;
	 }
	 var visitsCount= 0;
	 var StrAddress = address+poiName;
	$.ajax({
        url:'/position/userPosition/uploadPosition',
        data:JSON.stringify({
        	keyId:openId,
        	lng:lng,
        	lat:lat,
        	visitsCount:visitsCount,
        	cusName:name,
        	address:StrAddress,
        	baifangTime:date,
        	phone:phone
        }),
        type: 'POST',
        timeout: 10000,
      contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
             if(data.code =="200"){
            	//跳转到首頁
            	 window.location.href="../html/yuyuebaifang.html";
             }
             if(data.code =="201"){
            	mui.toast("请求失败,请稍后重试。");
             }
        },
        error: function (data) {
         console.log(data);
        }
    });
}

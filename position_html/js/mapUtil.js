 var map,addMarker;
  var geocoder;
  var placeSearch;
  var infoWindow ;
  var markers = [];
  var city;
  function mapInit(){
      // 加入高的地图
      map = new AMap.Map('container', {
             resizeEnable: true,
             zoom:19
         	
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
             zoomToAccuracy: true,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
             buttonPosition:'RB',       //定位按钮停靠位置，默认：'LB'，左下角
             showCircle: false,        //定位成功后用圆圈表示定位精度范围，默认：true
             showButton: true,       //显示定位按钮，默认：true
             showMarker: true        //定位成功后在定位到的位置显示点标记，默认：true
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
    /* 	lng = data.position.getLng();
     	lat = data.position.getLat();
     	lnglats.push([lng,lat]);*/
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
     myMapViewLocation();
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
             var str = "<ul>";
             for(var i=0;i<poiArr.length;i++){
                  //在地图上创建标注点
                  marker = new AMap.Marker({
                      icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png"
                  });
                  marker.setPosition(new AMap.LngLat(poiArr[i].location.lng,poiArr[i].location.lat));
                  marker.setMap(map);
                  marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
                      offset: new AMap.Pixel(3, 0),//修改label相对于maker的位置
                      content: String.fromCharCode(65+i)
                  });
                  marker.content = poiArr[i].name+"<br/>"+poiArr[i].address;
                  marker.on('click', markerClick);
                  markers.push(marker);
                  map.setFitView();
                     str+='<li>';
                      str+='<div class="res-data">';
                      str+='<div class="left res-marker">';
                      str+='<span>'+String.fromCharCode(65+i)+'</span>';
                      str+='</div>';
                          str+='<div class="left res-address">';
                              str+='<div class="title">'+poiArr[i].name+'</div>';
                              str+='<div>POI：<span class="poi">'+poiArr[i].id+'</span></div>';
                              str+='<div>地址：<span class="rout">'+poiArr[i].address+'</span></div>';
                              str+='<div>坐标：经度<span class="point">'+poiArr[i].location.lng+",纬度,"+poiArr[i].location.lat+'</span></div>';
                          str+='</div>';
                      str+='</div>';
                  str+='</li>';
             }
              str+='</ul>';
              $("#panel").html(str);
    	 }else{
    		 new CAlert().toast_ERROR("未查询到相关地址");
    		 }
    		 });
     
 }
 // 初始化回显
 function myMapViewLocation(){
     //console.log("回显坐标");
     var mlon = $("input[name=longitude]").val();
     var mlat = $("input[name=latitude]").val();
     var lnglatXY = [mlon,mlat];
     if(mlon&&mlat){
         addMarker(lnglatXY);
     }
 }
 // 实例化点标记
 function addMarker(lnglatXY) {
	 if(map.getAllOverlays()!=''){
	 map.remove(marker);
	 map.remove(markers);
	 }
	 marker = new AMap.Marker({
         icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
         position: lnglatXY
     });
     marker.setMap(map);
     map.setFitView();// 执行定位
 }
 // 填写地址
 function writeAddress(lnglatXY){
     geocoder.getAddress(lnglatXY, function(status, result) {
         if (status === 'complete' && result.info === 'OK') {
             geocoder_CallBack(result);
         }
     }); 
 }
 //点击标注  显示信息窗口及内容
 function markerClick(e) {
     infoWindow.setContent(e.target.content);
     infoWindow.open(map, e.target.getPosition());
 }
 // 地址回调
 function geocoder_CallBack(data) {
     var address = data.regeocode.formattedAddress; //返回地址描述
     $("input[name=address_detail]").val(address);
 }

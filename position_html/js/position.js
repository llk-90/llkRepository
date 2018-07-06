var map, geolocation;var lng;var lat; var address;var lnglat;
var lnglats=new Array();
var addressArr=new Array();


var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
$(function () {
	createMap();
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
});

//创建地图
function createMap(){
//消息体窗口
	//加载地图
	map = new AMap.Map('container', {
		resizeEnable: true,
		zoom:20
	});
}

//解析定位结果
function onComplete(data) {
	lng = data.position.getLng();
	lat = data.position.getLat();
	var lnglat=[lng, lat];
	 var lnglats = [
         [lng, lat],
         [120.187476,30.254382],
         [120.217775,30.199615]
     ];
	 regeocoder(lnglat);
     for (var i = 0; i < lnglats.length; i++) {
         var marker = new AMap.Marker({
             position: lnglats[i],
             map: map
         });
         marker.on('click', markerClick);
         marker.emit('click', {target: marker});
     }
     map.setFitView(); 
}


//点击标记点,展示信息框
function markerClick(e) {
	var lnglat = [e.target.getPosition().lng,e.target.getPosition().lat];
	  regeocoder(lnglat);
}

//解析定位错误信息
function onError(data) {
    //document.getElementById('tip').innerHTML = '定位失败';
} 


//根据坐标获取地理位置信息
function regeocoder(lnglat) { 
    var geocoder = new AMap.Geocoder({
        radius: 1000,
        extensions: "all"
    });        
    geocoder.getAddress(lnglat, function(status, result) {
        if (status === 'complete' && result.info === 'OK') {
        	geocoder_CallBack(result,lnglat);
        }
   });        
}
//获取地理位置信息
function geocoder_CallBack(data,lnglat) {
	   address = data.regeocode.formattedAddress; //返回地址描述
       //alert(address);
       infoWindow.setContent(address);
       infoWindow.open(map, lnglat);
} 

//上传当前坐标
function upload(){
	alert('经度:' + lng +"------"+'纬:' + lat);
	var openId= 'oCBdZw35rkQVQi3ZBMZ8WsDrR628';
	var lnglat = lng+","+lat;
	var count= 1;
	$.ajax({
        url:'/userPosition/info',
        data: {
        	openId:openId,
        	lnglat:lnglat,
        	count:count
              },
        type: 'POST',
        method: 'POST',
        timeout: 10000,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (data) {
           alert("gvjsoljnc");
        },
        error: function (xhr, type, errorThrown) {
         alert("服务器正在维护！！！");  
        }
    });
}


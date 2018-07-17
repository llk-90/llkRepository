/**
 * Created by George on 2018/3/13.
 */
var search = window.location.search;
var id = search.substring(4);
//页面初始化回调函数//userInfo.openId
$(document).ready(function(){
    $.ajax(baseURL+"/position/userPosition/findBaifangYuyueDetail",{
        data:{
            id: id
        },
        dataType: 'json', //服务器返回json格式数据
        type: 'post', //HTTP请求类型
        timeout: 10000, //超时时间设置为10秒；
        crossDomain:true,
        success: function(data) {
            //成功回调
                if(null!=data.cusName&&data.cusName!=""){
                    document.getElementById("cusName").innerHTML=data.cusName;
                }else{
                    $("#cusName").parent().remove();
                }
                if(null!=data.address&&data.address!=""){
                    document.getElementById("address").innerHTML=data.address;
                }else{
                    $("#address").parent().remove();
                }
                if(null!=data.phone&&data.phone!=""){
                    document.getElementById("phone").innerHTML=data.phone;
                }else{
                    $("#phone").parent().remove();
                }
                if(null!=data.visitsCount&&data.visitsCount!=""){
                    document.getElementById("visitsCount").innerHTML=data.visitsCount;
                }else{
                    $("#visitsCount").parent().remove();
                }     
                if(null!=data.notes&&data.notes!=""){
                    document.getElementById("notes").innerHTML=data.notes;
                }else{
                    $("#notes").parent().remove();
                }
        },
        error: function(e) {
            console.log(e);
            //失败回调
            mui.toast(e);
        }
    });
})




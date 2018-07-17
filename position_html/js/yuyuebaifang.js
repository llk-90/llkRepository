/**
 * Created by roger on 2018/1/23.
 */
mui.init({
    pullRefresh: {
        container: '#weixiaoyuanNewsWholeContent',
        down: {
            style: 'circle',
            offset: '100px',
            callback: pulldownRefresh
        },
        up: {
            auto: true,
            contentrefresh: '正在加载...',
            callback: pullupRefresh
        }
    }
});

var pageCount = 0;
var type = 0;

//上拉
function pullupRefresh() {

    setTimeout(function () {
        $.ajax("/position/userPosition/fingBaifangYuyue", {
            data: {
                pageCount: pageCount
            },
            dataType: 'json',
            type: 'post',
            timeout: 20000, //超时时间设置为10秒；
            crossDomain: true,
            success: function (data) {
                console.log(data);
                if (data.errcode == 0) {
                    addfirst1(data);
                    pageCount++;
                }
                else {
                    mui.toast(data.errcode.errMsg);
                }
            },
            error: function (e) {
                console.log(e);
                mui.toast("服务器正在维护");
            }
        });
    }, 1000);
}

mui("ul").on("tap", "a", function () {

    window.top.location.href = this.href;
});
mui("ul").on("tap", "li", function () {

    var liid = this.id.replace("li", "");
});
mui("nav").on("tap", "a", function () {
    window.top.location.href = this.href;
});

function addfirst1(data) {
    mui('#weixiaoyuanNewsWholeContent').pullRefresh().endPullupToRefresh((data.infoList.length < 5)); //参数为true代表没有更多数据了。

    var table = document.body.querySelector('#weixiaoyuanNewsContent');
    for (var i = 0; i < data.infoList.length; i++) {
        var li = document.createElement('li');
        li.setAttribute("style", "margin-bottom:10px")
        li.className = 'main';
        li.id = "li" + data.infoList[i].id;
        if (data.infoList[i].address.length > 51) {
            data.infoList[i].address = data.infoList[i].address.substr(0, 50)
        }
        li.innerHTML = '<span class="title" style="color: #DB3652">' + data.infoList[i].cusName + '</span><span class="typePro" style="color: #436EEE">' +  data.infoList[i].visitsCount + '</span></a>' +
        '<div class="contant" style="color: darkgray">' + "地址：" + data.infoList[i].address + '</div>' +
        '<div class="notes" style="color: darkgray">' + "备注：" + data.infoList[i].notes + '</div>' +
        '<div class="autime"><span class="time" style="color: #DB3652">' + getDateDiff(data.infoList[i].createTime) + '</span></div>';
        table.appendChild(li);
    }
}

$(".main").click(function () {
    var item = $(this).index();  //获取索引下标 也从0开始
    var textword = $(this).text();  //文本内容
    mui.toast("下标索引值为：" + item + "\n" + "文本内容是：" + textword); //  \n换行
})

function addfirst(data) {
    var table = document.body.querySelector('#weixiaoyuanNewsContent');
    //var newCount = cells.length>0?5:5;//首次加载5条，满屏
    table.innerHTML = "";
    for (var i = 0; i < data.infoList.length; i++) {
        var li = document.createElement('li');
        li.setAttribute("style", "margin-bottom:10px")
        li.className = 'main';
        li.id = "li" + data.infoList[i].id;
        if (data.infoList[i].address.length > 51) {
            data.infoList[i].address = data.infoList[i].address.substr(0, 50)
        }
        li.innerHTML = '<span class="title" style="color: #DB3652">' + data.infoList[i].cusName + '</span><span class="typePro" style="color: #436EEE">' +  data.infoList[i].visitsCount + '</span></a>' +
        '<div class="contant" style="color: darkgray">' + "地址：" + data.infoList[i].address + '</div>' +
        '<div class="notes" style="color: darkgray">' + "备注：" + data.infoList[i].notes + '</div>' +
        '<div class="autime"><span class="time" style="color: #DB3652">' + getDateDiff(data.infoList[i].createTime) + '</span></div>';
        table.appendChild(li);
    }
}

function addData() {

    $.ajax("/position/userPosition/fingBaifangYuyue", {
        data: {
            type: type,
            pageCount: 0
        },
        dataType: 'json',
        type: 'post',
        timeout: 20000, //超时时间设置为10秒；
        crossDomain: true,
        success: function (data) {
            console.log(data)
            if (data.errcode == 0) {
                pageCount = 1;
                addfirst(data);
                //mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //参数为true代表没有更多数据了。
                if (data.infoList.length < 5) {
                    mui('#weixiaoyuanNewsWholeContent').pullRefresh().refresh(true);
                }
            }
            else {
                mui.toast(data.errcode.errMsg);
            }
        },
        error: function (e) {
            console.log(e);
            mui.toast("服务器正在维护");
        }
    });
}

/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh() {

    setTimeout(function () {
        addData();
        mui('#weixiaoyuanNewsWholeContent').pullRefresh().endPulldownToRefresh();
        mui.toast("已刷新数据");
    }, 1500);
}

mui(".buttonmodel").on('tap', ".buttonChild", function (thingsHappen) {
    thingsHappen.srcElement.click();
})
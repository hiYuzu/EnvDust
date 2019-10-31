var map;
var maptimer = null;
var alarmList = [];
var isInit = true;
var maptreeList = [];
var treelevelflag = "";
var mapselected = "";
var points = {};
var mapajaxconn = 0;
var mylabel = null;
var ply = null;
var temMarkers = [];
var myValue = "";
var markerClusterer = null;
var lockReconnect = false;  //避免ws重复连接
var websocket = null; //websocket对象
var requireClusterer = true;
var notRequireStaFlow = false;

/* 异步加载地图 */
function loadJScript() {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "http://api.map.baidu.com/api?v=2.0&ak=AGl6mLfskQ2WZAD5dvD0Kls3mqsGBGcG&callback=initMap";
    document.body.appendChild(script);
}

var loadCount = 0;

/* 初始地图 */
function initMap() {
    $.getScript("http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js");
    $.getScript("../javascript/jquery-easyui-1.4.4/TextIconOverlay_min.js");
    $.getScript("../javascript/jquery-easyui-1.4.4/MarkerClusterer_min.js");
    map = new BMap.Map("allmap"); // 创建Map实例
    var point = new BMap.Point(117.1786889372559, 39.10762965106183); // 创建点坐标

 /*   map.addEventListener("tilesloaded", function () {//mapName是你的地图名称
        if (loadCount == 1) {
            map.setCenter(point);//point是设置的中心点
        }
        loadCount = loadCount + 1
    });*/
    map.centerAndZoom(point, 12);
    var top_left_control = new BMap.ScaleControl({
        anchor: BMAP_ANCHOR_BOTTOM_LEFT
    });// 左上角，添加比例尺
    var top_left_navigation = new BMap.NavigationControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        offset: new BMap.Size(10, 60) //进一步控制缩放按钮的水平竖直偏移量
    }); // 左上角，添加默认缩放平移控件
    var overViewOpen = new BMap.OverviewMapControl({
        isOpen: true,
        anchor: BMAP_ANCHOR_BOTTOM_RIGHT
    });
    var mapType = new BMap.MapTypeControl(
        {
            mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP],
            anchor: BMAP_ANCHOR_TOP_LEFT,
            offset: new BMap.Size(10, 25) //进一步控制缩放按钮的水平竖直偏移量
        }
    );
    map.addControl(top_left_control);
    map.addControl(top_left_navigation);
    map.addControl(overViewOpen); // 鹰眼
    map.addControl(mapType);
    map.enableScrollWheelZoom();
    map.enableInertialDragging();
    map.enableContinuousZoom();
    map.setDefaultCursor("Default"); // 设置地图默认的鼠标指针样式
    var size = new BMap.Size(10, 20); // 启用滚轮放大缩小
    map.addControl(new BMap.CityListControl({// 城市列表
        anchor: BMAP_ANCHOR_TOP_RIGHT,
        offset: size
    }));

    //搜索框
    function search() {
        this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
        this.defaultOffset = new BMap.Size(100, 20);
    }

    search.prototype = new BMap.Control();
    search.prototype.initialize = function (map) {
        return createSearchCtrl();
    }
    var mySearchCtrl = new search();	// 创建控件
    map.addControl(mySearchCtrl);	// 添加到地图当中

    //自动完成检索
    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
        {
            "input": "searchmapaddrText"
            , "location": map
        });
    ac.addEventListener("onconfirm", function (e) {
        var _value = e.item.value;
        myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
        //鼠标点击下拉列表后的事件
        setPlace();
    });
    document.getElementById("searchmapaddrText").value = "搜索地点";
    // 右键菜单
    map.addContextMenu(getMenu());


    /*
     * map.addEventListener("mousemove",function(e){//坐标拾取 if(mylabel!=null){
     * map.removeOverlay(mylabel); } var point = new
     * BMap.Point(e.point.lng,e.point.lat); var opts = { position : point, //
     * 指定文本标注所在的地理位置 offset : new BMap.Size(5, 20) //设置文本偏移量 } mylabel = new
     * BMap.Label(e.point.lng+","+e.point.lat, opts); // 创建文本标注对象
     * mylabel.setStyle({ color : "red", fontSize : "12px", height : "20px",
     * lineHeight : "20px", fontFamily:"微软雅黑" }); map.addOverlay(mylabel); });
     */
    selectedSearchData();
//	getAreaBoundary();//追加天津市津南区行政区域
    getMapAreaPol();//添加地图区域边框
//	maptimer = setInterval(selectedSearchDataConn, 200);

    /* 进行地图数据的实时加载  */
    initWebsocket();
}

//取出地图区域边框数据
function getMapAreaPol() {
    ajaxLoading();
    $.ajax({
        url: "../MapAreaController/getAllPoints",
        type: "post",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        async: true,
        error: function (json) {
            ajaxLoadEnd();
            $.messager.alert("错误", json.detail, "error");
        },
        success: function (json) {
            ajaxLoadEnd();
            if (json.result) {
                addMapAreaPol(json);//加载地图区域边框
            } else {
                $.messager.alert("错误", json.detail, "error");
            }
        }
    });
}

//加载地图区域边框
function addMapAreaPol(data) {
    var pointArray = [];
    var rowslen = data.rows.length;
    for (var i = 0; i < rowslen; i++) {
        var maxLng = 0;
        var maxLat = 0;
        var minLng = 180;
        var minLat = 90;
        var areaPoints = [];
        if (data.rows[i].points == null) {
            return;
        }
        var pntslen = data.rows[i].points.length;
        for (var j = 0; j < pntslen; j++) {
            areaPoints += data.rows[i].points[j].lng + "," + data.rows[i].points[j].lat + ";";
        }
        var points = areaPoints.split(";");
        if (pntslen > 2) {
            var mapPoint = [];
            for (var t = 0; t < pntslen; t++) {
                if (points[t] != "") {
                    var x = parseFloat((points[t].split(','))[0]);
                    var y = parseFloat((points[t].split(','))[1]);
                    if (x > maxLng) {
                        maxLng = x;
                    }
                    if (x < minLng) {
                        minLng = x;
                    }
                    if (y > maxLat) {
                        maxLat = y;
                    }
                    if (y < minLat) {
                        minLat = y;
                    }
                    mapPoint.push(new BMap.Point(x, y));
                }
            }
            var polygon = new BMap.Polygon(mapPoint, {strokeWeight: 2, strokeColor: "#0000FF", fillOpacity: 0.3});  //创建区域
            map.addOverlay(polygon);
            polygon.disableEditing();
            polygon.addContextMenu(getMenu());//右键菜单
            //得到区域中心点坐标
            var point = new BMap.Point((maxLng + minLng) / 2, (maxLat + minLat) / 2);
            //取得区域边界点
            pointArray = pointArray.concat(polygon.getPath());
            //设置信息窗口
            var opts = {
                title: "区域名称：" // 信息窗口标题
            }
            var content = data.rows[i].maName;
            addPolygonClickHandler(content, polygon, opts, point);
        }
    }
    //map.setViewport(pointArray, {zoomFactor:1});
}

function addPolygonClickHandler(content, polygon, opts, point) {//区域点击响应
    polygon.addEventListener("click", function () {
        var p = new BMap.Point(point.lng, point.lat);
        var infoWindow = new BMap.InfoWindow(content, opts);
        map.openInfoWindow(infoWindow, p);//打开信息窗口
    });
}

/* WebSocket客户端初始化 */
function initWebsocket() {
    var domain = window.location.host;
    $.getScript("../javascript/jquery-easyui-1.4.4/sockjs.js");
    try {
        /* 该浏览器支持使用websocket技术  */
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://" + domain + "/websocket");
            /* 能使用sockjs进行转换 */
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://" + domain + "/websocket");
        } else {
            /* sockjs对应的地址 */
            websocket = new SockJS("http://" + domain + "/sockJS");
        }
        websocket.onopen = onOpen;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        websocket.onclose = onClose;
    } catch (e) {
        reConnect();
        console.log("websocket初始化异常：" + e);
    }

    /* 已经连接 */
    function onOpen() {
        /*重置心跳检测*/
        heartCheck.reset().start();
    }

    /* 接受到消息    */
    function onMessage(event) {
        console.log("客户端收到数据：" + event.data);
        /*重置心跳检测*/
        heartCheck.reset().start();
        /* websocket还在打开状态 */
        if (websocket.readyState == websocket.OPEN) {
            /* 如果是点击刷新，就不要进行数据的重新获取 */
            if (event.data == "dateUpdate") {
                maptimer = selectedSearchDataConn();
            }
        }
    }

    /* 接受过程中出现错误 */
    function onError() {
        console.log("websocket客户端出现错误，开始重新连接！");
        reConnect();
    }

    /* 连接结束 */
    function onClose() {
//		var reLogin = confirm("\n地图自动更新已经关闭连接,原因：服务器关闭！\n\n\n 是否重新登陆尝试？ ");
//		if(reLogin){
//			 window.location.href='../login.html';
//		}
        console.log("websocket客户端出断连，开始重连！");
        reConnect();
    }

    /* 页面被关闭  */
    window.close = function () {
        websocket.onclose;
    }


    //绑定beforeunload事件
    $(window).bind('beforeunload', function () {
        return '您输入的内容尚未保存，确定离开此页面吗？';
    });

    window.onbeforeunload = function (event) {
        onClose()
    }

    //解除绑定
    $(window).unbind('beforeunload');

}


//添加行政区域
function getAreaBoundary() {
    var bdary = new BMap.Boundary();
    bdary.get("天津市津南区", function (rs) {       //获取行政区域
        var count = rs.boundaries.length; //行政区域的点有多少个
        if (count === 0) {
            alert('未能获取当前输入行政区域');
            return;
        }
        var pointArray = [];
        for (var i = 0; i < count; i++) {
            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#0000FF", fillOpacity: 0.3}); //建立多边形覆盖物
            map.addOverlay(ply);  //添加覆盖物
            pointArray = pointArray.concat(ply.getPath());
        }
        //map.setViewport(pointArray);//调整视野
        map.centerAndZoom('天津市津南区', 12);
        ply.addContextMenu(getMenu());//右键菜单
    });
}

//地图右键菜单
function getMenu() {
    // 右键菜单
    var menu = new BMap.ContextMenu();
    var txtMenuItem = [{
        text: '添加站点信息',
        callback: function (e) {
            var point = new BMap.Point(e.lng, e.lat);
            var pointarry = [];
            pointarry.push(point)
            addMonitorStation(point, pointarry);
        }
    }, {
        text: '更新站点位置',
        callback: function (e) {
            var point = new BMap.Point(e.lng, e.lat);
            var selectedTreeNode = $('#mytree').tree('getSelected');
            updateMonitorLocation(point, selectedTreeNode);
        }
    }];
    for (var i = 0; i < txtMenuItem.length; i++) {
        menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,
            txtMenuItem[i].callback, 100));
    }
    return menu;
}

// 创建搜搜索框
function createSearchCtrl() {
    var inDiv = document.createElement("div");
    // 输入框
    var inText = document.createElement("input");
    inText.setAttribute("type", "text");
    inText.id = "searchmapaddrText";
    inText.className = "input";
    inText.value = "搜索地点";
    inDiv.appendChild(inText);
    // 输入框的删除按钮
    var a = document.createElement("a");
    a.href = "#";
    a.id = "a";
    a.className = "clear";
    a.onclick = function () {
        clearInput();
    }
    inDiv.appendChild(a);
    // 搜索按钮
    var inButton = document.createElement("input");
    inButton.setAttribute("type", "button");
    inButton.id = "searchmapaddrBtn";
    inButton.onclick = function () {
        getBoundary();
    };
    inText.onfocus = function () {
        clearTitle();
    };
    inText.onkeyup = function () {
        var value = document.getElementById("searchmapaddrText").value;
        var dispay = document.getElementById("a").style.display;
        if (value != "" && dispay == "none") {
            document.getElementById("a").style.display = "inline";// a标签显示
        } else if (value == "" && dispay == "inline") {
            document.getElementById("a").style.display = "none";// a标签显示
        }
    };
    inDiv.appendChild(inButton);
    var listDiv = document.createElement("div");
    listDiv.id = "r-result";
    inDiv.appendChild(listDiv);
    map.getContainer().appendChild(inDiv);
    document.getElementById("a").style.display = "none";// a标签隐藏
    return inDiv;
}

// 获取省、直辖市或县位置
function getBoundary() {
    var bdary = new BMap.Boundary();
    var name = document.getElementById("searchmapaddrText").value;
    if (ply != null) {
        map.removeOverlay(ply);
    }
    if (temMarkers.length > 0) {
        for (var i = 0; i < temMarkers.length; i++) {
            map.removeOverlay(temMarkers[i]);
        }
    }
    bdary.get(name, function (rs) { // 获取行政区域
        var count = rs.boundaries.length; // 行政区域的点有多少个
        if (count != 0) {
            for (var i = 0; i < count; i++) {
                ply = new BMap.Polygon(rs.boundaries[i], {
                    strokeWeight: 2,
                    strokeColor: "#ff0000"
                }); // 建立多边形覆盖物
                // map.addOverlay(ply); // 添加覆盖物
                map.setViewport(ply.getPath()); // 调整视野
            }
        } else {
            var local = new BMap.LocalSearch(map, {
                renderOptions: {
                    map: map,
                    panel: "r-result"
                },
                pageCapacity: 5
            });
            local.search(name);
            local.setMarkersSetCallback(function (pois) {
                for (var i = pois.length; i--;) {
                    // pois[i].marker.hide();
                    temMarkers.push(pois[i].marker);
                }
            });
        }

    });
}

// 鼠标获取焦点时的处理
function clearTitle() {
    var value = document.getElementById("searchmapaddrText").value;
    if (value == "搜索地点") {
        document.getElementById("searchmapaddrText").value = "";
    }
}

function clearInput() {
    document.getElementById("searchmapaddrText").value = "";
    getBoundary();
    document.getElementById("searchmapaddrText").value = "搜索地点";
    document.getElementById("a").style.display = "none";//a标签显示
}

function setPlace() {// 创建地址解析器实例
    var myGeo = new BMap.Geocoder();// 将地址解析结果显示在地图上,并调整地图视野
    myGeo.getPoint(myValue, function (point) {
        if (point) {
            map.centerAndZoom(point, 16);
            map.addOverlay(new BMap.Marker(point));
        }
    }, "北京");
}

//var flagRefresh = false;
/* GPS坐标转换 */
function gpsIntoBaiduPoint(mapdata, gpspoints) {
    translateCallback = function (data) {
        if (data.status === 0) {
            for (var i = 0; i < data.points.length; i++) {
                if (mapdata != undefined) {
                    addMarker(data.points[i], mapdata[i]);
                }
            }
        }
    }
    var convertor = new BMap.Convertor();
    convertor.translate(gpspoints, 1, 5, translateCallback);
}

/* 添加标注 */
function addMarker(point, data) {
    var currTab = $('#mytab').tabs('getSelected');
    var title = currTab.panel('options').title;
    var deviceCode = data.deviceCode;
    var myIcon = null;
    var node = $('#mytree').tree('find', deviceCode);//设置默认选中树
    if (data != undefined) {
        if (data.statusCode == "N") {
            myIcon = new BMap.Icon("../images/pointlink.png", new BMap.Size(32,
                32));
            if (node != null && node.isDevice) {
                $('#mytree').tree('update', {
                    target: node.target,
                    iconCls: 'icon-stationlink'
                });
            }
        } else if (data.statusCode == "NT") {
            var image = "../images/pointalarm1.png";
            var icon =  'icon-stationalarm1';
            if(data.levelNo=="1"){
                image = "../images/pointalarm1.png";
                icon = 'icon-stationalarm1';
            }else if(data.levelNo=="2"){
                image = "../images/pointalarm2.png";
                icon = 'icon-stationalarm2';
            }else if(data.levelNo=="3"){
                image = "../images/pointalarm3.png";
                icon = 'icon-stationalarm3';
            }
            myIcon = new BMap.Icon(image, new BMap.Size(32,
                32));
            if (node != null && node.isDevice) {
                $('#mytree').tree('update', {
                    target: node.target,
                    iconCls: icon
                });
            }

        } else if (data.statusCode == "O" || data.statusCode == "Z") {
            myIcon = new BMap.Icon("../images/pointunlink.png", new BMap.Size(
                32, 32));
            if (node != null && node.isDevice) {
                $('#mytree').tree('update', {
                    target: node.target,
                    iconCls: 'icon-stationunlink'
                });
            }
        } else {
            myIcon = new BMap.Icon("../images/pointfault.png", new BMap.Size(
                32, 32));
            if (node != null && node.isDevice) {
                $('#mytree').tree('update', {
                    target: node.target,
                    iconCls: 'icon-stationfault'
                });
            }
        }
    }
    if (title == "地图") {
        if (singleclick) {
            $.each(points, function (e, marker) {
                map.removeOverlay(marker);
            })
//			map.clearOverlays();
            points = {};
            gpspoints = {};
        }
        if (points[deviceCode] != undefined && points[deviceCode] != null) { // 存在标记点先删除
            var marker = points[deviceCode]; // 移除坐标
            map.removeOverlay(marker); // 删除存储信息
            delete points.deviceCode;
        }
        var marker = new BMap.Marker(point, {
            icon: myIcon
        });
//		map.addOverlay(marker);
        if (isInit || singleclick) {
            // map.setCenter(point);
            isInit = false;
            singleclick = false;
        }
        points[deviceCode] = marker; // 将标记存储
        bindMarkerEvent(marker, data);
    }
}

//点聚合wanglei
function addMarkerClusterer(pointArray) {
    var currTab = $('#mytab').tabs('getSelected');
    var title = currTab.panel('options').title;

    if (title == "地图") {
        if (requireClusterer) {
            var markers = [];
            $.each(points, function (e, marker) {
                markers.push(marker);
            })
            if (markerClusterer != null) {
                markerClusterer.clearMarkers();
            }
            markerClusterer = new BMapLib.MarkerClusterer(map, {markers: markers});
        } else {
            $.each(points, function (e, marker) {
                map.addOverlay(marker);
            })
        }
    }
}

/* 绑定click事件，显示站点详细信息 */
function bindMarkerEvent(marker, data) {
    marker.addEventListener("click", function (e) {
        selectedSearchMarkerData(marker, data);
    });
    if (data.userTel != null && data.userTel != "") {
        marker.setTitle(data.deviceName + "\n负责人：" + data.userName + "\n负责人电话：" + data.userTel);
    } else if (data.userTel != null) {
        marker.setTitle(data.deviceName + "\n负责人：" + data.userName + "\n负责人电话：未设置");
    }
    else {
        marker.setTitle(data.deviceName + "\n负责人：未设置\n负责人电话：未设置");
    }
}

var singleclick = false;
var clickPointData = {};

function conversionSearchData() {
    selectedSearchMarkerData(null, clickPointData);
}

/* 点击菜单站点查询信息 */
function selectedSearchMarkerData(marker, data) {
    clickPointData = {};
    var deviceData = getDeviceInfo(data.deviceCode);
    $("#seletedMapDeviceCode").val(data.deviceCode);
    getMapMonitorThings();			//获取监控物
    getMapMonitorAlarmLine(data.deviceCode);	//获取警报线
    $("#panelModel").panel(
        {
            collapsible: true,
            collapsed: true,
            closable: true,
            fit: false,
            title: data.deviceName + "--" + deviceData.rows[0].areaName,
            content: '<div class="easyui-layout" style="width:420px;height:500px;"data-options="fit:true">'

                + '<div data-options="region:\'north\',border:false" style="height:45%">'
                + '<div id="searchNorthContentMap"  style="background:yellow;"></div>'
                + '</div>'
                + '<div data-options="region:\'center\',border:false" id="centerContentMap">'
                + '<div id="searchContentMap"  style="background:yellow;width:100%"></div>'
                + '</div>'
                + '</div>'
        }).panel("open");
    /* 重绘窗口 */
    $.parser.parse("#panelModel");
    $("#panelModel").panel("refresh").panel("expand").panel('resize', {
        width: $(window).width() * 0.38,
        height: $(window).height() * 0.88
    });
    searchMapDeviceDataFuc(data.deviceCode);	// 查询数据
    searchMapChartFunction(data.deviceCode);	// 查询图像
    clickPointData = data;
}

/* 点击站点后查询 */
function selectedSearchData() {
    var mapdata = null;
    var station = $('#mytree').tree('getSelected');
//	var gpspoints = [];
    var levelflag;
    maptreeList = [];
    treelevelflag = "";
    singleclick = true;
    var alarmInfo = "";
    var zsFlag = false;
    if ($('#mytree').tree('getRoots').length > 0) {
        if (station == null || station == undefined) {
            maptreeList.push(-1);
            mapselected = "-1";
        } else {
            mapselected = station.id;
            levelflag = station.levelFlag;
            treelevelflag = station.levelFlag;
            maptreeList.push(station.id);
            if (maptreeList.length == 0) {
                maptreeList.push("0");
            }
        }
    } else {
        if (markerClusterer != null) {
            markerClusterer.clearMarkers();
        }
        map.clearOverlays();
        return false;
    }
    if (($('#mapzVauleChekbox') != undefined) && ($('#mapzVauleChekbox').is(':checked'))) {
        zsFlag = true;
    }
    $.ajax({
        url: "../DeviceController/getDeviceMapData",
        type: "post",
        dataType: "json",
        data: {
            "projectId": $("#deviceProjectId").combobox("getValue"),
            "list": maptreeList,
            "levelflag": levelflag,
            "nostatus": "",
            "select": mapselected,
            "maxsize": 5000,
            "zsFlag": zsFlag
        },
        success: function (json) {
            if (json.result != null) {
                var isRefresh = false;// 是否更新表格
                if (json.select == mapselected) {
                    isRefresh = true;
                }
                if (isRefresh) {
                    mapdata = json.result;
                    alarmList = [];
                    var mapCount = mapdata.length;
                    for (var i = 0; i < mapCount; i++) {
                        var deviceCode = mapdata[i].deviceCode;
                        var point = new BMap.Point(mapdata[i].deviceX, mapdata[i].deviceY);
//						gpspoints.push(point);
                        addMarker(point, mapdata[i]);//描点

                        /*----------------------新增：点击站点弹出panel显示详细信息-------------------------*/
                        var title = $('#mytab').tabs('getSelected').panel('options').title;
                        if (isNaN(json.select) && title == "地图") {
                            selectedSearchMarkerData(point, mapdata[i]);
                        }
                        /*---------------------------------------------------------------------------*/

                        if (mapdata[i].statusCode != "N") {
                            var value = displayAlarmInfo(mapdata[i].deviceName, mapdata[i].statusInfo);
                            if (value != "") {
                                if (alarmInfo == "") {
                                    alarmInfo = value;
                                } else {
                                    alarmInfo = alarmInfo + "；" + value;
                                }
                                alarmList.push(mapdata[i].deviceCode);
                            }
                            if (alarmInfo != "") {
                                alarmInfo = alarmInfo.substr(0, 100);
                            }
                        }
                    }
                    var pointArray = [];
                    for (var i = 0; i < mapCount; i++) {
                        var jsonPoint = {"lng": mapdata[i].deviceX, "lat": mapdata[i].deviceY};
                        pointArray = pointArray.concat(jsonPoint);
                    }

                    addMarkerClusterer(pointArray);
                    //if(station != null && station != undefined){
                    var view = map.getViewport(pointArray); //获取最佳视角
                    var zoom = view.zoom; //获取最佳视角的缩放层级
                    if (zoom <= 4) {
                        zoom = map.getZoom();
                        map.setViewport(pointArray, {"zoomFactor": (zoom - 2)});
                    } else {
                        map.setViewport(pointArray);
                    }
                    if (alarmInfo != "") {
                        $("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/emergency.gif");
                        playSound();
                    } else {
                        $("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/alarm.png");
                    }
                    $("#alarminfos").html(alarmInfo.trim());
                    if (alarmList.length > 0) {
                        $("#morebtn").css('display', '');
                    } else {
                        $("#morebtn").css('display', 'none');
                    }
                    /*var length = Math.ceil(gpspoints.length / 10);
                    var mode = gpspoints.length % 10;
                    for (var i = 0; i < length; i++) {
                        var gpspointsf = [];
                        var mapdataf = [];
                        if (i == length - 1 && mode != 0) {
                            gpspointsf = gpspoints.slice(i * 10,
                                    (i * 10 + mode));
                            mapdataf = mapdata.slice(i * 10, (i * 10 + mode));
                        } else {
                            gpspointsf = gpspoints.slice(i * 10, (i + 1) * 10);
                            mapdataf = mapdata.slice(i * 10, (i + 1) * 10);
                        }
                        gpsIntoBaiduPoint(mapdataf, gpspointsf);
                    }*///gps坐标转百度坐标处理，暂时不用了，但先别删除
//					if(mapCount>150){
//						$.messager.alert("提示", "超过百度显示标记上线，将只显示部分标记，可缩小查看范围显示坐标！", "warning");
//					}
                }
            } else {
                $.each(points, function (e, marker) {
                    map.removeOverlay(marker);
                })
//				map.clearOverlays();
            }
        }
    });
}

/* 实时获取地图点位数据 */

/* ajax长链接方式，20s查询一次 */
function selectedSearchDataConn() {
    var station = null;
    var mapdata = null;
    var zsFlag = false;
//	var gpspoints = [];
    var alarmInfo = "";
    if (mapajaxconn > 0) {// 始终保持一个连接
        return false;
    }
    mapajaxconn++;// 发起连接，连接数加1
    if ($('#mytree').tree('getRoots').length == 0) {
        map.clearOverlays();
        if (markerClusterer != null) {
            markerClusterer.clearMarkers();
        }
        return false;
    }
    if (($('#mapzVauleChekbox') != undefined) && ($('#mapzVauleChekbox').is(':checked'))) {
        zsFlag = true;
    }
    $.ajax({
        url: "../DeviceController/getDeviceMapData",
        type: "post",
        dataType: "json",
        async: true,
        data: {
            "projectId": $("#deviceProjectId").combobox("getValue"),
            "list": maptreeList,
            "levelflag": treelevelflag,
            "nostatus": "",
            "select": mapselected,
            "maxsize": 5000,
            "zsFlag": zsFlag
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            mapajaxconn--;
        },
        success: function (json) {
            mapdata = null;
            if (json.result != null) {
                var isRefresh = false;// 是否更新表格
                if (json.select == mapselected) {
                    isRefresh = true;
                }
                if (isRefresh) {
                    mapdata = json.result;
                    alarmList = [];
                    var mapCount = mapdata.length;
                    for (var i = 0; i < mapCount; i++) {
                        var point = new BMap.Point(mapdata[i].deviceX,
                            mapdata[i].deviceY);
//						gpspoints.push(point);
                        addMarker(point, mapdata[i]);//描点
                        if (mapdata[i].statusCode != "N") {
                            var value = displayAlarmInfo(mapdata[i].deviceName,
                                mapdata[i].statusInfo);
                            if (value != "") {
                                if (alarmInfo == "") {
                                    alarmInfo = value;
                                } else {
                                    alarmInfo = alarmInfo + "；" + value;
                                }
                                alarmList.push(mapdata[i].deviceCode);
                            }
                            if (alarmInfo != "") {
                                alarmInfo = alarmInfo.substr(0, 100);
                            }
                        }
                    }
                    var pointArray = [];
                    for (var i = 0; i < mapCount; i++) {
                        var jsonPoint = {"lng": mapdata[i].deviceX, "lat": mapdata[i].deviceY};
                        pointArray = pointArray.concat(jsonPoint);
                    }
                    addMarkerClusterer(pointArray);
                    var view = map.getViewport(pointArray); //获取最佳视角
                    var zoom = view.zoom; //获取最佳视角的缩放层级
                    if (zoom <= 4) {
                        zoom = map.getZoom();
                        map.setViewport(pointArray, {"zoomFactor": (zoom - 2)});
                    } else {
                        map.setViewport(pointArray);
                    }
                    //  map.setViewport(pointArray);
                    if (alarmInfo != "") {
                        playSound();
                        $("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/emergency.gif");
                    } else {
                        $("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/alarm.png");
                    }
                    $("#alarminfos").html(alarmInfo);
                    if (alarmList.length > 0) {
                        $("#morebtn").css('display', '');
                    } else {
                        $("#morebtn").css('display', 'none');
                    }
                    /*var length = Math.ceil(gpspoints.length / 10);
                    var mode = gpspoints.length % 10;
                    for (var i = 0; i < length; i++) {
                        var gpspointsf = [];
                        var mapdataf = [];
                        if (i == length - 1 && mode != 0) {
                            gpspointsf = gpspoints.slice(i * 10,
                                    (i * 10 + mode));
                            mapdataf = mapdata.slice(i * 10, (i * 10 + mode));
                        } else {
                            gpspointsf = gpspoints.slice(i * 10, (i + 1) * 10);
                            mapdataf = mapdata.slice(i * 10, (i + 1) * 10);
                        }
                        gpsIntoBaiduPoint(mapdataf, gpspointsf);
                    }*///gps坐标转百度坐标处理，暂时不用了，但先别删除
                }
            }
            if (mapajaxconn > 0) {
                mapajaxconn--; // 当连接关闭、连接数减1
            }
//			if (mapajaxconn == 0) { // 如果连接数少于1 则发起新的连接
//				selectedSearchDataConn();
//			}
        }
    });
}

/* 显示主页更多报警信息 */
function getMoreAlarm() {
    $("#dialogModel").dialog({
        width: 600,
        height: 424,
        title: "报警信息",
        inline: true,
        modal: true,
        maximized: false,
        collapsible: false,
        minimizable: false,
        maximizable: true,
        iconCls: 'icon-alarm',
        resizable: true,
        closed: true,
        content: '<div id="dgAlarm" class="config-form"></div>',
        buttons: [{
            text: "确定",
            iconCls: "icon-ok",
            handler: function () {
                $("#dialogModel").dialog("close");
            }
        }]
    }).dialog('center');
    $("#dgAlarm").datagrid({
        view: myview,
        fit: true,
        border: false,
        pagination: true,
        fitColumns: true,
        singleSelect: true,
        pageList: [10, 50, 100, 150, 200, 250, 300],
        url: "../DeviceController/getDeviceMapAlarmDetail",
        queryParams: {
            list: maptreeList,
            nostatus: "N",
            levelflag: treelevelflag
        },
        pageSize: 10,
        autoRowHeight: false,
        rownumbers: true,
        columns: [[{
            field: 'deviceCode',
            title: '设备编号',
            width: 100,
            halign: 'center',
            align: 'center'
        }, {
            field: 'deviceName',
            title: '站点名称',
            width: 150,
            halign: 'center',
            align: 'center'
        }, {
            field: 'statusInfo',
            title: '报警信息',
            width: 100,
            halign: 'center',
            align: 'center'
        }]]
    }).datagrid('doCellTip', {cls: {'max-width': '500px'}});
    /* 定义分页器的初始显示默认值 */
    $("#dgAlarm").datagrid("getPager").pagination({
        total: 0
    });
    /* 重绘窗口 */
    $.parser.parse("#dialogModel");
    $("#dialogModel").dialog("open");
}

/* 获取设备信息 */
function getDeviceInfo(deviceCode) {
    var datajson = null;
    $.ajax({
        url: "../DeviceController/queryDevice",
        type: "post",
        dataType: "json",
        async: false,
        data: {
            "deviceCode": deviceCode
        },
        success: function (json) {
            datajson = json;
        }
    });
    return datajson;
}

// 存储监控物的范围值
var alarmMapRange = {};
//$.ajax({
//	url : "../MonitorStorageController/getAthorityMonitors",
//	type : "post",
//	dataType : "json",
//	async : false,
//	success : function(json) {
//		for (var i = 0; i < json.length; i++) {
//			alarmMapRange[json[i].name] = {
//				"max" : json[i].max,
//				"min" : json[i].min
//			};
//		}
//	}
//});

/* 查询设备24小时内的监控数据 */
function searchMapDeviceDataFuc(deviceCode) {
    var colums = [];// 存储列内容
    var monitors = {};// 监控物
    var datajson = [];// 存储列表数据
    ajaxLoading();
    var zsFlag = false;
    if ($('#mapzVauleChekbox').is(':checked')) {
        zsFlag = true;
    }
    $.ajax({
        url: "../MonitorStorageController/getTimelyMonitorData",
        data: {
            "devicecode": deviceCode,
            "isrepeat": false,
            "zsFlag": zsFlag
        },
        async: true,
        type: "post",
        dataType: "json",
        error: function (json) {
            ajaxLoadEnd();
        },
        success: function (data) {
            ajaxLoadEnd();
            var isRefresh = false;// 是否更新表格
            if (data.select == deviceCode) {
                isRefresh = true;
            }
            if (data.result != null) { // 请求成功
                isrepeatFlag = true;
                for (var key in data.result) {
                    var list = data.result[key];
                    for (var name in list) {// 获取监控物集合
                        if (monitors[name] == undefined) {
                            monitors[name] = name;
                        }
                    }
                    list["time"] = key;
                    datajson.push(list);
                }
                var frozenColumns = [];
                frozenColumns.push({
                    field: 'time',
                    title: '时间',
                    width: 130,
                    halign: 'center',
                    align: 'center',
                    frozen: true
                });
                var j = 0;
                for (var name in monitors) {
                    var title = name;
                    if (name.indexOf("zs") == -1) {
                        if (j > 3) {
                            colums.push({
                                field: name, title: title, width: 130, halign: 'center', align: 'center', hidden: true,
                                formatter: function (value, row, index) {
                                    var zvalue = (row[(this.field + "-zs")] == null) ? "---" : (row[(this.field + "-zs")]);
                                    return tableShowHandler(value, this.field, zsFlag, zvalue,alarmMapRange);
                                }
                            });
                        } else {
                            colums.push({
                                field: name, title: title, width: 130, halign: 'center', align: 'center',
                                formatter: function (value, row, index) {
                                    var zvalue = (row[(this.field + "-zs")] == null) ? "---" : (row[(this.field + "-zs")]);
                                    return tableShowHandler(value, this.field, zsFlag, zvalue,alarmMapRange);
                                }
                            });
                        }
                        j++;
                    }

                }
                cmenu = null;
                if (isRefresh) {
                    $("#searchNorthContentMap").datagrid({
                        view: myview,
                        fit: true,
                        border: false,
                        pagination: false,
                        fitColumns: false,
                        singleSelect: true,
                        pageList: [10, 50, 100, 150, 200, 250, 300],
                        pageSize: 10,
                        autoRowHeight: false,
                        rownumbers: true,
                        frozenColumns: [frozenColumns],
                        columns: [colums],
                        data: datajson,
                        onHeaderContextMenu: function (e, field) {
                            e.preventDefault();
                            if (!cmenu) {
                                createColumnMenu("searchNorthContentMap");
                            }
                            cmenu.menu('show', {
                                left: e.pageX,
                                top: e.pageY
                            });
                        }
                    });
                }
            }
        }
    });
}

/* 图表 */
function searchMapChartFunction(deviceCode) {
    var datajson = [];
//	var CentercontentMap = $("#centerContentMap");
//	CentercontentMap.html("");
//	$("#centerContentMap").append(
//			'<div id="searchContentMap" style="width:600px;"></div>');
    var yname = "";// 图表y轴名称
    var units = "";// 监控物单位
    var timelist = {};
    var legendData = [];
    var seriesData = [];
    ajaxLoading();
    var zsFlag = false;
    if ($('#mapzVauleChekbox').is(':checked')) {
        zsFlag = true;
    }
    $.ajax({
        url: "../MonitorStorageController/getTimelyMonitorChartData",
        type: "post",
        dataType: "json",
        async: true,
        data: {
            "devicecode": deviceCode,
            "zsFlag": zsFlag
        },
        error: function (json) {
            ajaxLoadEnd();
        },
        success: function (json) {
            ajaxLoadEnd();
            if (json.time != undefined) {
                var timeArry = json["time"];
                var max = 0;
                var selectedNum = 0;
                var selectedlegendData = {};
                for (var index in json) {
                    if (index != "time") {
                        legendData.push(index);
                        if (alarmMapRange[index] != undefined) {
                            max = alarmMapRange[index].max;
                        } else {
                            max = 0;
                        }
                        seriesData.push({
                            "name": index,
                            "type": 'line',
                            "data": json[index],
                            markPoint: {
                                data: [{
                                    type: 'max',
                                    name: '最大值'
                                }, {
                                    type: 'min',
                                    name: '最小值'
                                }]
                            },
                            markLine: {
                                itemStyle: {
                                    normal: {lineStyle: {width: 2}}
                                },
                                data: [
                                    [
                                        {name: '报警线', value: max, xAxis: timeArry[0], yAxis: max},
                                        {name: '报警线', xAxis: timeArry[timeArry.length - 1], yAxis: max}
                                    ]
                                ]
                            }
                        });
                        if (selectedNum < 6) {
                            selectedlegendData[index] = true;
                        } else {
                            selectedlegendData[index] = false;
                        }
                        selectedNum++;
                    } else {
                        timelist[index] = json[index];
                    }
                }
                initMapChart(timelist, legendData, selectedlegendData, seriesData, yname);
            }
//			else {
//				$.messager.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
//			}
        }
    });
}

/* 初始化表格 */
function initMapChart(timelist, legendData, selectedlegendData, seriesData, yname) {
    var dom = document.getElementById("searchContentMap");
    dom.style.cssText = "height:98%";
    var myChart = echarts.init(dom);
    var option = {
        tooltip: {
            trigger: 'axis'
        }, grid: {
            left: '4%',
            right: '22%',
            bottom: '8%',
            containLabel: true
        },
        legend: {
            itemWidth: 10,
            itemHeight: 12,
            itemGap: 10,
            type: 'scroll',
            orient: 'vertical',
            right: "0.5%",
            "top": '8%',
            data: legendData,
            formatter: function (name) {
                if (!name) return '';
                if (name.length > 9) {
                    name = name.slice(0, 9) + '...';
                }
                return name;
            },
            tooltip: {
                show: true
            },
            data: legendData,
            selected: selectedlegendData,
            show: true
        },
        toolbox: {
            show: true,
            orient: 'horizontal',
            x: 'center',
            feature: {
                mark: {show: true},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                dataZoom: {},
                saveAsImage: {show: true}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: timelist["time"],
            axisLine: {    // 轴线
                show: true,
                lineStyle: {
                    color: 'green',
                    type: 'solid',
                    width: 2
                }
            },
            axisLabel: {
                show: true,
                textStyle: {
                    color: 'green',
                    fontFamily: 'sans-serif',
                    fontSize: 13,
                    fontWeight: 'bold'
                }
            }
        },
        yAxis: {
            name: yname,
            type: 'value',
            axisLine: {    // 轴线
                show: true,
                lineStyle: {
                    color: 'green',
                    type: 'solid',
                    width: 2
                }
            },
            axisLabel: {
                formatter: '{value}',
                textStyle: {
                    color: 'green',
                    fontFamily: 'sans-serif',
                    fontSize: 13,
                    fontWeight: 'bold'
                }
            }
        },
        series: seriesData
    };
    myChart.setOption(option);
}

function displayAlarmInfo(deviceCode, statusCode) {
    return deviceCode + "：" + statusCode + "";
}
var mfrCodeDataMap = {};
var mfrCodeValueMap = null;
// 状态
var statusCodeDataMap = {};
var statusValueMap = null;
// 区域ID
var areaIdDataMap = {};
var areaValueMap = null;
// 负责人
var userIdDataMap = {};
var userValueMap = null;
// 监督单位
var orgIdDataMap = {};
var orgValueMap = null;
// 项目类型ID
var projectIdDataMap = {};
var projectValueMap = null;
/* 添加监控站点 */
var replyFlagData = {
    "2005": "2005协议",
    "2017": "2017协议"
};
var replyFlagDataValue = "2017";
var forceReplyData = {
    "0": "否",
    "1": "是"
};
var forceReplyDataValue = "0";
var staFlowData = {
    "0": "否",
    "1": "是"
};
var staFlowDataValue = "0";

function addMonitorStation(point, stationpoints) {
    //translateCallback = function(data) {
    //if (data.status === 0) {
    /*	var x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        var lng = 2 * point.lng - data.points[0].lng;
        var lat = 2 * point.lat - data.points[0].lat;*///地图转gps坐标暂时不用了
    var lng = point.lng;
    var lat = point.lat;
    initParamMap();
    $("<div></div>").dialog({
        id: "devdialogModel",
        width: 450,
        height: 520,
        title: "添加监控站点",
        modal: true,
        maximized: false,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        cache: false,
        resizable: true,
        closed: true,
        content: '<form id="devfrmdialogModel" class="config-form"></form>',
        onClose: function () {
            $(this).dialog('destroy');
        },
        buttons: [{
            text: "确定",
            iconCls: "icon-ok",
            handler: function () {
                if ($("#devfrmdialogModel").form("validate")) {
                    var staFlow = $("#staFlow").combobox("getValue");
                    if (staFlow == true) {
                        if ($("#pipeArea").val() == null || $("#pipeArea").val() == ""
                            || $("#pipeArea").val() <= 0) {
                            $.messager.alert("提示", "烟筒面积必须大于0！", "info");
                            return false;
                        }
                    }
                    var formdataArray = $("#devfrmdialogModel").serializeArray();// 将表单数据序列化创建一个json数组
                    var formdataJosn = getFormJson(formdataArray);// 转换成json数组
                    formdataJosn["staMinute"] = $('#staMinute').is(':checked');
                    formdataJosn["staHour"] = $('#staHour').is(':checked');
                    formdataJosn["staDay"] = $('#staDay').is(':checked');
                    // 发送ajax请求
                    $.ajax({
                        url: "../DeviceController/insertDevice",
                        type: "post",
                        dataType: "json",
                        data: formdataJosn,
                        error: function (json) {
                            $.messager.alert("提示", json.detail, "info");
                        },
                        success: function (json) {
                            if (json.result) {
                                $("#devdialogModel").dialog("destroy");
                                $.messager.alert("提示", "监控站点添加完成，请在“权限设置”中设置权限！", "info");
                            } else {
                                $.messager.alert("错误", json.detail, "error");
                            }
                        }
                    });
                }
            }
        }, {
            text: "取消",
            iconCls: "icon-cancel",
            handler: function () {
                $("#devdialogModel").dialog("destroy");
            }
        }]
    }).dialog('center');
    /* 初始化表单 */
    $("#devfrmdialogModel").html(function () {
        var htmlArr = [];
        htmlArr.push(createValidatebox({
            name: "deviceId",
            title: "设备ID",
            ishiden: true,
            value: "-1"
        }));
        htmlArr.push(createValidatebox({
            name: "deviceCode",
            title: "设备编号",
            noBlank: true,
            type: 'devicecode'
        }));
        htmlArr.push(createValidatebox({
            name: "deviceMn",
            title: "设备MN号",
            noBlank: true,
            type: 'maxLength[100]'
        }));
        htmlArr.push(createValidatebox({
            name: "deviceName",
            title: "设备名称",
            noBlank: true,
            type: 'maxLength[100]'
        }));
        htmlArr.push(createCombobox({
            name: "mfrCode",
            title: "所属厂商",
            data: mfrCodeDataMap,
            value: mfrCodeValueMap,
            noBlank: true
        }));
        htmlArr.push(createCombobox({
            name: "statusCode",
            title: "设备状态",
            data: statusCodeDataMap,
            value: statusValueMap,
            noBlank: true
        }));
        htmlArr.push(createValidatebox({
            name: "deviceIp",
            title: "设备IP",
            type: 'ip'
        }));
        htmlArr.push(createValidatebox({
            name: "devicePort",
            title: "设备端口",
            value: "1000",
            type: 'devicevalid'
        }));
        htmlArr.push(createValidatebox({
            name: "devicePwd",
            title: "设备访问密码",
            noBlank: true,
            type: 'maxLength[100]'
        }));
        htmlArr.push(createValidatebox({
            name: "deviceX",
            title: "设备经度",
            readonly: true
        }));
        htmlArr.push(createValidatebox({
            name: "deviceY",
            title: "设备纬度",
            readonly: true
        }));
        htmlArr.push(createComboboxEdit({
            name: "areaId",
            title: "所属区域",
            data: areaIdDataMap,
            value: areaValueMap,
            noBlank: true
        }));
        htmlArr.push(createValidatebox({
            name: "deviceKm",
            title: "监测范围"
        }));
        htmlArr.push(createComboboxEdit({
            name: "projectId",
            title: "项目类型",
            data: projectIdDataMap,
            value: projectValueMap,
            noBlank: true
        }));
        htmlArr.push(createComboboxEdit({
            name: "orgId",
            title: "监督单位",
            data: orgIdDataMap,
            value: orgValueMap
        }));
        htmlArr.push(createValidatebox({
            name: "buildFirm",
            title: "施工单位",
            type: 'maxLength[100]'
        }));
        htmlArr.push(createComboboxEdit({
            name: "userId",
            title: "负责人",
            data: userIdDataMap,
            value: userValueMap
        }));
        htmlArr.push(createValidatebox({
            name: "deviceAddress",
            title: "设备地址",
            type: 'maxLength[100]'
        }));
        htmlArr.push(createDatetimebox({
            name: "inspectTime",
            title: "巡检时间",
            showSeconds: true
        }));
        htmlArr.push(createValidatebox({
            name: "systemVersion",
            title: "系统版本",
            type: 'maxLength[25]'
        }));
        htmlArr.push(createCombobox({
            name: "replyFlag",
            title: "协议版本",
            data: replyFlagData,
            valueField: 'code',
            textField: 'name',
            value: replyFlagDataValue
        }));
        htmlArr.push(createValidatebox({
            name: "hourCount",
            title: "小时内实时数",
            noBlank: true,
            type: 'positiveInteger',
            value: 60
        }));
        htmlArr.push(createCombobox({
            name: "forceReply",
            title: "强制回复标识",
            data: forceReplyData,
            valueField: 'code',
            textField: 'name',
            value: forceReplyDataValue
        }));
        htmlArr.push(createCombobox({
            name: "staFlow",
            title: "标态流量统计",
            data: staFlowData,
            valueField: 'code',
            textField: 'name',
            value: staFlowDataValue
        }));
        htmlArr.push(createValidatebox({
            name: "pipeArea",
            title: "烟筒面积(米)",
            noBlank: true,
            ishiden: notRequireStaFlow,
            type: 'intOrFloat[0,10000]',
            value: 0
        }));
        htmlArr.push('<div>系统统计'
            + '<label style="width:auto;"><input type="checkbox" id="staMinute" name="staMinute" style="margin-left:100px;vertical-align:middle;"><span style="vertical-align:middle;">分钟</span></label>'
            + '<label style="width:auto;"><input type="checkbox" id="staHour" name="staHour" style="margin-left:10px;vertical-align:middle;"><span style="vertical-align:middle;margin-left:-5px;">小时</span></label>'
            + '<label style="width:auto;"><input type="checkbox" id="staDay" name="staDay" style="margin-left:10px;vertical-align:middle;"><span style="vertical-align:middle;">每日</span></label>'
            + '</div><br>');
        return htmlArr.join("");
    });
    /* 重绘窗口 */
    $.parser.parse("#devdialogModel");
    $("#devdialogModel").dialog("open");
    if (notRequireStaFlow) {
        $("#staFlow_div").css("display", "none");//隐藏
    }
    $("#areaId").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值  
            var allData = $(this).combobox("getData");   //获取combobox所有数据  
            var result = true;      //为true说明输入的值在下拉框数据中不存在  
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }

        }
    });
    $("#orgId").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值  
            var allData = $(this).combobox("getData");   //获取combobox所有数据  
            var result = true;      //为true说明输入的值在下拉框数据中不存在  
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }

        }
    });
    $("#userId").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值  
            var allData = $(this).combobox("getData");   //获取combobox所有数据  
            var result = true;      //为true说明输入的值在下拉框数据中不存在  
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }

        }
    });
    var selectrow = {
        "deviceX": lng.toFixed(6),
        "deviceY": lat.toFixed(6)
    };
    $("#devfrmdialogModel").form("load", selectrow);
    //}
    //}
    /*var convertor = new BMap.Convertor();
    convertor.translate(stationpoints, 1, 5, translateCallback);*/
}

/* 更新监测站位置 */
function updateMonitorLocation(point, treeNode) {
    if (treeNode == null) {
        $.messager.alert("错误", "请选择一个监测点！", "error");
        return;
    }
    var formData = {};
    formData["deviceCode"] = treeNode.id;
    formData["deviceName"] = treeNode.text;
    formData["deviceX"] = point.lng;
    formData["deviceY"] = point.lat;
    $("<div></div>").dialog({
        id: "editDevdialog",
        width: 520,
        height: 380,
        title: "更新监测点位置",
        iconCls: 'icon-edit',
        modal: true,
        maximized: false,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        cache: false,
        resizable: true,
        closed: true,
        content: '<form id="editDevfrmdialogModel" class="config-form" style="width:400px;float:left;"></form>',
        onClose: function () {
            $(this).dialog('destroy');
        },
        buttons: [
            {
                text: "确定",
                iconCls: "icon-ok",
                handler: function () {
                    if ($("#editDevfrmdialogModel").form("validate")) {
                        var formdataArray = $("#editDevfrmdialogModel").serializeArray();
                        var formdataJosn = getFormJson(formdataArray);// 转换成json数组
                        $.ajax({
                            url: "../DeviceController/updateDeviceLocation",
                            type: "post",
                            dataType: "json",
                            data: formdataJosn,
                            success: function (json) {
                                if (json.result) {
                                    $("#editDevdialog").dialog("destroy");
                                    $.messager.alert("提示", json.detail, "info");
                                    selectedSearchData();
                                } else {
                                    $.messager.alert("错误", json.detail, "error");
                                }
                            }
                        });
                    }
                }
            }, {
                text: "取消",
                iconCls: "icon-cancel",
                handler: function () {
                    $("#editDevdialog").dialog("destroy");
                }
            }
        ]
    }).dialog('center');

    /* 初始化表单 */
    $("#editDevfrmdialogModel").html(function () {
        var htmlArr = [];
        htmlArr.push(createValidatebox({
            name: "deviceCode",
            title: "监测点编号",
            readonly: true
        }));
        htmlArr.push(createValidatebox({
            name: "deviceName",
            title: "监测点名称",
            readonly: true
        }));
        htmlArr.push(createValidatebox({
            name: "deviceX",
            title: "经度",
            value: "0"
        }));
        htmlArr.push(createValidatebox({
            name: "deviceY",
            title: "维度",
            value: "0"
        }));
        return htmlArr.join("");
    });
    /* 重绘窗口 */
    $.parser.parse("#editDevdialog");
    $("#editDevdialog").dialog("open");
    $("#editDevfrmdialogModel").form("load", formData);
}

/* 初始化厂商ID、设备状态、区域ID,下拉框 */
function initParamMap() {
    $.ajax({
        url: "../DeviceController/queryDevicemfrCodeDropDown",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    mfrCodeDataMap[json.rows[i].code] = json.rows[i].name;
                }
                mfrCodeValueMap = json.rows[0].code;
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDevicestatusCodeDropDown",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    statusCodeDataMap[json.rows[i].code] = json.rows[i].name;
                }
                statusValueMap = json.rows[0].code;
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDeviceAreaDropDown",
        type: "post",
        dataType: "json",
        data: {
            "id": "-1",
            "levelFlag": "-1"
        },
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    areaIdDataMap[json.rows[i].id] = json.rows[i].name;
                }
                areaValueMap = json.rows[0].id;
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDevicePrincipleDropDown",
        type: "post",
        dataType: "json",
        data: {
            "devicePrinciple": "-1"
        },
        async: false,
        success: function (json) {
//			userIdDataMap[0] = "---请选择---";
//			userValueMap = "0";
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    userIdDataMap[json.rows[i].id] = json.rows[i].name;
                }
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDeviceOversightUnit",
        type: "post",
        dataType: "json",
        data: {
            "orgId": "-1"
        },
        async: false,
        success: function (json) {
//			orgIdDataMap[0] = "---请选择---";// orgID对应的键值
//			orgValueMap = 0; // orgdata默认的值
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    orgIdDataMap[json.rows[i].id] = json.rows[i].name;
                }
            }

        }
    });
    $.ajax({
        url: "../DeviceProjectController/queryDeviceProject",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    projectIdDataMap[json.rows[i].projectId] = json.rows[i].projectName;
                }
                projectValueMap = json.rows[0].projectId;
            }
        }
    });
}

//实时监控存储监控物的范围值
var alarmRange = {};
//实时监控存储监控物带单位
var monitorsThingJson = {};
var monitorsnetStautsThingJson = {};
//实时监控
var monitorslist = [];
//实时监控存储之前站点
var preStation = null;
var initMonitorsflag = true;
//记录监控状态当前页
var netStatuspageNumber = 1;
var netStatuspageSize = 10;
//记录监控数据当前页
var netDatapageNumber = 1;
var netDatapageSize = 10;

$(function () {
    window.onload = loadJScript; // 异步加载地图
    $("#mytree")
        .tree(
            {
                checkbox: false,
                onSelect: function (node) {
                    var currTab = $('#mytab').tabs('getSelected');
                    var title = currTab.panel('options').title;
                    if (title == "网络状态") {
                        searchNetStatusSelected();
                    } else if (title == "网络数据") {
                        searchNetDataSelected();
                    } else if (title == "地图") {
                        selectedSearchData();
                    }
                },
                onLoadSuccess: function (node, data) {
                    var currTab = $('#mytab').tabs('getSelected');
                    var currTabTitle = currTab.panel('options').title;
                    if (currTabTitle == "地图") {
                        $('#panelModel').panel('close');
                        if (data.length == 0) {
                            $("#promptInfo").html("未查到相应监控站点信息");
                            $("#promptInfo").css("display", "block");

                        } else {
                            $("#promptInfo").html("");
                            $("#promptInfo").css("display", "none")
                        }
                        if (map != undefined) {
                            selectedSearchData();
                        }
                    }
                    else if (currTabTitle == "网络状态") {
                        if (data.length > 0) {
                            var node = $("#mytree").tree('find', data[0].id);//找到id为”tt“这个树的节点id为”1“的对象
                            $("#mytree").tree('select', node.target);//设置选中该节点
                        }
                        searchNetStatusSelected();
                    } else if (currTabTitle == "网络数据") {
                        if (data.length > 0) {
                            var node = $("#mytree").tree('find', data[0].id);//找到id为”tt“这个树的节点id为”1“的对象
                            $("#mytree").tree('select', node.target);//设置选中该节点
                        }
                        searchNetDataSelected();
                    }
                }
            });
    var audio = document.getElementById("myaudio");
    audio.src = "./../../1262.mp3";

});


/*获取监控物*/
function getMapMonitorThings() {
    $.ajax({
        url: "../MonitorStorageController/getAthorityMonitors",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            for (var i = 0; i < json.length; i++) {
                monitorsThingJson[json[i].name] = json[i].describe;
                monitorslist.push(json[i].code);
            }
        }
    });
    if (monitorslist.length <= 0) {
        monitorslist.push(-1);
    } else {
        monitorslist = monitorslist;
    }
}

/*获取报警线*/
function getMapMonitorAlarmLine(deviceCode) {
    var stationlist = [];
    stationlist.push(deviceCode);
//	var selectedStation = $('#mytree').tree('getSelected');
//	if(selectedStation!=null && selectedStation!=undefined){
//		stationlist.push(deviceCode);
//	}else{
//		stationlist.push(deviceCode);
//	}
    if (monitorslist != undefined) {
        $.ajax({
            url: "../DeviceAlarmSetController/getDeviceAlarmLineThgName",
            type: "post",
            dataType: "json",
            async: false,
            data: {"listdev": stationlist, "listthg": monitorslist},
            success: function (json) {
                if (json) {
                    alarmMapRange = json[deviceCode];
                }
            }
        });
    }
}

//重新连接
function reConnect() {
    if (lockReconnect) {
        return;
    }
    lockReconnect = true;
    setTimeout(function () {     //没连接上会一直重连，设置延迟避免请求过多
        initWebsocket();
        lockReconnect = false;
    }, 10000);
}


//心跳检测
var heartCheck = {
    timeout: 60000,        //60秒发一次心跳
    timeoutObj: null,
    serverTimeoutObj: null,
    reset: function () {
        clearTimeout(this.timeoutObj);
        clearTimeout(this.serverTimeoutObj);
        return this;
    },
    start: function () {
        var self = this;
        this.timeoutObj = setTimeout(function () {
            //这里发送一个心跳，后端收到后，返回一个心跳消息，
            //onmessage拿到返回的心跳就说明连接正常
            websocket.send("1000");
            console.log("1000");
            self.serverTimeoutObj = setTimeout(function () {
                //如果超过一定时间还没重置，说明后端主动断开了
                console.log("heartCheck websocket.close!")
                websocket.close();
            }, self.timeout)
        }, this.timeout)
    }
}

function playSound() {
    /*   var audio = $("#myaudio");
       audio.attr("src",src);*/
    var audio = document.getElementById("myaudio");
    console.info(audio.muted);
    if (!audio.muted) {
        audio.play();
    } else {
        audio.pause();
    }

}

function closeSound() {
    var audio = document.getElementById("myaudio");
    if (audio.muted) {
        document.getElementById("myImage").src = "./../javascript/jquery-easyui-1.4.4/themes/icons/voiceplay.png";
        audio.muted = false;
        if ($("#alarminfos").html() != "") {
            audio.play();
        }
    } else {
        document.getElementById("myImage").src = "./../javascript/jquery-easyui-1.4.4/themes/icons/voicestop.png";
        audio.muted = true;
    }

}

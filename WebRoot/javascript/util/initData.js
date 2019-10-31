//var stopajax = false;
//var stopnetStatusajax = false;
//var stopnetDataajax = false;
var netStatustreeidList = [];
var longconnselected = "";
//存储点击的树节点
var netDataList = [];
var netDataSelected = "";
var realtreeid = -1;//选择的站点
var premultiStationFlag = false;//上一次tab页是否为多站
$(function () {


    $("#loginTime").html(formatterDate(new Date()));
    /*var loginname = "";
    loginname = getCookieValue('EnvDustusername');
    if(loginname!=null && loginname!=""){
        $("#username").html(loginname);
    }else{
        $("#username").html("");
    }*/
    getUserPower();
    setMainLogoWeb("./../");
    var qcloud = {};
    $('[_t_nav]').hover(
        function () {
            var _nav = $(this).attr('_t_nav');
            clearTimeout(qcloud[_nav + '_timer']);
            qcloud[_nav + '_timer'] = setTimeout(
                function () {
                    $('[_t_nav]')
                        .each(
                            function () {
                                $(this)[_nav == $(this).attr(
                                    '_t_nav') ? 'addClass'
                                    : 'removeClass']
                                ('nav-up-selected');
                            });
                    $('#' + _nav).stop(true, true).slideDown(200);
                }, 150);
        }, function () {
            var _nav = $(this).attr('_t_nav');
            clearTimeout(qcloud[_nav + '_timer']);
            qcloud[_nav + '_timer'] = setTimeout(function () {
                $('[_t_nav]').removeClass('nav-up-selected');
                $('#' + _nav).stop(true, true).slideUp(200);
            }, 150);
        });
    /*$('#manufacturer').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            searchStationFunc();
        }
    });*/
    $('#mytab').tabs({
        onBeforeClose: function (title, index) {
            if (title == "实时数据") {
                if ($("#realTimeCountDownTimer") != null && $("#realTimeCountDownTimer").TimeCircles() != null) {
                    $("#realTimeCountDownTimer").TimeCircles().destroy();
                }
            } else if (title == '网络状态') {
                if ($("#netStatusCountDownTimer") != null && $("#netStatusCountDownTimer").TimeCircles() != null) {
                    $("#netStatusCountDownTimer").TimeCircles().destroy();
                }
            } else if (title == "网络数据") {
                if ($("#netDataCountDownTimer") != null && $("#netDataCountDownTimer").TimeCircles() != null) {
                    $("#netDataCountDownTimer").TimeCircles().destroy();
                }
            } else if (title == "采样设备监控") {
                if ($("#netSampleDeviceTimer") != null && $("#netSampleDeviceTimer").TimeCircles() != null) {
                    $("#netSampleDeviceTimer").TimeCircles().destroy();
                }
            }
        },
        onClose: function (title, index) {
            if (title == "实时数据") {
//			   stopajax = true;
//			   clearInterval(timer);
                if ($("#realTimeCountDownTimer") != null && $("#realTimeCountDownTimer").TimeCircles() != null) {
                    $("#realTimeCountDownTimer").TimeCircles().destroy();
                }
            } else if (title == "网络状态") {
                selectStatusCode = "";
                if ($("#netStatusCountDownTimer") != null && $("#netStatusCountDownTimer").TimeCircles() != null) {
                    $("#netStatusCountDownTimer").TimeCircles().destroy();
                }
            } else if (title == "网络数据") {
                dataStatusCode = "";
                if ($("#netDataCountDownTimer") != null && $("#netDataCountDownTimer").TimeCircles() != null) {
                    $("#netDataCountDownTimer").TimeCircles().destroy();
                }
            } else if (title == "采样设备监控") {
                if ($("#netSampleDeviceTimer") != null && $("#netSampleDeviceTimer").TimeCircles() != null) {
                    $("#netSampleDeviceTimer").TimeCircles().destroy();
                }
            } else if (title == "历史监控热力图") {
                $('#panelModel').panel("close");
            }
        },
        onSelect: function (title) {
            var currMultiStation = false;
            currMultiStation = premultiStationFlag;
            if (title == "多站点单物质查询") {
                premultiStationFlag = true;
            } else {
                premultiStationFlag = false;
            }
            if (premultiStationFlag && !currMultiStation) {
                $("#mytree").tree({
                    checkbox: true,
                    data: $("#mytree").tree('getRoots')
                });
            }
            else if (!premultiStationFlag && currMultiStation) {
                $("#mytree").tree({
                    checkbox: false,
                    data: $("#mytree").tree('getRoots')
                });
            }
            if (title != "实时数据" && title != "网络状态" && title != "网络数据") {
//			   stopajax = true;
//			   stopnetStatusajax = true;
//			   stopnetDataajax = true;
            } else {
                if (title == "实时数据") {
//				   stopajax = false;
//				   stopnetStatusajax = true;
//				   stopnetDataajax = true;
                    if (realtreeid == -1) {
                        var nodeRoot = $("#mytree").tree('getRoot');
                        var node = $('#mytree').tree('find',
                            nodeRoot.id);
                        $('#mytree').tree('select', node.target);
                    }
                    else {
                        var node = $('#mytree').tree('find', realtreeid);
                        $('#mytree').tree('select', node.target);
                    }
                }
                else if (title == "网络状态") {
//				   stopnetStatusajax = false;
//				   stopajax = true;
//				   stopnetDataajax = true;
                    if (longconnselected != "") {
                        var node = $('#mytree').tree('find', longconnselected);
                        if (node != null) {
                            $('#mytree').tree('select', node.target);
                        }
                    }
                    else {
                        var nodeRoot = $("#mytree").tree('getRoot');
                        var node = $('#mytree').tree('find',
                            nodeRoot.id);
                        $('#mytree').tree('select', node.target);
                    }
                }
                else if (title == "网络数据") {
//				   stopnetDataajax = false;
//				   stopajax = true;
//				   stopnetStatusajax = true;
                    if (netDataSelected != "") {
                        var node = $('#mytree').tree('find', netDataSelected);
                        if (node != null) {
                            $('#mytree').tree('select', node.target);
                        }
                    } else {
                        var nodeRoot = $("#mytree").tree('getRoot');
                        var node = $('#mytree').tree('find',
                            nodeRoot.id);
                        $('#mytree').tree('select', node.target);
                    }
                }
            }
            if (title == "地图") {
                if (mapselected != undefined && mapselected != "" && mapselected != -1) {
                    var node = $('#mytree').tree('find', mapselected);
                    $('#mytree').tree('select', node.target);
                }
            } else {
                $('#panelModel').panel("close");
            }
        }
    });
});

/*切换布局颜色*/
function checkRadio(id) {
    switch (id) {
        case 0:
            changeThemeFun('LightGreen');
            break;
        case 2:
            changeThemeFun('blue');
            break;
        case 3:
            changeThemeFun('red');
            break;
        default:
            break;
    }
}

/**
 * 查询项目类型
 */
function searchDeviceProject() {
    $.ajax({
        url: "../DeviceProjectController/queryDeviceProject",
        dataType: "json",
        type: "post",
        success: function (json) {
            var data = [{"projectId": "", "projectName": "全部"}];
            var comboxData = data.concat(json.rows);
            $("#deviceProjectId").combobox({
                data: comboxData,
                valueField: "projectId",
                textField: 'projectName',
                editable: false,
                onChange: function (n, o) {
                    searchTreeStation();
                }
            });
            if (comboxData.length > 0) {
                $("#deviceProjectId").combobox("setValue", comboxData[0]["projectId"]);
                searchTreeStation();
            }
            /* $.each(comboxData, function(i, item){
                if(item["projectName"].indexOf("FID")>-1){
                    $("#deviceProjectId").combobox("setValue", item["projectId"]);
                }
              });*/
        },
        error: function (e) {
            console.info("查询项目类型异常！" + e.error);
        }
    })
}

/*查询站点名称*/
/*function searchStationFunc(){
	if($("#manufacturer").val()!=""){
		$.ajax({
			url : '../TreeController/getAuthorityDevices',
			type : "post",
			dataType : "json",
			data : {
				"devicename":$("#manufacturer").val(),
				"areaid":"-1"
			},
			success : function(json) {
				$("#mytree").tree({
					data:json
				});
			}
		});
	}else{
		initTreeNoe("mytree",'../TreeController/getAreaTree',null,false);
	}
}*/

/*初始化设备厂商*/
function initManufacturer() {
    $.ajax({
        url: "../ManufacturerController/queryManufacturer",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            var datajson = [];
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    datajson.push({"mfrId": json.rows[i].mfrId, "mfrName": json.rows[i].mfrName});
                }
            } else {
                datajson = [{"mfrId": "-1", "mfrName": "无"}];
            }
            $("#manufacturer").combobox({
                data: datajson,
                valueField: 'mfrId',
                textField: 'mfrName'
            });
        }
    });
}

/*菜单权限*/
function getUserPower() {
    var navDom = $(".navigation-v3 ul"), htmlArr = [], ddCnt = 0;
    $.ajax({
        url: "../AuthorityDetailController/getAuthorityDetail",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            navDom.append('<li class="ss" style="margin-right:10px;"><h2><span id="mainLogoId"></span><span style="font-size:16px;" id="sysName"></span></h2></li>');
            if (json.system) {//系统配置
                navDom.append('<li class="" _t_nav="sysConfig"><h2><a href="javascript:void(0)">系统设置</a></h2></li>');
                htmlArr.push('<div id="sysConfig" class="nav-down-menu menu-1" style="display: none;" _t_nav="sysConfig">');
                htmlArr.push('<div class="navigation-down-inner">');
                if (json.modifyPWD || json.userInfo || json.organInfo) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>用户信息</dt>');
                    if (json.modifyPWD) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/modifyPWD.js\');">修改密码</a></dd>');
                    }
                    if (json.organInfo) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/organInfo.js\');">设置组织</a></dd>');
                    }
                    if (json.userInfo) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/userInfo.js\');">设置用户</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if (json.configPower) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>权限</dt>');
                    htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/configPower.js\');">设置权限</a></dd>');
                    htmlArr.push("</dl>");
                }
                if (json.regionInfo || json.mapAreaInfo) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>行政区域</dt>');
                    if (json.regionInfo) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/regionInfo.js\');">设置区域信息</a></dd>');
                    }
                    if (json.mapAreaInfo) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/mapAreaInfo.js\');">设置边框信息</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if (json.configmanufacturerInfo || json.configdeviceInfo || json.configmonitorInfo
                    || json.sampledeviceInfo || json.sampledeviceboxInfo || json.deviceVideoInfo) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>设备、厂商、监控物信息</dt>');
                    if (json.configmanufacturerInfo) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/configmanufacturerInfo.js\');">设置厂商信息</a></dd>');
                    }
                    if (json.configdeviceInfo) {

                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/configdeviceInfo.js\');">设置监测设备信息</a></dd>');
                    }
                    if (json.configmonitorInfo) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/configmonitorInfo.js\');">设置监控物质信息</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if (json.configipInfo) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>IP信息</dt>');
                    htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/system/configipInfo.js\');">设置IP信息</a></dd>');
                    htmlArr.push("</dl>");
                }
                htmlArr.push('</div></div>');
            }
            if (json.generalMonitor) {// 实时监控
                navDom.append('<li class="" _t_nav="generalMonitor"><h2><a class="link" href="javascript:void(0)" >实时监控</a></h2></li>');
                htmlArr.push('<div id="generalMonitor" class="nav-down-menu menu-1" style="display: none;" _t_nav="generalMonitor">');
                htmlArr.push('<div class="navigation-down-inner">');
                if (json.generalPanel) {
                    htmlArr.push('<dl><dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/generalMonitor/generalPanel.js\');">实时统计</a></dd></dl>');
                }
                if (json.realtimeMonitor) {
                    htmlArr.push('<dl><dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/generalMonitor/realtimeMonitor.js\');">实时数据</a></dd></dl>');
                }
                htmlArr.push('</div></div>');
            }

            if (json.query) {// 数据查询
                navDom.append('<li class="" _t_nav="dataQuery"><h2><a href="javascript:void(0)">数据查询</a></h2></li>');
                htmlArr.push('<div id="dataQuery" class="nav-down-menu menu-1" style="display: none;" _t_nav="dataQuery">');
                htmlArr.push('<div class="navigation-down-inner">');
                if (json.originalDataMultParam) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>原始数据</dt>');
                    if (json.originalDataMultParam) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/query/originalDataMultParam.js\');">原始监测数据查询</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if (json.singleStationMultParam || json.multStationMultParam) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>统计数据</dt>');
                    if (json.singleStationMultParam) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/query/singleStationMultParam.js\');">单站点多物质查询</a></dd>');
                    }
                    if (json.multStationMultParam) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/query/MultStationMultParam.js\');">多站点单物质查询</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if (json.alarm) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>报警数据</dt>');
                    if (json.alarm) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/query/alarm.js\');">报警数据</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if(json.filelist){
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>文件存储</dt>');
                    if (json.filelist) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/query/filelist.js\');">文件上传下载列表</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                htmlArr.push('</div></div>');
            }
            if (json.heatMap) {//热力分布图
                navDom.append('<li class="" _t_nav="heatMap"><h2><a href="javascript:void(0)">分布溯源</a></h2></li>');
                htmlArr.push('<div id="heatMap" class="nav-down-menu menu-1" style="display: none;" _t_nav="heatMap">');
                htmlArr.push('<div class="navigation-down-inner">');
                if (json.areaWeatherMap) {
                    htmlArr.push('<dl><dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/heatMap/areaWeatherMap.js\');">区域气象分布图</a></dd></dl>');
                }
                if (json.areaPollutionMap) {
                    htmlArr.push('<dl><dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/heatMap/areaPollutionMap.js\');">区域污染分布图</a></dd></dl>');
                }
                if (json.areaPollutionSource) {
                    htmlArr.push('<dl><dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/heatMap/areaPollutionSource.js\');">区域污染溯源</a></dd></dl>');
                }
                htmlArr.push('</div></div>');
            }
            if (json.deviceManager) {// 站点管控
                navDom.append('<li class="" _t_nav="deviceManager"><h2><a href="javascript:void(0)">站点管控</a></h2></li>');
                htmlArr.push('<div id="deviceManager" class="nav-down-menu menu-1" style="display: none;" _t_nav="deviceManager">');
                htmlArr.push('<div class="navigation-down-inner">');
                if (json.alarmLineSet || json.dataIntervalSet) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>设置站点信息</dt>');
                    if (json.alarmLineSet) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/alarmLineSet.js\');">报警门限</a></dd>');
                    }
                    if (json.dataIntervalSet) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/dataIntervalSet.js\');">数据间隔</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if (json.getSpanTimeData || json.getRldData) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>数据获取</dt>');
                    if (json.getRldData) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/getRldData.js\');">实时数据</a></dd>');
                    }
                    if (json.getSpanTimeData) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/getSpanTimeData.js\');">分段数据</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                if (json.voltageRange || json.extremumRange || json.temperatureControl) {
                    htmlArr.push("<dl>");
                    htmlArr.push('<dt>参数设置</dt>');
                    if (json.voltageRange) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/voltageRange.js\');">零点量程</a></dd>');
                    }
                    if (json.extremumRange) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/extremumRange.js\');">极值范围</a></dd>');
                    }
//						if(json.calibrationPoint){
//							htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/calibrationPoint.js\');">校准点位</a></dd>');
//						}
                    if (json.temperatureControl) {
                        htmlArr.push('<dd><a class="link" href="javascript:void(0)" onclick="$.getScript(\'../javascript/menu/deviceManager/temperatureControl.js\');">温度控制</a></dd>');
                    }
                    htmlArr.push("</dl>");
                }
                htmlArr.push('</div></div>');
            }
        }
    });
    // 退出
    /*navDom.append('<li class="" _t_nav="logOff"><h2><a href="javascript:void(0)" onclick="$.getScript(\'/EnvDust/javascript/menu/outSystem.js\');">退出系统</a></h2></li>');*/

    $(".navigation-down").append(htmlArr.join(""));
    $("#sysName").html(SYS_NAME);
}

function mainlogOutFunc() {
    $.ajax({
        url: "../UserController/toLogout",
        type: "post",
        dataType: "json",
        error: function (json) {
            $.messager.confirm("提示", '退出请求未响应，是否强制退出？', function (r) {
                if (r) {
                    window.location.href = "../login.html";
                }
            });
        },
        success: function (json) {
            if (json.result) {
                window.location.href = "../login.html";
            } else {
                $.messager.alert("错误", json.detail, "error");
            }
        }
    });
}

function searchTreeStation() {
    var pojectId = $("#deviceProjectId").combobox("getValue");
    var currTab = $('#mytab').tabs('getSelected');
    var title = currTab.panel('options').title;
    var checkbobox = false;
    if (title.indexOf("多站点单物质查询") > -1) {
        checkbobox = true;
    }
    initTreeNoe("mytree", '../TreeController/getAreaTree', {
        "parentId": "",
        "projectId": pojectId
    }, checkbobox, "mywest");
}
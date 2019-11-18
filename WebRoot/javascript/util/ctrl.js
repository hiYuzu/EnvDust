/************************************
 * 功能：生成各种UI控件的方法
 * 日期：2016-3-10
 ************************************/
var ornCnCode = [ {
	"id" : "2011",
	"name" : "实时数据"
}, {
	"id" : "2051",
	"name" : "分钟数据"
}, {
	"id" : "2061",
	"name" : "小时数据"
}, {
	"id" : "2031",
	"name" : "每日数据"
} ];
/**
 * 函数功能：绘制ValidateBox
 **/
function createValidatebox(params){
	var tpl = '<div id="' + params.name +'_div" '
 			+ (params.ishiden ? 'style="display:none" ' : '' )
           + '>' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' 
			+ params.title 
			+ '</label>'
			+ '<input class="easyui-validatebox input-border" '
			+ (params.isPwd ? 'type="password" ' : 'type="text" ' ) 
			+ 'id="' + params.name + '" name="' + params.name
			+ (params.value==undefined ? '' : '"value="' + params.value) 
			+ (params.readonly? '"readonly=true' :'"')
			+ ' title="'
			+ (params.tooltip ? params.tooltip : tooltipInfo(params.type))
			+ '" data-options="' 
			+ (params.noBlank ? 'required:true,' : '') 
			+ (params.type ? 'validType:\'' + params.type + '\'' : '')
			+ '" style="width:150px;" /></a></div>';
	return tpl;
}

/**
 * 函数功能：绘制CheckBox
 **/
function createCheckbox(params){
	var tpl = '<div id="' + params.name +'_div">' 
			+ '<input type="checkbox" ' 
			+ (params.event ? 'onClick=' + params.event:'') 
			+ ' id="' + params.name + '" name="' + params.name 
			+ (params.checked ? '" checked="checked' : '')
			+ '" />' + params.title + '</div>';
	return tpl;
}

/**
 * 函数功能：绘制ComboBox
 **/
function createCombobox(params){
	var obj = params.data, strArr = [];
	for(var key in obj){
		strArr.push("{value:'"+key+"',text:'"+obj[key]+"'}");
		strArr.push(",");
	}
	strArr.pop();
	var tpl = '<div id="' + params.name +'_div" style="height:20px;">' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' 
			+ params.title 
			+ '</label>'
			+ '<input class="easyui-combobox"' 
			+ 'id="' + params.name + '" name="' + params.name
			+ (params.value==undefined ? '' : '"value="' + params.value)
			+ (params.disabled ? '" disabled=true' : '"')
			+ ' panelHeight=' + (strArr.length > 9 ? '120' : 'auto')
			+ ' data-options="'
			+ (params.noBlank ? 'required:true,' : '')
			+ (params.isMulti ? 'multiple:true,' : '')
			+ 'data:[' + strArr.join("") + ']'
			+ '" style="width:154px;" editable=false /></div>';
	return tpl;
}

/**
 * 函数功能：绘制ComboBox
 **/
function createComboboxEdit(params){
	var obj = params.data, strArr = [];
	for(var key in obj){
		strArr.push("{value:'"+key+"',text:'"+obj[key]+"'}");
		strArr.push(",");
	}
	strArr.pop();
	var tpl = '<div id="' + params.name +'_div" style="height:20px;">' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' 
			+ params.title 
			+ '</label>'
			+ '<input class="easyui-combobox"' 
			+ 'id="' + params.name + '" name="' + params.name
			+ (params.value==undefined ? '' : '"value="' + params.value)
			+ (params.disabled ? '" disabled=true' : '"')
			+ ' panelHeight=' + (strArr.length > 9 ? '120' : 'auto')
			+ ' data-options="'
			+ (params.noBlank ? 'required:true,' : '')
			+ (params.isMulti ? 'multiple:true,' : '')
			+ 'data:[' + strArr.join("") + ']'
			+ '" style="width:154px;" editable=true /></div>';
	return tpl;
}

/**
 * 函数功能：绘制DateTimeBox
 **/
function createDatetimebox(params){
	var tpl = '<div id="' + params.name +'_div">' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' + params.title + '</label>'
			+ '<input class="easyui-datetimebox" type="text" ' 
			+ 'id="' + params.name + '" name="' + params.name
			+ '" data-options="showSeconds:' + (params.showSeconds ? true : false)
			+ (params.noBlank ? ',required:true' : '') 
			+ (params.type ? ',validType:\'' + params.type + '\'' : '')
			+ (params.value ? '"value=\'' + params.value + '\'"' : '')
			+ '" style="width:154px;" /></div>';
	return tpl;
}

/**
 * 函数功能：根据输入框的校验规则生成相应的提示信息
 **/
function tooltipInfo(valid){
	var info = "", res = [];
	if(valid){
		res = valid.match(/number\[(\d+),(\d+)\]/);
		if(res){
			info = "可输入范围在" + res[1] + "至" + res[2] + "之间的数字";
		}
		res = valid.match(/min:(-?\d+),max:(\d+)/);
		if(res){
			info = "可输入范围在" + res[1] + "至" + res[2] + "之间的数字";
		}
		res = valid.match(/loginId/);
		if(res){
			info = "不能输入中文字符及空格";
		}
	}
	return info;
}

/**
 * 函数功能：显示年月的控件
 * @param id
 */
function createDateboxByYYMM(id){
	 $('#'+id).datebox({
       onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
           span.trigger('click'); //触发click事件弹出月份层
           if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
               tds = p.find('div.calendar-menu-month-inner td');
               tds.click(function (e) {
                   e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                   var year = /\d{4}/.exec(span.html())[0]//得到年份
                   , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                   $('#'+id).datebox('hidePanel')//隐藏日期对象
                   .datebox('setValue', year + '-' + month); //设置日期的值
               });
           }, 0)
       },
       parser: function (s) {
           if (!s) return new Date();
           var arr = s.split('-');
           return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
       },
       formatter: function (d) {
    	   var MM = (d.getMonth()+1);
    	   if((d.getMonth()+1)<10){//补零处理
    		   MM = "0" + MM;
    	   }
    	   var datetime = d.getFullYear() + '-' + MM;
    	   return datetime;
       }
   });
   var p = $('#'+id).datebox('panel'), //日期选择对象
       tds = false, //日期选择对象中月份
       span = p.find('span.calendar-text'); //显示月份层的触发控件
}

/*
 * 函数功能：将表单数据转换成Json格式
 */
function getFormJson(arr){
	var obj = {};
	for(var i = 0, len = arr.length; i < len; i++){
		obj[arr[i].name] = arr[i].value;
	}
	return obj;
}

/*
 * 函数功能：使用loading效果
 */
function ajaxLoading(msg){
	$("<div class='datagrid-mask'></div>").appendTo("body").css({
		"display":"block",
		"z-index":9999,
		"width":"100%",
		"height":$(window).height()
	});
    $("<div class='datagrid-mask-msg'></div>").html(msg ? msg : "正在处理,请稍候...").appendTo("body").css({
    	"display":"block",
    	"z-index":99999,
    	"left":($(document.body).outerWidth(true) - 190) / 2,
    	"top":($(window).height() - 45) / 2
    });
}


/*
 * 函数功能：结束loading效果
 */
function ajaxLoadEnd(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();               
}

function ajaxPartLoading(id){
	if(id!="" && id!=undefined){
        $('<div id="ajaxloader4"> <div class="outer"></div> <div class="inner"></div> </div>').appendTo("#"+id).css({
            "display":"block",
            "z-index":99999,
            "left":($("#"+id).outerWidth(true)-60) / 2,
            "top":($("#"+id).height()+2) / 2
        });
	}
}
/*
 * 函数功能：结束loading效果
 */
function ajaxLoadPartEnd(){
    $("#ajaxloader4").remove();
}

//Ajax文件下载
$.download = function(url, data, method){
	if(url && data){
		data = typeof data == 'string' ? data : $.param(data);
		var inputs = '';
		$.each(data.split('&'), function(){
			var pair = this.split('=');
			inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
		});
		$('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>').appendTo('body').submit().remove();
	};
};

//生成GUID
function guid(){
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}

/*
 *格式化当前日期（只有月日年）
 */
function formatterDate(date){
	var day=date.getDate()>9 ? date.getDate() : "0"+date.getDate();
	var month=(date.getMonth()+1)>9 ? (date.getMonth()+1) : "0"+(date.getMonth()+1);
	return date.getFullYear()+"-"+month+"-"+day;
}

/**
 *实时表格数据显示处理
 * @param value
 * @param field
 * @param zsFlag
 * @param zvalue
 * @returns {*}
 */
function tableShowHandler(value, field, zsFlag, zvalue,alarmMapRange,mulmonitorThing) {
    var levelScopes = [];
    if(mulmonitorThing!=null && mulmonitorThing!=""){
        field = mulmonitorThing;
    }
    if (value != undefined) {
    if (field != "" && field != undefined && alarmMapRange[field] != undefined
    ) {
        levelScopes = alarmMapRange[field];
    } else {
        levelScopes = [];
    }
    var levelNo = getLevelNo(value,levelScopes);
    var myColor = "#fff";
    if(levelNo!=""){
        myColor = getLevelColor(levelNo);//获取级别颜色
    }
    if (zsFlag) {//折算数据显示处理
        var zvaluecolor = "#fff";
        if (zvalue != "---" && levelNo!="") {
            zvaluecolor = myColor;
        } else {
            zvaluecolor = "#fff";
        }
        return '<span style="color:#000;display: inline-block;padding:0px 6px;width:60px;background:' + myColor + '">' + value + '</span>/' +
            '<span style="color:#000;display: inline-block;padding:0px 6px;width:60px;background:' + zvaluecolor + '">' + zvalue + '</span>';
    } else {
        return '<span style="color:#000;display: inline-block;padding:0px 6px;width:60px;background:' + myColor + '">' + value + '</span>';
    }
    } else {
        return "---";
    }
}

/**
 * 获取报警级别
 * @param value
 * @param levelScopes
 * @returns {string}
 */
function getLevelNo(value,levelScopes){
    var levelNo = "";
    for(var i=0;i<levelScopes.length;i++){
        var min = levelScopes[i].min;
        var max = levelScopes[i].max;
        if(value>=min && value<max){
            levelNo = levelScopes[i].lev;
            break;
        }
    }
    return levelNo;
}

/**
 * 获取级别颜色
 * @param levelNo
 * @returns {string}
 */
function getLevelColor(levelNo){
	var color = "#f64845";
	if(levelNo=="1"){
		color = "#faff72"
	}else if(levelNo=="2"){
		color = "#ff7500"
	}else{
		color = "#f64845";
	}
	return color;
}
/*********************************
 * 功能：GIS客户端登录界面的操作  
 ********************************/
$(function() {
	var UA = navigator.userAgent.toLowerCase();
	// 判断浏览器类型,只支持谷歌、火狐、opera和IE10以上
	var browserFlag = false;
	if (UA.indexOf("chrome") != -1) {// 谷歌浏览器
		browserFlag = true;
	}else if (UA.indexOf("firefox") != -1) {// 火狐浏览器
		browserFlag = true;
	} else if (UA.indexOf("opera") != -1) {// opera浏览器
		browserFlag = true;
	}else if (UA.indexOf("safari") != -1) {// 苹果浏览器
		browserFlag = true;
	}
	else if ("ActiveXObject" in window) {// ie浏览器
		if (!-[ 1, ] == false) {// ie10、ie11
			browserFlag = true;
		} else {
			browserFlag = false;
		}
	} else {
		browserFlag = false;
	}
	if (browserFlag == false) {
		window.location.href = 'unavailable.html';
	} else {
		/* 初始条件，输入框为空，光标聚焦在ID栏 */
		$(":input").val("");
		$("#userID").focus();

		/* 定义keyup事件 */
		$("#userID,#userPwd").keyup(function(e) {
			$(this).next("span").css({
				"z-index" : "0"
			});
		});

		/* 错误信息 */
		var succ_arr = [ true, true, true ];

		/* 定义focusout事件 */
		$(":input").focusout(function() {
			if ($(this).val() == "") {
				if ($(this).attr("id") == "userID") {
					succ_arr[0] = false;
					$("#idMsg").css({
						"z-index" : "1"
					});
				} else if ($(this).attr("id") == "userPwd") {
					succ_arr[1] = false;
					$("#pwdMsg").css({
						"z-index" : "1"
					});
				}
			}
		});
	}
});

//ajax验证数据
function ajaxVaild(){
	var flag = false;
	succ_arr = [ true, true, true ];
	var checkFlag = true;
	$(":input").focusout();
	for (x in succ_arr) {
		if (succ_arr[x] == false) {
			checkFlag = false;
			break;
		}
	}
	if (/[\u4e00-\u9fa5]/.test($("#userID").val())
			|| (/ +/.test($("#userID").val()))
			|| ($("#userID").val().length <= 0)
			|| ($("#userID").val().length > 25)) {
		checkFlag = false;
	}
	if (/[\u4e00-\u9fa5]/.test($("#userPwd").val())
			|| (/ +/.test($("#userPwd").val()))
			|| ($("#userPwd").val().length <=0)
			|| ($("#userPwd").val().length > 32)) {
		checkFlag = false;
	}
	if (checkFlag == true) {
		$.ajax({
			url : "./UserController/validateUser",
			type : "post",
			dataType : "json",
			async:false,
			data : {
				"userCode" : $("#userID").val(),
				"userPassword" : $("#userPwd").val()
			},
			error : function(data) {
				alert(json.detail);
			},
			success : function(json) {
				if(json.result){
					flag = true;
					setCookie("EnvDustusername",json.detail,24,"/");
				}else{
					flag = false;
					alert(json.detail);
				}
			}
		});
	}
	return flag;
}
var i =0;
function setCookie(name,value,hours,path){  
	if(i==0){
		var name = escape(name);  
		var value = escape(value);  
		var expires = new Date();  
		expires.setTime(expires.getTime() + hours * 60*60*1000);  
		path = path =="" ? "":";path=" + path;  
		_expires = (typeof hours) == "string" ? "": ";expires=" + expires.toUTCString();  
		document.cookie = name + "=" + value + _expires + path;  
	}
	i++;
}
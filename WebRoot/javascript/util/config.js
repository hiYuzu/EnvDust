//系统名称
const SYS_NAME = "环境在线监控系统";
//版权信息
const COPYRIGHT_INFO = "&nbsp;孔钰琦&nbsp;&nbsp;微信联系：k1816588824";

/**
 * 加载登录界面的logo
 * @param path 路径
 */
function setLogoWeb(path){
    $("#companyLogoId").html("");
    var htmlStr = '<img src="'+ path + 'images/logo.png" alt="logo" class="titleImg" />';
    $("#companyLogoId").append(htmlStr);
}

/**
 * 加载web版主页logo
 * @param path
 */
function setMainLogoWeb(path){
    $("#mainLogoId").html("");
    var htmlStr = '<img src="' + path + 'images/logox.png" alt="logox" style="float:left;margin-top:13px;"/>';
    $("#mainLogoId").append(htmlStr);
}
/************************************
 * 功能：初始化区域树
 ************************************/
/*初始化树*/
$(function(){
    searchDeviceProject();
});
/*初始化树*/
function initTreeNoe(id,url,data,ischeckbox,loadingid){
	if(data==null){
		data = {"parentId":""};
	}
    $("#"+id).html("");
    ajaxPartLoading(loadingid);
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		data :data,
		error : function(json) {
            ajaxLoadPartEnd();
		},
		success : function(json) {
            ajaxLoadPartEnd();
			var t = $("#"+id);
			t.tree({
				data:json,
			    checkbox:ischeckbox
			});

		}
	});
}


//遍历根节点,并调用递归方法
function getTreeList(nodes){
	var list=[];
	for(var i in nodes){
		if(getChildren(nodes[i]).length>0){
			list.push(getChildren(nodes[i]))
		}
	}
	return list;
}



//递归方法，递归条件是有子节点，最终得到节点的所有子节点。
//没有下级子节点，就认为是最后的节点
function getChildren(node) {
	var str = "";
	var nodes = $('#mytree').tree('getChildren', node.target);
	if (nodes != null && nodes.length > 0) {
		for ( var i in nodes) {
			var nodechild = getChildren(nodes[i]);
			if (node.isDevice) {
				str =nodechild
			}
		}
	} else {
		if (node.isDevice) {
			str = node.id;
		}
	}
	return str;
}

var fullLoginObject=localStorage.getItem("fullLoginObjectOfStig");
var selectedToolbar = localStorage.getItem("selectedToolbar");
var selectedToolbarItem = localStorage.getItem("selectedToolbarItem");
 var obj = JSON.parse(fullLoginObject);
 document.title = "Signature | "+localStorage.getItem("selectedToolbarItemName");
 if(null != obj){
 	document.write('<li class="sidebar-toggler-wrapper"><!-- BEGIN SIDEBAR TOGGLER BUTTON --><div class="sidebar-toggler">');
	document.write('</div><!-- END SIDEBAR TOGGLER BUTTON --></li>');
 	for(var i=0;i<obj["toolbarList"].length;i++)
	{
		if(obj["toolbarList"][i]["tbrName"]==selectedToolbar){

			document.write('<li class="active open" id="'+obj["toolbarList"][i]["tbrName"]+'"');
			if(0 == i)
				document.write('style="margin-top:16px;"');
			document.write('>');
		}else{
			document.write('<li id="'+obj["toolbarList"][i]["tbrName"]+'"');
			if(0 == i)
				document.write('style="margin-top:16px;"');
			document.write('>');
		}
		document.write('<a href="javascript:;" onclick="toolbarClickFunction(\''+obj["toolbarList"][i]["tbrName"]+'\',\''+
			obj["toolbarList"][i]["tbrIcon"]+'\');"');
		if(obj["toolbarList"][i]["tbrName"]==selectedToolbar)
			document.write('style="background:#E87E04 !important;"')
		document.write('>');
		document.write('<i class=" '+obj["toolbarList"][i]["tbrIcon"]+'"></i>');
		document.write('<span class="title">'+obj["toolbarList"][i]["tbrName"]+'</span>');
		if(obj["toolbarList"][i]["tbrName"]==selectedToolbar){
			document.write('<span class="selected "></span>');
			document.write('<span class="arrow open"></span></a>');
		}else{
			document.write('<span class="arrow"></span></a>');
		}

		document.write('<ul class="sub-menu">');
		if(obj["toolbarList"][i]["toolbars"].length != 0)
			fill_submenu(obj["toolbarList"][i]["toolbars"]);
		if(obj["toolbarList"][i]["toolbarItems"].length != 0)
			fill_toolbar_items(obj["toolbarList"][i]["toolbarItems"]);
		document.write('</ul>');
		document.write('</li>');
	}
 }
// active open
// active
jQuery(document).ready(function() {
	$('#page-header-fixed-top').load('headerPannel.html');
	$('#contentHeader').load('contentHeader.html');
});

//toolbarClickFunction
function toolbarClickFunction(toolbarName1111,toolbarIcon){
	localStorage.setItem("selectedToolbar", toolbarName1111);
	localStorage.setItem("selectedToolbarIcon", toolbarIcon);
}

//toolbarClickFunction
function toolbarItemClickFunction(toolbarItemUrl,toolbarItemName,toolbarItemIcon){
	localStorage.setItem("selectedToolbarItem", toolbarItemUrl);
	localStorage.setItem("selectedToolbarItemName", toolbarItemName);
	localStorage.setItem("selectedToolbarItemIcon", toolbarItemIcon);
}

function fill_submenu(data){
	for(var i = 0; i < data.length; i++){
		document.write('<li><a href="javascript:;">');
		document.write('<i class="'+data[i]["tbrIcon"]+'"></i>');
		document.write(' '+data[i]["tbrName"]);
		// if(data[i]["tbrName"]==selectedToolbar){
		// 	console.log('working');
		// 	document.write('<span class="selected "></span>');
		// 	document.write('<span class="arrow open"></span></a>');
		// }else{
			document.write('<span class="arrow"></span></a>');
		// }
		if(data[i]["toolbarItems"].length != 0){
			document.write('<ul class="sub-menu">');
			var temp = fill_toolbar_items(data[i]["toolbarItems"]);
			document.write('</ul>');
		}

		document.write('</li>');
		if(data[i]["toolbars"].length != 0)
			fill_submenu(data[i]["toolbars"]);
	}
}

function fill_toolbar_items(data){
	for (var j=0;j<data.length;j++) {
		if (data[j]["tbiUrl"] == selectedToolbarItem) {
			document.write('<li class="active" id="'+
			data[j]["tbiUrl"]+'">');
		}else{
			document.write('<li id="'+
			data[j]["tbiUrl"]+'">');
		}

		document.write('<a href="'+data[j]["tbiUrl"]+'.html" onclick="toolbarItemClickFunction(\''+
			data[j]["tbiUrl"]+'\',\''+data[j]["tbiName"]+'\',\''+data[j]["tbiIcon"]+'\');">');
			document.write('<i class="'+data[j]["tbiIcon"]+'"></i>');
		document.write(' '+data[j]["tbiName"]+'</a>');
		document.write('</li>');
	}
}
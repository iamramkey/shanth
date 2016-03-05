function Main(){}
var currentUser = JSON.parse(localStorage.getItem("fullLoginObject"));
if(currentUser){
	var userId=currentUser['userTbl']['usrId'], ussSessionCode=currentUser['ussSessionCode'],
	date = new Date(), curDate = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
}
Main.isLogedIn = function(){
	if(!currentUser)
		return false;
	var usrId = currentUser['userTbl']['usrId'], ussSessionCode = currentUser['ussSessionCode'];
	if(usrId && usrId != '' && ussSessionCode && ussSessionCode != '')
		return true;
	else
		return false;
}
Main.toggleButton = function(){
	if($('#logoInTheHeader').is(':visible'))
		$('#logoInTheHeader').hide();
	else
		$('#logoInTheHeader').show();
}
Main.showLoader = function(msg){
	Main.log("showing loader" + msg);
    $('#mainContainer').hide();
    $('#loaderContainer').show();
}

Main.hideLoader = function(){
	Main.log("closing loader");
    $('#loaderContainer').hide();
    $('#mainContainer').show();

}
Main.fetchToolbars = function(){
	return JSON.parse(localStorage.getItem("fullLoginObject"))['toolbarList'];
}
Main.fetchQNS = function(){
	if(JSON.parse(localStorage.getItem("fullLoginObject"))['qns'])
		return JSON.parse(localStorage.getItem("fullLoginObject"))['qns'];
}
Main.fetchFavs = function(){
	if(JSON.parse(localStorage.getItem("fullLoginObject"))['favs'])
		return JSON.parse(localStorage.getItem("fullLoginObject"))['favs'];
}
Main.getJsonInput = function(data){
	data['usrId'] = currentUser['userTbl']['usrId'];
	data['ussSessionCode'] = currentUser['ussSessionCode'];
	return JSON.stringify(data);
}
Main.fillTransactionBrudcrum = function(tbrIcon, tbrName, tbiIcon, tbiName, screenName){
	var temp = ' <span>';
		temp += '<i class="'+tbrIcon+'"></i> '+tbrName+' ';
		temp += '<i class="fa fa-angle-right"></i>';
		temp += '</span>';
		temp += ' <span>';
		temp += '<i class="'+tbiIcon+'"></i> '+tbiName+' ';
		temp += '<i class="fa fa-angle-right"></i>';
		temp += '</span>'
		temp += ' <span style="text-transform: capitalize"> '+screenName;
		temp += '</span>'
	$('#headerBreadcrumbContainer').html(temp);
}

Main.fillBreadcrum = function(data){
	$('#headerBreadcrumbContainer').html('');
	for(var i = 0; i < data.length; i++){
		var temp = ' <span>';
			temp += '<i class="'+data[i].bcIcon+'"></i> '+data[i].bcName+' ';
		if(data.length-1 != i)
			temp += '<i class="fa fa-angle-right"></i>';
			temp += '</span>'
		$('#headerBreadcrumbContainer').append(temp);
		if(i == data.length-1){
            scope.displayCode = data[i].bcName;
			$('#searchHeader').html('<i class="'+data[i].bcIcon+'"></i>'+data[i].bcName+' <small>Search</small>');
			$('#modelTitle').html('<i class="'+data[i].bcIcon+'"></i>'+data[i].bcName+'');
		}
	}
}
Main.fillHistory = function(data){
	$('#historyDropdownContainer').html('');
	for(var i = data.length-1; i >= 0; i--){
		var temp = '<li><a href="#/'+data[i].ushUrl+'">';
			temp += '<span class="details">';
			temp += '<span class="label label-sm label-icon label-success">';
			temp += '<i class="'+data[i].ushIcon+'"></i>';
			temp += '</span> ';
			temp += data[i].ushName+' </span>';
			temp += '</a></li>';
		$('#historyDropdownContainer').append(temp);
	}
}
Main.log = function(message) {
	console.log(message);
}

Main.alert = function(message) {
	Main.alert(message, false);
}

Main.alert = function(message, autoClose) {
	if (!autoClose)
		autoClose = false;
	$('#alertMessage').html(message);
	$('#alertModal').modal('show');
	Main.log(message);
	if (autoClose)
		setTimeout(function() {
			$('#alertModal').modal('hide')
		}, 2000);
}

//login functionality
function login(jsonData){
	var inputJsonData;
	if(jsonData)
		inputJsonData = jsonData;
	else{
		var username = $('#username').val();
		var password = $('#password').val();
		var encriptedPassword=$().crypt({method:"sha1",source:password}) ;
		console.log("The user name is: "+username);
		console.log("The encripted Password is "+$().crypt({method:"sha1",source:password}) );
		var formData = {usrName : username,	usrPassword : encriptedPassword};
		var inputJsonData = JSON.stringify(formData);
	}
   // Send data to the server
	$.ajax({
		type : "POST",
        url : baseUrl+"login",
        async : false,
        contentType : "application/json;",
        data : inputJsonData,
		error : function(data)
        {
        	$('#loaderContainer').hide();
        	$('#mainContainer').show();
	       Main.alert("Error in connecting "+XMLHttpRequest);
        },
		success: function(data)
		{
			// We will give some time for the animation to finish, then execute the following procedures
			setTimeout(function()
			{
				// If login is invalid, we store the
				if('errorCode' in data)
				{
					$(".login-page").removeClass('logging-in');
					alert(data.errorMessage  );
                     location.reload();
                     // window.location.href = "login.html";
				}
				else
				{
					$('#mainContainer').show();
	            	$('#loaderContainer').hide();
	            	document.getElementById("loginForm").reset();
					if(data.passwordStatus == -2){
						document.getElementById("changePasswordForm").reset();
						document.getElementById('uId').value = data.userTbl.usrId;
						document.getElementById('uSession').value =  data.ussSessionCode;
						$('#changePasswordModel').modal('show');
					}else{
						// Check browser support
						if (typeof(Storage) != "undefined") {
							localStorage.setItem("fullLoginObject", JSON.stringify(data));
						} else {
							alert("your browser doesnot support local storage please try in some latest browser");
							console.log("Sorry, your browser does not support Web Storage...");
						}
						// Redirect to login page
						setTimeout(function()
						{
							// window.location.href = "main.html";
							if(data.passwordStatus == 0)
								window.location.href = "main.html";
							if(data.passwordStatus == -1){
								alert("warning!!! your recomended to change the password");
								window.location.href = "main.html#/changepassword";
							}
						}, 100);
					}

				}
			}, 1000);
		}
	});
}
//fetch list api's
Main.toCallListApi = function(url,inputData,fieldId){
	$.ajax({
		type: "POST",
		url: baseUrl+url,
		// async : false,
		contentType: "application/json;",
		data: inputData,
		success: function(data){
			var err = (data.errorCode);
			if (err < 0) {
				$('div.message').text(data.errorMessage);
				$('div.message').addClass("alert-danger");
				$('div.message').text(data.errorMessage);
				$('div.message').addClass("alert-danger");
				//alert('error');
				console.log(data);
				console.log(data.errorMessage);
			}
			else{
				console.log(data.errorMessage);
				//code to fill the select option
				var selectOptionFieldForCreate = document.getElementById( fieldId );
				// nodeName1
				console.log(fieldId)
				var fpsNameList=data.list;
				for(var i = 0; i < fpsNameList.length; i++) {
					var theOption = new Option;
					theOption.text = fpsNameList[i];
					theOption.value = fpsNameList[i];
					selectOptionFieldForCreate.options[i] = theOption;
				}
				// $('#'+fieldId).select2("val", null);
			}
			if(err==-3) {
				setTimeout(function() {
					window.location.href = "logout.html";
				}, 500);
			}
		},
		error: function(data){
			console.log(data);
		},
	});
}

//date time formater
Date.prototype.getMonthName = function() {
var m = ['Jan','Feb','Mar','Apr','May','Jun','Jul',
'Aug','Sep','Oct','Nov','Dec'];
return m[this.getMonth()];
}
Date.prototype.getDayName = function() {
var d = ['Sun','Mon','Tue','Wed',
'Thu','Fri','Sat'];
return d[this.getDay()];
}


Main.fillTreeTable = function(id, data, subParam, onclickFunction){
	$('#'+id).html('');
	for(var i = 0; i < data.length; i++){
		//level one
		'use strict';
		var level1 = '<tr style="font-weight:800;" class="expanding" onclick="'+onclickFunction+'(\''+i+data[i].destinationGroup.replace(/ /g , '').replace(/&/g , '_')+'\')"><td>'+data[i].destinationGroup+'</td>';
			level1 += '<td>'+data[i].direction+'</td>';
			level1 += '<td>'+data[i].startDate+'</td>';
			level1 += '<td>'+data[i].endDate+'</td>';
			level1 += '<td>'+data[i].committed+'</td>';
			level1 += '<td>'+data[i].totalVolume+'</td>';
			level1 += '<td>'+data[i].trend+'</td>';
			level1 += '<td style="background:'+data[i].blockColor+'">'+data[i].required+'</td></tr>';
			$('#'+id).append(level1);
			
			if(data[i][subParam]){
				$('#'+id).append('<tr class="expanding '+i+data[i].destinationGroup.replace(/ /g , '').replace(/&/g , '_')+'">');
				//level two
				'use strict';
				for(var j = 0; j < data[i].volumeDetailsList.length; j++){
					var level2 = '<tr style="font-weight:800;display:none;" class="expanding '+i+data[i].destinationGroup.replace(/ /g , '').replace(/&/g , '_')+'" onclick="'+onclickFunction+'(\''+i+j+data[i].destinationGroup.replace(/ /g , '').replace(/&/g , '_')+'\')"><td>'+data[i][subParam][j].destinationGroup+'</td>';
					level2 += '<td>'+data[i][subParam][j].direction+'</td>';
					level2 += '<td>'+data[i][subParam][j].startDate+'</td>';
					level2 += '<td>'+data[i][subParam][j].endDate+'</td>';
					level2 += '<td>'+data[i][subParam][j].committed+'</td>';
					level2 += '<td>'+data[i][subParam][j].totalVolume+'</td>';
					level2 += '<td>'+data[i][subParam][j].trend+'</td>';
					level2 += '<td style="background:'+data[i][subParam][j].blockColor+'">'+data[i][subParam][j].required+'</td></tr>';
					$('#'+id).append(level2);
					
					if(data[i][subParam]){
						//level three
						'use strict';
						for(var k = 0; k < data[i][subParam][j][subParam].length; k++){
							var level3 = '<tr style="display:none;" class="child'+i+data[i].destinationGroup.replace(/ /g , '').replace(/&/g , '_')+' '+i+j+data[i].destinationGroup.replace(/ /g , '').replace(/&/g , '_')+'"><td>- '+data[i][subParam][j][subParam][k].destinationGroup+'</td>';
							level3 += '<td>'+data[i][subParam][j][subParam][k].direction+'</td>';
							level3 += '<td>'+data[i][subParam][j][subParam][k].startDate+'</td>';
							level3 += '<td>'+data[i][subParam][j][subParam][k].endDate+'</td>';
							level3 += '<td>'+data[i][subParam][j][subParam][k].committed+'</td>';
							level3 += '<td>'+data[i][subParam][j][subParam][k].totalVolume+'</td>';
							level3 += '<td>'+data[i][subParam][j][subParam][k].trend+'</td>';
							level3 += '<td style="background:'+data[i][subParam][j][subParam][k].blockColor+'">'+data[i][subParam][j][subParam][k].required+'</td></tr>';
							$('#'+id).append(level3);
						}
					}
				}
				$('#'+id).append('</tr>');
			}

	}
}
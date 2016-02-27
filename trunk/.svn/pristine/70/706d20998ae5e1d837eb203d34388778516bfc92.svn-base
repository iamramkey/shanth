'use strict';
ThrazeApp.controller('ChangepasswordController',function($rootScope, $scope, $http) {
	Main.log("ChangepasswordController"); 
	initChangePwd();
});
var minLength;
//form submit function
$(document)
.on('submit', 'form#changePwdForm', function(){
	var oldPwd = $('#oldPwd').val(),
		newPwd1 = $('#newPwd1').val(),
		newPwd2 = $('#newPwd2').val();
	if(newPwd1 == newPwd2){
		if(checkPwd(newPwd1,minLength)){
			var usrPassword=$().crypt({method:"sha1",source:oldPwd}) ;
			var usrNewPassword=$().crypt({method:"sha1",source:newPwd1}) ;
			var data = {usrId:userId,ussSessionCode:ussSessionCode,usrPassword:usrPassword,usrNewPassword:usrNewPassword};
			var inputData = JSON.stringify(data);
			console.log(inputData);
			changePwd(inputData);
		}else{
			$('div.message').fadeIn();
			$('div.message').text('New Password should contain one number,one special character and minimun '+minLength+' character total!');
			$('div.message').removeClass("alert-success");
			$('div.message').addClass("alert-danger");
		}
	}else{
		$('div.message').fadeIn();
		$('div.message').text('Password And Confirm Password not matched!');
		$('div.message').removeClass("alert-success");
		$('div.message').addClass("alert-danger");
	}
  	return false;
});
function initChangePwd(){
	var data = {usrId:userId,ussSessionCode:ussSessionCode};
	var inputData = JSON.stringify(data);
	$.ajax({
		type : "POST",
		url : baseUrl+"passwordsettingsfetch",
		async : false,
		contentType : "application/json;",
		data : inputData,
		success :function(data){
			var err=data.errorCode;
			if (err < 0) {
				alert(data.notification);
			}else{

				// document.getElementById('jobLogs').innerHTML=data.log;
				minLength = data.userPasswordSettings.upsMinLength;
				console.log(minLength);
			}
			if(err==-3)
			{

				window.location.href = "logout.html";
			}

		},
		error :function(data){
			console.log(data);
		},
	});
}

// function to check password
function checkPwd(pwd,minLength){
	var re=  /^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*]{0,}$/;
	if(pwd.length>=minLength){
		if(pwd.match(re))
		 return 1;
		 else
		 return 0;
	}else{
		return 0;
	}
}


//function for create and update
function changePwd(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+'userchangepassword',
		async : false,
		contentType : "application/json;",
		data : inputData,
		success :function(data){
			var err = (data.errorCode);

			if (err < 0) {
				$('div.message').fadeIn();
				$('div.message').text(data.errorMessage);
				$('div.message').removeClass("alert-success");
				$('div.message').addClass("alert-danger");
				setTimeout(function()
				{
					$('div.message').fadeOut(1000);
				}, 600);
				//alert('error');
				console.log(data);
				console.log(data.errorMessage);
						}
			else{
				 $('div.message').fadeIn();
				 $('div.message').text(data.notification);
				 $('div.message').removeClass("alert-danger");
					 $('div.message').addClass("alert-success");
				 console.log(data.errorMessage);
				 setTimeout(function()
				{
					$('div.message').fadeOut();
					location.reload();

				}, 600);
				}
				if(err==-3)
				{
					 setTimeout(function()
					{
						window.location.href = "logout.html";
					}, 500);
				}
			},
			error :function(data){
				$('div.message').fadeIn();
				$('div.message').text('Something wrong with the connection!!!Pleace check the connection and try again');
				$('div.message').removeClass("alert-success");
				$('div.message').addClass("alert-danger");
				setTimeout(function()
					{
						$('div.message').fadeOut(1000);
					}, 800);

				// alert("Something went wrong"+data);
			console.log(data);
		},
			});
}
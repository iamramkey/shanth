'use strict';
SignatureApp.controller('ProfileController',function($rootScope, $scope, $http) {
	Main.log("ProfileController"); 
	setTimeout(function(){
		var data = {usrId:userId,ussSessionCode:ussSessionCode, name: 'UserTbl', id: userId};
		var inputData = JSON.stringify(data);
		userFetch(inputData);
	}, 500);
});
//function for create and update
function userFetch(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+'masterfetch',
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
				 setTimeout(function()
				{
					$('div.message').fadeOut(1000);
				}, 600);
				 document.getElementById('rol1').value = data.data.rol;
				 document.getElementById('usrName').value = data.data.usrName;
				 document.getElementById('usrDisplayName').value = data.data.usrDisplayName;
				 document.getElementById('usrEmail').value = data.data.usrEmail;
				 document.getElementById('usrMobilePhone').value = data.data.usrMobilePhone;
				 document.getElementById('usrStatus').value = data.data.usrStatus;

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
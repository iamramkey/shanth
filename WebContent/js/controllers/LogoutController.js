'use strict';
SignatureApp.controller('LogoutController',function($rootScope, $scope, $http) {
	Main.log("LogoutController"); 
	$(document).ready(function () {
        var usrId=$.cookie('usrId');
        var formData = {usrId : usrId};
		var inputJsonData = JSON.stringify(formData);
        $.ajax({
			type : "POST",
			url : baseUrl+"logout",
			async : false,
			contentType : "application/json;",
			data : inputJsonData,
			error : function(data) 
            {
               	localStorage.removeItem("fullLoginObject")
    			window.location.href = "index.html";
			}, 
			success: function(data)
			{
				localStorage.removeItem("fullLoginObject")
    			window.location.href = "index.html";
			}
		});
	});
});
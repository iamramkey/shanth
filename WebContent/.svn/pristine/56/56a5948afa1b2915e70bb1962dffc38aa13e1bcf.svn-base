var Login = function () {

	var handleLogin = function() {
		$('.login-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                username: {
	                    required: "Username is required."
	                },
	                password: {
	                    required: "Password is required."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	            	$('#mainContainer').hide();
	            	$('#loaderContainer').show();
	            	setTimeout(function()
				{
					login();
	               // form.submit();
	    //            var username = $('#username').val();
				 //   var password = $('#password').val();
     //               var encriptedPassword=$().crypt({method:"sha1",source:password}) ;
     //               console.log("The user name is: "+username);
     //               console.log("The encripted Password is "+$().crypt({method:"sha1",source:password}) );
     //               var formData = {usrName : username,	usrPassword : encriptedPassword, client : clientDetails};
				 //   var inputJsonData = JSON.stringify(formData);
				 //   // Send data to the server
					// $.ajax({
					// 	type : "POST",
				 //        url : baseUrl+"login",
				 //        async : false,
				 //        contentType : "application/json;",
				 //        data : inputJsonData,
					// 	error : function(data) 
     //                    {
					//        alert("Error in connecting "+XMLHttpRequest);
				 //        }, 
					// 	success: function(data)
					// 	{						
					// 		// We will give some time for the animation to finish, then execute the following procedures	
					// 		setTimeout(function()
					// 		{
					// 			// If login is invalid, we store the 
					// 			if('errorCode' in data)
					// 			{	
					// 				$(".login-page").removeClass('logging-in');
     //                                 alert(data.errorMessage  );
     //                                 window.location.href = "login.html";
					// 			}
					// 			else
					// 			{
					// 				// Check browser support
					// 				if (typeof(Storage) != "undefined") {
    	// 								localStorage.setItem("fullLoginObject", JSON.stringify(data));
					// 				} else {
					// 					alert("your browser doesnot support local storage please try in some latest browser");
    	// 								console.log("Sorry, your browser does not support Web Storage...");
					// 				}
					// 				// Redirect to login page
					// 				setTimeout(function()
					// 				{
					// 					window.location.href = "main.html";
					// 				}, 400);
					// 			}
								
					// 		}, 1000);
					// 	}
					// });
				}, 650);
	            }
	        });

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                    $('.login-form').submit();
	                }
	                return false;
	            }
	        });
	}

	var handleForgetPassword = function () {
		$('.forget-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                email: {
	                    required: true,
	                    email: true
	                }
	            },

	            messages: {
	                email: {
	                    required: "Email is required."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });

	        $('.forget-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    $('.forget-form').submit();
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	        });

	}

    
    return {
        //main function to initiate the module
        init: function () {
            handleLogin();
            handleForgetPassword();  
        }

    };

}();

//function for create and update
function createUpdateTable(url,inputData,redirectURL){
	$.ajax({
				type : "POST",
				url : baseUrl+url,
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
						// setTimeout(function()
						// {
						// 	$('div.message').fadeOut(1000);
						// }, 1000);
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
							// $('div.message').fadeOut();
							window.location.href = redirectURL;

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
					// setTimeout(function()
					// 	{
					// 		$('div.message').fadeOut(1000);
					// 	}, 1000);
					
					// alert("Something went wrong"+data);
				console.log(data);
		},
			});
}
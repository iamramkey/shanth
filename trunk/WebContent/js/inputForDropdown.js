//fetch list api's
function toCallListApi(url,inputData,fieldId){
	$.ajax({
				type : "POST",
				url : baseUrl+url,
			async : false,
		contentType : "application/json;",
		data : inputData,
		success :function(data){
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

						var fpsNameList=data.list;
						for(var i = 0; i < fpsNameList.length; i++) { 
							var theOption = new Option; 
							theOption.text = fpsNameList[i]; 
							theOption.value = fpsNameList[i]; 
							selectOptionFieldForCreate.options[i] = theOption; 
							} 
							$('#'+fieldId).select2("val", null);
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
			console.log(data);
		},
			});

}
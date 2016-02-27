// Common HttpPost Function
function httpPost(urlName,input,successCallback) {
  $.ajax({
    type: "POST",
    url: baseUrl+urlName,
//    async: false,
    contentType: "application/json;",
    data: input,
    success: function(data){
      if(data.errorCode==-3){
        Main.alert(data.errorMessage);
        window.location.href = "lock.html";
      }
      if(data.errorCode){
        // alert(data.errorMessage);
        Main.alert(data.errorMessage);
        return true;
      }
      successCallback(data);
    },
    error: function(error){
      console.log(error);
      if(error.responseText)
    	  Main.alert(error.responseText);
      else if(error.statusText)
    	  Main.alert(error.status + " Error :  Reason '"+ error.statusText+"'");
      else
    	  Main.alert("Internal Server Error, please check log for details");
    },
  });
}


function httpPostDashboard(urlName,input,successCallback) {
  $.ajax({
    type: "POST",
    url: baseUrl+urlName,
//    async: true,
    contentType: "application/json;",
    data: input,
    success: function(data){
      if(data.errorCode==-3){
        Main.alert(data.errorMessage);
        window.location.href = "lock.html";
      }
      if(data.errorCode){
        // alert(data.errorMessage);
        Main.alert(data.errorMessage);
        return true;
      }
      successCallback(data);
    },
    error: function(error){
      console.log(error);
      if(error.responseText)
        Main.alert(error.responseText);
      else if(error.statusText)
        Main.alert(error.status + " Error :  Reason '"+ error.statusText+"'");
      else
        Main.alert("Internal Server Error, please check log for details");
    },
  });
}
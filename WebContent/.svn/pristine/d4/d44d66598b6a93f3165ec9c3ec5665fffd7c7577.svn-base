'use strict';
SignatureApp.controller('StartupjobController',function($rootScope, $scope, $http) {
	Main.log("StartupjobController"); 
	initStartupJob();
});
var downloadFlag = false, timeOutCount, userId=currentUser['userTbl']['usrId'], ussSessionCode= currentUser['ussSessionCode'];
function initStartupJob(){
	var data = {usrId: userId,ussSessionCode:ussSessionCode};
   		var inputData = JSON.stringify(data);
	 	searchStartupJobDataTable(inputData);

	 	//to fetch server settings
	var dataForServerSettings = {usrId:userId,ussSessionCode:ussSessionCode,sesCode:"AutoRefreshInterval"};
   	var inputDataForServerSettings = JSON.stringify(dataForServerSettings);
	$.ajax({
		type : "POST",
		url : baseUrl+"serversettingsfetch",
		async : false,
		contentType : "application/json;",
		data : inputDataForServerSettings,
		success :function(data){
			var err=data.errorCode;
			if(err==-3)
			{
				alert(data.errorMessage)
				window.location.href = "logout.html";
			}
			timeOutCount = data.serverSettings.sesValue*1000;
		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}
setInterval(function(){
	var modelActiveCheck = $('#jobLogModel').hasClass('in');
	var status = $('#jobStatusForViewLog').val();
	if(modelActiveCheck && status == 'Running'){
		console.log('its working '+modelActiveCheck);
		var jobId = $('#jobIdForViewLog').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId};
	var inputData = JSON.stringify(data);
	viewStartupJobLog(inputData);
	}
}, timeOutCount);
//search fields function
$(document)
.on( 'keyup change', "#start_job_data_table thead input", function () {
	console.log("working");
	var otable = $('#start_job_data_table').DataTable();
        otable
            .column( $(this).parent().index()+':visible' )
            .search( this.value )
            .draw();
})

  //refresh button on the view log modal
.click('click','#reloadViewLogBtn', function(){
	var jobId = $('#jobIdForViewLog').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId};
	var inputData = JSON.stringify(data);
	document.getElementById('modalHead1').innerHTML= '';
	document.getElementById('jobStatusForViewLog').value= '';
	document.getElementById('jobLogs').innerHTML= '';
	$('div.downloadErrorMessage').fadeOut();
	downloadFlag = false;
	viewStartupJobLog(inputData);
})

   //download log button click function
.on( 'click', '#start_job_data_table tbody #downloadLogBtn', function () {
	var tr = $(this).parents('tr');
	var newTableValue=  $('#start_job_data_table').dataTable();
	var row=newTableValue.api().row(tr).data();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:row[0]};
	var inputData = JSON.stringify(data);
	downloadFlag = true;
	$('div.downloadErrorMessage').fadeOut();
	viewStartupJobLog(inputData);
})

//restart button click function
.on( 'click', '#start_job_data_table tbody #viewLogBtn', function () {
  	var tr = $(this).parents('tr');
	var newTableValue=  $('#start_job_data_table').dataTable();
    var row=newTableValue.api().row(tr).data();
    var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:row[0]};
	var inputData = JSON.stringify(data);
	document.getElementById('modalHead1').innerHTML= '';
	document.getElementById('jobStatusForViewLog').value= '';
	document.getElementById('jobLogs').innerHTML= '';
	$('div.downloadErrorMessage').fadeOut();
	document.getElementById('jobIdForViewLog').value=row[0];
	downloadFlag = false;
	viewStartupJobLog(inputData);
})
  //refresh button  click function
.on('click', "#refreshDataTable", function() {
  	//destroy the datatable and start fresh
  	var table = $('#start_job_data_table').dataTable();
  	 table.fnClearTable(this);
	var data = {usrId:userId,ussSessionCode:ussSessionCode};
   		var inputData = JSON.stringify(data);
	 	console.log(inputData);
	 	searchStartupJobDataTable(inputData);
});

//function to fill the data table
function searchStartupJobDataTable(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+"startupjobsearch",
		async : false,
		contentType : "application/json;",
		data : inputData,
		success :function(data){
			var err=data.errorCode;
			if(err==-3)
			{
				alert(data.errorMessage)
				window.location.href = "logout.html";
			}
			var table = $('#start_job_data_table').DataTable( {
			"bDestroy" : true,
        	"bSort" : false,
            "bSortable": false,
			"processing": false,
			"bServerSide": false,
			"dom": 'T<"clear">lfrtip',
	        "oTableTools": {
	            "aButtons": [
//	                "copy",
	                "print",
	                {
	                    "sExtends":    "collection",
	                    "sButtonText": "Save",
	                    "aButtons":    [ "xls" ],
	                    "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
	                }
	            ]
	        },
		});

			console.log("the count is1 "+data.jobList.length);
			for(var i=0;i<data.jobList.length;i++){
				var a1=data.jobList[i]["jobId"];
				var a2=data.jobList[i]["nod"];
				var a3=data.jobList[i]["jbt"];
				var a4=data.jobList[i]["jobStatus"];
				var a5=data.jobList[i]["jobStartDttm"];
				var a6=data.jobList[i]["jobExtra1"];
				var a7=data.jobList[i]["jobExtra2"];
				var a8=data.jobList[i]["jobExtra3"];
				var a9=data.jobList[i]["jobPriority"];
				var a10=data.jobList[i]["jobModifiedDttm"];
				var a11 = '<button id="viewLogBtn" style="background:transparent;border:1px solid #DDD;" class="delete btn default btn-xs" data-toggle="modal" href="#jobLogModel"><i class="fa fa-eye"></i>View Log</button><button id="downloadLogBtn" style="background:transparent;border:1px solid #DDD;" class="delete btn default btn-xs" ><i class="fa fa-download"></i>Download Log</button>';
				table.row.add([a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11]);
			}
			table.draw();
		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}

//view log function
function viewStartupJobLog(inputData){
		$.ajax({
		type : "POST",
		url : baseUrl+"joblog",
		async : false,
		contentType : "application/json;",
		data : inputData,
		success :function(data){
			var err=data.errorCode;
			if (err < 0) {
				$('div.message1').fadeIn();
				$('div.message1').text(data.errorMessage);
				$('div.message1').removeClass("alert-success");
				$('div.message1').addClass("alert-danger");

				if(downloadFlag){
					$('div.downloadErrorMessage').fadeIn();
					$('div.downloadErrorMessage').text(data.errorMessage);
					$('div.downloadErrorMessage').removeClass("alert-success");
					$('div.downloadErrorMessage').addClass("alert-danger");
				}
				// setTimeout(function()
				// {
				// 	$('div.message1').fadeOut(1000);
				// }, 1000);
			}else{
				 $('div.message1').fadeIn();
				 $('div.message1').text(data.notification);
				 $('div.message1').removeClass("alert-danger");
				 $('div.message1').addClass("alert-success");
				//  setTimeout(function()
				// {
				// 	$('div.message1').fadeOut(1000);
				// }, 1000);
				 document.getElementById('modalHead1').innerHTML=data.jobLogHeader;
				document.getElementById('jobStatusForViewLog').value= data.jobStatus;
				document.getElementById('jobLogs').innerHTML=data.log;

				if(downloadFlag){
					downloadStartupJob(data.jobLogHeader, data.log);
				}
			}
			if(err==-3)
			{

				window.location.href = "logout.html";
			}

		},
		error :function(data){
			console.log(data);
			// alert("something wrong with the network..check the network and try again");
			$('div.message1').fadeIn();
			$('div.message1').text('Something wrong with the connection!!!Pleace check the connection and try again');
			$('div.message1').removeClass("alert-success");
			$('div.message1').addClass("alert-danger");
			// setTimeout(function()
			// 	{
			// 		$('div.message1').fadeOut(1000);
			// 	}, 1000);

			// alert("Something went wrong"+data);
		console.log(data);
		},
	});
}

//log file download function
function downloadStartupJob(filename, text) {
	//to replace <gr> to \n in the text
	text = text.replace(/\<br>/g,"\n");
    var pom = document.createElement('a');
    pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    pom.setAttribute('download', filename);

    if (document.createEvent) {
        var event = document.createEvent('MouseEvents');
        event.initEvent('click', true, true);
        pom.dispatchEvent(event);
    }
    else {
        pom.click();
    }
}
'use strict';
SignatureApp.controller('JobController',function($rootScope, $scope, $http) {
	Main.log("JobController"); 

	$(".date-picker").datepicker({ 
	        autoclose: true,
	        todayHighlight: true
	  }).datepicker('update', curDate);;

});

var date = new Date();
var curDate = date.getFullYear()+'-' + ('0' + (date.getMonth()+1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

	
var timeOutCount ;
setTimeout(function(){
	$('#fromDate').val(curDate);
	$('#toDate').val(curDate);
	$('#endFromDate').val(curDate);
	$('#endToDate').val(curDate);
	$('#createdFromDate').val(curDate);
	$('#createdToDate').val(curDate);
	document.getElementById('maxCount').value=1000;
	initJobScreen();
}, 500);
(function(){
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
})();
  //search fields function
$(document)
.on( 'keyup change', "#job_data_table thead input", function () {
 	var otable = $('#job_data_table').DataTable();
            otable
                .column( $(this).parent().index()+':visible' )
                .search( this.value )
                .draw();
})

   //form submit function
.on('submit', 'form#searchForm', function(){
  	// $('#job_data_table').dataTable().fnDestroy();
  	var table = $('#job_data_table').dataTable();
  	 table.fnClearTable(this);
	var jobStartDttmFrom = $('#fromDate').val(),
    	jobStartDttmTo = $('#toDate').val(),
    	jobEndDttmFrom = $('#endFromDate').val(),
    	jobEndDttmTo = $('#endToDate').val(),
    	jobCreatedDttmFrom = $('#createdFromDate').val(),
    	jobCreatedDttmTo = $('#createdToDate').val(),
    	rowCount = $('#maxCount').val(),
    	jobId = $('#jobId').val(),
    	nodeName = $('#job_nodeName').val(),
    	jbtName = $('#jbtName').val(),
    	jobStatus = $('#jobStatus').val(),
    	jobExtra1 = $('#jobExtra1').val(),
    	jobExtra2 = $('#jobExtra2').val(),
    	jobExtra3 = $('#jobExtra3').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId,jobStartDttmFrom:jobStartDttmFrom,jobStartDttmTo:jobStartDttmTo,jobEndDttmFrom:jobEndDttmFrom,jobEndDttmTo:jobEndDttmTo,jobCreatedDttmFrom:jobCreatedDttmFrom,jobCreatedDttmTo:jobCreatedDttmTo,rowCount:rowCount,nodeName:nodeName,jbtName:jbtName,jobStatus:jobStatus,jobExtra1:jobExtra1,jobExtra2:jobExtra2,jobExtra3:jobExtra3};
	var inputData = JSON.stringify(data);
 	console.log(inputData);
 	$('#job_data_table').dataTable().fnDestroy();
 	searchJobDataTable(inputData);
  	return false;
})

//restart button click function
.on( 'click', '#job_data_table tbody #restartBtn', function () {
  	var tr = $(this).parents('tr');
	var newTableValue=  $('#job_data_table').dataTable();
    var row=newTableValue.api().row(tr).data();
    $( "#modalColor" ).removeClass( "portlet box" );
    $( "#modalColor" ).addClass( "portlet box red" );
	document.getElementById('modalHead').innerHTML="Are You sure want to restart the job?";
    document.getElementById('jobId1').value = row['jobId'];
    $('#restartModal').modal('show');
})

//restart form submit
.on('submit', 'form#restartform', function(){
  	var jobId = $('#jobId1').val();
  	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId};
	var inputData = JSON.stringify(data);
    createUpdateTable("jobrestart",inputData,"jobsearch.html");
    return false;
})

//refresh button on the view log modal
.on('click', '#reloadViewLogBtn', function(){
	var jobId = $('#jobIdForViewLog').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId};
	var inputData = JSON.stringify(data);
	document.getElementById('modalHead1').innerHTML= '';
	document.getElementById('jobStatusForViewLog').value= '';
	document.getElementById('jobLogs').innerHTML= '';
	downloadFlag = false;
	viewLog(inputData);
})

//download log button click function
.on( 'click', '#job_data_table tbody #downloadLogBtn', function () {
  	var tr = $(this).parents('tr');
	var newTableValue=  $('#job_data_table').dataTable();
    var row=newTableValue.api().row(tr).data();
    var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:row[0]};
	var inputData = JSON.stringify(data);
	downloadFlag = true;
	$('div.downloadErrorMessage').fadeOut();
	viewLog(inputData);
})

//view log button click function
.on( 'click', '#job_data_table tbody #viewLogBtn', function () {
  	var tr = $(this).parents('tr');
	var newTableValue=  $('#job_data_table').dataTable();
    var row=newTableValue.api().row(tr).data();
    var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:row[0]};
	var inputData = JSON.stringify(data);
	document.getElementById('jobIdForViewLog').value=row[0];
	document.getElementById('modalHead1').innerHTML= '';
	document.getElementById('jobStatusForViewLog').value= '';
	document.getElementById('jobLogs').innerHTML= '';
	$('div.downloadErrorMessage').fadeOut();
	downloadFlag = false;
	viewLog(inputData);
})
  //refresh button  click function
.on('click', "#refreshDataTable", function() {
  	var date = new Date();
	var curDate = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	//to set the current date to the datePicker
	$('.date-picker').datepicker('update', curDate);
	document.getElementById('maxCount').value=1000;
	document.getElementById('jobId').value = '';
  	//destroy the datatable and start fresh
  	var table = $('#job_data_table').dataTable();
  	 table.fnClearTable(this);
	var jobStartDttmFrom = $('#fromDate').val(),
    	jobStartDttmTo = $('#toDate').val(),
    	jobEndDttmFrom = $('#endFromDate').val(),
    	jobEndDttmTo = $('#endToDate').val(),
    	jobCreatedDttmFrom = $('#createdFromDate').val(),
    	jobCreatedDttmTo = $('#createdToDate').val(),
    	rowCount = $('#maxCount').val(),
    	jobId = $('#jobId').val(),
    	nodeName = $('#job_nodeName').val(),
    	jbtName = $('#jbtName').val(),
    	jobStatus = $('#jobStatus').val(),
    	jobExtra1 = $('#jobExtra1').val(),
    	jobExtra2 = $('#jobExtra2').val(),
    	jobExtra3 = $('#jobExtra3').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId,jobStartDttmFrom:jobStartDttmFrom,jobStartDttmTo:jobStartDttmTo,jobEndDttmFrom:jobEndDttmFrom,jobEndDttmTo:jobEndDttmTo,jobCreatedDttmFrom:jobCreatedDttmFrom,jobCreatedDttmTo:jobCreatedDttmTo,rowCount:rowCount,nodeName:nodeName,jbtName:jbtName,jobStatus:jobStatus,jobExtra1:jobExtra1,jobExtra2:jobExtra2,jobExtra3:jobExtra3};
	var inputData = JSON.stringify(data);
 	console.log(inputData);
 	searchJobDataTable(inputData);
})

  // modelInput
.on('change', '.modelInput:input', function(){
	console.log('changed');
})
.on('keyup', '.modelInput:input', function(){
	console.log('changed11');
});
// timeOutCount = timeOutCount*1000;
setInterval(function(){
	var modelActiveCheck = $('#jobLogModel').hasClass('in');
	var status = $('#jobStatusForViewLog').val();
	if(modelActiveCheck && status == 'Running'){
		console.log('its working '+modelActiveCheck);
		var jobId = $('#jobIdForViewLog').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId};
	var inputData = JSON.stringify(data);
	viewLog(inputData);
	}
}, timeOutCount);
function initJobScreen(){
	var jobStartDttmFrom = $('#fromDate').val(),
    	jobStartDttmTo = $('#toDate').val(),
    	jobEndDttmFrom = $('#endFromDate').val(),
    	jobEndDttmTo = $('#endToDate').val(),
    	jobCreatedDttmFrom = $('#createdFromDate').val(),
    	jobCreatedDttmTo = $('#createdToDate').val(),
    	rowCount = $('#maxCount').val(),
    	jobId = $('#jobId').val(),
    	nodeName = $('#job_nodeName').val(),
    	jbtName = $('#jbtName').val(),
    	jobStatus = $('#jobStatus').val(),
    	jobExtra1 = $('#jobExtra1').val(),
    	jobExtra2 = $('#jobExtra2').val(),
    	jobExtra3 = $('#jobExtra3').val();
    var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId,jobStartDttmFrom:jobStartDttmFrom,jobStartDttmTo:jobStartDttmTo,jobEndDttmFrom:jobEndDttmFrom,jobEndDttmTo:jobEndDttmTo,jobCreatedDttmFrom:jobCreatedDttmFrom,jobCreatedDttmTo:jobCreatedDttmTo,rowCount:rowCount,nodeName:nodeName,jbtName:jbtName,jobStatus:jobStatus,jobExtra1:jobExtra1,jobExtra2:jobExtra2,jobExtra3:jobExtra3};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchJobDataTable(inputData);
}

//function to fill the data table
function searchJobDataTable(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+"jobsearch",
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
			var table = $('#job_data_table').DataTable( {
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
	                    "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]
	                }
	            ]
	        },
		});

			console.log("the count is1 "+data.jobList.length);
			for(var i=0;i<data.jobList.length;i++){
				var a1=data.jobList[i]["jobId"],
					a2=data.jobList[i]["nod"],
					a3=data.jobList[i]["jbt"],
					a4=data.jobList[i]["jobStatus"],
					a5=data.jobList[i]["jobStartDttm"],
					a6=data.jobList[i]["jobEndDttm"],
					a7=data.jobList[i]["jobCreatedDttm"],
					a8=data.jobList[i]["jobExtra1"],
					a9=data.jobList[i]["jobExtra2"],
					a10=data.jobList[i]["jobExtra3"],
					a11=data.jobList[i]["jobPriority"];
				if(a4=='failed'){
					var a12 = '<button id="viewLogBtn" style="background:transparent;border:1px solid #DDD;" class="delete btn default btn-xs" data-toggle="modal" href="#jobLogModel"><i class="fa fa-eye"></i>View Log</button><button id="downloadLogBtn" style="background:transparent;border:1px solid #DDD;" class="delete btn default btn-xs" ><i class="fa fa-download"></i>Download Log</button><button id="restartBtn" style="background:transparent;border:1px solid #DDD;margin-top:5px;width:104px;" class="edit btn default btn-xs blue-hoki" data-toggle="modal" href="#responsive1"><i class="fa fa-edit"></i>Restart</button>';
				}else if(a4=='pending'){
					var a12 = '';
				}else{
					var a12 = '<button id="viewLogBtn" style="background:transparent;border:1px solid #DDD;" class="delete btn default btn-xs" data-toggle="modal" href="#jobLogModel"><i class="fa fa-eye"></i>View Log</button><button id="downloadLogBtn" style="background:transparent;border:1px solid #DDD;" class="delete btn default btn-xs" ><i class="fa fa-download"></i>Download Log</button>';
				}
				table.row.add([a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12]);
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
function viewLog(inputData){
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
			}else{
				 $('div.message1').fadeIn();
				 $('div.message1').text(data.notification);
				 $('div.message1').removeClass("alert-danger");
				 $('div.message1').addClass("alert-success");
				document.getElementById('modalHead1').innerHTML=data.jobLogHeader;
				document.getElementById('jobStatusForViewLog').value= data.jobStatus;
				document.getElementById('jobLogs').innerHTML=data.log;
				if(downloadFlag){
					download(data.jobLogHeader, data.log);
				}
			}
			if(err==-3)
			{

				window.location.href = "logout.html";
			}

		},
		error :function(data){
			console.log(data);
			$('div.message1').fadeIn();
			$('div.message1').text('Something wrong with the connection!!!Pleace check the connection and try again');
			$('div.message1').removeClass("alert-success");
			$('div.message1').addClass("alert-danger");
		},
	});
}

//log file download function
function download(filename, text) {
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
'use strict';
SignatureApp.controller('PendingjobController',function($rootScope, $scope, $http) {
	Main.log("PendingjobController"); 
	initPendingjob();
});
var userId=currentUser['userTbl']['usrId'], ussSessionCode=currentUser['ussSessionCode'], timeOutCount;
function initPendingjob(){
	var data = {usrId:userId,ussSessionCode:ussSessionCode};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchPendingjobDataTable(inputData);
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
			//console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}
setInterval(function(){
	$( "#refreshDataTable" ).click();
}, timeOutCount);
  //search fields function
$(document)
.on( 'keyup change', "#pendingjob_data_table thead input", function () {
	var otable = $('#pendingjob_data_table').DataTable();
        otable
            .column( $(this).parent().index()+':visible' )
            .search( this.value )
            .draw();
})
// timeOutCount = timeOutCount*1000;
 	
  //refresh button  click function
.on('click', "#refreshDataTable",function() {
	///destroy the datatable and start fresh
	var table = $('#pendingjob_data_table').dataTable();
	 table.fnClearTable(this);
	var data = {usrId:userId,ussSessionCode:ussSessionCode};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchPendingjobDataTable(inputData);
})

  // stop button click function
.on( 'click', '#pendingjob_data_table tbody #cancelJobBtn',function () {
	var tr = $(this).parents('tr');
	var newTableValue=  $('#pendingjob_data_table').dataTable();
    var row=newTableValue.api().row(tr).data();
    document.getElementById('jobId').value = row.jobId;
} )

//confirm form submit action
.on('submit', 'form#confirmForm',function(){
	var jobId = $('#jobId').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId};
	var inputData = JSON.stringify(data);
	createUpdateTable("jobcancel",inputData,"#/pendingjob");
	$('#confirmModel').modal('toggle');
	setTimeout(function() {
		location.reload();
	}, 1500);
	return false;
})

//update job priority
.on( 'click', '#pendingjob_data_table tbody #updatePriorityBtn',function () {
	var tr = $(this).parents('tr');
	var newTableValue=  $('#pendingjob_data_table').dataTable();
    var row=newTableValue.api().row(tr).data();
    document.getElementById('UpdatejobId').value = row.jobId;
    document.getElementById('jobPriority').value = row.jobPriority;


	// var tr = $(this).parents('tr');
	// var newTableValue=  $('#pendingjob_data_table').dataTable();
 //    var row=newTableValue.api().row(tr).data();
 //    var jobId = row.jobId;
 //    var jobPriority = $("#jobPriority").val();
 //    var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId,jobPriority:jobPriority};
	// var inputData = JSON.stringify(data);
	// createUpdateTable("jobupdate",inputData,"#/pendingjob");
})

.on('submit', 'form#UpdatePendingForm',function(){
	var jobId = $('#UpdatejobId').val();
	var jobPriority = $("#jobPriority").val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobId:jobId,jobPriority:jobPriority};
	var inputData = JSON.stringify(data);
	createUpdateTable("jobupdate",inputData,"#/pendingjob");
	$('#UpdatePendingModel').modal('toggle');
	setTimeout(function() {
		location.reload();
	}, 1500);
	return false;
});


	//for the editable field when the mouse is entered
// .on("mouseenter","#pendingjob_data_table .editable_text",function() {
//  	var tr1 = $(this).parents('tr');
// 	var newTableValue1=  $('#pendingjob_data_table').dataTable();
// 		var row1=newTableValue1.api().row(tr1).data();
// 	var cellData = row1.jobPriority;
// 	if (cellData != '' && typeof cellData != 'undefined' && cellData != 'null')
// 		$( this ).html( $( '<input type="text" class="form-control input-sm jbc-input" id="jobPriority" value="'+cellData+'" placeholder="'+cellData+'" /><button id="updatePriorityBtn" class="delete btn default btn-xs blue-hoki"><i class="fa fa-refresh"></i> Update</button>' ) );
// })

 //for the normal field when the mouse leaves
// .on("mouseleave","#pendingjob_data_table .editable_text",function() {
// 	var tr1 = $(this).parents('tr');
// 	var newTableValue1=  $('#pendingjob_data_table').dataTable();
// 	var row0=newTableValue1.api().row(tr1).data();
// 	var cellData = row0.jobPriority;
//     $( this ).find( "input" ).remove();
//     $( this ).find( "button" ).remove();
//     $( this ).html(cellData);
// });

//function to fill the data table
function searchPendingjobDataTable(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+"pendingjobsearch",
		async : false,
		contentType : "application/json;",
		data : inputData,
		success :function(data){
			var err=data.errorCode;
			if(err==-3)
			{
				alert(data.errorMessage);
				window.location.href = "logout.html";
			}
			var table = $('#pendingjob_data_table').DataTable( {
			"bDestroy" : true,
        	"bSort" : false,
            "bSortable": false,
			"processing": false,
			"bServerSide": false,
			"aaData": data.jobList,
			// "iDisplayLength" :20,
			"dom": 'T<"clear">lfrtip',
	        "oTableTools": {
	            "aButtons": [
//	                "copy",
	                "print",
	                {
	                    "sExtends":    "collection",
	                    "sButtonText": "Save",
	                    "aButtons":    [ "xls" ],
	                    "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
	                }
	            ]
	        },
			"aoColumns": [
			{
				"mData":"jobId",
			},{
				"mData": "jbt",
			},{
				"mData": "jobParentJob",
			},{
				"mData": "jobName",
			},{
				"mData": "jobStatus",
			},{
				"mData": "jobRetryCount",
			},{
				"mData": "jobCreatedDttm",
			},{
				"mData": "jobExtra1",
			},{
				"mData": "jobExtra2",
			},{
				"mData": "jobExtra3",
			},{
				"mData": "jobPriority",
			}],
			 "columnDefs": [
			 {
            "targets": 10,


            "class": 	'editable_text notedit',

                "width": "30%",

        },{
            "targets": 11,
            "data": null,
            "defaultContent": '<button id="cancelJobBtn" style="background:transparent;border:1px solid #DDD;" class="delete btn default btn-xs" data-toggle="modal" href="#confirmModel"><i class="fa fa-stop"></i> Cancel</button><button id="updatePriorityBtn" class="delete btn default btn-xs blue-hoki" data-toggle="modal" href="#UpdatePendingModel"><i class="fa fa-refresh"></i> Update</button>'
        } ],
		});
		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}
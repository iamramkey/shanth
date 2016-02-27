'use strict';
ThrazeApp.controller('AuditfileController',function($rootScope, $scope, $http) {
	Main.log("AuditfileController"); 

	$(".date-picker").datepicker({ 
	        autoclose: true,
	        todayHighlight: true
	  }).datepicker('update', curDate);;

});

var date = new Date();
var curDate = date.getFullYear()+'-' + ('0' + (date.getMonth()+1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

setTimeout(function(){
	document.getElementById('maxCount').value=1000;
	$('#fromDate').val(curDate);
	$('#toDate').val(curDate);
	initAuditFile();
	// $('.date-picker').datepicker('update', curDate);
}, 500)

//search fields function
$(document)
.on( 'keyup change', "#auditfile_data_table thead input", function () {
	console.log("working");
	var otable = $('#auditfile_data_table').DataTable();
        otable
            .column( $(this).parent().index()+':visible' )
            .search( this.value )
            .draw();
})

//form submit function
.on('submit', 'form#searchForm', function(){
	$('#auditfile_data_table').dataTable().fnDestroy();
	var aufDttmFrom = $('#fromDate').val();
	var aufDttmTo = $('#toDate').val();
	var rowCount = $('#maxCount').val();
	var fileName = $('#fileName').val();
	var parentFileName = $('#parentFileName').val();
	var fileType = $('#fileType').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,aufDttmFrom:aufDttmFrom,aufDttmTo:aufDttmTo,rowCount:rowCount,filName:fileName,parentFilName:parentFileName,aufFilType:fileType};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchAuditfileDataTable(inputData);
	return false;
})

//refresh button  click function
.on("click", "#refreshDataTable", function() {
  	$("input.input-sm:text").val("");
  	console.log("work");
  //	var date = new Date();
	//var curDate = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	//to set the current date to the datePicker
	//$('.date-picker').datepicker('update', curDate);
	// document.getElementById('maxCount').value=1000;
	//document.getElementById('fileName').value = '';
  	//destroy the datatable and start fresh
  	$('#fromDate').val(curDate);
	$('#toDate').val(curDate);
	
  	$('#auditfile_data_table').dataTable().fnDestroy();
	var aufDttmFrom = $('#fromDate').val();
	var aufDttmTo = $('#toDate').val();
	var rowCount = $('#maxCount').val();
	var fileName = $('#fileName').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,aufDttmFrom:aufDttmFrom,aufDttmTo:aufDttmTo,rowCount:rowCount,filName:fileName};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchAuditfileDataTable(inputData);
});

function initAuditFile(){
	var aufDttmFrom = $('#fromDate').val();
	var aufDttmTo = $('#toDate').val();
	var rowCount = $('#maxCount').val();
	var fileName = $('#fileName').val();
	var parentFileName = $('#parentFileName').val();
	var fileType = $('#fileType').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,aufDttmFrom:aufDttmFrom,aufDttmTo:aufDttmTo,rowCount:rowCount,filName:fileName,parentFilName:parentFileName,aufFilType:fileType};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchAuditfileDataTable(inputData);
}

//function to fill the data table
function searchAuditfileDataTable(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+"auditfilesearch",
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
        $('#auditfile_data_table').DataTable( {
        	"bSort" : false,
            "bSortable": false,
			"processing": false,
			"bServerSide": false,
			"aaData": data.auditFileList,
			"dom": 'T<"clear">lfrtip',
	        "oTableTools": {
	            "aButtons": [
	                "print",
	                {
	                    "sExtends":    "collection",
	                    "sButtonText": "Save",
	                    "aButtons":    [ "xls" ],
	                    "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7]
	                }
	            ]
	        },
			"aoColumns": [
			{
				"mData":"filId",
			},{
				"mData":"filName",
			},{
				"mData":"parentFilName",
			},{
				"mData": "aufFileType",
			},{
				"mData": "jobId",
			},{
				"mData": "aufChecksum",
			},{
				"mData": "aufFilesize",
			},{
				"mData": "aufChildCount",
			}
			,{
				"mData": "aufCreatedDttm",
			}],
		});
		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}
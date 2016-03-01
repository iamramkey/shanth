'use strict';
SignatureApp.controller('AlertController',function($rootScope, $scope, $http) {
	Main.log("AlertController"); 

	$(".date-picker").datepicker({ 
	        autoclose: true,
	        todayHighlight: true
	  }).datepicker('update', curDate);;
	
});

var date = new Date();
var curDate = date.getFullYear()+'-' + ('0' + (date.getMonth()+1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

setTimeout(function(){
	$('#alertCreatedDttmFrom').val(curDate);
	$('#alertCreatedDttmTo').val(curDate);
	initAlert();
}, 500);

  //search fields function
$(document)
.on( 'keyup change', "#alert_data_table thead input", function () {
	var otable = $('#alert_data_table').DataTable();
        otable
            .column( $(this).parent().index()+':visible' )
            .search( this.value )
            .draw();
})

  //form submit function
.on('submit', 'form#searchForm', function(){
  	$('#alert_data_table').dataTable().fnDestroy();
	var alertCreatedDttmFrom = $('#alertCreatedDttmFrom').val();
	var alertCreatedDttmTo = $('#alertCreatedDttmTo').val();
	var nodeName = $('#alert_nodeName').val();
	var alertType = $('#alertType').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,alertCreatedDttmFrom:alertCreatedDttmFrom,alertCreatedDttmTo:alertCreatedDttmTo,nodeName:nodeName,alertType:alertType};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchAlertDataTable(inputData);
  	return false;
})

//refresh button  click function
.on("click", "#refreshDataTable", function() {
	$("input.input-sm:text").val("");
	
	$('#alertCreatedDttmFrom').val(curDate);
	$('#alertCreatedDttmTo').val(curDate);

	//destroy the datatable and start fresh
	$('#alert_data_table').dataTable().fnDestroy();
	var data = {usrId:userId,ussSessionCode:ussSessionCode};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchAlertDataTable(inputData);
});

function initAlert(){
	var alertCreatedDttmFrom = $('#alertCreatedDttmFrom').val();
	var alertCreatedDttmTo = $('#alertCreatedDttmTo').val();
	var nodeName = $('#alert_nodeName').val();
	var alertType = $('#alertType').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,alertCreatedDttmFrom:alertCreatedDttmFrom,alertCreatedDttmTo:alertCreatedDttmTo,nodeName:nodeName,alertType:alertType};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchAlertDataTable(inputData);
}

//function to fill the data table
function searchAlertDataTable(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+"alertsearch",
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
        $('#alert_data_table').dataTable( {
        	"bDestroy" : true,
        	// "bSort" : false,
            "bSortable": true,
			"processing": false,
			"bServerSide": false,
			"aaData": data.alertList,
          "dom": 'T<"clear">lfrtip',
	        "oTableTools": {
	            "aButtons": [
//	                "copy",
	                "print",
	                {
	                    "sExtends":    "collection",
	                    "sButtonText": "Save",
	                    "aButtons":    [ "xls" ],
	                    "mColumns": [ 0, 1, 2, 3, 4, 5, 6 ]
	                }
	            ]
	        },
			"aoColumns": [
			{
				"mData": "nod",
			},{
				"mData": "atp",
			},{
				"mData": "altDesc",
			},{
				"mData": "altCreatedDttm",
			},{
				"mData": "altExtra1",
			},{
				"mData": "altExtra2",
			},{
				"mData": "altExtra3",
			}],
		});
		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}
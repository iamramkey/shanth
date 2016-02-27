'use strict';
ThrazeApp.controller('FileController',function($rootScope, $scope, $http) {
	Main.log("FileController"); 
	
	$(".date-picker").datepicker({ 
	        autoclose: true,
	        todayHighlight: true
	  }).datepicker('update', curDate);;

});

var date = new Date();
var curDate = date.getFullYear()+'-' + ('0' + (date.getMonth()+1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);


//to set the current date to the datePicker
setTimeout(function(){
	// $('.date-picker').datepicker('update', curDate);
	$('#filDttmFrom').val(curDate);
	$('#filDttmTo').val(curDate);
	document.getElementById('rowCount').value=1000;
	initFileScreen()
}, 500);
  //search fields function
$(document)
.on( 'keyup change', "#file_data_table thead input", function () {
	var otable = $('#file_data_table').DataTable();
        otable
            .column( $(this).parent().index()+':visible' )
            .search( this.value )
            .draw();
})

  //form submit function
.on('submit', 'form#searchForm', function(){
	$('#file_data_table').dataTable().fnDestroy();
	var filName = $('#filName').val();
	var filDttmFrom = $('#filDttmFrom').val();
	var filDttmTo = $('#filDttmTo').val();
	var rowCount = $('#rowCount').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,filName:filName,filDttmFrom:filDttmFrom,filDttmTo:filDttmTo,rowCount:rowCount};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchFilleDataTable(inputData);
	return false;
})

  //refresh button  click function
.on("click", "#refreshDataTable", function() {
  	//var date = new Date();
	//var curDate = date.getFullYear()+'-' + ('0' + (date.getMonth()+1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);
	//to set the current date to the datePicker
	//$('.date-picker').datepicker('update', curDate);
	//document.getElementById('rowCount').value=1000;
//	document.getElementById('filName').value = '';
  	//destroy the datatable and start fresh
  	$('#filDttmFrom').val(curDate);
	$('#filDttmTo').val(curDate);
	
  	$('#file_data_table').dataTable().fnDestroy();
	var filName = $('#filName').val();
	var filDttmFrom = $('#filDttmFrom').val();
	var filDttmTo = $('#filDttmTo').val();
	var rowCount = $('#rowCount').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,filName:filName,filDttmFrom:filDttmFrom,filDttmTo:filDttmTo,rowCount:rowCount};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchFilleDataTable(inputData);
});

function initFileScreen(){
	var filName = $('#filName').val();
	var filDttmFrom = $('#filDttmFrom').val();
	var filDttmTo = $('#filDttmTo').val();
	var rowCount = $('#rowCount').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,filName:filName,filDttmFrom:filDttmFrom,filDttmTo:filDttmTo,rowCount:rowCount};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchFilleDataTable(inputData);
}

//function to fill the data table
function searchFilleDataTable(inputData){
	$.ajax({
		type : "POST",
		url : baseUrl+"filesearch",
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
        $('#file_data_table').DataTable( {
        	"bSort" : false,
            "bSortable": false,
			"processing": false,
			"bServerSide": false,
			"aaData": data.fileTblList,
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
	                    "mColumns": [ 0, 1, 2, 3, 4, 5]
	                }
	            ]
	        },
			"aoColumns": [
			{
				"mData":"filName",
			},{
				"mData": "filPath",
			},{
				"mData": "filIdentifier",
			},{
				"mData": "filType",
			},{
				"mData": "job",
			}
			,{
				"mData": "filExtra1",
			}],
		});
		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}
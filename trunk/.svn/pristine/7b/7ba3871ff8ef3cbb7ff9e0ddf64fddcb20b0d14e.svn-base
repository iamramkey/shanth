'use strict';
ThrazeApp.controller('MonitorjobController',function($rootScope, $scope, $http) {
	Main.log("MonitorjobController"); 

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
	var dataForDropdown = {"usrId":currentUser['userTbl']['usrId'],"ussSessionCode":currentUser['ussSessionCode'], prefix:'nod'};
	var inputDataForDropdown = JSON.stringify(dataForDropdown);
	Main.toCallListApi('masterlist',inputDataForDropdown,'nodeName1');
}, 500);

//to set the current date to the datePicker
	//$('.date-picker').datepicker('update', curDate);

	// $('#nodeName1').select2('data', $('#nodeName1').find('option')[0]);
	// $('select option:first-child').attr("selected", "selected");
	// var myDDL = $('#nodeName1');
	// myDDL[0].selectedIndex = 0;
	
setTimeout(function(){
	var jobStartDttmFrom = $('#fromDate').val();
    var jobStartDttmTo = $('#toDate').val();
    var nodName = $('#nodeName1').val();
		var data = {usrId:currentUser['userTbl']['usrId'],ussSessionCode:currentUser['ussSessionCode'],jobStartDttmFrom:curDate,jobStartDttmTo:curDate,nodName:nodName};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchMonitorjobDataTable(inputData);
}, 1500);
	
var dataForServerSettings = {usrId:currentUser['userTbl']['usrId'],ussSessionCode:currentUser['ussSessionCode'],sesCode:"AutoRefreshInterval"};
var inputDataForServerSettings = JSON.stringify(dataForServerSettings);
//to fetch server settings
(function(){
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
				window.location.href = "index.html";
			}
			timeOutCount = data.serverSettings.sesValue*1000;

		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
})();

// var timeOutCount = 30*1000;
setInterval(function(){
	var jobStartDttmFrom = $('#fromDate').val();
	var jobStartDttmTo = $('#toDate').val();
	var nodName = $('#nodeName1').val();
		var data = {usrId:currentUser['userTbl']['usrId'],ussSessionCode:currentUser['ussSessionCode'],jobStartDttmFrom:jobStartDttmFrom,jobStartDttmTo:jobStartDttmTo,nodName:nodName};
	var inputData = JSON.stringify(data);
	searchMonitorjobDataTable(inputData);
}, timeOutCount);

  //search fields function
 $(document)

.on( 'keyup change', "#monitorjob_data_table thead input", function () {
	console.log("working");
	var otable = $('#monitorjob_data_table').DataTable();
        otable
            .column( $(this).parent().index()+':visible' )
            .search( this.value )
            .draw();
})
   //form submit function
.on('submit', 'form#searchForm',  function(){
  	// $('#monitorjob_data_table').dataTable().fnDestroy();
  	var table = $('#monitorjob_data_table').dataTable();
  	 table.fnClearTable(this);
  	var jobStartDttmFrom = $('#fromDate').val();
    var jobStartDttmTo = $('#toDate').val();
    var nodName = $('#nodeName1').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,nodName:nodName,jobStartDttmFrom:jobStartDttmFrom,jobStartDttmTo:jobStartDttmTo};
   		var inputData = JSON.stringify(data);
	 	console.log(inputData);
	 	$('#monitorjob_data_table').dataTable().fnDestroy();
	 	searchMonitorjobDataTable(inputData);
  	return false;
  })
  //refresh button  click function
.on('click', "#refreshDataTable", function() {
  	var date = new Date();
	var curDate = date.getFullYear()+'-' + ('0' + (date.getMonth()+1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);
	//to set the current date to the datePicker
	$('.date-picker').datepicker('update', curDate);
	// $('#nodeName1').select2('index', $('#nodeName1').find('option')[0]);
	// $('select option:first-child').attr("selected", "selected");
  	///destroy the datatable and start fresh
  	var table = $('#monitorjob_data_table').dataTable();
  	 table.fnClearTable(this);
	var jobStartDttmFrom = $('#fromDate').val();
    var jobStartDttmTo = $('#toDate').val();
    var nodName = $('#nodeName1').val();
	var data = {usrId:userId,ussSessionCode:ussSessionCode,jobStartDttmFrom:jobStartDttmFrom,jobStartDttmTo:jobStartDttmTo,nodName:nodName};
	var inputData = JSON.stringify(data);
	console.log(inputData);
	searchMonitorjobDataTable(inputData);
  });

//function to fill the data table
function searchMonitorjobDataTable(inputData){
	// return;
	$.ajax({
		type : "POST",
		url : baseUrl+"jobmonitor",
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
			$('#monitorjob_data_table').DataTable( {
			"bDestroy" : true,
        	"bSort" : false,
            "bSortable": false,
			"processing": false,
			"bServerSide": false,
			"aaData": data.jobList,
			"dom": 'T<"clear">lfrtip',
	        "oTableTools": {
	            "aButtons": [
	                "print",
	                {
	                    "sExtends":    "collection",
	                    "sButtonText": "Save",
	                    "aButtons":    [ "xls" ],
	                    "mColumns": [ 0, 1, 2, 3, 4, 5, 6]
	                }
	            ]
	        },
			"aoColumns": [
			{
				"mData":"jbtName",
			},{
				"mData": "cancelled",
			},{
				"mData": "pending",
			},{
				"mData": "running",
			},{
				"mData": "failed",
			},{
				"mData": "completed",
			},{
				"mData": "total",
			}],
		});
		},
		error :function(data){
			console.log(data);
			alert("something wrong with the network..check the network and try again");
		},
	});
}
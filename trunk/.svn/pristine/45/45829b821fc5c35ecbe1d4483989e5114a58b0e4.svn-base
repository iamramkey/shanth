'use strict';
var activePageCode, formData, headerActions, rowActions, userId = currentUser['userTbl']['usrId'], ussSessionCode = currentUser['ussSessionCode'], scope;

ThrazeApp.controller('MasterFormController',
		function($rootScope, $scope, $http) {
			Main.log("Master Form Controller");
		});

ThrazeApp.controller('MasterController', function($rootScope, $scope, $http,
		$timeout, $modal) {
	Main.log("Master Controller");
	scope = $scope;
	$scope.$on('$viewContentLoaded', function() {
		// initialize core components
		Metronic.initAjax();
	});
	// set sidebar closed and body solid layout mode
	$rootScope.settings.layout.pageBodySolid = true;
	$rootScope.settings.layout.pageSidebarClosed = false;

	activePageCode = window.location.hash.split('/')[2];
	$scope.searchTitle = activePageCode;
	$scope.modalTitle = activePageCode;
	formData = undefined;
	init();

	function init() {
		httpPost('mastersearch', Main.getJsonInput({
			'name' : activePageCode
		}), fillAVHMSearch);
	}

	// on click of the refresh button
	$scope.refreshTable = function() {
		$('#masterTable').dataTable().fnDestroy();
		init();
	}

	$scope.hello = function() {
		Main.log("hello. Testing method");
	}

	$scope.auditModal = function(id) {
		Main.log("audit btn clicked " + id);
		$scope.fetchAuditData(id);
		$("#auditModal").modal("toggle");
	}

	$scope.addNewModal = function() {
		Main.log("add btn clicked ");
		$('#id').val(0);
		$scope.modalTitle = 'Add New ' + $scope.displayCode;
		$scope.modelContent = 'Create ' + $scope.displayCode;
		$scope.prepareModal(false, false);
		$("#formDataModal").modal("toggle");
	}

	$scope.editModal = function(id) {
		Main.log("edit btn clicked " + id);
		$scope.modalTitle = 'Edit ' + $scope.displayCode;
		$scope.modelContent = 'Update ' + $scope.displayCode;
		$scope.prepareModal(false, true);
		$scope.fetchData(id);
		$("#formDataModal").modal("toggle");
	}

	$scope.viewModal = function(id) {
		Main.log("viewModal from scope.view " + id);
		$scope.modalTitle = 'View ' + $scope.displayCode;
		$scope.modalContent = 'Viewing ' + $scope.displayCode;
		$scope.prepareModal(true, true);
		$scope.fetchData(id);
		$("#formDataModal").modal("toggle");
	}

	$scope.deactivateModal = function (id) {
		Main.log(id);
		//Main.showLoader();
		//Sugir make this togglable with activate button
		httpPost('userdeactivate', Main.getJsonInput({
			id : id
		}), $scope.callProcessed);
	};

	$scope.activateModal = function (id) {
		Main.log(id);
		//Main.showLoader();
		//Sugir make this togglable with de-activate button
		httpPost('useractivate', Main.getJsonInput({
			id : id
		}), $scope.callProcessed);
	};

	$scope.resetpasswordModal = function (id) {
		Main.log(id);
		Main.showLoader();
		httpPost('userpasswordreset', Main.getJsonInput({
			id : id
		}), $scope.callProcessed);
	};

	$scope.refreshModal = function (id) {
		Main.log(id);
		Main.showLoader();
		//Sugir make this togglable with de-activate button
		httpPost('nodejobcapabilityrefresh', Main.getJsonInput({
			id : id
		}), $scope.callProcessed);
	};

	$scope.shutdownModal = function (id) {
		Main.log(id);
		Main.showLoader();
		//Sugir make this togglable with de-activate button
		httpPost('nodeshutdown', Main.getJsonInput({
			id : id
		}), $scope.callProcessed);
	};

	$scope.startModal = function (id) {
		Main.log(id);
		Main.showLoader();
		//Sugir make this togglable with de-activate button
		httpPost('jobcapabilitystart', Main.getJsonInput({
			id : id
		}), $scope.callProcessed);
	};

	$scope.stopModal = function (id) {
		Main.log(id);
		Main.showLoader();
		//Sugir make this togglable with de-activate button
		httpPost('jobcapabilitystop', Main.getJsonInput({
			id : id
		}), $scope.callProcessed);
	};

	$scope.callProcessed = function (result) {
		init();
		Main.hideLoader();
		Main.alert(result.notification);
	};

	$scope.prepareModal = function(readOnly, inputActive) {
		Main.log("prepare Modal" + readOnly);
		$("#avhmForm :input").attr("readonly", readOnly);
		if (inputActive)
			$("#avhmForm label").addClass('inputActive');
		else
			$("#avhmForm label").removeClass('inputActive');

		if (document.getElementById("avhmForm"))
			document.getElementById("avhmForm").reset();
		if (readOnly)
			$('#saveBtn').hide();
		else
			$('#saveBtn').show();
	}

	$scope.fetchData = function(id) {
		httpPost('masterfetch', Main.getJsonInput({
			name : activePageCode,
			id : id
		}), $scope.fetchSuccessfullyCallBack);
	}

	$scope.fetchSuccessfullyCallBack = function(result) {
		Main.log("Fetch Succesfully CallBack from scope");
		$('#id').val(result.id);
		for ( var key in result.data)
			$('#' + key).val(result.data[key]), $("#" + key).select2("val",
					result.data[key]);
	}
	
	$scope.fetchAuditData = function(id) {
		Main.log("Audit Fetching");
		httpPost('viewauditinfo', Main.getJsonInput({
			tableName : activePageCode,
			id : id
		}), $scope.fetchAuditSuccessfullyCallBack);
	}

	$scope.fetchAuditSuccessfullyCallBack = function(result) {
		Main.log("Audit Fetch Succesfully CallBack from scope");
		Main.log(result);
		$scope.auditData = result;
	}
	
	$scope.openUrl = function(id, url) {
		if (id && !url) {
			url = id;
			id = null;
		}
		if (id == null)
			$(location).attr("href", "#" + url);
		else
			$(location).attr("href", "#" + url + "/" + id);
	}

	// TODO: should be in Report Controller ?
	$scope.runNew = function(id) {
		Main.log("run New" + id);
	}
});

// on model form submit
$(document).on('submit', '#avhmForm', function() {
	console.log("submit on avhm form");
	var inputData = {};
	inputData['id'] = $('#id').val();
	inputData['name'] = activePageCode;
	var data = {};
	for (var i = 0; i < formData.length; i++) {
		var tempVar = $('#' + formData[i].id).val();
		if (tempVar == '' || !tempVar) {
			Main.alert('Please fill ' + formData[i].label);
			return false;
		}
		data[formData[i].id] = tempVar;
	}
	inputData['data'] = data;
	Main.log(Main.getJsonInput(inputData));
	httpPost('mastersave', Main.getJsonInput(inputData), savedSuccessfully);
	return false;
});

function savedSuccessfully(result) {
	$('#formDataModal').modal('hide');
	Main.alert(result.notification, true);
	$('#refreshTable').click();
}

// TODO: who calls this ?
function errorWhileSaving(error) {
	Main.alert('Network error.');
	Main.log(error);
}

function fillAVHMSearch(result) {
	Main.fillBreadcrum(result.breadCrum);
	Main.fillHistory(result.history);
	headerActions = result.headerActions, rowActions = result.rowActions;
	if (!formData)
		fillAVHMForm(result.form);

	scope.headerActions = result.headerActions;
	scope.tableHeader = result.headers;
	scope.tableData = result.data;
	scope.rowActions = result.rowActions;
//	Main.log(scope.rowActions);	
	setTimeout(function() {
		$('#masterTable').dataTable();
	}, 500)

}

function fillAVHMForm(data) {
	formData = data;
	$('#avhmFormContainer').html('');
	for (var i = 0; i < data.length; i = i + 2) {
		var index1Required = '';
		if (data[i]['isMandatory']) {
			index1Required = ' *'
		}
		;
		var temVar = '<div class="form-group">';
		temVar += '<div class="group">';
		temVar += formComponent(data[i]['type'], data[i]['id'],
				data[i]['label'], data[i]['isMandatory']);
		temVar += '<span class="highlight"></span>';
		temVar += '<span class="bar"></span>';
		if (data[i]['type'] != 'select' && data[i]['type'] != 'boolean')
			temVar += '<label class="md_label">' + data[i]['label']
					+ index1Required + ' </label>'
		temVar += '</div>';
		if (i + 1 < data.length) {
			var index2Required = '';
			if (data[i + 1]['isMandatory']) {
				index2Required = ' *'
			}
			;
			temVar += '<div class="group">';
			temVar += formComponent(data[i + 1]['type'], data[i + 1]['id'],
					data[i + 1]['label'], data[i + 1]['isMandatory']);
			temVar += '<span class="highlight"></span>';
			temVar += '<span class="bar"></span>';
			if (data[i + 1]['type'] != 'select'
					&& data[i + 1]['type'] != 'boolean')
				temVar += '<label class="md_label">' + data[i + 1]['label']
						+ index2Required + '</label>';
			temVar += '</div>';
		}
		temVar += '</div>';
		$('#avhmFormContainer').append(temVar);
		if (data[i]['type'] == 'select')
			Main.toCallListApi('masterlist', Main.getJsonInput({
				'prefix' : data[i]['id']
			}), data[i]['id']);
		if (i + 1 < data.length && data[i + 1]['type'] == 'select')
			Main.toCallListApi('masterlist', Main.getJsonInput({
				'prefix' : data[i + 1]['id']
			}), data[i + 1]['id']);

		if (data[i]['type'] == 'boolean')
			$('#' + data[i]['id'])
					.html(
							'<option value="true">Yes</option><option value="false">No</option>');
		if (i + 1 < data.length && data[i + 1]['type'] == 'boolean')
			$('#' + data[i + 1]['id'])
					.html(
							'<option value="true">Yes</option><option value="false">No</option>');
	}
	// setTimeout($('.date-picker').datepicker(),2000);
	// $('.date-picker').datepicker();
}

function formComponent(type, id, placeholder, mandatory) {
	var mand = '', mandForSelect = '';
	if (mandatory)
		mand = 'required', mandForSelect = '*';
	switch (type) {
	case 'dttm':
		return '<input id="'
				+ id
				+ '" '
				+ mand
				+ ' class="form-control form-control-inline input-medium date-picker inputMaterial" size="16" type="date" value=""/>';
	case 'select':
		return '<select class="form-control input-medium select2me" data-placeholder="'
				+ placeholder
				+ '" id="'
				+ id
				+ '" '
				+ mand
				+ '></select><label class="select_label">Select '
				+ placeholder
				+ ' ' + mandForSelect + '</label>';
	case 'bigstring':
		return '<textarea id="' + id + '" name="" class="inputMaterial" ' + mand
				+ '></textarea>';
	case 'boolean':
		return '<select class="form-control input-medium" id="' + id
				+ '" ></select>';
		// return '<input type="checkbox" id="'+id+'" name=""
		// class="inputMaterial" '+mand+'/>';
	default:
		return '<input type="text" id="' + id
				+ '" name="" class="inputMaterial" ' + mand + '/>';
	}
}

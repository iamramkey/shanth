function yesNoModalShow(label, submitUrl, id) {
	console.log('yes no modal show');
	$('#customModelTitle').html(label);
	$('#customModelMessage').html('Do you want to ' + label + ' ?');
	$('#customeModelSaveButton').attr('onclick', 'yesNoModalSave()');
	$('#customeModelSaveButton').show();
	$('#customModelUrl').val(submitUrl);
	$('#customModelId').val(id);
}

function yesNoModalSave() {
	console.info('id : ' + $('#customModelId').val());
	console.info('url: ' + $('#customModelUrl').val());
	var inputData = {
		'usrId' : userId,
		'ussSessionCode' : ussSessionCode,
		'id' : $('#customModelId').val()
	};
	httpPost($('#customModelUrl').val(), Main.getJsonInput(inputData),
			yesNoModalSaveSuccess);
}

function yesNoModalSaveSuccess(result) {
	alert(result.notification);
	$('#customModel').modal('toggle');
	$('#refreshTable').click();
}

function reasonModalShow(label, submitUrl, id) {
	console.log('reason modal show');
	$('#customModelTitle').html(label);
	$('#customModelMessage').html('Do you want to ' + label + ' ?');
	$('#customModelMessage')
			.html(
					'<div class="row"><div class="col-md-12"><textarea id="reason" class="form-control" placeholder="Enter the reason *"></textarea></div></div>');
	$('#customeModelSaveButton').attr('onclick', 'reasonModalSave()');
	$('#customeModelSaveButton').show();
	$('#customModelUrl').val(submitUrl);
	$('#customModelId').val(id);
}

function reasonModalSave() {
	console.info('id : ' + $('#customModelId').val());
	console.info('url: ' + $('#customModelUrl').val());
	if ($('#reason').val() == '') {
		alert('Reason is a required field');
		return true;
	}

	var inputData = {
		'usrId' : userId,
		'ussSessionCode' : ussSessionCode,
		'id' : $('#customModelId').val(),
		'reason' : $('#reason').val()
	};
	httpPost($('#customModelUrl').val(), Main.getJsonInput(inputData),
			reasonModalSaveSuccess);
}

function reasonModalSaveSuccess(result) {
	alert(result.notification);
	$('#customModel').modal('toggle');
	$('#refreshTable').click();
}

function messageModalShow(label, fetchUrl, id) {
	console.log('message no modal show');
	$('#customModelTitle').html(label);
	$('#customModelMessage').html('');
	$('#customeModelSaveButton').hide();
	httpPost(fetchUrl, Main.getJsonInput({
		'usrId' : userId,
		'ussSessionCode' : ussSessionCode,
		'id' : id
	}), messageModalSuccess);
}

function messageModalSuccess(result) {
	$('#customModelMessage').html(result.notification);
}

function stocktransfercomplete(action, id, netAmount){
	$('#customModelTitle').html('Stock Transfer Cost.');
	var formDataForStockTransfer = '<div class="row">'+
	'<label for="inputPassword12" class="col-md-2 control-label">Charge<span class="required">* </span></label>'+
	'<div class="col-md-4">'+
	'<input type="text" id="transferCharge00" class="form-control" placeholder="Cost For Transfer" required></div>'+
	'<label for="inputPassword12" class="col-md-2 control-label">Net Amount</label>'+								
	'<div class="col-md-4">'+
	'<input type="text" id="netAmount00" class="form-control" placeholder="Total Amount" readonly></div></div>'+						
	'<br><div class="row">'+								
	'<label for="inputPassword12" class="col-md-2 control-label">Description <span class="required"> *</span></label>'+								
	'<div class="col-md-10">'+
	'<textarea class="form-control" id="transferDescription" placeholder="Description" required></textarea></div></div>';					
	$('#customModelMessage').html(formDataForStockTransfer);
	$('#customeModelSaveButton').attr('onclick', 'stockTransferCompleateModalSave()');
	$('#customModelId').val(id);
	$('#netAmount00').val(fetchRowdata(netAmount)[8]);
}

function stockTransferCompleateModalSave(){
	if($('#transferCharge00').val() == ''){alert('Transfer Charge is a required field.');return true;}
	if($('#transferDescription').val() == ''){alert('Description is a required field.');return true;}
	console.log($('#transferCharge00').val());
	console.log($('#transferDescription').val());
	var strId = $('#customModelId').val();
  	var strTransferCost = $('#transferCharge00').val();
  	var strTransferCostDesc = $('#transferDescription').val();
	var rawData = {strId:strId,strTransferCost:strTransferCost,strTransferCostDesc:strTransferCostDesc};
	httpPost('stocktransfer/complete', Main.getJsonInput(rawData), stockTransferCompleatSuccesscallback);
}

function stockTransferCompleatSuccesscallback(result){
	if(result.errorCode){
		alert(result.errorMessage);
		return true;
	}
	$('#newModel').modal('toggle');
	$('#alertMessage').html(result.notification);
	$('#alertModel').modal('toggle');
	$('#refreshTable').click();
}

// Purchase order specfic custom functions start
function poSendInfo(action, id){
	$('#customModelTitle').html('Purchase Order Send Info.');
	$('#customeModelSaveButton').attr('onclick', 'poSendInfoModalSave()');
	$('#customModelId').val(id);
	httpPost('purchaseorder/sendfetch', Main.getJsonInput({id:id}), poSendInfoFetchSuccesscallback);
}
function poSendInfoFetchSuccesscallback(data){
	console.log(data);
	var formDataForPoSend = '<div class="row"><label for="inputPassword12" class="col-md-2 control-label">Order No:</label>'
		+'<div class="col-md-4"><input type="text" class="form-control" id="por" readonly value="'
		+data.purchaseOrderSi.por+'"></div>'
		+'<label for="inputPassword12" class="col-md-2 control-label">Terms Of Delivery <span id="req22" class="required"> *</span></label>'
		+'<div class="col-md-4"><textarea class="form-control" id="prsTermsOfDelivery" required value="'
		+data.purchaseOrderSi.prsTermsOfDelivery+'"></textarea></div></div>'
		+'<div class="row"><label for="inputPassword12" class="col-md-2 control-label">Subject<span id="req24" class="required"> *</span></label>'
		+'<div class="col-md-4"><input type="text" class="form-control" id="prsSubject"  required value="'
		+data.purchaseOrderSi.prsSubject+'"></div>'
		+'<label for="inputPassword12" class="col-md-2 control-label">Name</label><div class="col-md-4">'
		+'<input type="text" class="form-control" id="prsName" readonly value="'+data.purchaseOrderSi.prsName+'"></div></div>'
		+'<div class="row"><label for="inputPassword12" class="col-md-2 control-label">Email<span id="req23" class="required">* </span></label>'
		+'<div class="col-md-4"><textarea class="form-control" id="prsEmail" rows="2" required value="'
		+data.purchaseOrderSi.prsEmail+'"></textarea><span style="font-size:10px;">*use comma to enter multiple email</span></div>'
		+'<label for="inputPassword12" class="col-md-2 control-label">Kind Attentation<span id="req25" class="required"> *</span></label>'
		+'<div class="col-md-4"><textarea class="form-control" id="prsKindAttn" rows="2" required value="'
		+data.purchaseOrderSi.prsKindAttn+'"></textarea></div></div>'
		+'<div class="row"><label for="inputPassword12" class="col-md-2 control-label">Default Print<span id="req27" class="required"> *</span></label>'
		+'<div class="col-md-4"><textarea class="form-control" id="prsDefaultPrint" rows="2" required value="'
		+data.purchaseOrderSi.prsDefaultPrint+'"></textarea></div>'
		+'<label for="inputPassword12" class="col-md-2 control-label">Sign<span id="req28" class="required"> *</span></label>'
		+'<div class="col-md-4"><input type="text" class="form-control" id="prsSign" required value="'
		+data.purchaseOrderSi.prsSign+'"></div></div>'
		+'<div class="row"><label for="inputPassword12" class="col-md-2 control-label">Reference <span id="req29" class="required"> *</span></label>'
		+'<div class="col-md-4"><input type="text" class="form-control" id="prsSupRef" required value="'
		+data.purchaseOrderSi.prsSupRef+'"></div><label for="inputPassword12" class="col-md-2 control-label">Other Reference <span id="req30" class="required"> *</span></label>'
		+'<div class="col-md-4"><input type="text" class="form-control" id="prsOtherRef" required value="'
		+data.purchaseOrderSi.prsOtherRef+'"></div></div>'
		+'<div class="row"><label for="inputPassword12" class="col-md-2 control-label">Despatch Through <span id="req27" class="required"> *</span></label>'
		+'<div class="col-md-4"><input type="text" class="form-control" id="prsDespatchThrough" required value="'
		+data.purchaseOrderSi.prsDespatchThrough+'"></div></div>';
	$('#customModelMessage').html(formDataForPoSend);
}
function poSendInfoModalSave(){
	console.log('poSendInfoModalSave is working ');
	var porId = $('#customModelId').val();
  	var prsTermsOfDelivery = $('#prsTermsOfDelivery').val();
  	var prsEmail = $('#prsEmail').val();
  	var prsSubject = $('#prsSubject').val();
  	var prsKindAttn = $('#prsKindAttn').val();
  	var prsDefaultPrint = $('#prsDefaultPrint').val();
  	var prsSign = $('#prsSign').val();
  	var prsSupRef = $('#prsSupRef').val();
  	var prsOtherRef = $('#prsOtherRef').val();
  	var prsDespatchThrough = $('#prsDespatchThrough').val();
  	var dataForSendPO = {porId:porId,termsOfDelivery:prsTermsOfDelivery,email:prsEmail,subject:prsSubject,kindAttn:prsKindAttn,defaultPrint:prsDefaultPrint,sign:prsSign,supRef:prsSupRef,otherRef:prsOtherRef,despatchThrough:prsDespatchThrough};
	httpPost('purchaseorder/send', Main.getJsonInput(dataForSendPO), poSendInfoSuccesscallback);
}
function poSendInfoSuccesscallback(result){
	$('#customModel').modal('hide');
	avprmalert(result.notification, true);
	$('#refreshTable').click();
}
// Purchase order specfic custom functions end
'use strict';

SignatureApp.controller('MakeADealController',function($rootScope, $scope, $http, dataService) {

	// Log active controller
	Main.log("MakeADealController");

	// Populate Counterpart dropdown
	dataService.getCarrierList(function(carrierListResponse) {
		//console.log('getCarrierList:');
		//console.log(carrierListResponse.data.list);
		$scope.carriers = carrierListResponse.data.list;
	});

	// Carrier selected
	$scope.carrierSelected = function(data) {
    console.log('OMG the following item was selected:');
		console.log(data);
		$scope.accountManagers[0].accountManagerName = 'Hello';
		$scope.accountManagers[0].ticked = true;
		console.log()
  }

	// Populate Account Manager dropdown
	dataService.getAccountManagerList(function(accountManagerListResponse) {
		//console.log('getAccountManagerList:');
		//console.log(accountManagerListResponse.data.list);
		$scope.accountManagers = accountManagerListResponse.data.list;
	});

	// Populate Currency dropdown
	dataService.getCurrencyList(function(currencyListResponse) {
		//console.log('getCurrencyList:');
		//console.log(currencyListResponse.data.list);
		$scope.currencies = currencyListResponse.data.list;
	});

	// Populate Outgoing Destinations dropdown
	dataService.getOutgoingDestinationList(function(outgoingDestinationListResponse) {
		//console.log('getOutgoingDestinationList:');
		//console.log(outgoingDestinationListResponse.data.list);
		$scope.outgoingDestinations = outgoingDestinationListResponse.data.list;
	});

	// Populate Incoming Destinations dropdown
	dataService.getIncomingDestinationList(function(incomingDestinationListResponse) {
		//console.log('getIncomingDestinationList:');
		//console.log(incomingDestinationListResponse.data.list);
		$scope.incomingDestinations = incomingDestinationListResponse.data.list;
	});

	// Populate Deal Types dropdown
	dataService.getDealTypeList(function(dealTypeListResponse) {
		//console.log('getDealTypeList:');
		//console.log(dealTypeListResponse.data.list);
		$scope.dealTypes = dealTypeListResponse.data.list;
	});

	// Daterangepicker
	//$scope.datePicker.date = {startDate: null, endDate: null};

	// Grace Period
	// TODO: Rewrite function to make it more efficient
	$scope.grace0 = "active";
	$scope.grace15 = "inactive";
	$scope.grace30 = "inactive";
	$scope.grace60 = "inactive";

	$scope.toggleGrace = function(input) {

		if(input === 0) {
			$scope.grace0 = "active";
			$scope.grace15 = "inactive";
			$scope.grace30 = "inactive";
			$scope.grace60 = "inactive";
			$scope.graceCustom = "inactive";
		}
		else if(input === 15) {
			$scope.grace0 = "inactive";
			$scope.grace15 = "active";
			$scope.grace30 = "inactive";
			$scope.grace60 = "inactive";
			$scope.graceCustom = "inactive";
		}
		else if(input === 30) {
			$scope.grace0 = "inactive";
			$scope.grace15 = "inactive";
			$scope.grace30 = "active";
			$scope.grace60 = "inactive";
			$scope.graceCustom = "inactive";
		}
		else if(input === 60) {
			$scope.grace0 = "inactive";
			$scope.grace15 = "inactive";
			$scope.grace30 = "inactive";
			$scope.grace60 = "active";
			$scope.graceCustom = "inactive";
		}
		else if(input === "custom") {
			$scope.grace0 = "inactive";
			$scope.grace15 = "inactive";
			$scope.grace30 = "inactive";
			$scope.grace60 = "inactive";
			$scope.graceCustom = "active";
		}
	};

	// UI-Grid
	$scope.gridOptions = {
    enableFullRowSelection: false,
		enableCellEdit: true,
		enableCellEditOnFocus: true
  };

	$scope.gridOptions.columnDefs = [
		{ name: 'destinationGroup', grouping: { groupPriority: 0 } },
		{ name: 'destination' },
		{ name: 'origin', },
		{ name: 'service' },
		{ name: 'tier' },
		{ name: 'volumeFrom' },
		{ name: 'volumeTo' },
		{ name: 'volume' },
		{ name: 'rate' },
		{ name: 'settlementRate' },
		{ name: 'incrementalRate' },
		{ name: 'cost' },
		{ name: 'price' },
		{ name: 'calculatedCost' },
		{ name: 'weighting' },
		{ name: 'committed' },
		{ name: 'currency' },
		{ name: 'validFrom' },
		{ name: 'validTo' }
	];

	// Add data test
	$scope.addDestinationTest = function() {
    var n = $scope.gridOptions.data.length + 1;
    $scope.gridOptions.data.push({
			"destinationGroup": "Sweden",
	    "destination": "Sweden - Fixed",
	    "origin": "All",
	    "service": "Gold",
	    "tier": "1",
	    "volumeFrom": "0",
	    "volumeTo": "1000000",
	    "volume": "1000000",
	    "rate": "0.005",
	    "settlementRate": "0.005",
	    "incrementalRate": "0.0045",
	    "cost": "0.0035",
	    "price": "0.0048",
	    "calculatedCost": "0.0032",
	    "weighting": "1.95",
	    "committed": true,
	    "currency": "EUR",
	    "validFrom": "2015-01-01",
	    "validTo": "2015-06-30"
    });
  };

	$http.get('mock/destinations.json')
		.success(function(data) {
      for ( var i = 0; i < data.length; i++ ){
        data[i].destinationGroup = data[i].destinationGroup;
				data[i].destination = data[i].destination;
				data[i].origin = data[i].origin;
				data[i].service = data[i].service;
				data[i].tier = data[i].tier;
				data[i].volumeFrom = data[i].volumeFrom;
				data[i].volumeTo = data[i].volumeTo;
				data[i].volume = data[i].volume;
				data[i].rate = data[i].rate;
				data[i].settlementRate = data[i].settlementRate;
				data[i].incrementalRate = data[i].incrementalRate;
				data[i].cost = data[i].cost;
				data[i].price = data[i].price;
				data[i].calculatedCost = data[i].calculatedCost;
				data[i].weighting = data[i].weighting;
				data[i].committed = data[i].committed;
				data[i].currency = data[i].currency;
				data[i].validFrom = data[i].validFrom;
				data[i].validTo = data[i].validTo;
      }
      $scope.gridOptions.data = data;
  });

});

'use strict';

SignatureApp.controller('MakeADealController',function($rootScope, $scope, $http, dataService) {

	// Log active controller
	Main.log("MakeADealController");

	// Populate Counterpart dropdown
	dataService.getCarrierList(function(carrierListResponse) {
		$scope.carriers = carrierListResponse.data.list;
	});
	
	$scope.rangeDate = {
        startDate: moment().subtract(1, "days"),
        endDate: moment()
    };
    $scope.singleDate = moment();

    $scope.opts = {
        locale: {
            applyClass: 'btn-green',
            applyLabel: "Apply",
            fromLabel: "From",
            format: "YYYY-MM-DD",
            toLabel: "To",
            cancelLabel: 'Cancel',
            customRangeLabel: 'Custom range'
        },
        ranges: {
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()]
        }
    };

    $scope.setStartDate = function () {
        $scope.rangeDate.startDate = moment().subtract(4, "days").toDate();
    };

    $scope.setRange = function () {
        $scope.rangeDate = {
            startDate: moment().subtract(5, "days"),
            endDate: moment()
        };
    };

    //Watch for date changes
    $scope.$watch('rangeDate', function(newDate) {	
        console.log('New date set: ', newDate);
    }, false);



	// Carrier selected
	$scope.carrierSelected = function(data) {

		// Set default Currency
		for (var i = 0; i < $scope.accountManagers.length ; i++) {

			// Find index of default Currency
			if ($scope.accountManagers[i].accountManagerId === data.defaultAccountManagerId) {

				// Deselect previous Currency (looping through all since we don't know the index of the selected item)
				for (var y = 0; y < $scope.accountManagers.length ; y++) {
					$scope.accountManagers[y].selected = false;
				}

				// Select Currency
				$scope.accountManagers[i].selected = true;
	    }
		}

		// Set default Currency
		for (var i = 0; i < $scope.currencies.length ; i++) {

			// Find index of default Account Manager
			if ($scope.currencies[i].currencyCode === data.defaultCurrency) {

				// Deselect previous Account Manager (looping through all since we don't know the index of the selected item)
				for (var y = 0; y < $scope.currencies.length ; y++) {
					$scope.currencies[y].selected = false;
				}

				// Select Account Manager
				$scope.currencies[i].selected = true;
	    }
		}

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

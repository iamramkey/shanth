'use strict';

angular.module('SignatureApp')

  .service('dataService', function($http) {

    this.getCarrierList = function(callback) {
      var data = JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode});
      $http.post(baseUrl + 'signcustomervendorlist', data).then(callback);
    };

    this.getAccountManagerList = function(callback) {
      var data = JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode});
      $http.post(baseUrl + 'accountmanagerlist', data).then(callback);
    };

    this.getCurrencyList = function(callback) {
      var data = JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode});
      $http.post(baseUrl + 'currencylist', data).then(callback);
    };

    this.getOutgoingDestinationList = function(callback) {
      var data = JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode});
      $http.post(baseUrl + 'signdestinationlist', data).then(callback);
    };

    this.getIncomingDestinationList = function(callback) {
      var data = JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode});
      $http.post(baseUrl + 'signdestinationlist', data).then(callback);
    };

    this.getDealTypeList = function(callback) {
      var data = JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode});
      $http.post(baseUrl + 'signdealtypelist', data).then(callback);
    };

    this.carrierSelected = function(callback) {
      //console.log(callback);
      console.log('This is the carrierSelected service!');
    };

  });

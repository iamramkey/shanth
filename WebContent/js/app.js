/***
Metronic AngularJS App Main Script
***/

/* Metronic App */
var SignatureApp = angular.module('SignatureApp', [
    'ui.router',
    'ui.bootstrap',
    'oc.lazyLoad',
    'ngSanitize',
    'ngTouch',
    'isteven-multi-select',
	'ngMessages',
    'daterangepicker',
    'ui.grid',
    'ui.grid.selection',
    'ui.grid.edit',
    'ui.grid.cellNav',
    'ui.grid.grouping'
]);

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
SignatureApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        // global configs go here
    });
}]);

/********************************************
 BEGIN: BREAKING CHANGE in AngularJS v1.3.x:
*********************************************/
/**
`$controller` will no longer look for controllers on `window`.
The old behavior of looking on `window` for controllers was originally intended
for use in examples, demos, and toy apps. We found that allowing global controller
functions encouraged poor practices, so we resolved to disable this behavior by
default.

To migrate, register your controllers with modules rather than exposing them
as globals:

Before:

```javascript
function MyController() {
  // ...
}
```

After:

```javascript
angular.module('myApp', []).controller('MyController', [function() {
  // ...
}]);

Although it's not recommended, you can re-enable the old behavior like this:

```javascript
angular.module('myModule').config(['$controllerProvider', function($controllerProvider) {
  // this option might be handy for migrating old apps, but please don't use it
  // in new ones!
  $controllerProvider.allowGlobals();
}]);
**/

//AngularJS v1.3.x workaround for old style controller declarition in HTML
SignatureApp.config(['$controllerProvider', function($controllerProvider) {
  // this option might be handy for migrating old apps, but please don't use it
  // in new ones!
  $controllerProvider.allowGlobals();
}]);

/********************************************
 END: BREAKING CHANGE in AngularJS v1.3.x:
*********************************************/

/* Setup global settings */
SignatureApp.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        layoutImgPath: Metronic.getAssetsPath() + 'admin/layout/img/',
        layoutCssPath: Metronic.getAssetsPath() + 'admin/layout/css/'
    };

    $rootScope.settings = settings;

    return settings;
}]);

/***
Layout Partials.
By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial
initialization can be disabled and Layout.init() should be called on page load complete as explained above.
***/

/* Setup Layout Part - Header */
SignatureApp.controller('HeaderController', ['$scope', function($scope) {
    $scope.qns = Main.fetchQNS();
    $scope.favs = Main.fetchFavs();
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
    });
}]);

/* Setup Layout Part - Sidebar */
SignatureApp.controller('SidebarController', ['$scope', function($scope) {
    $scope.toolbars = Main.fetchToolbars();
    $scope.$on('$includeContentLoaded', function() {
        Layout.initSidebar(); // init sidebar
    });
}]);

/* Setup Layout Part - Footer */
SignatureApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter(); // init footer
    });
}]);

/* Setup Rounting For All Pages */
SignatureApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

    $stateProvider
	    .state('notported', {
	        url: "/notported",
	        templateUrl: "views/dashboard.html",
	        data: {pageTitle: 'Not Ported'},
	        resolve: {}
	    })
        .state('home', {
            url: "/home",
            templateUrl: "views/dashboard.html",
            data: {pageTitle: 'Dashboard'},
            controller: "DashboardController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            // 'theme/assets/global/plugins/select2/select2.css',
                            'theme/assets/global/plugins/morris/morris.css',
                            'theme/assets/admin/pages/css/tasks.css',

                            // 'theme/assets/global/plugins/select2/select2.min.js',
                            'theme/assets/global/plugins/morris/morris.min.js',
                            'theme/assets/global/plugins/morris/raphael-min.js',
                            'theme/assets/global/plugins/jquery.sparkline.min.js',

                            'theme/assets/admin/pages/scripts/index3.js',
                            'theme/assets/admin/pages/scripts/tasks.js',
                            'js/chart/highcharts.js',
                            // 'js/chart/exporting.js',
                            'js/inputForDropdown.js',
                            'js/toolbar.js',
                            'js/controllers/DashboardController.js'
                        ]
                    });
                }]
            }
        })
        .state('dealdashboard', {
            url: "/dealdashboard",
            templateUrl: "views/dealdashboard.html",
            data: {pageTitle: 'Deal Dashboard'},
            controller: "DealdashboardController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            // 'theme/assets/global/plugins/select2/select2.css',
                            'theme/assets/global/plugins/morris/morris.css',
                            'theme/assets/admin/pages/css/tasks.css',

                            // 'theme/assets/global/plugins/select2/select2.min.js',
                            'theme/assets/global/plugins/morris/morris.min.js',
                            'theme/assets/global/plugins/morris/raphael-min.js',
                            'theme/assets/global/plugins/jquery.sparkline.min.js',

                            'theme/assets/admin/pages/scripts/index3.js',
                            'theme/assets/admin/pages/scripts/tasks.js',
                            'js/chart/highcharts.js',
                            // 'js/chart/exporting.js',
                            'js/inputForDropdown.js',
                            'js/controllers/DealdashboardController.js'
                        ]
                    });
                }]
            }
        })
        .state('countrydashboard', {
            url: "/countrydashboard",
            templateUrl: "views/countrydashboard.html",
            data: {pageTitle: 'Country Dashboard'},
            controller: "CountrydashboardController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            // 'theme/assets/global/plugins/select2/select2.css',
                            'theme/assets/global/plugins/morris/morris.css',
                            'theme/assets/admin/pages/css/tasks.css',

                            // 'theme/assets/global/plugins/select2/select2.min.js',
                            'theme/assets/global/plugins/morris/morris.min.js',
                            'theme/assets/global/plugins/morris/raphael-min.js',
                            'theme/assets/global/plugins/jquery.sparkline.min.js',

                            'theme/assets/admin/pages/scripts/index3.js',
                            'theme/assets/admin/pages/scripts/tasks.js',
                            'js/chart/highcharts.js',
                            // 'js/chart/exporting.js',
                            'js/inputForDropdown.js',
                            'js/controllers/CountrydashboardController.js',
                        ]
                    });
                }]
            }
        })
        .state('master',{
            url: "/master/:code",
            templateUrl: "views/master.html",
            data: {pageTitle: 'MASTER'},
            controller: "MasterController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            // 'theme/assets/global/plugins/select2/select2.css',
                            // 'theme/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css',
                            // 'theme/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
                            // 'theme/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css',
                            // 'theme/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css',

                            // 'theme/assets/global/plugins/select2/select2.min.js',
                            // 'theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
                            // 'theme/assets/global/plugins/datatables/all.min.js',
                            // 'js/scripts/table-advanced.js',
                            // 'theme/assets/admin/pages/scripts/components-pickers.js',

                             'js/controllers/MasterController.js',
                             'js/controllers/MastercustomController.js'
                        ]
                    });
                }]
            }
        })
        .state('monitorjob',{
            // url: "/monitorjob/:code/:id",
            url: "/monitorjob",
            templateUrl: "views/monitorjob.html",
            data: {pageTitle: 'Monitor Job'},
            controller: "MonitorjobController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            // "theme/assets/global/plugins/bootstrap-datepicker/css/datepicker3.css",
                            // "theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js",
                            // "theme/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css",
                            // "js/plugins/jquery.dataTables.columnFilter.js",
                            // "js/dataTable/TableTools.min.js",
                            // "js/dataTable/jquery.dataTables.min.js",
                            // "js/dataTable/dataTables.bootstrap.js",
                            'js/controllers/MonitorjobController.js'
                        ]
                    });
                }]
            }
        })
        .state('job',{
            url: "/job",
            templateUrl: "views/job.html",
            data: {pageTitle: 'Job'},
            controller: "JobController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/JobController.js'
                        ]
                    });
                }]
            }
        })
        .state('startupjob',{
            url: "/startupjob",
            templateUrl: "views/startupjob.html",
            data: {pageTitle: 'Startup Job'},
            controller: "StartupjobController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/StartupjobController.js'
                        ]
                    });
                }]
            }
        })
        .state('pendingjob',{
            url: "/pendingjob",
            templateUrl: "views/pendingjob.html",
            data: {pageTitle: 'Pending Job'},
            controller: "PendingjobController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/insertUpdateDBHelper.js',
                            'js/controllers/PendingjobController.js'
                        ]
                    });
                }]
            }
        })
        .state('auditfile',{
            url: "/auditfile",
            templateUrl: "views/auditfile.html",
            data: {pageTitle: 'Audit File'},
            controller: "AuditfileController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/AuditfileController.js'
                        ]
                    });
                }]
            }
        })
        .state('file',{
            url: "/file",
            templateUrl: "views/file.html",
            data: {pageTitle: 'File'},
            controller: "FileController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/FileController.js'
                        ]
                    });
                }]
            }
        })
        .state('alert',{
            url: "/alert",
            templateUrl: "views/alert.html",
            data: {pageTitle: 'Alert'},
            controller: "AlertController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/AlertController.js'
                        ]
                    });
                }]
            }
        })
        .state('makeadeal',{
            url: "/makeadeal",
            templateUrl: "views/makeadeal.html",
            data: {pageTitle: 'Make a Deal'},
            controller: "MakeADealController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/MakeADealController.js'
                        ]
                    });
                }]
            }
        })
        .state('changepassword',{
            url: "/changepassword",
            templateUrl: "views/changepassword.html",
            data: {pageTitle: 'change Password'},
            controller: "ChangepasswordController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            "js/lib/jquery.crypt.js",
                            'js/controllers/ChangepasswordController.js'
                        ]
                    });
                }]
            }
        })
        .state('profile',{
            url: "/profile",
            templateUrl: "views/profile.html",
            data: {pageTitle: 'Profile'},
            controller: "ProfileController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/ProfileController.js'
                        ]
                    });
                }]
            }
        })
        .state('logout',{
            url: "/logout",
            templateUrl: "logout.html",
            data: {pageTitle: 'Logout'},
           controller: "LogoutController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'SignatureApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
                        files: [
                            'js/controllers/LogoutController.js'
                        ]
                    });
                }]
            }
        })
		.state('lock',{
            url: "/lock",
            templateUrl: "views/lock.html",
            data: {pageTitle: 'Logout'},
        })
        ;
    // Redirect any unmatched url
    if(Main.isLogedIn())
        $urlRouterProvider.otherwise("/home");
    else
        $(location).attr('href','index.html');
}])
.filter('secondsToDateTime', [function() {
    return function(seconds) {
      var date = new Date(seconds);
      return date.getDate()+' '+date.getMonthName();
      // return date.getDayName()+' '+date.getDate()+' '+date.getMonthName()+' '+date.getFullYear();
    };
}]);
/* Init global settings and run the app */
SignatureApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
}]);
/* Setup App Main Controller */
SignatureApp.controller('AppController', function($rootScope, $scope, $http) {
     $scope.$on('$viewContentLoaded', function() {
        Metronic.initComponents(); // init core components
        //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive
    });
    if(localStorage.getItem("fullLoginObject")){
        $scope.usrDisplayname = JSON.parse(localStorage.getItem("fullLoginObject"))['userTbl']['usrDisplayName'];
        $scope.usrDisplayLetter = JSON.parse(localStorage.getItem("fullLoginObject"))['userTbl']['usrDisplayName'].charAt(0);
        $scope.header = JSON.parse(localStorage.getItem("fullLoginObject"))['header'];
    }
    $scope.logout = function(){
        $http({
            'method': 'POST',
            'url': baseUrl + 'logout',
            'headers': {'Content-Type': "application/json;"},
            'data': Main.getJsonInput({})
        }).then(function(result){
            console.log(result);
        }, function(error){
            console.log(error);
        }).finally(function(){
            localStorage.removeItem("fullLoginObject");
            $(location).attr('href','index.html');
        })

    }
});

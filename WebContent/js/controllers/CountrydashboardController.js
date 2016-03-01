'use strict';
SignatureApp.controller('CountrydashboardController', function($rootScope, $scope, $http,$timeout) {
	$scope.$on('$viewContentLoaded', function() {

		// initialize core components
		Metronic.initAjax();

		$('.select2').select2();

		$("#cd_table_latout").hide();
		$("#cd_table_layout_trend").hide();

	//variable necessory for the graph toggle
	var filmentchartInCategories, filmentchartOutCategories, filmentchartInSeries, filmentchartOutSeries;

	});

	// set sidebar closed and body solid layout mode
	$rootScope.settings.layout.pageBodySolid = true;
	$rootScope.settings.layout.pageSidebarClosed = false;

	(function() {
		$('#headerBreadcrumbContainer').html('Country Dashboard');
	})();

	// Filter variables
	var ProjectionListArray = [], FulfilmentFilter = 'Both';
	var topUncommittedListArray = [], primaryFilter = 'Both';
	var totalfillshortfalistArray = [], primaryFilter_fillshortfalist = 'Both', secondaryFilter_fillshortfalist = 'VOL' ;


	var urlParameters ='';
	urlParameters = decodeURI(window.location.hash).split('#/countrydashboard?')[1];
	if(urlParameters != '' && urlParameters){
		//FulfilmentFilter = 'Both'

		$("#cd_table_latout").hide();
		$("#cd_table_layout_trend").hide();

		var countryName = decodeURIComponent(urlParameters.split('=')[1]);
		console.log(countryName);

		$("#CommittedVoulmeChart_Chart").html('');
		$("#UncommittedChart").html('');
		$("#VolumeANDRevenueChart").html('');

		$("#dealDashboar-body").html('');
		$("#dealDashboar-body-1").html('');

		$("#pageTitleName").html('');
		$("#pageTitleName").html(countryName);
		$("#AgreeTable_Heading").text(countryName);

		$("#countryNameList").select2("val", null);
		toCallListApi('masterlist',JSON.stringify({prefix:'ctr',usrId:userId,ussSessionCode:ussSessionCode}),'countryNameList');
		setTimeout(function(){
			$("#countryNameList").select2("val", countryName);
		}, 1000);

		var inputForCharApi = {usrId:userId,ussSessionCode:ussSessionCode,countryName:countryName,currencyCode:"INR"};
			inputForCharApi = JSON.stringify(inputForCharApi);
			initFillDetails(inputForCharApi);
	}
	else {
		$("#cd_table_latout").hide();
		$("#cd_table_layout_trend").hide();

		toCallListApi('masterlist',JSON.stringify({prefix:'ctr',usrId:userId,ussSessionCode:ussSessionCode}),'countryNameList');
	}


	function initFillDetails(input){

		httpPost('cdbstatboard',input,toFillStatBoard,errorCallback);

		// //to fill Full Screen Active Deals and New Deals
		httpPost('cdbdealscount',input,fillActiveNewDeals,errorCallback);

		//to fill Full Screen Committed Volume and Volume Trend Deals
		httpPost('cdbvolumerevenue',input,fillVolumeTrend,errorCallback);
		httpPost('cdbmargin',input,fillCommittedVolume,errorCallback);

		//to fill Committed Volume Chart
		httpPost('cdbcommittedtraffichart',input,fillCommittedVoulmeChart,errorCallback);

		//to fill Uncommitted Traffic Chart
		httpPost('cdbuncommittedtraffichart',input,fillUncommittedChart,errorCallback);

		//to fill Volume And Revenue Chart
    	httpPost('cdbvolumeandrevenuechart',input,fillVolumeANDRevenueChart,errorCallback);

    	//to fill Full Top Uncommitted List
		httpPost('cdbtopuncommittedlist',input,fillTopuncommittedlist,errorCallback);

    	//to fill Ful Filment Projection List
    	httpPost('cdbfulfilmentprojectionlist',input,CountryfillProjectionlist,errorCallback);

		//to fill Full Fill Top Shortfall List
    	httpPost('cdbshortfallwarninglist',input,fillshortfalist,errorCallback);

    	//to fill Agreement Table
    	httpPost('cdbagreementsforcountry',input,fillAgreementTable,errorCallback);

	}

	// Change Country List
	$("#countryNameList").change(function(){
		window.location.href = '#/countrydashboard';
		$("#CommittedVoulmeChart_Chart").html('');
		$("#UncommittedChart").html('');
		$("#VolumeANDRevenueChart").html('');

		$("#dealDashboar-body").html('');
		$("#dealDashboar-body-1").html('');

		$("#pageTitleName").html('');
		$("#pageTitleName").html($('#countryNameList').val());
		$("#AgreeTable_Heading").text($('#countryNameList').val());

		var inputForCharApi = {usrId:userId,ussSessionCode:ussSessionCode,countryName:$('#countryNameList').val(),currencyCode:"INR"};
			inputForCharApi = JSON.stringify(inputForCharApi);
			initFillDetails(inputForCharApi);
	});
	
	$(document).on('click', '#committedChart_buttons', function(){
		$(window).resize();
	}).on('click', '#UncommittedChart_buttons', function(){
		$(window).resize();
	}).on('click', '#VolumeANDRevenueChart_buttons', function(){
		$(window).resize();
	});

	$(".cd_view_more_deal").click(function(){
		$('#Cd_First_Full_screen').click();
		if($("#Cd_First_Full_screen").addClass("fullscreen on")) {
			$("body").addClass("page-portlet-fullscreen");
			$("#cd_table_latout").show();
		}
	});
 
 	$(".cd_view_more_trend").click(function(){
 	// $(document).on("click",".view_more_trend", function(){
		$('#Cd_Second_Full_screen').click();
		if($("#Cd_Second_Full_screen").addClass("fullscreen on")) {
			$("body").addClass("page-portlet-fullscreen");
			$("#cd_table_layout_trend").show();
		}
	 }); 
	 
	$("#Cd_First_Full_screen").click(function(){
		$("#cd_table_latout").hide();
	});

	$("#Cd_Second_Full_screen").click(function(){
	    $("#cd_table_layout_trend").hide();
	});


	// To fill StatBoard 
	function toFillStatBoard(result){
		$('#activeDeals').html(result.activeDeals);
		$('#newDeals').html(result.newDeals);
		$('#marginTrend').html(result.marginTrend);
		$('#volumeTrend').html(result.volumeTrend);
	}

	// To fill Active And New Deals View More Screen 
	function fillActiveNewDeals(result){
		$('#dealDashboar-body').html('');
		$('#dealDashboar-body-1').html('');
		$('#Dealdetails_table_loading').hide();
		$('#dealDetails-tbl').show();
		for(var i = 0; i < result.newDealsList.length; i++){
			var row = '<tr style="font-weight:800;" class="expanding" onclick="redirectToDD_CD(\''+result.newDealsList[i].carrier+'\', \''+result.newDealsList[i].deal+'\')" ><td>'+result.newDealsList[i].carrier+'</td>';
			row += '<td>'+result.newDealsList[i].dealId+'</td>';
			row += '<td>'+result.newDealsList[i].validFrom+'</td>';
			row += '<td>'+result.newDealsList[i].validTo+'</td>';
			row += '<td>'+result.newDealsList[i].committedIn+'</td>';
			row += '<td>'+result.newDealsList[i].committedOut+'</td>';
			row += '<td>'+result.newDealsList[i].inVolume+'</td>';
			row += '<td>'+result.newDealsList[i].outVolume+'</td>';
			row += '<td>'+result.newDealsList[i].revenueIn+'</td>';
			row += '<td>'+result.newDealsList[i].revenueOut+'</td></tr>';
			$('#dealDashboar-body').append(row);
			}
		for(var i = 0; i < result.activeDealsList.length; i++){
			var row = '<tr style="font-weight:800;" class="expanding" onclick="redirectToDD_CD(\''+result.activeDealsList[i].carrier+'\', \''+result.activeDealsList[i].deal+'\')" ><td>'+result.activeDealsList[i].carrier+'</td>';
			row += '<td>'+result.activeDealsList[i].dealId+'</td>';
			row += '<td>'+result.activeDealsList[i].validFrom+'</td>';
			row += '<td>'+result.activeDealsList[i].validTo+'</td>';
			row += '<td>'+result.activeDealsList[i].committedIn+'</td>';
			row += '<td>'+result.activeDealsList[i].committedOut+'</td>';
			row += '<td>'+result.activeDealsList[i].inVolume+'</td>';
			row += '<td>'+result.activeDealsList[i].outVolume+'</td>';
			row += '<td>'+result.activeDealsList[i].revenueIn+'</td>';
			row += '<td>'+result.activeDealsList[i].revenueOut+'</td></tr>';
			$('#dealDashboar-body-1').append(row);
		}
	}

	//  To Fill Volume Trend View More Screen Begin 
	function fillVolumeTrend(result){
		// console.log(result);
		$("#dealDashboar-thead-volume").html('');
		$("#dealDashboar-body-volume").html('');
		$("#dealDashboar-thead-revenue").html('');
		$("#dealDashboar-body-revenue").html('');
		$("#dealDashboar-thead-margin").html(''); 
		$("#dealDashboar-body-margin").html(''); 
		
		$('#trend_table_loading').hide();
		$('#Trend_Details').show();
		var row ='', customHeaders = '';
		for(var x=0; x < result.headers.length; x++) {		
			row+= '<th>'+result.headers[x]+'</th>';

			if("Committed" == result.headers[x])	{
				customHeaders+= '<th class="hide_column">'+result.headers[x]+'</th>';
			}
			else {
				customHeaders+= '<th>'+result.headers[x]+'</th>';
			}	
		}
		$('#dealDashboar-thead-volume').append(row);
		$('#dealDashboar-thead-revenue').append(customHeaders);	

		for(var i = 0; i < result.dailyVolumesList.length; i++){
			var row_trend = '<tr style="font-weight:800;" class="expanding" ><td>'+result.dailyVolumesList[i].deal+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].direction+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].committed+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].total+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].date1+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].date2+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].date3+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].date4+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].date5+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].date6+'</td>';
			row_trend += '<td>'+result.dailyVolumesList[i].date7+'</td></tr>';
			$('#dealDashboar-body-volume').append(row_trend);
		}
		for(var i = 0; i < result.dailyRevenueList.length; i++){
			var row_trend = '<tr style="font-weight:800;" class="expanding" ><td>'+result.dailyRevenueList[i].deal+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].direction+'</td>';
			// row_trend += '<td>'+result.dailyRevenueList[i].committed+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].total+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].date1+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].date2+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].date3+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].date4+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].date5+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].date6+'</td>';
			row_trend += '<td>'+result.dailyRevenueList[i].date7+'</td></tr>';
			$('#dealDashboar-body-revenue').append(row_trend);
		}
	}

	//  To Fill Volume Trend View More Screen End  


	//  To Fill Committed Volume View More Screen Begin  
		
	function fillCommittedVolume(result){
		$('#trend_table_loading').hide();
		$('#Trend_Details').show();
		for(var x=0; x < result.headers.length; x++) {		
			var row = '<th>'+result.headers[x]+'</th>';			
			$('#dealDashboar-thead-margin').append(row);			
		}
		for(var i = 0; i < result.marginList.length; i++){
			var row_trend = '<tr style="font-weight:800;" class="expanding" ><td>'+result.marginList[i].deal+'</td>';
			row_trend += '<td>'+result.marginList[i].direction+'</td>';
			row_trend += '<td>'+result.marginList[i].total+'</td>';
			row_trend += '<td>'+result.marginList[i].date1+'</td>';
			row_trend += '<td>'+result.marginList[i].date2+'</td>';
			row_trend += '<td>'+result.marginList[i].date3+'</td>';
			row_trend += '<td>'+result.marginList[i].date4+'</td>';
			row_trend += '<td>'+result.marginList[i].date5+'</td>';
			row_trend += '<td>'+result.marginList[i].date6+'</td>';
			row_trend += '<td>'+result.marginList[i].date7+'</td></tr>';
			$('#dealDashboar-body-margin').append(row_trend);
		}
	}

	//  To Fill Committed Volume View More Screen End


	//Fullfilment Projection Begin 

	$(".projection_list_btn").click(function(e) {
		if($('#Projectionlist_in').is(':checked'))
			FulfilmentFilter = 'IN';
		if($('#Projectionlist_out').is(':checked'))
			FulfilmentFilter = 'OUT';
		if($('#Projectionlist_in').is(':checked') && $('#Projectionlist_out').is(':checked'))
			FulfilmentFilter = 'Both';
		if(!$('#Projectionlist_in').is(':checked') && !$('#Projectionlist_out').is(':checked'))
			FulfilmentFilter = 'Both';
		toFillProjectionList();
	});

	function CountryfillProjectionlist(result) {
		ProjectionListArray = result.projectionDataList;
		toFillProjectionList();
	}

	function toFillProjectionList(){
		$('#Fulfilment_loading').hide();
	    $('#FulfilmentContainer').html('');
	    var FillProjectionList = '<ul class="feeds">';
	    for(var i = 0; i < ProjectionListArray.length; i++){
			if(FulfilmentFilter == ProjectionListArray[i].direction || FulfilmentFilter == 'Both'){
				FillProjectionList += '<li class="deal_dashboard" data-account="'+ProjectionListArray[i].account+'" data-deal="'+ProjectionListArray[i].deal+'">';
				FillProjectionList += '<div class="col1 cd_toolTip" data-toggle="popover" data-placement="bottom" data-trigger="hover" data-content="'+ProjectionListArray[i].data+'"><div class="cont"><div class="cont-col1">';
				FillProjectionList += '<div class="label label-sm label-info" style="background-color: '+ProjectionListArray[i].color+';"><i class="'+ProjectionListArray[i].icon+'"></i></div></div>';
				FillProjectionList += '<div class="cont-col2"><div class="desc" style="color:'+ProjectionListArray[i].labelColor+'">'+ProjectionListArray[i].data;
				FillProjectionList += '</span></div></div></div></div>';
				FillProjectionList += '<div class="col2"><div class="date">'+ProjectionListArray[i].period+'</div></div>';
				FillProjectionList += '</li>';
	    	}
	    }
	    FillProjectionList += '</ul>';
		$('#FulfilmentContainer').html(FillProjectionList);
	}

	//Fullfilment Projection End 


	// Top Uncommitted Begin

	$(".TopuncommittedOptions").click(function(e) {
		if($('#TopuncommittedOptions_in').is(':checked'))
			primaryFilter = 'In';
		if($('#TopuncommittedOptions_out').is(':checked'))
			primaryFilter = 'Out';
		if($('#TopuncommittedOptions_in').is(':checked') && $('#TopuncommittedOptions_out').is(':checked'))
			primaryFilter = 'Both';
		if(!$('#TopuncommittedOptions_in').is(':checked') && !$('#TopuncommittedOptions_out').is(':checked'))
			primaryFilter = 'Both';
		toFillFilteredDesList();
	});

	

	function fillTopuncommittedlist(result) {
		topUncommittedListArray = result.topUncommittedDataList;
		toFillFilteredDesList();
	}

	function toFillFilteredDesList(){
		$('#Topuncommitted_loading').hide();
	    $('#TopuncommittedListContainer').html('');
	    var topUncommittedList = '<ul class="feeds">';
	    for(var i = 0; i < topUncommittedListArray.length; i++){
	    		if(primaryFilter == topUncommittedListArray[i].primaryFilter || primaryFilter == 'Both'){
	    			topUncommittedList += '<li>';
					topUncommittedList += '<div class="col1 cd_toolTip" data-toggle="popover" data-placement="bottom" data-trigger="hover" data-content="'+topUncommittedListArray[i].data+'"><div class="cont"><div class="cont-col1">';
					topUncommittedList += '<div class="label label-sm label-info" style="background-color: '+topUncommittedListArray[i].color+';"><i class="'+topUncommittedListArray[i].icon+'"></i></div></div>';
					topUncommittedList += '<div class="cont-col2"><div class="desc" style="color:'+topUncommittedListArray[i].labelColor+'">'+topUncommittedListArray[i].data;
					topUncommittedList += '</span></div></div></div></div>';
					topUncommittedList += '<div class="col2"><div class="date">'+topUncommittedListArray[i].period+'</div></div>';
					topUncommittedList += '</li>';
	    		
	    	}
	    }
	    topUncommittedList += '</ul>';
		$('#TopuncommittedListContainer').html(topUncommittedList);
	}

	// Top Uncommitted End


	$(".shortfall").click(function(e) {
		secondaryFilter_fillshortfalist = $(this).attr("data-value");
		toFillShortFaList();
	});

	$(".ShortfallwarningOptions").click(function(e) {
		if($('#ShortfallwarningOptions_in').is(':checked'))
			primaryFilter_fillshortfalist = 'In';
		if($('#ShortfallwarningOptions_out').is(':checked'))
			primaryFilter_fillshortfalist = 'Out';
		if($('#ShortfallwarningOptions_in').is(':checked') && $('#ShortfallwarningOptions_out').is(':checked'))
			primaryFilter_fillshortfalist = 'Both';
		if(!$('#ShortfallwarningOptions_in').is(':checked') && !$('#ShortfallwarningOptions_out').is(':checked'))
			primaryFilter_fillshortfalist = 'Both';
		toFillShortFaList();
	});

	
	function fillshortfalist(result) {
	    totalfillshortfalistArray = result.shortfallWarningDataList;
	    toFillShortFaList();
	}

	function toFillShortFaList(){

	    $('#shortfall_loading').hide();
		$("#inSeriesShort").show();
	    $('#shortFallListContainer').html('');
		var inSeriesShortPer = '<ul class="feeds">';
		for(var i = 0; i < totalfillshortfalistArray.length; i++){
			if(secondaryFilter_fillshortfalist == totalfillshortfalistArray[i].secondaryFilter){
				if(primaryFilter_fillshortfalist == totalfillshortfalistArray[i].primaryFilter || primaryFilter_fillshortfalist == 'Both'){
					inSeriesShortPer += '<li>';
					inSeriesShortPer += '<div class="col1 cd_toolTip" data-toggle="popover" data-placement="bottom" data-trigger="hover" data-content="'+totalfillshortfalistArray[i].data+'"><div class="cont"><div class="cont-col1">';
					inSeriesShortPer += '<div class="label label-sm label-info" style="background-color: '+totalfillshortfalistArray[i].color+';"><i class="'+totalfillshortfalistArray[i].icon+'"></i></div></div>';
					inSeriesShortPer += '<div class="cont-col2"><div class="desc" style="color:'+totalfillshortfalistArray[i].labelColor+'">'+totalfillshortfalistArray[i].data;
					inSeriesShortPer += '</span><span class="label label-sm label-info" style="margin-left:10px;">'+totalfillshortfalistArray[i].subData+'</span></div></div></div></div>';
					inSeriesShortPer += '<div class="col2"><div class="date">'+totalfillshortfalistArray[i].period+'</div></div>';
					inSeriesShortPer += '</li>'; 
				}
			}
		}
		inSeriesShortPer += '</ul>';
		$('#shortFallListContainer').append(inSeriesShortPer);
	}


	// To Fill Agreement Detail Table 

	function fillAgreementTable(result){
		$('#Agreements_table_loading').hide();
		Main.fillTreeTable('Agreements_Table_Body', result.volumeDetailsList, 'volumeDetailsList', 'dealDetailParent');
	}



	

	function errorCallback(error){
		console.log(error);
	}

	// Graph Begin 
	function fillVolumeANDRevenueChart(result){
		plotVolumeANDRevenueChart(result.categories,result.volumeSeries,result.revenueSeries);
	// Req_chart(result.categories,result.volumeSeries,result.revenueSeries);
	}

	function plotVolumeANDRevenueChart(categories,volumeSeries,revenueSeries){
		$('#VolumeANDRevenueChart_loading').hide();
		$('#VolumeANDRevenueChart_content').show();
		$('#VolumeANDRevenueChart').highcharts({
		credits: {
			enabled: false
		},
		exporting: {
			buttons: {
				contextButtons: {
					enabled: false,
					menuItems: null
				}
			},
			enabled: false
		},

        chart: {
        	backgroundColor: 'rgba(0,0,0,0)',
            zoomType: 'xy'
        },
        title: {
            text: ' '
        },
        xAxis: [{
			categories: categories,
            crosshair: true,
			lineWidth: 0,
			tickLength: 5,
			tickColor: '#D8D8D8',
            color: '#D8D8D8'
        }],
        yAxis: [{ // Primary yAxis
            labels: {
				style: {
					color: '#555559'
				}
            },
            title: {
                text: 'Min',
                align: 'middle',
				style: {
					color: '#555559'
				}
            }
        }, { // Secondary yAxis
            title: {
                text: '€',
                align: 'middle',
				style: {
					color: '#555559'
				}
            },
            labels: {
				style: {
					color: '#555559'
				}
            },
            min: '0',
            opposite: true
        }],
        tooltip: {
            shared: true
        },
         plotOptions: {
            area: {
                fillOpacity: 0.01
            }
        },

		series: [{
            name: 'Vol In',
            type: 'column',
            pointPadding: 0.01,
            yAxis: 0,
            color: '#67809F',
            data: volumeSeries[0].data,
            tooltip: {
                valueSuffix: ' min'
            }

        },{
            name: 'Vol Out',
            type: 'column',
            pointPadding: 0.01,
            yAxis: 0,
            color: '#2C3E50',
            data: volumeSeries[1].data,
            tooltip: {
                valueSuffix: ' min'
            }

        }, {

			name: 'Rev In',
			yAxis: 1,
            data: revenueSeries[0].data,
            type: 'spline',
            color: '#7771A9',
            tooltip: {
                valueSuffix: ' €'
            }
        },{
            name: 'Rev Out',
            yAxis: 1,
            data: revenueSeries[1].data,
            type: 'spline',
            color: '#343256',
            tooltip: {
                valueSuffix: ' €'
            }
        }]
    });

	}

	function Req_chart(categories,volumeSeries,revenueSeries){
		$('#Req_Vol_Chart_loading').hide();
		$('#Req_Vol_content').show();
			$('#Req_Vol_Chart').highcharts({
			exporting: {
				buttons: {
					contextButtons: {
						enabled: false,
						menuItems: null
					}
				},
				enabled: false
			},
			credits: {
				enabled: false
			},
			chart: {
				backgroundColor: '#F7F7F7',
				zoomType: 'xy'
			},
			title: {
				text: ' '
			},
			xAxis: [{
				color: '#555559',
				categories: categories,	
				crosshair: true
			}],
			yAxis: [{ // Primary yAxis
				labels: {
					format: '{value}min',
					style: {
						color: '#555559'
					}
				},
				title: {
					text: 'Min(M)',
					align: 'high',
					style: {
						color: '#555559'
					}
				}
			}, { // Secondary yAxis
				title: {
					text: '€(k)',
					align: 'middle',
					style: {
						color: '#555559'
					}
				},
				labels: {
					format: '{value} k',
					style: {
						color: Highcharts.getOptions().colors[1]
					}
				},
				opposite: true
			}],
			tooltip: {
				shared: true
			},
			 plotOptions: {
				area: {
					fillOpacity: 0.01
				}
			},
			
			series: [{
				name: 'Volume-IN',
				type: 'column',
				pointPadding: 0.01,
				yAxis: 1,
				data: volumeSeries[0].data,
				tooltip: {
					valueSuffix: ' min'
				}

			},{
				name: 'Volume-OUT',
				type: 'column',
				pointPadding: 0.01,
				yAxis: 1,
				data: volumeSeries[1].data,
				tooltip: {
					valueSuffix: ' min'
				}

			}, {
				
				name: 'Revenue-IN',
				data: revenueSeries[0].data,
				type: 'area',
				tooltip: {
					valueSuffix: 'k'
				}
			},{
				name: 'Revenue-OUT',
				data: revenueSeries[1].data,
				type: 'area',
				tooltip: {
					valueSuffix: 'k'
				}
			}]
		});
			
	}

// Committed Volume Chart

var inVolumeSeries, outVolumeSeries, categoriesCommitted;

function fillCommittedVoulmeChart(result){
	inVolumeSeries = [], outVolumeSeries = []
	for(var i = 0; i < result.inVolumeSeries.length; i++){
		inVolumeSeries.push({
			'name': result.inVolumeSeries[i].name,
			'data': result.inVolumeSeries[i].data,
			tooltip: {
	                valueSuffix: ' Min'
	            }
		});
	}
	for(var i = 0; i < result.outVolumeSeries.length; i++){
		outVolumeSeries.push({
			'name': result.outVolumeSeries[i].name,
			'data': result.outVolumeSeries[i].data,
			tooltip: {
	                valueSuffix: ' Min'
	            }
		});
	}
	categoriesCommitted = result.categories;
	fillGraphforCommittedVolumeChart(categoriesCommitted,inVolumeSeries);
	// console.log(inVolumeSeries,outVolumeSeries);
}


$(document).on('click','#committedChart_buttons',function(){
	if($('#committedChart_IN').attr('checked')) {
		fillGraphforCommittedVolumeChart(categoriesCommitted,inVolumeSeries);
	}
	if($('#committedChart_OUT').attr('checked')) {
		fillGraphforCommittedVolumeChart(categoriesCommitted,outVolumeSeries);
	}
		
});

function fillGraphforCommittedVolumeChart(categoriesCommitted,seriesVolumeArray){
	
	$('#CommittedVoulmeChart_loading').hide();
	$('#CommittedVoulmeChart_content').show();
	$('#CommittedVoulmeChart_Chart').html('');
	$('#CommittedVoulmeChart_Chart').highcharts({
  //       chart: {
  //           type: 'column',
  //           backgroundColor: '#F7F7F7'
  //       },
  //       title: {
  //           text: ''
  //       },
  //       exporting: {
		// 	buttons: {
		// 		contextButtons: {
		// 			enabled: false,
		// 			menuItems: null
		// 		}
		// 	},
		// 	enabled: false
		// },
  //       credits: {
		//       enabled: false
		// },
  //       xAxis: {
  //           categories: categoriesCommitted
  //       },
  //       yAxis: {
  //           min: 0,
  //           title: {
  //               text: 'Min'
  //           }
  //       },
  //       legend: {
  //           reversed: true
  //       },
  //       plotOptions: {
  //           series: {
  //               stacking: 'normal'
  //           }
  //       },
  //       series: seriesVolumeArray
  //   });

	chart: {
            type: 'column',
            backgroundColor:'#F1F4F7' 
        },
         credits: {
		    enabled: false
		},
        title: {
            text: ' '
        },
        xAxis: {
            categories: categoriesCommitted
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Mins'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'center',
            x: 0,
            verticalAlign: 'bottom',
            y: 0,
            floating: false,
            backgroundColor: '#F1F4F7',
            borderColor: '#F1F4F7',
            borderWidth: 0,
            shadow: false
        },
        tooltip: {
            headerFormat: '<b>{point.x}</b><br/>',
            pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || '#F1F4F7',
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },
        series: seriesVolumeArray
	});

}

var inSeriesArray, outSeriesArray, allSeriesArray;
function fillUncommittedChart(result){
	inSeriesArray = [], outSeriesArray = [], allSeriesArray = [];
	for(var i = 0; i < result.inUncommittedTrafficChartList.length; i++){
		inSeriesArray.push({
			'type': 'areaspline',
			'name': result.inUncommittedTrafficChartList[i].destination,
			'data': result.inUncommittedTrafficChartList[i].objectArray
		});
	}
	for(var i = 0; i < result.outUncommittedTrafficChartList.length; i++){
		outSeriesArray.push({
			'type': 'areaspline',
			'name': result.outUncommittedTrafficChartList[i].destination,
			'data': result.outUncommittedTrafficChartList[i].objectArray
		});
	}

	for(var j = 0; j < result.inUncommittedTrafficChartList.length; j++){
		allSeriesArray.push({
			'type': 'areaspline',
			'name': result.inUncommittedTrafficChartList[j].destination,
			'data': result.inUncommittedTrafficChartList[j].objectArray
		});
	}
	for(var j = 0; j < result.outUncommittedTrafficChartList.length; j++){
		allSeriesArray.push({
			'type': 'areaspline',
			'name': result.outUncommittedTrafficChartList[j].destination,
			'data': result.outUncommittedTrafficChartList[j].objectArray
		});
	}

	fillUncommittedChart_graph(allSeriesArray);
}

$(".UncommittedTrafficOptions").click(function(e) {
	if($('#UncommittedChart_IN').is(':checked')) {
		$('#UncommittedChart').html('');
		fillUncommittedChart_graph(inSeriesArray);
	}
	if($('#UncommittedChart_OUT').is(':checked')) {
		$('#UncommittedChart').html('');
		fillUncommittedChart_graph(outSeriesArray);
	}
	if($('#UncommittedChart_IN').is(':checked') && $('#UncommittedChart_OUT').is(':checked')) {
		$('#UncommittedChart').html('');
		fillUncommittedChart_graph(allSeriesArray);
	}
	if(!$('#UncommittedChart_IN').is(':checked') && !$('#UncommittedChart_OUT').is(':checked')) {
		$('#UncommittedChart').html('');
		fillUncommittedChart_graph(allSeriesArray);
	}
		
});

function fillUncommittedChart_graph(seriesArray){
	$('#UncommittedChart').html('');
	$('#UncommittedChart_loading').hide();
	$('#UncommittedChart_content').show();
	$('#UncommittedChart').highcharts({
			exporting: {
				buttons: {
					contextButtons: {
						enabled: false,
						menuItems: null
					}
				},
				enabled: false
			},

			credits: {
				enabled: false
			},
            chart: {
            	backgroundColor: 'rgba(0,0,0,0)',
                zoomType: 'x',
                resetZoomButton: {
	                theme: {
	                    fill: '#FFFFFF',
	                    stroke: '#CCC',
	                    r: 4,
	                    style: {
	                        color: '#666'
	                            },
	                    states: {
	                        hover: {
	                            fill: '#E6E6E6',
	                            stroke: '#ADADAD',
	                            style: {
	                                color: '#666'
	                            }
	                        }
	                    }
	                }
            	}
            },
            title: {
                text: ' '
            },
            subtitle: {
            },
            xAxis: {
                type: 'datetime',
                lineWidth: 0,
				tickLength: 5,
				tickColor: '#D8D8D8'
            },
            yAxis: {

                title: {
                    text: 'Min',
                     align:'middle',
                     style: {
					 	color: '#555559'
					 }
                },

                labels: {
                	enabled: true
                }

            },
            legend: {
                enabled: true
            },
            plotOptions: {
				areaspline: {
					stacking: 'normal'
				}
            },

            series: seriesArray
        });
}


	function vol_Proj_Chart_graph(data,data1){
		$('#Vol_proj_Chart_loading').hide();
		$('#Vol_proj_Chart_content').show();
		$('#Vol_proj_Chart').highcharts({
			exporting: {
				buttons: {
					contextButtons: {
						enabled: false,
						menuItems: null
					}
				},
				enabled: false
			},
			
			credits: {
				enabled: false
			},
            chart: {
            	backgroundColor: '#F7F7F7',
                zoomType: 'x'
            },
            title: {
                text: ' '
            },
            subtitle: {
                // text: document.ontouchstart === undefined ?
                        // 'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                title: {
                    text: 'Min (M)',
                     align:'high'
                }
            },
            legend: {
                enabled: false
            },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: {
                        radius: 2
                    },
                    lineWidth: 1,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    },
                    threshold: null
                }
            },

            series: [{
                type: 'area',
                name: 'Deal',
                data: data
            },{
                type: 'area',
                name: 'Non Deal',
                data: data1
            }]
        });
	}

//  Graph End

$(document).on("mouseover",".cd_toolTip",function(){
	$('[data-toggle="popover"]').popover(); 
});

});


//extra function 
function redirectToDD_CD(carrier, deal){
	window.location.href = '#/dealdashboard?accNameList='+carrier+'_&_dealIdList='+deal;
	$("#Cd_First_Full_screen").click();
	// $("#Cd_First_Full_screen").removeClass("on");
}

function dealDetailParent(className){
    if($('.'+className).parent('#Agreements_Table_Body').find('.'+className).is(":visible"))
        $('.'+className).parent('#Agreements_Table_Body').find('.'+className).hide();
    else
        $('.'+className).parent('#Agreements_Table_Body').find('.'+className).show();
}

'use strict';
ThrazeApp.controller('CountrydashboardController', function($rootScope, $scope, $http,$timeout) {
	$scope.$on('$viewContentLoaded', function() {
		// initialize core components
		Metronic.initAjax();

		$('.select2').select2();

	//variable necessory for the graph toggle
	var filmentchartInCategories, filmentchartOutCategories, filmentchartInSeries, filmentchartOutSeries;
	toCallListApi('masterlist',JSON.stringify({prefix:'ctr',usrId:userId,ussSessionCode:ussSessionCode}),'countryNameList');

	});

	// set sidebar closed and body solid layout mode
	$rootScope.settings.layout.pageBodySolid = true;
	$rootScope.settings.layout.pageSidebarClosed = false;

	(function() {
		$('#headerBreadcrumbContainer').html('Country Dashboard');
	})();

	// Change Country List
	$(document).on("change","#countryNameList",function(){

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

	function initFillDetails(input){

		httpPost('cdbstatboard',input,toFillStatBoard,errorCallback);

		//to fill Full Screen Active Deals and New Deals
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

	$(document).on("click",".view_more_deal", function(){
		$('#Full_screen').click();
		if($("#Full_screen").addClass("fullscreen on")) {
			$("body").addClass("page-portlet-fullscreen");
			$("#table_latout").show();
		}
	});
 
 	$(document).on("click",".view_more_trend", function(){
		$('#Full_screen_Trend').click();
		if($("#Full_screen_Trend").addClass("fullscreen on")) {
			$("body").addClass("page-portlet-fullscreen");
			$("#table_layout_trend").show();
		}
	 }); 
	 
	$(document).on("click","#Full_screen", function(){
		$("#table_latout").hide();
	});

	$(document).on("click","#Full_screen_Trend", function(){
	    $("#table_layout_trend").hide();
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
		$('#Dealdetails_table_loading').hide();
		$('#dealDetails-tbl').show();
		for(var i = 0; i < result.newDealsList.length; i++){
			var row = '<tr style="font-weight:800;" class="expanding" ><td>'+result.newDealsList[i].carrier+'</td>';
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
			var row = '<tr style="font-weight:800;" class="expanding" ><td>'+result.activeDealsList[i].carrier+'</td>';
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
		$('#trend_table_loading').hide();
		$('#Trend_Details').show();
		for(var x=0; x < result.headers.length; x++) {		
			var row = '<th>'+result.headers[x]+'</th>';			
			$('#dealDashboar-thead-volume').append(row);
			$('#dealDashboar-thead-revenue').append(row);				
		}
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
			row_trend += '<td>'+result.dailyRevenueList[i].committed+'</td>';
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

	var ProjectionListArray = [], FulfilmentFilter = 'Both';

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
				FillProjectionList += '<div class="col1"><div class="cont"><div class="cont-col1">';
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

	var topUncommittedListArray = [], primaryFilter = 'Both';

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
	    			topUncommittedList += '<li class="deal_dashboard" data-account="'+topUncommittedListArray[i].account+'" data-deal="'+topUncommittedListArray[i].deal+'">';
					topUncommittedList += '<div class="col1"><div class="cont"><div class="cont-col1">';
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

	var totalfillshortfalistArray = [], primaryFilter_fillshortfalist = 'Both', secondaryFilter_fillshortfalist = 'VOL' ;
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
					inSeriesShortPer += '<div class="col1"><div class="cont"><div class="cont-col1">';
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
	    //console.log(result);
		$('#Agreements_table_loading').hide();
	    $('#Agreements_Table_Body').html('');

	        for(var i = 0; i < result.volumeDetailsList.length; i++){

	        var row = '<tr style="font-weight:800;" class="expanding" onclick="dealDetailParent(\''+result.volumeDetailsList[i].destinationGroup.replace(/ /g , '')+'\')"><td>'+result.volumeDetailsList[i].destinationGroup+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].direction+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].startDate+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].endDate+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].committed+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].totalVolume+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].trend+'</td>';
				row += '<td style="background:'+result.volumeDetailsList[i].blockColor+'">'+result.volumeDetailsList[i].required+'</td></tr>';
	            $('#Agreements_Table_Body').append(row);
	            if(result.volumeDetailsList[i].volumeDetailsList){
	            	for(var j = 0; j < result.volumeDetailsList[i].volumeDetailsList.length; j++){
			            var row2 = '<tr style="display:none;" class="'+result.volumeDetailsList[i].destinationGroup.replace(/ /g , '')+'"><td>- '+result.volumeDetailsList[i].volumeDetailsList[j].destinationGroup+'</td>';
			            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].direction+'</td>';
			            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].startDate+'</td>';
			            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].endDate+'</td>';
			            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].committed+'</td>';
			            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].totalVolume+'</td>';
			            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].trend+'</td>';
			            row2 += '<td style="background:'+result.volumeDetailsList[i].volumeDetailsList[j].blockColor+'">'+result.volumeDetailsList[i].volumeDetailsList[j].required+'</td></tr>';
			            $('#Agreements_Table_Body').append(row2);
			        }
	            }
	        
	    }
	    
	}

	function dealDetailParent(className){
	    // console.log(className);
	    if($('.'+className).parent().find('.'+className).is(":visible"))
	        $('.'+className).parent().find('.'+className).hide();
	    else
	        $('.'+className).parent().find('.'+className).show();
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
        chart: {
            type: 'column',
            backgroundColor: '#F7F7F7'
        },
        title: {
            text: ''
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
        credits: {
		      enabled: false
		},
        xAxis: {
            categories: categoriesCommitted
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Min'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            }
        },
        series: seriesVolumeArray
    });
}


var inSeriesArray, outSeriesArray;
function fillUncommittedChart(result){
	inSeriesArray = [], outSeriesArray = [];
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
	fillUncommittedChart_graph(inSeriesArray);
}

$(document).on('click','#UncommittedChart_buttons',function(){
	if($('#UncommittedChart_IN').attr('checked'))
		fillUncommittedChart_graph(inSeriesArray);
	if($('#UncommittedChart_OUT').attr('checked'))
		fillUncommittedChart_graph(outSeriesArray);
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




});

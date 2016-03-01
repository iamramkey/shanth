'use strict';
var filmentchartInCategories,filmentchartInSeries,filmentchartOutCategories,filmentchartOutSeries;
SignatureApp.controller('DealdashboardController', function($rootScope, $scope, $http,$timeout) {
	$scope.$on('$viewContentLoaded', function() {
		// initialize core components
		Metronic.initAjax();

		$('.select2').select2();

		//variable necessory for the graph toggle
		var filmentchartInCategories, filmentchartOutCategories, filmentchartInSeries, filmentchartOutSeries;
		toCallListApi('customervendorlist',JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode}),'accNameList');
	});

	(function() {
		$('#headerBreadcrumbContainer').html('Deal Dashboard');
	})();

	function initSelectbox(input) {
		httpPost('ddbstatboard',input,toFillStatBoard,errorCallback);
	}

	$("#bd_table_latout").hide();
	$("#bd_table_layout_trend").hide();

	$("#accNameList").change(function(){
		$("#dealIdList").select2("val", null);
		$("#dealIdList").html('');
		toCallListApi('deallist',JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode,accName:$('#accNameList').val()}),'dealIdList');
	});

	$("#dealIdList").change(function(){
		var inputForCharApi = {usrId:userId,ussSessionCode:ussSessionCode,deal:$('#dealIdList').val(),currencyCode:"INR"};
		inputForCharApi = JSON.stringify(inputForCharApi);
		initFillDetails(inputForCharApi);
	});

	var fillProjectionListArray = [], FulProjectionFilter = 'Both';

	var urlParameters ='';
	urlParameters = decodeURI(window.location.hash).split('#/dealdashboard?')[1];
	if(urlParameters != '' && urlParameters){
		var accNameList = decodeURIComponent(urlParameters.split('_&_')[0].split('accNameList=')[1]);
		var dealIdList = decodeURIComponent(urlParameters.split('_&_')[1].split('dealIdList=')[1]);
		$("#accNameList").select2("val", null);
		$("#dealIdList").select2("val", null);
		$("#dealIdList").html('');
		toCallListApi('deallist',JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode,accName:accNameList}),'dealIdList');
		setTimeout(function(){
			$("#accNameList").select2("val", accNameList);
			$("#dealIdList").select2("val", dealIdList);
		}, 1000);
		var inputForCharApi = {usrId:userId,ussSessionCode:ussSessionCode,deal:dealIdList,currencyCode:"INR"};
		inputForCharApi = JSON.stringify(inputForCharApi);
		//initSelectbox(inputForCharApi);
		initFillDetails(inputForCharApi);
	}
	else {
		window.location.href = '#/dealdashboard';
		toCallListApi('customervendorlist',JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode}),'accNameList');
	}

	function initFillDetails(input){

		httpPost('ddbstatboard',input,toFillStatBoard,errorCallback);

		//to fill Full Screen Margin Details
		httpPost('ddbmargindetails',input,fillTrendMarginDetails,errorCallback);

		//to fill Full Screen Volume Details
		httpPost('ddbvolumedetails',input,fillVolumeDetails,errorCallback);

		//to fill Volume And Revenue Chart
		httpPost('ddbvolumeandrevenuechart',input,fillVolumeANDRevenueChart,errorCallback);

		//to fill Volume And Fulfilment Chart
		httpPost('ddbfulfilmentchart',input,fillFulfilmentChart,errorCallback);

		//to fill Volume And Margintrend Chart
		httpPost('ddbmargintrendchart',input,fillMarginTrendChart,errorCallback);

		//to fill Volume And Fulfilment Projection List
		httpPost('ddbfulfilmentprojectionlist',input,fillProjectionlist,errorCallback);

		//to fill deal Details Table
		httpPost('ddbdealdetails',input,fillDealDetails,errorCallback);
	}

	$(".bd_view_more_deal").click(function(){
		$('#Bd_Full_screen').click();
		if($("#Bd_Full_screen").addClass("fullscreen on")) {
			$("body").addClass("page-portlet-fullscreen");
			$("#bd_table_latout").show();
		}
	 });

	$(".bd_view_more_trend").click(function(){
		$('#Bd_Full_screen_Trend').click();
		if($("#Bd_Full_screen_Trend").addClass("fullscreen on")) {
			$("body").addClass("page-portlet-fullscreen");
			$("#bd_table_layout_trend").show();
		}
	 });

	$(document).on('click',"#Bd_Full_screen", function(){
		$("#bd_table_latout").hide();
	});

	$(document).on('click',"#Bd_Full_screen_Trend", function(){
	    $("#bd_table_layout_trend").hide();
	});

	$("#deal_details_fullscreen").click(function(){
		$('#dealDetailsTableBody').attr('style', 'height: auto');
		if($("#deal_details_fullscreen").hasClass("fullscreen on")) {
			$('#dealDetailsTableBody').attr('style', 'height: auto');
		}
		else {
			$("#dealDetailsTableBody").css("height","90vh");
			$("#dealDetails-tbl_deal").addClass("scroller");
			$("#dealDetails-tbl_deal").find(".scroller").css("height","90vh")
		}
		
	});

	$(document).on('click', '#VolumeANDRevenueChart_buttons', function(){
		$(window).resize();
	}).on('click', '#FulfilmentChart_buttons', function(){
		$(window).resize();
	}).on('click', '#MarginTrendChart_buttons', function(){
		$(window).resize();
	});

	$(document).on("click", "li.country_dashboard" , function() {
		var countryName = $(this).attr('data-country');
	  	//toolbarItemClickFunction('dealdashboard','Deal Dashboard',' icon-bar-chart ');
		window.location.href = '#/countrydashboard?CountryName='+countryName;
	});

	function toFillStatBoard(result){
		$('#currentMargin').html('&euro;'+result.currentMargin);
		$('#marginTrend').html(result.marginTrend);
		$('#projectedMargin').html('&euro;'+result.projectedMargin);
		$('#volumeTrend').html(result.volumeTrend);
		$('#volumeTrend').html(result.volumeTrend);
		$('.volume_trend_icon').addClass(result.volumeTrendIcon);
		$('.volume_trend_icon').show();
		$('#volume_bg_icon').hide();
	}

	function fillVolumeANDRevenueChart(result){
		plotVolumeANDRevenueChart(result.categories,result.volumeSeries,result.revenueSeries);
	}

	function plotVolumeANDRevenueChart(categories,volumeSeries,revenueSeries){
		$("VolumeANDRevenueChart").html('');
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

	//fillFulfilmentChart
	function fillFulfilmentChart(result){
		filmentchartInCategories = result.inCategories;
		filmentchartInSeries = result.inSeries;
		filmentchartOutCategories = result.outCategories;
		filmentchartOutSeries = result.outSeries;
		plotFulfilmentChart(filmentchartInCategories,filmentchartInSeries);
	}

	$(document).on('click','#FulfilmentChart_buttons',function(){
		if($('#FulfilmentChart_IN').attr('checked'))
			plotFulfilmentChart(filmentchartInCategories,filmentchartInSeries);
		if($('#FulfilmentChart_OUT').attr('checked'))
			plotFulfilmentChart(filmentchartOutCategories,filmentchartOutSeries);
	});

	function plotFulfilmentChart(categories,filseries){
		$('#FulfilmentChart').html('');
		$('#FulfilmentChart_loading').hide();
		$('#FulfilmentChart_content').show();
		$('#FulfilmentChart').highcharts({
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
	            type: 'column'
	        },
	        title: {
	            text: ' '
	        },
	        xAxis: {
	            categories: categories,
	            lineWidth: 0,
				tickLength: 5,
				tickColor: '#D8D8D8'
	        },
	        yAxis: [{
	            min: 0,
	            title: {
	                text: 'Min',
	                align: 'middle',
	                style: {
						color: '#555559'
					}

	            },
	            labels: {
	                    enabled: true,
	                    style: {
							color: '#555559'
						}
	                }
	        }],
	        legend: {
	            shadow: false
	        },
	        tooltip: {
	            shared: true
	        },
	        plotOptions: {
	            column: {
	                grouping: false,
	                shadow: false,
	                borderWidth: 0
	            }
	        },
	        series: [{
	            name: 'Committed',
	            color: '#2C3E50',
	            data: filseries[0].data,
	            pointPadding: 0.1,
	        }, {
	            name: 'Actual',
	            color: '#67809F',
	            data: filseries[1].data,
	            pointPadding: 0.3,
	        }]
	    });
	}

	function fillMarginTrendChart(result){
		plotMarginTrendChart(result.dataList);
	}
	function plotMarginTrendChart(data){
		$('#MarginTrendChart').html('');
		$('#MarginTrendChart_loading').hide();
		$('#MarginTrendChart_content').show();
		$('#MarginTrendChart').highcharts({
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
				min: 0,
				title: {
					text: '€',
					 align:'middle',
	                 style: {
					 	color: '#555559'
					}

				},
	            labels: {
	                    enabled: true,
	                    style: {
						 	color: '#555559'
						}
	                }
			},
			legend: {
				enabled: true
			},
			plotOptions: {
				areaspline: {
					lineWidth: 2,
					states: {
						hover: {
							lineWidth: 2
						}
					},
					threshold: null
				}
			},

			series: [{
				type: 'areaspline',
				name: 'Margin Trend',
				data: data,
				color:'#2C3E50'
			}]
		});
	}


	function fillTrendMarginDetails(result){

		$('#dealDashboar-thead-margin').html('');
		$('#dealDashboar-body-margin').html('');

		$('#Dealdetails_table_loading_margin').hide();
		$('#dealDetails-margin').show();
		$('.dealDetails-carrier').html(result.carrier);
	    $('.dealDetails-startDate').html(result.startDate);
	    $('.dealDetails-endDate').html(result.endDate);
	    $('.dealDetails-dealId').html(result.dealId);
	    
	    var thead = '';
		for(var x = 0; x < result.headers.length; x++) {
			if("Destination Group" == result.headers[x]) {
				thead += '<th width="20%">'+result.headers[x]+'</th>';
			}
			else {
				thead += '<th width="8%">'+result.headers[x]+'</th>';
			}
		}
		$('#dealDashboar-thead-margin').html(thead);

	    for(var i = 0; i < result.marginDetailsList.length; i++){
	        var row = '<tr style="font-weight:800;" class="expanding" onclick="dealDetailParentTable(\''+result.marginDetailsList[i].destinationGroup.replace(/ /g , '')+'\')"><td>'+result.marginDetailsList[i].destinationGroup+'</td>';
	            row += '<td>'+result.marginDetailsList[i].direction+'</td>';
	            row += '<td>'+result.marginDetailsList[i].total+'</td>';
	            row += '<td>'+result.marginDetailsList[i].date1+'</td>';
	            row += '<td>'+result.marginDetailsList[i].date2+'</td>';
	            row += '<td>'+result.marginDetailsList[i].date3+'</td>';
	            row += '<td>'+result.marginDetailsList[i].date4+'</td>';
				row += '<td>'+result.marginDetailsList[i].date5+'</td>';
				row += '<td>'+result.marginDetailsList[i].date6+'</td>';
				row += '<td>'+result.marginDetailsList[i].date7+'</td></tr>';
	            $('#dealDashboar-body-margin').append(row);
	        for(var j = 0; j < result.marginDetailsList[i].marginDetailsList.length; j++){
	            //console.log(result.marginDetailsList[i].marginDetailsList[j].destinationGroup);
	            var row2 = '<tr style="display:none;" class="'+result.marginDetailsList[i].destinationGroup.replace(/ /g , '')+'"><td>- '+result.marginDetailsList[i].marginDetailsList[j].destinationGroup+'</td>';
	            row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].direction+'</td>';
	            row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].total+'</td>';
	            row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].date1+'</td>';
	            row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].date2+'</td>';
	            row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].date3+'</td>';
				row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].date4+'</td>';
				row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].date5+'</td>';
				row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].date6+'</td>';
	            row2 += '<td>'+result.marginDetailsList[i].marginDetailsList[j].date7+'</td></tr>';
	            $('#dealDashboar-body-margin').append(row2);
	        }
	    }
	}

	function fillVolumeDetails(result){
		$('#dealDashboar-thead-volume').html('');
		$('#dealDashboar-body-volume').html('');

		$('#trend_table_loading').hide();
		$('#Trend_Details').show();

	    $('.volumedealDetails-carrier').html(result.carrier);
	    $('.volumedealDetails-startDate').html(result.startDate);
	    $('.volumedealDetails-endDate').html(result.endDate);
	    $('.volumeDetails-dealId').html(result.dealId);

	    var thead = '';
		for(var x = 0; x < result.headers.length; x++) {
			if("Destination Group" == result.headers[x]) {
				thead += '<th width="20%">'+result.headers[x]+'</th>';
			}
			else {
				thead += '<th width="8%">'+result.headers[x]+'</th>';
			}
		}

		$('#dealDashboar-thead-volume').html(thead);

	    for(var i = 0; i < result.volumeDetailsList.length; i++){

	        var row = '<tr style="font-weight:800;" class="expanding" onclick="dealVolumeParentTable(\''+result.volumeDetailsList[i].destinationGroup.replace(/ /g , '')+'\')"><td>'+result.volumeDetailsList[i].destinationGroup+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].direction+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].committed+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].total+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].date1+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].date2+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].date3+'</td>';
	            row += '<td>'+result.volumeDetailsList[i].date4+'</td>';
				row += '<td>'+result.volumeDetailsList[i].date5+'</td>';
				row += '<td>'+result.volumeDetailsList[i].date6+'</td>';
				row += '<td>'+result.volumeDetailsList[i].date7+'</td></tr>';
	            $('#dealDashboar-body-volume').append(row);
	        for(var j = 0; j < result.volumeDetailsList[i].volumeDetailsList.length; j++){

	            var row2 = '<tr style="display:none;" class="'+result.volumeDetailsList[i].destinationGroup.replace(/ /g , '')+'"><td>- '+result.volumeDetailsList[i].volumeDetailsList[j].destinationGroup+'</td>';
	            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].direction+'</td>';
	            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].committed+'</td>';
	            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].total+'</td>';
	            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].date1+'</td>';
	            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].date2+'</td>';
	            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].date3+'</td>';
				row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].date4+'</td>';
				row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].date5+'</td>';
				row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].date6+'</td>';
	            row2 += '<td>'+result.volumeDetailsList[i].volumeDetailsList[j].date7+'</td></tr>';
	            $('#dealDashboar-body-volume').append(row2);
	        }
	    }
	}


	//Fullfilment Projection Begin 

	$(".projection_list_btn").click(function(e) {

		if($('#Projectionlist_in').is(':checked'))
			FulProjectionFilter = 'IN';
		if($('#Projectionlist_out').is(':checked'))
			FulProjectionFilter = 'OUT';
		if($('#Projectionlist_in').is(':checked') && $('#Projectionlist_out').is(':checked'))
			FulProjectionFilter = 'Both';
		if(!$('#Projectionlist_in').is(':checked') && !$('#Projectionlist_out').is(':checked'))
			FulProjectionFilter = 'Both';
		toFillProjectionList();
	});

	function fillProjectionlist(result) {
		fillProjectionListArray = result.projectionDataList;
		toFillProjectionList();
	}

	function toFillProjectionList(){
		$('#Fulfilment_Projection_list_loading').hide();
	    $('#projectionListContainer').html('');
	    var FillProjectionList = '<ul class="feeds">';
	    for(var i = 0; i < fillProjectionListArray.length; i++){
			if(FulProjectionFilter == fillProjectionListArray[i].direction || FulProjectionFilter == 'Both'){
				FillProjectionList += '<li class="country_dashboard" data-country="'+fillProjectionListArray[i].country+'">';
				FillProjectionList += '<div class="col1 cd_toolTip" data-toggle="popover" data-placement="bottom" data-trigger="hover" data-content="'+fillProjectionListArray[i].data+'"><div class="cont"><div class="cont-col1">';
				FillProjectionList += '<div class="label label-sm label-info" style="background-color: '+fillProjectionListArray[i].color+';"><i class="'+fillProjectionListArray[i].icon+'"></i></div></div>';
				FillProjectionList += '<div class="cont-col2"><div class="desc" style="color:'+fillProjectionListArray[i].labelColor+'">'+fillProjectionListArray[i].data;
				FillProjectionList += '</span></div></div></div></div>';
				FillProjectionList += '<div class="col2"><div class="date">'+fillProjectionListArray[i].period+'</div></div>';
				FillProjectionList += '</li>';
	    	}
	    }
	    FillProjectionList += '</ul>';
		$('#projectionListContainer').html(FillProjectionList);
	}

	//Fullfilment Projection End 


	//to fill deal details
	function fillDealDetails(result){
		$('#Dealdetails_table_loading').hide();
		$("#display_container").show();
		$(".display_content_table").show();
	    $('#dealDetails-gracePeriod').html(result.gracePeriod);
	    $('#dealDetails-carrier, .dealDetails-carrier').html(result.carrier);
	    $('#dealDetails-startDate, .dealDetails-startDate').html(result.startDate);
	    $('#dealDetails-endDate, .dealDetails-endDate').html(result.endDate);
	    $('#dealDetails-Currency').html(result.currency);
	    $('#dealDetails-dealId, .dealDetails-dealId').html(result.dealId);
	    $('#dealDashboar-body').html('');
	    for(var i = 0; i < result.dealDetailsList.length; i++){
	        var row = '<tr style="font-weight:800;" class="expanding" onclick="dealDetailParent(\''+result.dealDetailsList[i].destination.replace(/ /g , '')+'\')"><td>'+result.dealDetailsList[i].destination+'</td>';
	            row += '<td>'+result.dealDetailsList[i].direction+'</td>';
	            row += '<td>'+result.dealDetailsList[i].tier+'</td>';
	            row += '<td>'+result.dealDetailsList[i].volume+'</td>';
	            row += '<td>'+result.dealDetailsList[i].rate+'</td>';
	            row += '<td>'+result.dealDetailsList[i].nextRate+'</td>';
	            row += '<td>'+result.dealDetailsList[i].validFrom+'</td>';
	            row += '<td>'+result.dealDetailsList[i].validTo+'</td></tr>';
	            $('#dealDashboar-body').append(row);
	        for(var j = 0; j < result.dealDetailsList[i].dealDetailsList.length; j++){
	            var row2 = '<tr style="display:none;" class="'+result.dealDetailsList[i].destination.replace(/ /g , '')+'"><td>- '+result.dealDetailsList[i].dealDetailsList[j].destination+'</td>';
	            row2 += '<td>'+result.dealDetailsList[i].dealDetailsList[j].direction+'</td>';
	            row2 += '<td>'+result.dealDetailsList[i].dealDetailsList[j].tier+'</td>';
	            row2 += '<td>'+result.dealDetailsList[i].dealDetailsList[j].volume+'</td>';
	            row2 += '<td>'+result.dealDetailsList[i].dealDetailsList[j].rate+'</td>';
	            row2 += '<td>'+result.dealDetailsList[i].dealDetailsList[j].nextRate+'</td>';
	            row2 += '<td>'+result.dealDetailsList[i].dealDetailsList[j].validFrom+'</td>';
	            row2 += '<td>'+result.dealDetailsList[i].dealDetailsList[j].validTo+'</td></tr>';
	            $('#dealDashboar-body').append(row2);
	        }

	    }

	}

	function errorCallback(error){
		console.log(error);
	}

	$(document).on("mouseover",".desc",function(){
		$('[data-toggle="popover"]').popover(); 
	});

});

function dealDetailParent(className){
    if($('.'+className).parent('#dealDashboar-body').find('.'+className).is(":visible"))
        $('.'+className).parent('#dealDashboar-body').find('.'+className).hide();
    else
        $('.'+className).parent('#dealDashboar-body').find('.'+className).show();
}

// fillTrendMarginDetails
function dealDetailParentTable(className){
    if($('.'+className).parent('#dealDashboar-body-margin').find('.'+className).is(":visible"))
        $('.'+className).parent('#dealDashboar-body-margin').find('.'+className).hide();
    else
        $('.'+className).parent('#dealDashboar-body-margin').find('.'+className).show();
}


// fillVolumeDetails
function dealVolumeParentTable(className){
    if($('.'+className).parent('#dealDashboar-body-volume').find('.'+className).is(":visible"))
        $('.'+className).parent('#dealDashboar-body-volume').find('.'+className).hide();
    else
        $('.'+className).parent('#dealDashboar-body-volume').find('.'+className).show();
}

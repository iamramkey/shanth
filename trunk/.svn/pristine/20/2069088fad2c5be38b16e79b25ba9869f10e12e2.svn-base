'use strict';
ThrazeApp.controller('DashboardController', function($rootScope, $scope, $http,$timeout) {
	$scope.$on('$viewContentLoaded', function() {
		// initialize core components
		Metronic.initAjax();

		$('.select2').select2();
		//variable necessosry for the graph toggle
		var filmentchartInCategories, filmentchartOutCategories, filmentchartInSeries, filmentchartOutSeries;
		toCallListApi('customervendorlist',JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode}),'accNameList');
		$(document).on("change", "#accNameList", function(){
			console.log("ok");
			$("#dealIdList").select2("val", null);
			$("#dealIdList").html('');
			toCallListApi('deallist',JSON.stringify({usrId:userId,ussSessionCode:ussSessionCode,accName:$('#accNameList').val()}),'dealIdList');
		});

		//to fill ddbstatboard
		var inputForCharApi = {usrId:userId,ussSessionCode:ussSessionCode,currencyCode:"INR"};
		inputForCharApi = JSON.stringify(inputForCharApi);
		httpPostDashboard('bdbstatboard',inputForCharApi,toFillStatBoard,errorCallback);

		//to fill Volume And Revenue Chart
		httpPostDashboard('bdbvolumeandrevenuechart',inputForCharApi,fillVolumeANDRevenueChart,errorCallback);

		//to fill Volume And Margintrend Chart
		httpPostDashboard('bdbmargintrendchart',inputForCharApi,fillMarginTrendChart,errorCallback);

		//to fill Volume And Fulfilment Chart
		httpPostDashboard('bdbdealnondealtrafficchart',inputForCharApi,fillmentChart,errorCallback);

		//to fill Full Fill Changes Traffic List
		httpPostDashboard('bdbchangesintrafficlist',inputForCharApi,fillchangetrafficlist,errorCallback);

		//to fill Full Fill Recent Activity List
		httpPostDashboard('bdbrecentactivitylist',inputForCharApi,fillrecentactivitylist,errorCallback);

		//to fill Full Fill Top Destinations List
		httpPostDashboard('bdbtopdestinationslist',inputForCharApi,filltopdestinationslist,errorCallback);

		//to fill Full Fill Top Deal Expiring List
		httpPostDashboard('bdbdealexpiringlist',inputForCharApi,filldealexpiringlist,errorCallback);

		//to fill Full Fill Top Short Fall  List
		httpPostDashboard('bdbshortfallwarninglist',inputForCharApi,fillshortfalist,errorCallback);

		//var deal = localStorage.getItem("dealList");
		var inputForCharApi = {usrId:userId,ussSessionCode:ussSessionCode,currencyCode:"INR"};
		inputForCharApi = JSON.stringify(inputForCharApi);
		console.log(inputForCharApi);

		httpPostDashboard('bdbdealscount',inputForCharApi,fillDealDetails,errorCallback);

		httpPostDashboard('bdbvolumerevenue',inputForCharApi,fillTrendDetails,errorCallback);

		httpPostDashboard('bdbmargin',inputForCharApi,fillTrendMarginDetails,errorCallback);

	});
	// set sidebar closed and body solid layout mode
	$rootScope.settings.layout.pageBodySolid = true;
	$rootScope.settings.layout.pageSidebarClosed = false;

	(function() {
		$('#headerBreadcrumbContainer').html('Dashboard');
	})();

	
	$(document).on("change","#dealIdList",function(){
		console.log($('#dealIdList').val());
		toolbarItemClickFunction('dealdashboard','Deal Dashboard',' icon-bar-chart ');
		window.location.href = '#/dealdashboard?accNameList='+$('#accNameList').val()+'&&&dealIdList='+$('#dealIdList').val();
	});

	$(document).on("click", "li.deal_dashboard" , function() {
		var account = $(this).attr('data-account');
		var deal = $(this).attr('data-deal');
	  	toolbarItemClickFunction('dealdashboard','Deal Dashboard',' icon-bar-chart ');
		window.location.href = '#/dealdashboard?accNameList='+account+'&&&dealIdList='+deal;
	});

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


	function toFillStatBoard(result){
		console.log(result);
		$('#activeDeals').html(result.activeDeals);
		$('#marginTrend').html(result.marginTrend);
		$('#newDeals').html(result.newDeals);
		$('#volumeTrend').html(result.volumeTrend);
	}

	function fillVolumeANDRevenueChart(result){
		plotVolumeANDRevenueChart(result.categories,result.volumeSeries,result.revenueSeries);
	}

	function plotVolumeANDRevenueChart(categories,volumeSeries,revenueSeries){
		$('#VolumeANDRevenueChart_loading').hide();
		$('#VolumeANDRevenueChart_content').show();
			$('#VolumeANDRevenueChart').highcharts({
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
				zoomType: 'xy'
			},
			title: {
				text: ' '
			},
			xAxis: [{
				color: '#555559',
				categories: categories,
	            lineWidth: 0,
				tickLength: 5,
				tickColor: '#D8D8D8',
				crosshair: true
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
				data: volumeSeries[0].data,
				color: '#67809F',
				tooltip: {
					valueSuffix: ' min'
				}


			},{
				name: 'Vol Out',
				type: 'column',
				pointPadding: 0.01,
				yAxis: 0,
				data: volumeSeries[1].data,
				color: '#2C3E50',
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
		$('#FulfilmentChart_loading').hide();
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
	            categories: categories
	        },
	        yAxis: [{
	            min: 0,
	            title: {
	                text: 'Min',
	                align: 'middle'
	            },
	            //valueSuffix: ''
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
	            name: 'Comitted',
	            color: 'rgba(165,170,217,1)',
	            data: filseries[0].data,
	            pointPadding: 0.1,
	        }, {
	            name: 'Actual',
	            color: 'rgba(248,161,63,1)',
	            data: filseries[1].data,
	            pointPadding: 0.3,
	        }]
	    });
	}

	function fillMarginTrendChart(result){
		plotMarginTrendChart(result.dataList);
	}

	function plotMarginTrendChart(data){
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
	            //text: document.ontouchstart === undefined ?
	                   // 'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
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
					}//,
	//                    formatter: function() {
	//                        return this.value / 1000;
	//                    }
	            }

	        },
	        legend: {
	            enabled: true
	        },
	        plotOptions: {
	            areaspline: {
	//                    fillColor: {
	//                        linearGradient: {
	//                           x1: 0,
	//                          y1: 0,
	//                          x2: 0,
	//                          y2: 1
	//                       },
	//                      stops: [
	//                           [0, Highcharts.getOptions().colors[0]],
	//                         [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	  //                   ]
	 //                },
	 //                marker: {
	 //                    radius: 2
	 //                },
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

	function fillmentChart(result){
		fillmentChart_graph(result.dealDataList,result.nonDealDataList);
	}
	function fillmentChart_graph(data,data1){
		$('#dealChart_loading').hide();
		$('#dealChart_content').show();
		$('#dealChart').highcharts({
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
	               // text: document.ontouchstart === undefined ?
	                        //'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
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
	                	enabled: true//,
	//                    formatter: function() {
	//                        return this.value / 1000000;
	//                    }
	                }

	            },
	            legend: {
	                enabled: true
	            },
	            // plotOptions: {
	            //     area: {
	            //         fillColor: {
	            //             linearGradient: {
	            //                 x1: 0,
	            //                 y1: 0,
	            //                 x2: 0,
	            //                 y2: 1
	            //             },
	            //             stops: [
	            //                 [0, Highcharts.getOptions().colors[0]],
	            //                 [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	            //             ]
	            //         },
	            //         marker: {
	            //             radius: 2
	            //         },
	            //         lineWidth: 2,
	            //         states: {
	            //             hover: {
	            //                 lineWidth: 2
	            //             }
	            //         },
	            //         threshold: null
	            //     }
	            // },
	            plotOptions: {
					areaspline: {
						stacking: 'normal'
					}
	            },

	            series: [{
	                type: 'areaspline',
	                name: 'Deal',
	                data: data,
	                color: '#67809F'
	            },{
	                type: 'areaspline',
	                name: 'Non Deal',
	                data: data1,
	                color: '#2C3E50'
	            }]
	        });
	}

	function fillchangetrafficlist(result){
		$('#Changes_traffic_loading').hide();
		console.log(result);
		$('#changesTrafficListContainer').html('');

		var inSeriesTraffic = '<ul class="feeds" id="inSeriesTraffic" style="display:none;">';
		for(var i = 0; i < result.changesInTrafficDataList.length; i++){

			if(result.changesInTrafficDataList[i].direction == 'IN')
	        {
		      inSeriesTraffic += '<li class="deal_dashboard" data-account="'+result.changesInTrafficDataList[i].account+'" data-deal="'+result.changesInTrafficDataList[i].deal+'">';
		      inSeriesTraffic += '<div class="col1"><div class="cont"><div class="cont-col1">';
		      inSeriesTraffic += '<div class="label label-sm label-info" style="background-color: '+result.changesInTrafficDataList[i].color+';"><i class="'+result.changesInTrafficDataList[i].icon+'"></i></div></div>';
		      inSeriesTraffic += '<div class="cont-col2"><div class="desc" style="color:'+result.changesInTrafficDataList[i].labelColor+'">'+result.changesInTrafficDataList[i].data;
		      inSeriesTraffic += '</span></div></div></div></div>';
		      inSeriesTraffic += '<div class="col2"><div class="date">'+result.changesInTrafficDataList[i].period+'</div></div>';
		      inSeriesTraffic += '</li>';
			}
		}
		inSeriesTraffic += '</ul>';
		$('#changesTrafficListContainer').append(inSeriesTraffic);
		var outSeriesTraffic = '<ul class="feeds" id="outSeriesTraffic" style="display:none;">';
		for(var i = 0; i < result.changesInTrafficDataList.length; i++){
	        if(result.changesInTrafficDataList[i].direction == 'OUT') {
		      outSeriesTraffic += '<li class="deal_dashboard" data-account="'+result.changesInTrafficDataList[i].account+'" data-deal="'+result.changesInTrafficDataList[i].deal+'">';
		      outSeriesTraffic += '<div class="col1"><div class="cont"><div class="cont-col1">';
		      outSeriesTraffic += '<div class="label label-sm label-info" style="background-color: '+result.changesInTrafficDataList[i].color+';"><i class="'+result.changesInTrafficDataList[i].icon+'"></i></div></div>';
		      outSeriesTraffic += '<div class="cont-col2"><div class="desc" style="color:'+result.changesInTrafficDataList[i].labelColor+'">'+result.changesInTrafficDataList[i].data;
		      outSeriesTraffic += '</span></div></div></div></div>';
		      outSeriesTraffic += '<div class="col2"><div class="date">'+result.changesInTrafficDataList[i].period+'</div></div>';
		      outSeriesTraffic += '</li>';
	        }
		}
		outSeriesTraffic += '</ul>';
		$('#changesTrafficListContainer').append(outSeriesTraffic);
		var allSeriesTraffic = '<ul class="feeds" id="allSeriesTraffic">';
		for(var i = 0; i < result.changesInTrafficDataList.length; i++){
		  allSeriesTraffic += '<li class="deal_dashboard" data-account="'+result.changesInTrafficDataList[i].account+'" data-deal="'+result.changesInTrafficDataList[i].deal+'">';
	      allSeriesTraffic += '<div class="col1"><div class="cont"><div class="cont-col1">';
	      allSeriesTraffic += '<div class="label label-sm label-info" style="background-color: '+result.changesInTrafficDataList[i].color+';"><i class="'+result.changesInTrafficDataList[i].icon+'"></i></div></div>';
	      allSeriesTraffic += '<div class="cont-col2"><div class="desc" style="color:'+result.changesInTrafficDataList[i].labelColor+'">'+result.changesInTrafficDataList[i].data;
	      allSeriesTraffic += '</span></div></div></div></div>';
	      allSeriesTraffic += '<div class="col2"><div class="date">'+result.changesInTrafficDataList[i].period+'</div></div>';
	      allSeriesTraffic += '</li>';
		}
		allSeriesTraffic += '</ul>';
		$('#changesTrafficListContainer').append(allSeriesTraffic);
	}

	$(".ChangeTrafficOptions").click(function(e) {

		if($('#ChangeTrafficOptions_out').is(':checked') && !$('#ChangeTrafficOptions_in').is(':checked')) {
			$('#outSeriesTraffic').show();
			$('#inSeriesTraffic').hide();
			$('#allSeriesTraffic').hide();
		}
		else if($('#ChangeTrafficOptions_in').is(':checked') && !$('#ChangeTrafficOptions_out').is(':checked')) {
			$('#outSeriesTraffic').hide();
			$('#inSeriesTraffic').show();
			$('#allSeriesTraffic').hide();
		}
		else if($('#ChangeTrafficOptions_out').is(':checked') && $('#ChangeTrafficOptions_in').is(':checked')){
			$('#outSeriesTraffic').hide();
			$('#inSeriesTraffic').hide();
			$('#allSeriesTraffic').show();
		}
		else{
			$('#outSeriesTraffic').hide();
			$('#inSeriesTraffic').hide();
			$('#allSeriesTraffic').show();
		}
	});

	var totalRecentActivityArray = [], primaryFilter_RecentActivity = "ALL";

	function fillrecentactivitylist(result) {
		var FilterOptions="";

		for(var x=0; x < result.bandGroupList.length;x++) {

			FilterOptions = '<li><a href="javascript:;" data-recent-value="'+result.bandGroupList[x]+'" class="RecentActivityOptions" id="regional_stat_world">'+result.bandGroupList[x]+'</a></li>';
			$('#drop_down_recent_activity').append(FilterOptions);
		}
		totalRecentActivityArray = result.recentActivityList;
	    toFillRecentActivityList();
	}

	$(document).on('click','.RecentActivityOptions',function(){
		console.log($(this).attr("data-recent-value"));
		primaryFilter_RecentActivity = $(this).attr("data-recent-value");
		toFillRecentActivityList();
	});


	function toFillRecentActivityList(){
	    $('#recentActivity_loading').hide();
		$("#inSeriesContent").show();
	    $('#recentactivityListContainer').html('');
		var inSeriesContent = '<ul class="feeds" id="inSeriesContent">';
		 for(var i = 0; i < totalRecentActivityArray.length; i++){
			if(primaryFilter_RecentActivity == totalRecentActivityArray[i].group || primaryFilter_RecentActivity=="ALL"){
				inSeriesContent += '<li class="deal_dashboard" data-account="'+totalRecentActivityArray[i].account+'" data-deal="'+totalRecentActivityArray[i].deal+'">';
				inSeriesContent += '<div class="col1"><div class="cont"><div class="cont-col1">';
				inSeriesContent += '<div class="label label-sm label-info" style="background-color: '+totalRecentActivityArray[i].color+';"><i class="'+totalRecentActivityArray[i].icon+'"></i></div></div>';
				inSeriesContent += '<div class="cont-col2"><div class="desc" style="color:#67809F !important;">'+totalRecentActivityArray[i].data;
				inSeriesContent += '</span></div></div></div></div>';
				inSeriesContent += '<div class="col2"><div class="date">'+totalRecentActivityArray[i].period+'</div></div>';
				inSeriesContent += '</li>';
			}
		}
		inSeriesContent += '</ul>';
	    $('#recentactivityListContainer').append(inSeriesContent);
	}

	function filldealexpiringlist(result){
	    $('#dealsExpiring_loading').hide();
	    console.log(result);
	    $('#dealsExpiringListContainer').html('');

	    var inSeriesContent = '<ul class="feeds" id="inSeriesContent">';
	    for(var i = 0; i < result.dealExpiringDataList.length; i++){
			inSeriesContent += '<li class="deal_dashboard" data-account="'+result.dealExpiringDataList[i].account+'" data-deal="'+result.dealExpiringDataList[i].deal+'">';
			inSeriesContent += '<div class="col1"><div class="cont"><div class="cont-col1">';
			inSeriesContent += '<div class="label label-sm label-info" style="background-color: '+result.dealExpiringDataList[i].color+';"><i class="'+result.dealExpiringDataList[i].icon+'"></i></div></div>';
			inSeriesContent += '<div class="cont-col2"><div class="desc" style="color:'+result.dealExpiringDataList[i].labelColor+'">'+result.dealExpiringDataList[i].data;
			inSeriesContent += '</span></div></div></div></div>';
			inSeriesContent += '<div class="col2"><div class="date">'+result.dealExpiringDataList[i].period+'</div></div>';
			inSeriesContent += '</li>';
	    }
	    inSeriesContent += '</ul>';
	    $('#dealsExpiringListContainer').append(inSeriesContent);
	}

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
					inSeriesShortPer += '<li class="deal_dashboard" data-account="'+totalfillshortfalistArray[i].account+'" data-deal="'+totalfillshortfalistArray[i].deal+'">';
					inSeriesShortPer += '<div class="col1"><div class="cont"><div class="cont-col1">';
					inSeriesShortPer += '<div class="label label-sm label-info" style="background-color: '+totalfillshortfalistArray[i].color+';"><i class="'+totalfillshortfalistArray[i].icon+'"></i></div></div>';
					inSeriesShortPer += '<div class="cont-col2"><div class="desc" style="color:'+totalfillshortfalistArray[i].labelColor+'">'+totalfillshortfalistArray[i].data;
					inSeriesShortPer += '</span><span class="label label-sm" style="margin-left:10px; background: #67809F;">'+totalfillshortfalistArray[i].subData+'</span></div></div></div></div>';
					inSeriesShortPer += '<div class="col2"><div class="date">'+totalfillshortfalistArray[i].period+'</div></div>';
					inSeriesShortPer += '</li>';
				}
			}
		}
		inSeriesShortPer += '</ul>';
		$('#shortFallListContainer').append(inSeriesShortPer);
	}

	$(".TopDestiantionsOptions").click(function(e) {
		if($('#TopDestiantionsOptions_in').is(':checked'))
			primaryFilter = 'In';
		if($('#TopDestiantionsOptions_out').is(':checked'))
			primaryFilter = 'Out';
		if($('#TopDestiantionsOptions_in').is(':checked') && $('#TopDestiantionsOptions_out').is(':checked'))
			primaryFilter = 'Both';
		if(!$('#TopDestiantionsOptions_in').is(':checked') && !$('#TopDestiantionsOptions_out').is(':checked'))
			primaryFilter = 'Both';
		toFillFilteredDesList();
	});

	$(".topDestinations_options").click(function(e) {
		secondaryFilter = $(this).attr("data-value");
		toFillFilteredDesList();
	});

	var totalFilltopdestinationslistArray = [], primaryFilter = 'Both', secondaryFilter = 'VOL';
	function filltopdestinationslist(result) {
		totalFilltopdestinationslistArray = result.topDestinationsDataList;
		toFillFilteredDesList();
	}

	function toFillFilteredDesList(){
		$('#topDestinations_loading').hide();
	    $('#topDestinationsContainer').html('');
	    var TopDestination_all = '<ul class="feeds">';
	    for(var i = 0; i < totalFilltopdestinationslistArray.length; i++){
	    	if(secondaryFilter == totalFilltopdestinationslistArray[i].secondaryFilter){
	    		if(primaryFilter == totalFilltopdestinationslistArray[i].primaryFilter || primaryFilter == 'Both'){
	    			TopDestination_all += '<li class="deal_dashboard" data-account="'+totalFilltopdestinationslistArray[i].account+'" data-deal="'+totalFilltopdestinationslistArray[i].deal+'">';
					TopDestination_all += '<div class="col1"><div class="cont"><div class="cont-col1">';
					TopDestination_all += '<div class="label label-sm label-info" style="background-color: '+totalFilltopdestinationslistArray[i].color+';"><i class="'+totalFilltopdestinationslistArray[i].icon+'"></i></div></div>';
					TopDestination_all += '<div class="cont-col2"><div class="desc" style="color:'+totalFilltopdestinationslistArray[i].labelColor+'">'+totalFilltopdestinationslistArray[i].data;
					TopDestination_all += '</span></div></div></div></div>';
					TopDestination_all += '<div class="col2"><div class="date">'+totalFilltopdestinationslistArray[i].period+'</div></div>';
					TopDestination_all += '</li>';
	    		}
	    	}
	    }
	    TopDestination_all += '</ul>';
		$('#topDestinationsContainer').html(TopDestination_all);
	}

	function dealDetailParent(className){
	    console.log(className);
	    if($('.'+className).parent().find('.'+className).is(":visible"))
	        $('.'+className).parent().find('.'+className).hide();
	    else
	        $('.'+className).parent().find('.'+className).show();
	}

	function errorCallback(error){
		console.log(error);
	}

	function fillDealDetails(result){
		console.log(result);
		$('#Dealdetails_table_loading').hide();
		$('#dealDetails-tbl').show();
		$('.dealDetails-carrier').html(result.carrier);
		$('.dealDetails-startDate').html(result.startDate);
		$('.dealDetails-endDate').html(result.endDate);
		$('.dealDetails-dealId').html(result.dealId);
		$('#dealDashboa-table1').dataTable( {
	        	"bDestroy" : true,
	        	"bSort" : false,
	            "bSortable": true,
				"processing": false,
				"bServerSide": false,
				"bPaginate": false,
				"aaData": result.newDealsList,
	          	//"dom": 'T<"clear">lfrtip',
		        // "oTableTools": {
		        //     "aButtons": [
		        //         "copy",
		        //         "print",
		        //         {
		        //             "sExtends":    "collection",
		        //             "sButtonText": "Save",
		        //             "aButtons":    [ "xls" ],
		        //             "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
		        //         }
		        //     ]
		        // },
				"aoColumns": [{"mData": "carrier"},
							{"mData": "dealId"},
							{"mData": "validFrom"},
							{"mData": "validTo"},
							{"mData": "committedIn"},
							{"mData": "committedOut"},
							{"mData": "inVolume"},
							{"mData": "outVolume"},
							{"mData": "revenueIn"},
							{"mData": "revenueOut"}],
			});
		$('#dealDashboa-table2').dataTable( {
	        	"bDestroy" : true,
	        	"bSort" : false,
	            "bSortable": true,
				"processing": false,
				"bServerSide": false,
				"bPaginate": false,
				"aaData": result.activeDealsList,
	         //  "dom": 'T<"clear">lfrtip',
		        // "oTableTools": {
		        //     "aButtons": [
		        //         "copy",
		        //         "print",
		        //         {
		        //             "sExtends":    "collection",
		        //             "sButtonText": "Save",
		        //             "aButtons":    [ "xls" ],
		        //             "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
		        //         }
		        //     ]
		        // },
				"aoColumns": [{"mData": "carrier"},
							{"mData": "dealId"},
							{"mData": "validFrom"},
							{"mData": "validTo"},
							{"mData": "committedIn"},
							{"mData": "committedOut"},
							{"mData": "inVolume"},
							{"mData": "outVolume"},
							{"mData": "revenueIn"},
							{"mData": "revenueOut"}],
			});
		// for(var i = 0; i < result.newDealsList.length; i++){
		// 	var row = '<tr style="font-weight:800;" class="expanding" ><td>'+result.newDealsList[i].carrier+'</td>';
		// 		row += '<td>'+result.newDealsList[i].dealId+'</td>';
		// 		row += '<td>'+result.newDealsList[i].validFrom+'</td>';
		// 		row += '<td>'+result.newDealsList[i].validTo+'</td>';
		// 		row += '<td>'+result.newDealsList[i].inVolume+'</td>';
		// 		row += '<td>'+result.newDealsList[i].outVolume+'</td>';
		// 		row += '<td>'+result.newDealsList[i].committedIn+'</td>';
		// 		row += '<td>'+result.newDealsList[i].committedOut+'</td>';
		// 		row += '<td>'+result.newDealsList[i].revenueIn+'</td>';
		// 		row += '<td>'+result.newDealsList[i].revenueOut+'</td></tr>';
		// 		$('#dealDashboar-body').append(row);
		// 	}
		// for(var i = 0; i < result.activeDealsList.length; i++){
		// 	var row = '<tr style="font-weight:800;" class="expanding" ><td>'+result.activeDealsList[i].carrier+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].dealId+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].validFrom+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].validTo+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].inVolume+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].outVolume+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].committedIn+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].committedOut+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].revenueIn+'</td>';
		// 		row += '<td>'+result.activeDealsList[i].revenueOut+'</td></tr>';
		// 		$('#dealDashboar-body-1').append(row);
		// }
	}

	function fillTrendDetails(result){
		console.log(result);
		$('#trend_table_loading').hide();
		$('#Trend_Details').show();

		for(var x=0; x < result.headers.length; x++) {
			var row = '<th>'+result.headers[x]+'</th>';
			$('#dealDashboar-thead-volume').append(row);
			if(result.headers[x]!="Committed") {
				$('#dealDashboar-thead-revenue').append(row);
			} 
		}
		$('#dealDashboar-volume-table').dataTable( {
	        	"bDestroy" : true,
	        	"bSort" : false,
	            "bSortable": true,
				"processing": false,
				"bServerSide": false,
				"bPaginate": false,
				"aaData": result.dailyVolumesList,
	         //  "dom": 'T<"clear">lfrtip',
		        // "oTableTools": {
		        //     "aButtons": [
		        //         "copy",
		        //         "print",
		        //         {
		        //             "sExtends":    "collection",
		        //             "sButtonText": "Save",
		        //             "aButtons":    [ "xls" ],
		        //             "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]
		        //         }
		        //     ]
		        // },
				"aoColumns": [{"mData": "deal"},
							{"mData": "direction"},
							{"mData": "committed"},
							{"mData": "total"},
							{"mData": "date1"},
							{"mData": "date2"},
							{"mData": "date3"},
							{"mData": "date4"},
							{"mData": "date5"},
							{"mData": "date6"},
							{"mData": "date7"},],
			});
			$('#dealDashboar-revenue-table').dataTable( {
	        	"bDestroy" : true,
	        	"bSort" : false,
	            "bSortable": true,
				"processing": false,
				"bServerSide": false,
				"bPaginate": false,
				"aaData": result.dailyRevenueList,
	         //  "dom": 'T<"clear">lfrtip',
		        // "oTableTools": {
		        //     "aButtons": [
		        //         "copy",
		        //         "print",
		        //         {
		        //             "sExtends":    "collection",
		        //             "sButtonText": "Save",
		        //             "aButtons":    [ "xls" ],
		        //             "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]
		        //         }
		        //     ]
		        // },
				"aoColumns": [{"mData": "deal"},
				{"mData": "direction"},
				// {"mData": "committed"},
				{"mData": "total"},
				{"mData": "date1"},
				{"mData": "date2"},
				{"mData": "date3"},
				{"mData": "date4"},
				{"mData": "date5"},
				{"mData": "date6"},
				{"mData": "date7"},],
			});
	}

	function fillTrendMarginDetails(result){
		console.log(result);
		$('#trend_table_loading').hide();
		$('#Trend_Details').show();

		for(var x=0; x < result.headers.length; x++) {
			var row = '<th>'+result.headers[x]+'</th>';
			$('#dealDashboar-thead-margin').append(row);
		}
		$('#dealDashboar-margin-table').dataTable( {
        	"bDestroy" : true,
        	"bSort" : false,
            "bSortable": true,
			"processing": false,
			"bServerSide": false,
			"bPaginate": false,
			"aaData": result.marginList,
         //  "dom": 'T<"clear">lfrtip',
	        // "oTableTools": {
	        //     "aButtons": [
	        //         "copy",
	        //         "print",
	        //         {
	        //             "sExtends":    "collection",
	        //             "sButtonText": "Save",
	        //             "aButtons":    [ "xls" ],
	        //             "mColumns": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
	        //         }
	        //     ]
	        // },
			"aoColumns": [{"mData": "deal"},
						{"mData": "direction"},
						{"mData": "total"},
						{"mData": "date1"},
						{"mData": "date2"},
						{"mData": "date3"},
						{"mData": "date4"},
						{"mData": "date5"},
						{"mData": "date6"},
						{"mData": "date7"},],
		});

	}



	
});

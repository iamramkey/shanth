function fillCharts(inData,outData,type,mode){
    if(mode == 'Clustered'){
        valueAxesIn = [{"gridColor":"#FFFFFF","gridAlpha": 0.2,"dashLength": 0,"labelsEnabled": true,"position": "left"}]
        valueAxesOut = [{"gridColor":"#FFFFFF","gridAlpha": 0.2,"dashLength": 0,"labelsEnabled": true,"position": "left"}]
    }else if(mode == 'Stacked'){
        valueAxesIn = [{"stackType": "regular","axisAlpha": 0.3,"gridAlpha": 0}]
        valueAxesOut = [{"stackType": "regular","axisAlpha": 0.3,"gridAlpha": 0}]
    }else{
        valueAxesIn = [{"stackType": "100%","axisAlpha": 0,"gridAlpha": 0,"labelsEnabled": true,"position": "left"}]
        valueAxesOut = [{"stackType": "100%","axisAlpha": 0,"gridAlpha": 0,"labelsEnabled": true,"position": "left"}]
    }
    if(type == 'Revenue'){
        grapFunction(inData,outData,"actualRevenue" ,"commitedRevenue",valueAxesIn,valueAxesOut,"currency");
    }else{
        grapFunction(inData,outData,"actualUsage" ,"commitedUsage" ,valueAxesIn,valueAxesOut,"mins");
    }
    
}

// function for the usage 
function grapFunction(inData,outData,graphVar1,graphVar2,valueAxesIn,valueAxesOut, unit){

    // TODO, if different currencies are there for in and out , it will not work correctly.. 
    // TODO, if different currencies are there 1s and 2nd row , it will not work correctly.. 
    if(unit == 'currency' && inData.length > 1)
        unit = inData[0].cur; 

    var chart = AmCharts.makeChart("chart_1", {
    "type": "serial",
    "theme": "light",
    "dataProvider": inData,
    "valueAxes": valueAxesIn,
    "gridAboveGraphs": true,
    "startDuration": 1,
    "graphs":[{
        "balloonText": "Actual ("+ unit+ ") : <b>[[value]]</b>",
        "fillColors":"#67809F",
        "fillAlphas": 0.8,        
        "lineAlpha": 0.2,
        "type": "column",
        "valueField": graphVar1     
    },{
        "balloonText": "Commited ("+ unit+ ") : <b>[[value]]</b>",
        "fillColors":"#E87E04",
        "fillAlphas": 0.8,        
        "lineAlpha": 0.2,
        "type": "column",
        "valueField": graphVar2      
    }],
    "chartCursor": {
        "categoryBalloonEnabled": false,
        "cursorAlpha": 0,
        "zoomable": false
    },
    "categoryField": "bandGroup",
    "categoryAxis": {
        "gridPosition": "start",
        "gridAlpha": 0,
         "labelRotation" : 45,
    },
});
    var chart = AmCharts.makeChart("chart_2", {
    "type": "serial",
    "theme": "light",
    "dataProvider":  outData,
    "valueAxes": valueAxesOut,
    "gridAboveGraphs": true,
    "startDuration": 1,
    "graphs": [{
        "balloonText": "Actual ("+ unit+ ") : <b>[[value]]</b>",
        "fillAlphas": 0.8,
        "lineAlpha": 0.2,
        "fillColors":"#67809F",
        "type": "column",
        "valueField": graphVar1    
    },{
        "balloonText": "Commited ("+ unit+ ") : <b>[[value]]</b>",
        "fillAlphas": 0.8,
        "lineAlpha": 0.2,
        "fillColors":"#E87E04",
        "type": "column",
        "valueField": graphVar2      
    }],
    "chartCursor": {
        "categoryBalloonEnabled": false,
        "cursorAlpha": 0,
        "zoomable": false
    },
    "categoryField": "bandGroup",
    "categoryAxis": {
        "gridPosition": "start",
        "gridAlpha": 0,
         "labelRotation" : 45,
    },
});
}
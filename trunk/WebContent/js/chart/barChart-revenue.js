function fillCharts(inData,mode){

    if(mode == 'Clustered'){
        valueAxes = [{"gridColor":"#FFFFFF","gridAlpha": 0.2,"dashLength": 0,"labelsEnabled": true,"position": "left"}]
    }else if(mode == 'Stacked'){
        valueAxes = [{"stackType": "regular","axisAlpha": 0.3,"gridAlpha": 0,"labelsEnabled": true,"position": "left"}]
    }else{
        valueAxes = [{"stackType": "100%","axisAlpha": 0,"gridAlpha": 0,"labelsEnabled": true,"position": "left"}]
    }
        grapFunction(inData ,valueAxes);
}

// function for the usage 
function grapFunction(inData,valueAxes){
    var unit = "currency";
     if(inData.length > 1)
        unit = inData[0].cur; 

    var chart = AmCharts.makeChart("chart_1", {
    "type": "serial",
    "theme": "light",
    "dataProvider": inData,
    "valueAxes": valueAxes,
    "gridAboveGraphs": true,
    "startDuration": 1,
    "graphs":[{
        "balloonText": "Actual ("+ unit+ ") : <b>[[value]]</b>",
        "fillColors":"#67809F",
        "fillAlphas": 0.8,
        "lineAlpha": 0.2,
        "type": "column",
        "valueField": "actualRevenue"     
    },{
        "balloonText": "Commited ("+ unit+ ") : <b>[[value]]</b>",
        "fillColors":"#E87E04",
        "fillAlphas": 0.8,
        "lineAlpha": 0.2,
        "type": "column",
        "valueField": "commitedRevenue"      
    }],
    "chartCursor": {
        "categoryBalloonEnabled": false,
        "cursorAlpha": 0,
        "zoomable": false
    },
    "categoryField": "direction",
    "categoryAxis": {
        "gridPosition": "start",
        "gridAlpha": 0,
         "labelRotation" : 45,
    },
});
}
(function (reports) {
    "use strict";

    initMusicSummary();
    initSilenceSummary();
    reports.$filterDateInput.addEventListener('input', initMusicSummary);
    reports.$filterDateInput.addEventListener('input', initSilenceSummary);

    function initMusicSummary() {
        // this month
        google.charts.load('current', {'packages': ['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            console.log("drawChart");

            // Create the data table.
            var data = new google.visualization.arrayToDataTable([
                ['Day', 'Activity', 'Music', 'Silient'],
                ['1', 12, 3, 4],
                ['2', 3, 3-1, 1],
                ['3', 11, 4-1, 4],
                ['4', 2, 2-1, 1],
                ['5', 1, 1-1, 3],
                ['6', 3, 3-1, 1],
                ['7', 4, 4-1, 1],
                ['8', 5, 5-1, 0.5]
            ]);

            var options = {
                title : 'Monthly activity summary',
                vAxis: {title: 'Hours'},
                hAxis: {title: 'Month'},
                seriesType: 'bars',
                series: {2: {type: 'line'}}
            };

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.ComboChart(document.getElementById('reports-music-ratio'));
            chart.draw(data, options);
        }
    }

    function initSilenceSummary() {
        // this month
        google.charts.load('current', {'packages': ['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            console.log("drawChart");

            // Create the data table.
            var data = new google.visualization.arrayToDataTable([
                ['Day', 'Silient'],
                ['1', 74],
                ['2', 44],
                ['3', 81],
                ['4', 23],
                ['5', 23],
                ['6', 12],
                ['7', 11],
                ['8', 55]
            ]);

            var options = {
                title : 'Silience %',
                vAxis: {title: 'Hours'},
                hAxis: {title: 'Month'},
                seriesType: 'bars'
            };

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.ComboChart(document.getElementById('reports-silence-ratio'));
            chart.draw(data, options);
        }
    }

})(window.app.reports);
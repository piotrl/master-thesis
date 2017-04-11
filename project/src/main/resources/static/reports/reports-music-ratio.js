(function (reports) {
    "use strict";
    google.charts.load('current', {'packages': ['corechart']});

    initMusicSummary();
    initSilenceSummary();
    reports.$filterDateInput.addEventListener('input', initMusicSummary);
    reports.$filterDateInput.addEventListener('input', initSilenceSummary);

    function initMusicSummary() {
        // this month
        const date = dateFns.parse(reports.$filterDateInput.value);
        const year = dateFns.getYear(date);
        const month = dateFns.getMonth(date) + 1;
        fetch(`http://localhost:8080/api/stats/music/year/${year}/month/${month}/summary`)
            .then(response => response.json())
            .then(data => {
                google.charts.setOnLoadCallback(drawChart(data));
            });
        // Set a callback to run when the Google Visualization API is loaded.

        function drawChart(stats) {
            stats = stats.map((column => {
                return [dateFns.format(dateFns.parse(column.timestamp), 'MM-DD'), column.activity, column.music, column.salience];
            }));            // Create the data table.

            const options = {
                title: 'Monthly activity summary',
                vAxis: {title: 'Hours'},
                hAxis: {title: 'Days'},
                seriesType: 'bars',
                series: {2: {type: 'line'}}
            };

            // Instantiate and draw our chart, passing in some options.
            return () => {
                const data = new google.visualization.arrayToDataTable([
                    ['Day', 'Activity', 'Music', 'Silient'],
                    ... stats
                ]);
                const chart = new google.visualization.ComboChart(document.getElementById('reports-music-ratio'));
                chart.draw(data, options);
            };
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
                ['Day', 'Silient', 'Productivity'],
                ['1', 74, 41],
                ['2', 44, 12],
                ['3', 81, 11],
                ['4', 23, 55],
                ['5', 23, 66],
                ['6', 12, 12],
                ['7', 11, 81],
                ['8', 55, 91]
            ]);

            var options = {
                title : 'Silience %',
                vAxis: {title: 'Hours'},
                hAxis: {title: 'Days'},
                seriesType: 'bars'
            };

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.ComboChart(document.getElementById('reports-silence-ratio'));
            chart.draw(data, options);
        }
    }

})(window.app.reports);
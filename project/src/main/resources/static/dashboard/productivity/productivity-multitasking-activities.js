(function (productivity) {
    "use strict";
    google.charts.load('current', {'packages': ['corechart']});

    initMultitaskingChart();
    initSpentTimeTasksScatterChart();
    productivity.$filterDateInput.addEventListener('input', initMultitaskingChart);
    productivity.$filterDateInput.addEventListener('input', initSpentTimeTasksScatterChart);
    // document.querySelector("[href='#panel-music-ratio']").addEventListener("click", initMultitaskingChart);


    function initMultitaskingChart() {
        // this month
        const date = dateFns.parse(productivity.$filterDateInput.value);
        const year = dateFns.format(date, "YYYY");
        const month = dateFns.format(date, "MM");
        const day = dateFns.format(date, "DD");
        fetch(`http://localhost:8080/api/stats/activities/year/${year}/month/${month}/day/${day}/multitasking`)
            .then(response => response.json())
            .then(data => {
                google.charts.setOnLoadCallback(drawChart(data, 'productivity-multitasking-activities'));
            });
    }

    function initSpentTimeTasksScatterChart() {
        const date = dateFns.parse(productivity.$filterDateInput.value);
        const year = dateFns.format(date, "YYYY");
        const month = dateFns.format(date, "MM");
        const day = dateFns.format(date, "DD");
        fetch(`http://localhost:8080/api/stats/activities/year/${year}/month/${month}/day/${day}/spentTimeTasksScatter`)
            .then(response => response.json())
            .then(data => {
                google.charts.setOnLoadCallback(drawScatterChart(data.productive, 'activities-multitasking-scatter-productive'));
                google.charts.setOnLoadCallback(drawScatterChart(data.distraction, 'activities-multitasking-scatter-distraction'));
                google.charts.setOnLoadCallback(drawScatterChart(data.neutral, 'activities-multitasking-scatter-neutral'));
                google.charts.setOnLoadCallback(drawProductivityDonut(data, 'activities-multitasking-donut'));
            });
    }

    function drawChart(stats, id) {
        stats = stats.map(column => {
            return [dateFns.format(dateFns.parse(column.date), 'HH:mm'), column.activitiesCount, column.productive];
        });

        const options = {
            vAxis: {title: 'Hours'},
            hAxis: {title: 'Days'},
            height: 400,
            seriesType: 'bars',
            series: {1: {
                type: 'line',
                curveType: 'function',
                color: 'black'
            }}
        };

        // Instantiate and draw our chart, passing in some options.
        return () => {
            const data = new google.visualization.arrayToDataTable([
                ['Hours', 'Tasks', 'Productivity'],
                ...stats
            ]);
            const chart = new google.visualization.ComboChart(document.getElementById(id));
            chart.draw(data, options);
        };
    }

    function drawScatterChart(stats, id) {
        var options = {
            vAxis: {title: 'Task switches in 15min', minValue: 0, maxValue: 20},
            hAxis: {title: 'Productive spent [minutes]', minValue: 0, maxValue: 15},
            legend: 'none',
            trendlines: {
                0: {
                    type: 'exponential',
                    showR2: true,
                    pointsVisible: false
                }
            }

        };

        stats = stats.map(column => {
            return [column.sumTimeByPeriod / 60.0, column.tasksInPeriod];
        });

        return () => {
            const data = new google.visualization.arrayToDataTable([
                ['Productive spent', 'Tasks'],
                ...stats
            ]);
            const chart = new google.visualization.ScatterChart(document.getElementById(id));
            chart.draw(data, options);
        };
    }

    function drawProductivityDonut(stats, id) {
        var options = {
            pieHole: 0.5,
            pieSliceText: 'none'
        };

        return () => {
            const data = new google.visualization.arrayToDataTable([
                ['Group', 'Activities'],
                ['Productive', calcMinutes(stats.productive)],
                ['Distractions', calcMinutes(stats.distraction)],
                ['Neutral', calcMinutes(stats.neutral)]
            ]);
            const chart = new google.visualization.PieChart(document.getElementById(id));
            chart.draw(data, options);

            function calcMinutes(arrayScatter) {
                return arrayScatter.reduce((acc, val) => acc + val.activityTime, 0) / 60.0;
            }
        };
    }

})(window.app.productivity);
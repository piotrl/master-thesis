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
                google.charts.setOnLoadCallback(drawScatterChart(data, 'activities-multitasking-scatter'));
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
            series: {1: {type: 'line'}}
        };

        // Instantiate and draw our chart, passing in some options.
        return () => {
            const data = new google.visualization.arrayToDataTable([
                ['Hours', 'Tasks', 'Productivity'],
                ... stats
            ]);
            const chart = new google.visualization.ComboChart(document.getElementById(id));
            chart.draw(data, options);
        };
    }

    function drawScatterChart(stats, id) {
        stats = stats.map(column => {
            return [column.sumTimeIn5min / 60.0, column.tasksInPeriod];
        });

        var options = {
            title: 'Tasks in 15min period vs. Activity time',
            vAxis: {title: 'Tasks', minValue: 0, maxValue: 50},
            hAxis: {title: 'Productive spent', minValue: 0, maxValue: 15},
            legend: 'none'
        };

        // Instantiate and draw our chart, passing in some options.
        return () => {
            const data = new google.visualization.arrayToDataTable([
                ['Productive spent', 'Tasks'],
                ... stats
            ]);
            const chart = new google.visualization.ScatterChart(document.getElementById(id));
            chart.draw(data, options);
        };
    }

})(window.app.productivity);
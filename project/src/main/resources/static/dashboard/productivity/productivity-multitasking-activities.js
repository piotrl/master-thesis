(function (productivity) {
    "use strict";
    google.charts.load('current', {'packages': ['corechart']});

    initMultitaskingChart();
    productivity.$filterDateInput.addEventListener('input', initMultitaskingChart);
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

    function drawChart(stats, id) {
        stats = stats.map(column => {
            return [dateFns.format(dateFns.parse(column.date), 'HH:mm'), column.activitiesCount, column.productive];
        });
        console.log('stats', stats);

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

})(window.app.productivity);
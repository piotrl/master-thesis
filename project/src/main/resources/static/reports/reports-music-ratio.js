(function (reports) {
    "use strict";
    google.charts.load('current', {'packages': ['corechart']});

    initMusicSummary();
    musicPlayedDuringActivities();
    reports.$filterDateInput.addEventListener('input', initMusicSummary);
    reports.$filterDateInput.addEventListener('input', musicPlayedDuringActivities);
    document.querySelector("[href='#panel-music-ratio']").addEventListener("click", initMusicSummary);
    document.querySelector("[href='#reports-music-during-activities']").addEventListener("click", musicPlayedDuringActivities);


    function initMusicSummary() {
        // this month
        const date = dateFns.parse(reports.$filterDateInput.value);
        const year = dateFns.getYear(date);
        const month = dateFns.getMonth(date) + 1;
        fetch(`http://localhost:8080/api/stats/music/year/${year}/month/${month}/summary`)
            .then(response => response.json())
            .then(data => {
                google.charts.setOnLoadCallback(drawChart(data, 'reports-music-ratio'));
            });
    }

    function musicPlayedDuringActivities() {
        const date = dateFns.parse(reports.$filterDateInput.value);
        const year = dateFns.getYear(date);
        const month = dateFns.getMonth(date) + 1;
        fetch(`http://localhost:8080/api/stats/music/year/${year}/month/${month}/musicPlayedDuringActivities`)
            .then(response => response.json())
            .then(data => {
                google.charts.setOnLoadCallback(drawChart(data, 'reports-music-during-activities'));
            });
    }

    function drawChart(stats, id) {
        stats = stats.map(column => {
            return [dateFns.format(dateFns.parse(column.timestamp), 'MM-DD'), column.activity, column.music, column.salience];
        });

        const options = {
            vAxis: {title: 'Hours'},
            hAxis: {title: 'Days'},
            height: 400,
            seriesType: 'bars',
            series: {2: {type: 'line'}}
        };

        // Instantiate and draw our chart, passing in some options.
        return () => {
            const data = new google.visualization.arrayToDataTable([
                ['Day', 'Activity', 'Music', 'Salience'],
                ... stats
            ]);
            const chart = new google.visualization.ComboChart(document.getElementById(id));
            chart.draw(data, options);
        };
    }

})(window.app.reports);
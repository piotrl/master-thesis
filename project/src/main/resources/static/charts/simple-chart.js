var chart = c3.generate({
    bindto: '#chart-day',
    data: {
        columns: [
            ['Music', 30, 200, 100, 400, 150, 250],
            ['Activity', 50, 20, 10, 40, 15, 25]
        ]
    }
});

(function () {
    document.addEventListener('DOMContentLoaded', () => {
        const year = 2017;
        const month = 2;
        const montlyChart = `http://localhost:8080/api/raw/activities/year/${year}/month/${month}`;

        fetch(montlyChart)
            .then(res => res.json())
            .then(activitiesInMonth => {
                console.log(activitiesInMonth);
                c3.generate({
                    bindto: '#chart-month',
                    data: {
                        columns: [
                            ['Activity', ...activitiesInMonth]
                        ]
                    }
                });
            })

    });
})();
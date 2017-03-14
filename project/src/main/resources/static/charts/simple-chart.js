const chart = c3.generate({
    bindto: '#chart-day',
    data: {
        columns: [
            ['Music', 30, 200, 100, 400, 150, 250],
            ['Activity', 50, 20, 10, 40, 15, 25]
        ]
    }
});

(function () {
    // this month
    document.addEventListener('DOMContentLoaded', () => {
        const date = new Date();
        const year = date.getFullYear();
        const month = date.getMonth();

        const monthlyActivityChart = `http://localhost:8080/api/raw/activities/year/${year}/month/${month}`;

        fetch(monthlyActivityChart)
            .then(res => res.json())
            .then(activitiesInMonth => {
                console.log(activitiesInMonth);
                c3.generate({
                    bindto: '#chart-month',
                    data: {
                        type: 'scatter',
                        columns: [
                            ['Very distracting', ...activitiesInMonth[-2]],
                            ['Distracting', ...activitiesInMonth[-1]],
                            ['Neutral', ...activitiesInMonth[0]],
                            ['Productive', ...activitiesInMonth[1]],
                            ['Very productive', ...activitiesInMonth[2]]
                        ]
                    },
                    axis: {
                        x: {
                            label: 'Dni w miesiącu',
                            tick: {
                                fit: false
                            }
                        },
                        y: {
                            label: 'Spędzone godziny'
                        }
                    }
                });
            })

    });
})();
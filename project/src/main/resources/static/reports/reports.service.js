(function (app) {
    app.reports = {
        filterDate: new Date()
    };

    const $input = document.getElementById("filter-month");
    init();

    function init() {
        updateInput(app.reports.filterDate);
        const $buttonRight = document.getElementById("filter-button-right");
        const $buttonLeft = document.getElementById("filter-button-left");

        $buttonLeft.addEventListener('click', function() {
            app.reports.filterDate = dateFns.subMonths(app.reports.filterDate, 1);
            updateInput(app.reports.filterDate);
        });

        $buttonRight.addEventListener('click', function() {
            app.reports.filterDate = dateFns.addMonths(app.reports.filterDate, 1);
            updateInput(app.reports.filterDate);
        });
    }

    function updateInput(date) {
        console.log(date);
        $input.value = dateFns.format(date, 'YYYY-MM');
    }
})(window.app || {});
(function (app) {
    app.reports = {
        filterDate: new Date(),
        $filterDateInput: document.getElementById("filter-month")
    };
    const reports = app.reports;

    reports.$filterDateInput.addEventListener('input', () => {
        reports.filterDate = dateFns.parse(reports.$filterDateInput.value);
    });

    init();

    function init() {
        updateInput(app.reports.filterDate);
        const $buttonRight = document.getElementById("filter-button-right");
        const $buttonLeft = document.getElementById("filter-button-left");

        $buttonLeft.addEventListener('click', function() {
            reports.filterDate = dateFns.subMonths(app.reports.filterDate, 1);
            updateInput(app.reports.filterDate);
        });

        $buttonRight.addEventListener('click', function() {
            reports.filterDate = dateFns.addMonths(app.reports.filterDate, 1);
            updateInput(app.reports.filterDate);
        });
    }

    function updateInput(date) {
        reports.$filterDateInput.value = dateFns.format(date, 'YYYY-MM');
        var event = new Event('input');
        reports.$filterDateInput.dispatchEvent(event);
    }
})(window.app || {});
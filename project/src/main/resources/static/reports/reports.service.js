(function (app) {
    app.reports = {
        filterDate: new Date()
    };

    const $input = document.getElementById("filter-month");
    $input.value = dateFns.format(app.reports.filterDate, 'YYYY-MM');
})(window.app || {});
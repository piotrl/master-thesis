(function (app) {
    app.productivity = {
        filterDate: new Date(),
        $filterDateInput: document.getElementById("filter-day")
    };
    const vm = app.productivity;

    vm.$filterDateInput.addEventListener('input', () => {
        vm.filterDate = dateFns.parse(vm.$filterDateInput.value);
    });

    init();

    function init() {
        updateInput(vm.filterDate);
        const $buttonRight = document.getElementById("filter-button-right");
        const $buttonLeft = document.getElementById("filter-button-left");

        $buttonLeft.addEventListener('click', function () {
            vm.filterDate = dateFns.subDays(vm.filterDate, 1);
            updateInput(vm.filterDate);
        });

        $buttonRight.addEventListener('click', function () {
            vm.filterDate = dateFns.addDays(vm.filterDate, 1);
            updateInput(vm.filterDate);
        });
    }

    function updateInput(date) {
        vm.$filterDateInput.value = dateFns.format(date, 'YYYY-MM-DD');
        const event = new Event('input');
        vm.$filterDateInput.dispatchEvent(event);
    }
})(window.app || {});
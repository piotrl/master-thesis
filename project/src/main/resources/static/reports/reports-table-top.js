(function (reports) {
    "use strict";
    google.charts.load('current', {'packages': ['corechart']});

    initTopTags();
    initTopArtists();
    reports.$filterDateInput.addEventListener('input', initTopTags);
    reports.$filterDateInput.addEventListener('input', initTopArtists);

    function initTopTags() {
        // this month
        const date = dateFns.parse(reports.$filterDateInput.value);
        const year = dateFns.getYear(date);
        const month = dateFns.getMonth(date) + 1;
        fetch(`http://localhost:8080/api/stats/tags/year/${year}/month/${month}/popular`)
            .then(response => response.json())
            .then(data => {
                drawTagsTable(data);
            });
    }

    function initTopArtists() {
        const date = dateFns.parse(reports.$filterDateInput.value);
        const year = dateFns.getYear(date);
        const month = dateFns.getMonth(date) + 1;
        fetch(`http://localhost:8080/api/stats/artists/year/${year}/month/${month}/popular`)
            .then(response => response.json())
            .then(drawArtistsTable);
    }

    function drawTagsTable(rows) {
        const htmlRows = rows.map(row => {
            return `
                    <tr>
                        <td class="mdl-data-table__cell--non-numeric">${row.name}</td>
                        <td>${row.playedTimes}</td>
                    </tr>
            `
        });

        document.querySelector("#table-top-tags tbody").innerHTML = htmlRows.join("");
    }

    function drawArtistsTable(rows) {
        const htmlRows = rows.map(row => {
            return `
                    <tr>
                        <td><img src="${row.imageUrl}" alt="-" /></td>
                        <td class="mdl-data-table__cell--non-numeric">${row.name}</td>
                        <td>${row.playedTimes}</td>
                    </tr>
            `
        });

        document.querySelector("#table-top-artists tbody").innerHTML = htmlRows.join("");
    }

})(window.app.reports);
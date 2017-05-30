(function (reports, http) {
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
        http.get(`./api/stats/tags/year/${year}/month/${month}/popular`)
            .then(data => {
                drawTagsTable(data);
            });
    }

    function initTopArtists() {
        const date = dateFns.parse(reports.$filterDateInput.value);
        const year = dateFns.getYear(date);
        const month = dateFns.getMonth(date) + 1;
        http.get(`./api/stats/artists/year/${year}/month/${month}/popular`)
            .then(drawArtistsTable);
    }

    function drawTagsTable(rows) {
        const htmlRows = rows.map(row => {
            let hours = Math.floor(row.duration / 60);
            let minutes = row.duration - (hours * 60);
            return `
                    <tr>
                        <td class="mdl-data-table__cell--non-numeric">${row.name}</td>
                        <td>${row.playedTimes}</td>
                        <td>${(hours > 0) ? hours + "h " + minutes + "min" : minutes + "min"}</td>

                    </tr>
            `
        });

        document.querySelector("#table-top-tags tbody").innerHTML = htmlRows.join("");
    }

    function drawArtistsTable(rows) {
        const htmlRows = rows.map(row => {
            let hours = Math.floor(row.duration / 60);
            let minutes = row.duration - (hours * 60);
            return `
                    <tr>
                        <td><img src="${row.imageUrl}" alt="-" class="mdl-list__item-icon" /></td>
                        <td class="mdl-data-table__cell--non-numeric">${row.name}</td>
                        <td>${row.playedTimes}</td>
                        <td>${(hours > 0) ? hours + "h " + minutes + "min" : minutes + "min"}</td>
                    </tr>
            `
        });

        document.querySelector("#table-top-artists tbody").innerHTML = htmlRows.join("");
    }

})(window.app.reports, window.app.http);
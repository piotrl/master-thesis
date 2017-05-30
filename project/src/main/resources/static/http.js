(function (app) {
    let pendingRequests = 0;
    const $loader = document.querySelector(".loader");

    app.http = {
        get: function get(url) {
            addLoader();

            var request = new Request(url, {
                method: 'GET',
                mode: 'same-origin',
                credentials: 'same-origin', // sent auth cookies over REST API
                redirect: 'follow'
            });

            try {
                return fetch(request)
                    .then(response => {
                        removeLoader();
                        return response;
                    })
                    .then(response => response.json())
                    .catch(error => {
                        removeLoader();
                        showNotification(error);
                        throw error;
                    })
            } catch (e) {
                removeLoader();
                showNotification(e);
                console.error("Http error", e);
            }
        }
    };

    function removeLoader() {
        pendingRequests--;
        if (pendingRequests <= 0) {
            $loader.classList.remove("overlay");
        }
    }

    function addLoader() {
        pendingRequests++;
        $loader.classList.add("overlay");
    }

    function showNotification(e) {
        var notification = document.querySelector('.mdl-js-snackbar');
        notification.MaterialSnackbar.showSnackbar(
            {
                message: "Error: " + e
            }
        );

    }

})(window.app);
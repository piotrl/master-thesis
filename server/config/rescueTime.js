var apiKey = 'B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe';

var urls = {
    data: 'https://www.rescuetime.com/anapi/data'
};

var defaultParams = {
    key: apiKey,
    format: 'json',
    perspective: 'interval'
};

module.exports = {
    urls: urls,
    apiKey: apiKey,
    dataUrl: function(params) {
        var url = this.urls.data;
        return url + prepareQuery(params)
    }
};

function prepareQuery(params) {
    var queryString = '?';

    for (var defaultKey in defaultParams) {
        queryString += (queryString === '?') ? '' : '&';
        queryString += defaultKey + '=' + defaultParams[defaultKey];
    }

    for (var key in params) {
        queryString += (queryString === '?') ? '' : '&';
        queryString += key + '=' + params[key];
    }

    return queryString;
}
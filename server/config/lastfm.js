var LastFmNode = require('lastfm').LastFmNode;

module.exports = {
    api_key: '5f062176c25b0c3570a65bca887188f8',
    secret: '680127560c4190a9ce1c1785fb82d684',
    username: 'grovman',
    connect: function() {
        return connectTo(this.api_key, this.secret);
    }
};

function connectTo(api_key, secret) {
    return new LastFmNode({
        'api_key' : api_key,
        'secret' : secret
    });
}


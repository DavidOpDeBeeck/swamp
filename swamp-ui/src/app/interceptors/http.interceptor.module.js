import Logger from "../common/logger";

function RequestConverter() {
    return {
        response(response) {
            let url = response.config.url;
            if (url.startsWith("/api"))
                response.data = response.data.data;
            return response;
        }
    };
}

function RequestLogger() {
    return {
        response(response) {
            let url = response.config.url;
            if (url.startsWith("/api"))
                Logger.log("Loaded API data from " + url);
            if (url.endsWith(".html"))
                Logger.log("Loaded template from " + url);
            return response;
        }
    };
}

function RequestProvider($httpProvider) {
    $httpProvider.interceptors.push('RequestConverter');
    $httpProvider.interceptors.push('RequestLogger');
}

export default angular.module('swamp.interceptors', [])
    .factory('RequestLogger', RequestLogger)
    .factory('RequestConverter', RequestConverter)
    .config(['$httpProvider', RequestProvider])
    .name;
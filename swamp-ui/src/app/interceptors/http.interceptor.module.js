import Logger from "../common/logger";

function RequestConverter() {
    return {
        response(response) {
            let data = response.data;
            let url = response.config.url;
            if (url.startsWith("/api")){
                response.errors = data.errors;
                response.data = data.data;
            }
            return response;
        }
    };
}

function RequestLogger() {
    return {
        response(response) {
            let url = response.config.url;
            if (url.startsWith("/api"))
                Logger.info("Loaded API data from " + url);
            if (url.endsWith(".html"))
                Logger.info("Loaded template from " + url);
            if (response.errors)
                Logger.error(response.errors);
            return response;
        }
    };
}

function RequestProvider($httpProvider) {
    $httpProvider.interceptors.push('RequestLogger');
    $httpProvider.interceptors.push('RequestConverter');
}

export default angular.module('swamp.interceptors', [])
    .factory('RequestLogger', RequestLogger)
    .factory('RequestConverter', RequestConverter)
    .config(['$httpProvider', RequestProvider])
    .name;
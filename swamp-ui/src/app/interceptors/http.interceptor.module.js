import Angular from "angular";
import Logger from "domain/logger";

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

export default Angular.module('swamp.interceptors', [])
    .factory('RequestLogger', RequestLogger)
    .factory('RequestConverter', RequestConverter)
    .config(['$httpProvider', RequestProvider])
    .name;
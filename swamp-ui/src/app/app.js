import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import uiRouter from "angular-ui-router";
import services from "./services/services.module";
import layout from "./components/layout/layout.module";
import shared from "./components/shared/shared.module";
import home from "./components/home/home.module";
import projects from "./components/projects/projects.module";
import locations from "./components/locations/locations.module";

const app = angular.module('swamp', [
    uiRouter,
    uiBootstrap,
    services,
    layout,
    shared,
    home,
    projects,
    locations
]);

app.config(['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => $urlRouterProvider.otherwise("/")]);

app.run(['$rootScope', ($rootScope) => {
    $rootScope.$on('$stateChangeSuccess', (event, to, toParams, from, fromParams) => {
        $rootScope.$previousState = {
            name: from.name,
            params: fromParams
        };
    });
}]);

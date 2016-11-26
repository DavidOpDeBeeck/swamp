import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import uiRouter from "angular-ui-router";
import "ng-stomp";
import httpInterceptor from "./interceptors/http.interceptor.module";
import field from "./field/field.module";
import AppRoute from "./app.route";
import AppConfig from "./app.config";
import services from "./services/services.module";
import layout from "./components/layout/layout.module";
import shared from "./components/shared/shared.module";
import home from "./components/home/home.module";
import projects from "./components/projects/projects.module";
import locations from "./components/locations/locations.module";
import scheduler from "./components/scheduler/scheduler.module";

angular.module('swamp', [
        'ngStomp',
        uiRouter,
        uiBootstrap,
        httpInterceptor,
        field,
        services,
        layout,
        shared,
        home,
        projects,
        locations,
        scheduler
    ])
    .config(AppRoute)
    .run(AppConfig);
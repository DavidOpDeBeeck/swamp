import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import uiRouter from "angular-ui-router";
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
        uiRouter,
        uiBootstrap,
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

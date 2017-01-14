import Angular from "angular";
import UIRouter from "angular-ui-router";
import UIBootstrap from "angular-ui-bootstrap";
import "ng-stomp";
import HttpInterceptor from "./interceptors/http.interceptor.module";
import Filters from "./filters/filters.module";
import AppRoute from "./app.route";
import AppConfig from "./app.config";
import Services from "./services/services.module";
import Field from "./components/fields/fields.module";
import Layout from "./components/layout/layout.module";
import Shared from "./components/shared/shared.module";
import Home from "./components/home/home.module";
import Projects from "./components/projects/projects.module";
import Locations from "./components/locations/locations.module";
import ProjectInstances from "./components/project-instances/project-instances.module";
import Version from "./components/version/version.module";

let module = Angular.module('swamp', [
        'ngStomp',
        UIRouter,
        UIBootstrap,
        HttpInterceptor,
        Filters,
        Services,
        Field,
        Layout,
        Shared,
        Home,
        Projects,
        Locations,
        ProjectInstances,
        Version
    ])
    .config(AppRoute);

module.run(AppConfig);
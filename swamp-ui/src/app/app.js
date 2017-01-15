import Angular from "angular";
import UIRouter from "angular-ui-router";
import UIBootstrap from "angular-ui-bootstrap";
import UINotification from "angular-ui-notification";
import "ng-stomp";
import HttpInterceptor from "./interceptors/http.interceptor.module";
import Filters from "./filters/filters.module";
import AppConfigs from "./configs/configs.module";
import AppRoute from "./app.route";
import Services from "./services/services.module";
import Field from "./components/fields/fields.module";
import Layout from "./components/layout/layout.module";
import Shared from "./components/shared/shared.module";
import Home from "./components/home/home.module";
import Projects from "./components/projects/projects.module";
import Locations from "./components/locations/locations.module";
import ProjectInstances from "./components/project-instances/project-instances.module";
import Version from "./components/version/version.module";

const module = Angular.module('swamp', [
        'ngStomp',
        UIRouter,
        UIBootstrap,
        UINotification,
        HttpInterceptor,
        Filters,
        Services,
        AppConfigs,
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
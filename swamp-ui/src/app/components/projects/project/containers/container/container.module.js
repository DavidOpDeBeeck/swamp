import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../../../services/services.module";
import ContainerRoute from "./container.route";
import ContainerEditRoute from "./edit/container-edit.route";
import ContainerDirective from "./container.directive";

const module = angular.module('swamp.container', [services, uiBootstrap])
    .directive("container", ContainerDirective)
    .config(ContainerRoute)
    .config(ContainerEditRoute);

export default module.name;

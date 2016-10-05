import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../../../services/services.module";
import ContainerRoute from "./container.route";
import ContainerEditRoute from "./edit/container-edit.route";

const module = angular.module('swamp.container', [services, uiBootstrap])
    .config(ContainerRoute)
    .config(ContainerEditRoute);

export default module.name;

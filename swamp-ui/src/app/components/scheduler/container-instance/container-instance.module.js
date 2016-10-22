import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../services/services.module";
import ContainerInstanceDirective from "./container-instance.directive";

const module = angular.module('swamp.scheduler.container-instance', [services, uiBootstrap])
    .directive('containerInstance', ContainerInstanceDirective);

export default module.name;

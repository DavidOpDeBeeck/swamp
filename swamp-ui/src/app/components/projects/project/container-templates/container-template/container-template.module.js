import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../../../services/services.module";
import ContainerTemplateRoute from "./container-template.route";
import ContainerTemplateDirective from "./container-template.directive";

const module = Angular.module('swamp.container-template', [UIBootstrap, Services])
    .directive("containerTemplate", ContainerTemplateDirective)
    .config(ContainerTemplateRoute);

export default module.name;

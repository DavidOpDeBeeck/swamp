import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../../../services/services.module";
import ContainerRoute from "./container.route";
import ContainerDirective from "./container.directive";

const module = Angular.module('swamp.container', [UIBootstrap, Services])
    .directive("container", ContainerDirective)
    .config(ContainerRoute);

export default module.name;

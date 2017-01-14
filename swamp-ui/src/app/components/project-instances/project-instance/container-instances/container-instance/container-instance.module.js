import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../../../services/services.module";
import ContainerInstanceDirective from "./container-instance.directive";

const module = Angular.module('swamp.projectInstances.projectInstance.containerInstances.containerInstance', [UIBootstrap, Services])
    .directive('containerInstance', ContainerInstanceDirective);

export default module.name;

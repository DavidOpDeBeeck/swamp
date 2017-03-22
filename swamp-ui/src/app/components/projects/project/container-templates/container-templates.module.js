import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../../services/services.module";
import ContainerTemplate from "./container-template/container-template.module";
import ContainerTemplatesRoute from "./container-templates.route";

const module = Angular.module('swamp.container-templates', [UIBootstrap, Services, ContainerTemplate])
    .config(ContainerTemplatesRoute);

export default module.name;

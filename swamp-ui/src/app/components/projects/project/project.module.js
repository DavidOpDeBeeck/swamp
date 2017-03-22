import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../services/services.module";
import ContainerTemplates from "./container-templates/container-templates.module";
import ProjectRoute from "./project.route";

const module = Angular.module('swamp.project', [UIBootstrap, Services, ContainerTemplates])
    .config(ProjectRoute);

export default module.name;

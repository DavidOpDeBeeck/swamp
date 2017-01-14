import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../services/services.module";
import Containers from "./containers/containers.module";
import ProjectRoute from "./project.route";

const module = Angular.module('swamp.project', [UIBootstrap, Services, Containers])
    .config(ProjectRoute);

export default module.name;

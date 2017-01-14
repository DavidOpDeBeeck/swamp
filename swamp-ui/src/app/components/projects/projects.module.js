import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../services/services.module";
import Project from "./project/project.module";
import ProjectsRoute from "./projects.route";

const module = Angular.module('swamp.projects', [UIBootstrap, Services, Project])
    .config(ProjectsRoute);

export default module.name;

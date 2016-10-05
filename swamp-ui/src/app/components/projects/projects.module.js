import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../services/services.module";
import Project from "./project/project.module";
import ProjectsRoute from "./projects.route";
import ProjectsCreateRoute from "./create/projects-create.route";

const module = angular.module('swamp.projects', [services, uiBootstrap, Project])
    .config(ProjectsRoute)
    .config(ProjectsCreateRoute);

export default module.name;

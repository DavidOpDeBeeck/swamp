import angular from "angular";
import Project from "./project/project.module";
import ProjectsRoute from "./projects.route";
import ProjectsCreateRoute from "./create/projects-create.route";
import ProjectsController from "./projects.controller";
import ProjectsCreateController from "./create/projects-create.controller";

const module = angular.module('swamp.projects', ['swamp.services', 'ui.bootstrap', Project])
    .controller('ProjectsController', ProjectsController)
    .controller('ProjectsCreateController', ProjectsCreateController)
    .config(ProjectsRoute)
    .config(ProjectsCreateRoute);

export default module.name;

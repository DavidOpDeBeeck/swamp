import angular from "angular";
import ProjectsRoute from "./projects.route";
import ProjectsCreateRoute from "./create/projects-create.route";
import ProjectsEditRoute from "./edit/projects-edit.route";
import ContainersRoute from "./containers/containers.route";
import ContainersCreateRoute from "./containers/create/containers-create.route";
import ProjectsController from "./projects.controller";
import ProjectsCreateController from "./create/projects-create.controller";
import ProjectsEditController from "./edit/projects-edit.controller";
import ContainersController from "./containers/containers.controller";
import ContainersCreateController from "./containers/create/containers-create.controller";

const module = angular.module('swamp.projects', ['swamp.services', 'ui.bootstrap'])
    .controller('ProjectsController', ProjectsController)
    .controller('ProjectsCreateController', ProjectsCreateController)
    .controller('ProjectsEditController', ProjectsEditController)
    .controller('ContainersController', ContainersController)
    .controller('ContainersCreateController', ContainersCreateController)
    .config(ProjectsRoute)
    .config(ProjectsCreateRoute)
    .config(ProjectsEditRoute)
    .config(ContainersRoute)
    .config(ContainersCreateRoute);

export default module.name;

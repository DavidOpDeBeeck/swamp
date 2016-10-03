import angular from "angular";
import ProjectRoute from "./project.route";
import ProjectEditRoute from "./edit/project-edit.route";
import ContainersRoute from "./containers/containers.route";
import ContainersCreateRoute from "./containers/create/containers-create.route";
import ProjectEditController from "./edit/project-edit.controller";
import ContainersController from "./containers/containers.controller";
import ContainersCreateController from "./containers/create/containers-create.controller";

const module = angular.module('swamp.project', ['swamp.services', 'ui.bootstrap'])
    .controller('ProjectEditController', ProjectEditController)
    .controller('ContainersController', ContainersController)
    .controller('ContainersCreateController', ContainersCreateController)
    .config(ProjectRoute)
    .config(ProjectEditRoute)
    .config(ContainersRoute)
    .config(ContainersCreateRoute);

export default module.name;

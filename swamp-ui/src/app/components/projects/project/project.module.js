import angular from "angular";
import Containers from "./containers/containers.module";
import ProjectRoute from "./project.route";
import ProjectEditRoute from "./edit/project-edit.route";
import ProjectEditController from "./edit/project-edit.controller";

const module = angular.module('swamp.project', ['swamp.services', 'ui.bootstrap', Containers])
    .controller('ProjectEditController', ProjectEditController)
    .config(ProjectRoute)
    .config(ProjectEditRoute);

export default module.name;

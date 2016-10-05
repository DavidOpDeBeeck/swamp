import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../services/services.module";
import Containers from "./containers/containers.module";
import ProjectRoute from "./project.route";
import ProjectEditRoute from "./edit/project-edit.route";

const module = angular.module('swamp.project', [services, uiBootstrap, Containers])
    .config(ProjectRoute)
    .config(ProjectEditRoute);

export default module.name;

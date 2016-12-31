import angular from "angular";
import services from "../../services/services.module";
import ProjectInstancesRoute from "./project-instances.route";
import ProjectInstance from "./project-instance/project-instance.module";

const module = angular.module('swamp.projectInstances', [services, ProjectInstance])
    .config(ProjectInstancesRoute);

export default module.name;

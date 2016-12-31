import angular from "angular";
import services from "../../../services/services.module";
import ContainerInstances from "./container-instances/container-instances.module";
import ProjectInstanceRoute from "./project-instance.route";

const module = angular.module('swamp.projectInstances.projectInstance', [services, ContainerInstances])
    .config(ProjectInstanceRoute);

export default module.name;

import angular from "angular";
import services from "../../../../services/services.module";
import ContainerInstance from "./container-instance/container-instance.module";
import ContainerInstancesRoute from "./container-instances.route";

const module = angular.module('swamp.projectInstances.projectInstance.containerInstances', [services, ContainerInstance])
    .config(ContainerInstancesRoute);

export default module.name;

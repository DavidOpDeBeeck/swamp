import Angular from "angular";
import Services from "../../../../services/services.module";
import ContainerInstance from "./container-instance/container-instance.module";
import ContainerInstancesRoute from "./container-instances.route";

const module = Angular.module('swamp.projectInstances.projectInstance.containerInstances', [Services, ContainerInstance])
    .config(ContainerInstancesRoute);

export default module.name;

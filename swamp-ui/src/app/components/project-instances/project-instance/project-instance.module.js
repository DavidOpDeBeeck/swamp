import Angular from "angular";
import Services from "../../../services/services.module";
import ContainerInstances from "./container-instances/container-instances.module";
import ProjectInstanceRoute from "./project-instance.route";

const module = Angular.module('swamp.projectInstances.projectInstance', [Services, ContainerInstances])
    .config(ProjectInstanceRoute);

export default module.name;

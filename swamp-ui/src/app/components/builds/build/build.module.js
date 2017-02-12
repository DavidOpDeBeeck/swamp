import Angular from "angular";
import Services from "../../../services/services.module";
import ContainerInstances from "./container-instances/container-instances.module";
import BuildRoute from "./build.route";

const module = Angular.module('swamp.builds.build', [Services, ContainerInstances])
    .config(BuildRoute);

export default module.name;

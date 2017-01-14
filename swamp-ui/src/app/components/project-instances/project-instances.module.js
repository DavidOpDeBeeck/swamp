import Angular from "angular";
import Services from "../../services/services.module";
import ProjectInstancesRoute from "./project-instances.route";
import ProjectInstance from "./project-instance/project-instance.module";

const module = Angular.module('swamp.projectInstances', [Services, ProjectInstance])
    .config(ProjectInstancesRoute);

export default module.name;

import Angular from "angular";
import Services from "../../services/services.module";
import BuildsRoute from "./builds.route";
import Build from "./build/build.module";

const module = Angular.module('swamp.projectInstances', [Services, Build])
    .config(BuildsRoute);

export default module.name;

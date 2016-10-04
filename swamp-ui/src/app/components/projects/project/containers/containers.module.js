import angular from "angular";
import ContainersRoute from "./containers.route";
import ContainersCreateRoute from "./create/containers-create.route";
import ContainersController from "./containers.controller";
import ContainersCreateController from "./create/containers-create.controller";

const module = angular.module('swamp.containers', ['swamp.services', 'ui.bootstrap'])
    .controller('ContainersController', ContainersController)
    .controller('ContainersCreateController', ContainersCreateController)
    .config(ContainersRoute)
    .config(ContainersCreateRoute);

export default module.name;

import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../../services/services.module";
import Container from "./container/container.module";
import ContainersRoute from "./containers.route";
import ContainersCreateRoute from "./create/containers-create.route";

const module = angular.module('swamp.containers', [services, uiBootstrap, Container])
    .config(ContainersRoute)
    .config(ContainersCreateRoute);

export default module.name;

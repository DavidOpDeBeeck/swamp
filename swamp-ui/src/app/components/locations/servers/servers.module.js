import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../services/services.module";
import ServersRoute from "./servers.route";
import ServersCreateRoute from "./create/servers-create.route";

const module = angular.module('swamp.servers', [services, uiBootstrap])
    .config(ServersRoute)
    .config(ServersCreateRoute);

export default module.name;
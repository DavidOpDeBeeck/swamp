import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../services/services.module";
import Continents from "./continents/continents.module";
import Datacenters from "./datacenters/datacenters.module";
import ServersRoute from "./servers/servers.route";
import ServersCreateRoute from "./servers/create/servers-create.route";
import ServersController from "./servers/servers.controller";
import ServersCreateController from "./servers/create/servers-create.controller";

const module = angular.module('swamp.locations', [services, uiBootstrap, Continents, Datacenters])
    .controller('ServersController', ServersController)
    .controller('ServersCreateController', ServersCreateController)
    .config(ServersRoute)
    .config(ServersCreateRoute);

export default module.name;

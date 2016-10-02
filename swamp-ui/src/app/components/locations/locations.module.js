import angular from "angular";
import ContinentsRoute from "./continents/continents.route";
import ContinentsCreateRoute from "./continents/create/continents-create.route";
import DatacentersRoute from "./datacenters/datacenters.route";
import DatacentersCreateRoute from "./datacenters/create/datacenters-create.route";
import ServersRoute from "./servers/servers.route";
import ServersCreateRoute from "./servers/create/servers-create.route";
import ContinentsController from "./continents/continents.controller";
import ContinentsCreateController from "./continents/create/continents-create.controller";
import DatacentersController from "./datacenters/datacenters.controller";
import DatacentersCreateController from "./datacenters/create/datacenters-create.controller";
import ServersController from "./servers/servers.controller";
import ServersCreateController from "./servers/create/servers-create.controller";

const module = angular.module('swamp.locations', ['swamp.services', 'ui.bootstrap'])
    .controller('ContinentsController', ContinentsController)
    .controller('ContinentsCreateController', ContinentsCreateController)
    .controller('DatacentersController', DatacentersController)
    .controller('DatacentersCreateController', DatacentersCreateController)
    .controller('ServersController', ServersController)
    .controller('ServersCreateController', ServersCreateController)
    .config(ContinentsRoute)
    .config(ContinentsCreateRoute)
    .config(DatacentersRoute)
    .config(DatacentersCreateRoute)
    .config(ServersRoute)
    .config(ServersCreateRoute);

export default module.name;

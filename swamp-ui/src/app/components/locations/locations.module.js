import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../services/services.module";
import Continents from "./continents/continents.module";
import Datacenters from "./datacenters/datacenters.module";
import Servers from "./servers/servers.module";

const module = angular.module('swamp.locations', [services, uiBootstrap, Continents, Datacenters, Servers]);

export default module.name;

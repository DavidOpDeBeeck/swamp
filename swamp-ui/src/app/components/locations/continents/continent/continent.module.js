import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../../services/services.module";
import ContinentRoute from "./continent.route";

const module = angular.module('swamp.continent', [services, uiBootstrap])
    .config(ContinentRoute);

export default module.name;
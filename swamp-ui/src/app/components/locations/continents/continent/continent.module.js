import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../../services/services.module";
import ContinentRoute from "./continent.route";
import ContinentEditRoute from "./edit/continent-edit.route";

const module = angular.module('swamp.continent', [services, uiBootstrap])
    .config(ContinentRoute)
    .config(ContinentEditRoute);

export default module.name;
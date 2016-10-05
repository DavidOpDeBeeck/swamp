import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../services/services.module";
import ContinentsRoute from "./continents.route";
import ContinentsCreateRoute from "./create/continents-create.route";

const module = angular.module('swamp.continents', [services, uiBootstrap])
    .config(ContinentsRoute)
    .config(ContinentsCreateRoute);

export default module.name;
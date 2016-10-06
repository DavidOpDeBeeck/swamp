import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../../services/services.module";
import ServerRoute from "./server.route";
import ServerEditRoute from "./edit/server-edit.route";

const module = angular.module('swamp.server', [services, uiBootstrap])
    .config(ServerRoute)
    .config(ServerEditRoute);

export default module.name;
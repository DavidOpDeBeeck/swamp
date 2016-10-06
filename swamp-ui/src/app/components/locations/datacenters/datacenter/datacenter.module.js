import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../../services/services.module";
import DatacenterRoute from "./datacenter.route";
import DatacenterEditRoute from "./edit/datacenter-edit.route";

const module = angular.module('swamp.datacenter', [services, uiBootstrap])
    .config(DatacenterRoute)
    .config(DatacenterEditRoute);

export default module.name;
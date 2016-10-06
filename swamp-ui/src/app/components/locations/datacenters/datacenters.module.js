import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../../services/services.module";
import Datacenter from "./datacenter/datacenter.module";
import DatacentersRoute from "./datacenters.route";
import DatacentersCreateRoute from "./create/datacenters-create.route";

const module = angular.module('swamp.datacenters', [services, uiBootstrap, Datacenter])
    .config(DatacentersRoute)
    .config(DatacentersCreateRoute);

export default module.name;
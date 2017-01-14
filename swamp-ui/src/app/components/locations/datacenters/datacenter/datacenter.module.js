import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import services from "../../../../services/services.module";
import DatacenterRoute from "./datacenter.route";

const module = Angular.module('swamp.datacenter', [UIBootstrap, services])
    .config(DatacenterRoute);

export default module.name;
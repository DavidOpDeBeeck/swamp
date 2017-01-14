import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../services/services.module";
import Datacenter from "./datacenter/datacenter.module";
import DatacentersRoute from "./datacenters.route";

const module = Angular.module('swamp.datacenters', [UIBootstrap, Services, Datacenter])
    .config(DatacentersRoute);

export default module.name;
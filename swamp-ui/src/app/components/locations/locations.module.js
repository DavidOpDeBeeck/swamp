import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../services/services.module";
import Continents from "./continents/continents.module";
import Datacenters from "./datacenters/datacenters.module";
import Servers from "./servers/servers.module";

const module = Angular.module('swamp.locations', [UIBootstrap, Services, Continents, Datacenters, Servers]);

export default module.name;

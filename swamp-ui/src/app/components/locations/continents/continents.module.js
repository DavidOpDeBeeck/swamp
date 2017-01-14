import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../services/services.module";
import Continent from "./continent/continent.module";
import ContinentsRoute from "./continents.route";

const module = Angular.module('swamp.continents', [UIBootstrap, Services, Continent])
    .config(ContinentsRoute);

export default module.name;
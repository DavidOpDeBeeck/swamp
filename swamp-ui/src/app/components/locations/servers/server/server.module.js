import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../../services/services.module";
import ServerRoute from "./server.route";

const module = Angular.module('swamp.server', [UIBootstrap, Services])
    .config(ServerRoute);

export default module.name;
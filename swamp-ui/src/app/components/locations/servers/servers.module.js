import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../services/services.module";
import Server from "./server/server.module";
import ServersRoute from "./servers.route";

const module = Angular.module('swamp.servers', [UIBootstrap, Services, Server])
    .config(ServersRoute);

export default module.name;
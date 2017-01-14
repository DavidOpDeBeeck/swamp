import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../../../services/services.module";
import Container from "./container/container.module";
import ContainersRoute from "./containers.route";

const module = Angular.module('swamp.containers', [UIBootstrap, Services, Container])
    .config(ContainersRoute);

export default module.name;

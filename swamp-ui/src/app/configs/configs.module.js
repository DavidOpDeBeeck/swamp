import Angular from "angular";
import StateConfig from "./state.config";
import NotificationConfig from "./notification.config";

const module = Angular.module('swamp.configs', []);

module.run(StateConfig);
module.run(NotificationConfig);

export default module.name;
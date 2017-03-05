import Angular from "angular";
import StateConfig from "./state.config";
import NotificationConfig from "./notification.config";

const module = Angular.module('swamp.configs', [])
    .run(StateConfig)
    .run(NotificationConfig);

export default module.name;
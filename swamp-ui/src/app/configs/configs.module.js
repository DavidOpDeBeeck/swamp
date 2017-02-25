import Angular from "angular";
import StateConfig from "./state.config";

const module = Angular.module('swamp.configs', [])
    .run(StateConfig);

export default module.name;
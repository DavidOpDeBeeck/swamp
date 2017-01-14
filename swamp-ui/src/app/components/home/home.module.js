import Angular from "angular";
import HomeRoute from "./home.route";

const module = Angular.module('swamp.home', [])
    .config(HomeRoute);

export default module.name;

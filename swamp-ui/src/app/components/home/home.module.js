import angular from "angular";
import HomeRoute from "./home.route";

const module = angular.module('swamp.home', [])
    .config(HomeRoute);

export default module.name;

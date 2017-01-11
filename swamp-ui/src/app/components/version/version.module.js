import angular from "angular";
import VersionDirective from "./version.directive";

const module = angular.module('swamp.version', [])
    .directive('version', VersionDirective);

export default module.name;

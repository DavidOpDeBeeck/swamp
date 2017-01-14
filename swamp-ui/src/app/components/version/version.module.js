import Angular from "angular";
import VersionDirective from "./version.directive";

const module = Angular.module('swamp.version', [])
    .directive('version', VersionDirective);

export default module.name;

import angular from "angular";
import HeaderDirective from "./header/header.directive";

const module = angular.module('swamp.layout', ['swamp.services'])
    .directive('layoutHeader', () => new HeaderDirective());

export default module.name;

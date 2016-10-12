import angular from "angular";
import HeaderDirective from "./header/header.directive";
import FooterDirective from "./footer/footer.directive";

const module = angular.module('swamp.layout', [])
    .directive('layoutHeader', () => new HeaderDirective())
    .directive('layoutFooter', () => new FooterDirective());

export default module.name;

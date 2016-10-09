import angular from "angular";
import ConfigurationPickerController from "./configuration-picker/configuration-picker.controller";
import LocationPickerController from "./location-picker/location-picker.controller";
import ConfigurationPickerDirective from "./configuration-picker/configuration-picker.directive";
import LocationPickerDirective from "./location-picker/location-picker.directive";
import uiBreadcrumbs from "./ui-breadcrumbs/ui-breadcrumbs.directive";

const module = angular.module('swamp.shared', ['swamp.services', 'ui.bootstrap'])
    .controller('ConfigurationPickerController', ConfigurationPickerController)
    .controller('LocationPickerController', LocationPickerController)
    .directive('configurationPicker', () => new ConfigurationPickerDirective())
    .directive('locationPicker', () => new LocationPickerDirective())
    .directive('uiBreadcrumbs', ['$interpolate', '$state', ($interpolate, $state) => new uiBreadcrumbs($interpolate, $state)]);

export default module.name;

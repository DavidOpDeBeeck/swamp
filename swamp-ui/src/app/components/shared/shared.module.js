import angular from "angular";
import ConfigurationPickerController from "./configuration-picker/configuration-picker.controller";
import LocationPickerController from "./location-picker/location-picker.controller";
import ConfigurationPickerDirective from "./configuration-picker/configuration-picker.directive";
import LocationPickerDirective from "./location-picker/location-picker.directive";
import BreadcrumbsDirective from "./ui-breadcrumbs/ui-breadcrumbs.directive";
import InputFieldDirective from "./field/input/input-field.directive";

const module = angular.module('swamp.shared', ['swamp.services', 'ui.bootstrap'])
    .controller('ConfigurationPickerController', ConfigurationPickerController)
    .controller('LocationPickerController', LocationPickerController)
    .directive('configurationPicker', ConfigurationPickerDirective)
    .directive('locationPicker', LocationPickerDirective)
    .directive('uiBreadcrumbs', BreadcrumbsDirective)
    .directive('inputField', InputFieldDirective);

export default module.name;

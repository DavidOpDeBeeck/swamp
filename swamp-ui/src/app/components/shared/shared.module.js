import angular from "angular";
import ConfigurationPickerController from "./configuration-picker/configuration-picker.controller";
import LocationPickerController from "./location-picker/location-picker.controller";
import ConfigurationPickerDirective from "./configuration-picker/configuration-picker.directive";
import LocationPickerDirective from "./location-picker/location-picker.directive";
import BreadcrumbsDirective from "./ui-breadcrumbs/ui-breadcrumbs.directive";
import FieldDirective from "./field/field.directive";

const module = angular.module('swamp.shared', ['swamp.services', 'ui.bootstrap'])
    .controller('ConfigurationPickerController', ConfigurationPickerController)
    .controller('LocationPickerController', LocationPickerController)
    .directive('configurationPicker', ConfigurationPickerDirective)
    .directive('locationPicker', LocationPickerDirective)
    .directive('uiBreadcrumbs', BreadcrumbsDirective)
    .directive('field', FieldDirective);

export default module.name;

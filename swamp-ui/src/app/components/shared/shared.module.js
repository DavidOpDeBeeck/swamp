import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import services from "../../services/services.module";
import ConfigurationPickerDirective from "./configuration-picker/configuration-picker.directive";
import LocationPickerDirective from "./location-picker/location-picker.directive";
import BreadcrumbsDirective from "./ui-breadcrumbs/ui-breadcrumbs.directive";
import InputFieldDirective from "./field/input/input-field.directive";
import TextareaFieldDirective from "./field/textarea/textarea-field.directive";
import SelectFieldDirective from "./field/select/select-field.directive";

const module = angular.module('swamp.shared', [services, uiBootstrap])
    .directive('configurationPicker', ConfigurationPickerDirective)
    .directive('locationPicker', LocationPickerDirective)
    .directive('uiBreadcrumbs', BreadcrumbsDirective)
    .directive('inputField', InputFieldDirective)
    .directive('textareaField', TextareaFieldDirective)
    .directive('selectField', SelectFieldDirective);

export default module.name;

import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../services/services.module";
import ConfigurationPickerDirective from "./configuration-picker/configuration-picker.directive";
import LocationPickerDirective from "./location-picker/location-picker.directive";
import BreadcrumbsDirective from "./ui-breadcrumbs/ui-breadcrumbs.directive";
import ScrollBottomDirective from "./scroll-bottom/scroll-bottom.directive";
import TemplatePickerDirective from "./template-picker/template-picker.directive";

const module = Angular.module('swamp.shared', [UIBootstrap, Services])
    .directive('configurationPicker', ConfigurationPickerDirective)
    .directive('locationPicker', LocationPickerDirective)
    .directive('uiBreadcrumbs', BreadcrumbsDirective)
    .directive('scrollBottom', ScrollBottomDirective)
    .directive('templatePicker', TemplatePickerDirective);

export default module.name;

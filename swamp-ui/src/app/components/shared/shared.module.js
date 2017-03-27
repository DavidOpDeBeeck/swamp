import Angular from "angular";
import UIBootstrap from "angular-ui-bootstrap";
import Services from "../../services/services.module";
import ConfigurationPickerDirective from "./configuration-picker/configuration-picker.directive";
import CredentialsPickerDirective from "./credentials-picker/credentials-picker.directive";
import LocationPickerDirective from "./location-picker/location-picker.directive";
import BreadcrumbsDirective from "./ui-breadcrumbs/ui-breadcrumbs.directive";
import ScrollBottomDirective from "./scroll-bottom/scroll-bottom.directive";
import TemplatePickerDirective from "./template-picker/template-picker.directive";
import FileDropZoneDirective from "./file-drop-zone/file-drop-zone.directive";

const module = Angular.module('swamp.shared', [UIBootstrap, Services])
    .directive('configurationPicker', ConfigurationPickerDirective)
    .directive('credentialsPicker', CredentialsPickerDirective)
    .directive('locationPicker', LocationPickerDirective)
    .directive('uiBreadcrumbs', BreadcrumbsDirective)
    .directive('scrollBottom', ScrollBottomDirective)
    .directive('templatePicker', TemplatePickerDirective)
    .directive('fileDropZone', FileDropZoneDirective);

export default module.name;

import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import InputFieldDirective from "./input/input-field.directive";
import TextareaFieldDirective from "./textarea/textarea-field.directive";
import AutocompleteFieldDirective from "./autocomplete/autocomplete-field.directive";
import MultipleAutocompleteField from "./multiple-autocomplete/multiple-autocomplete-field.directive";
import SelectFieldDirective from "./select/select-field.directive";
import PropertyFieldDirective from "./property/property-field.directive";
import MultiplePropertyFieldDirective from "./multiple-property/multiple-property-field.directive";

const module = angular.module('swamp.field', [uiBootstrap])
    .directive('inputField', InputFieldDirective)
    .directive('textareaField', TextareaFieldDirective)
    .directive('autocompleteField', AutocompleteFieldDirective)
    .directive('multipleAutocompleteField', MultipleAutocompleteField)
    .directive('multiplePropertyField', MultiplePropertyFieldDirective)
    .directive('selectField', SelectFieldDirective)
    .directive('propertyField', PropertyFieldDirective);

export default module.name;

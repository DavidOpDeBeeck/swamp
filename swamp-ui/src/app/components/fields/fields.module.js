import angular from "angular";
import uiBootstrap from "angular-ui-bootstrap";
import IncludeReplaceDirective from "./include-replace.directive";
import InputFieldDirective from "./input/input-field.directive";
import TextareaFieldDirective from "./textarea/textarea-field.directive";
import AutocompleteFieldDirective from "./autocomplete/autocomplete-field.directive";
import SelectFieldDirective from "./select/select-field.directive";
import PropertyFieldDirective from "./property/property-field.directive";
import MultiplePropertyFieldDirective from "./multiple/multiple-property/multiple-property-field.directive";
import MultipleAutocompleteFieldDirective from "./multiple/multiple-autocomplete/multiple-autocomplete-field.directive";
import MultipleInputFieldDirective from "./multiple/multiple-input/multiple-input-field.directive";

const module = angular.module('swamp.field', [uiBootstrap])
    .directive('includeReplace', IncludeReplaceDirective)
    .directive('inputField', InputFieldDirective)
    .directive('textareaField', TextareaFieldDirective)
    .directive('autocompleteField', AutocompleteFieldDirective)
    .directive('selectField', SelectFieldDirective)
    .directive('propertyField', PropertyFieldDirective)
    .directive('multipleAutocompleteField', MultipleAutocompleteFieldDirective)
    .directive('multiplePropertyField', MultiplePropertyFieldDirective)
    .directive('multipleInputField', MultipleInputFieldDirective);

export default module.name;

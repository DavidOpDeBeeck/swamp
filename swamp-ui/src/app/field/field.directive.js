import angular from "angular";
import FieldController from "./field.controller.js";

class FieldDirective {
    constructor({
        extraScope = {},
        controller = FieldController,
        controllerAs = 'field',
        templateUrl = ''
    }) {
        this.restrict = 'E';
        this.scope = {
            'label': '@',
            'value': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = controller;
        this.controllerAs = controllerAs;
        this.templateUrl = templateUrl;
        angular.extend(this.scope, extraScope);
    }
}

export default FieldDirective
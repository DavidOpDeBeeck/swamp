import MultipleFieldController from "./multiple.field.controller.js";

class MultipleFieldDirective {
    constructor({
        extraScope = {},
        controller = MultipleFieldController,
        controllerAs = 'multiple',
        templateUrl = undefined
    }) {
        this.restrict = 'E';
        this.scope = {
            'label': '@',
            'list': '=',
            'entity': '@',
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

export default MultipleFieldDirective
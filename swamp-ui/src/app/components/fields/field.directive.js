import Angular from "angular";
import FieldController from "./field.controller.js";

class FieldDirective {
    constructor({
        extraScope = {},
        controller = FieldController,
        controllerAs = 'field',
        templateUrl = templateUrl
    }) {
        this.url = templateUrl;
        this.restrict = 'E';
        this.scope = {
            'label': '@',
            'required': '@',
            'value': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = controller;
        this.controllerAs = controllerAs;
        this.template = '<div ng-include="templateUrl" include-replace></div>';
        Angular.extend(this.scope, extraScope);
    }

    link($scope) {
        let url = this.url;
        if ($scope[this.controllerAs].editable)
            url = url.replace("field.template.html", "editable-field.template.html");
        $scope.templateUrl = url;
    }
}

export default FieldDirective
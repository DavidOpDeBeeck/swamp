import MultipleFieldController from "./multiple.field.controller";

class MultipleFieldDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'label': '@',
            'list': '=',
            'entity': '@',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = MultipleFieldController;
        this.controllerAs = 'multiple';
    }
}

export default MultipleFieldDirective
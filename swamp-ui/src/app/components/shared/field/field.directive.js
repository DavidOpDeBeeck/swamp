class FieldDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'value': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = () => {};
        this.controllerAs = 'field';
        this.templateUrl = '/app/components/shared/field/field.template.html';
    }
}

export default () => new FieldDirective()
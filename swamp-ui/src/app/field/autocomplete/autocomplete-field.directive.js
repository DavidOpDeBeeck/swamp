import FieldDirective from "./../field.directive";

class InputFieldDirective extends FieldDirective {
    constructor() {
        super({
            extraScope: {
                'autocompleteList': '='
            },
            templateUrl: '/app/field/autocomplete/autocomplete-field.template.html'
        });
    }
}

export default () => new InputFieldDirective()
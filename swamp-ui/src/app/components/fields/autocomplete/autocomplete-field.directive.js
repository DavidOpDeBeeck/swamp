import FieldDirective from "../field.directive";

class AutocompleteFieldDirective extends FieldDirective {
    constructor() {
        super({
            extraScope: {
                'autocompleteList': '='
            },
            templateUrl: '/assets/templates/autocomplete-field.template.html'
        });
    }
}

export default () => new AutocompleteFieldDirective()
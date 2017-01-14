import FieldDirective from "../field.directive";

class TextareaFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/assets/templates/textarea-field.template.html'
        });
    }
}

export default () => new TextareaFieldDirective()
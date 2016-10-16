import FieldDirective from "./../field.directive";

class TextareaFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/app/field/textarea/textarea-field.template.html'
        });
    }
}

export default () => new TextareaFieldDirective()
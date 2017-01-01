import FieldDirective from "../field.directive";

class TextareaFieldDirective extends FieldDirective {
    constructor() {
        super({
            templateUrl: '/app/components/fields/textarea/textarea-field.template.html'
        });
    }
}

export default () => new TextareaFieldDirective()
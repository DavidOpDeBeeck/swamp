import FieldDirective from "../field.directive";

class PropertyFieldDirective extends FieldDirective {
    constructor() {
        super({
            extraScope: {
                'nameField': '@',
                'valueField': '@'
            },
            templateUrl: '/assets/templates/property-field.template.html'
        });
    }
}

export default () => new PropertyFieldDirective()
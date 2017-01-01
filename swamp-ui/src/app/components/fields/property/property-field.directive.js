import FieldDirective from "../field.directive";

class PropertyFieldDirective extends FieldDirective {
    constructor() {
        super({
            extraScope: {
                'nameField': '@',
                'valueField': '@'
            },
            templateUrl: '/app/components/fields/property/property-field.template.html'
        });
    }
}

export default () => new PropertyFieldDirective()
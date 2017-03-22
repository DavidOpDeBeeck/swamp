class ContainerDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'containerTemplate': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = () => {
        };
        this.controllerAs = 'ContainerTemplateCtrl';
        this.templateUrl = '/assets/templates/container-template.template.html';
    }
}

export default () => new ContainerDirective()
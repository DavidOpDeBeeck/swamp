import ContainerInstanceController from "./container-instance.controller";

class ContainerInstanceDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'projectInstance': '=',
            'initial': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = ContainerInstanceController;
        this.controllerAs = 'ContainerInstanceCtrl';
        this.templateUrl = '/assets/templates/container-instance.template.html';
    }
}

export default () => new ContainerInstanceDirective()
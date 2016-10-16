class ContainerDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {
            'container': '=',
            'editable': '='
        };
        this.bindToController = true;
        this.replace = true;
        this.controller = () => {
        };
        this.controllerAs = 'ContainerCtrl';
        this.templateUrl = '/app/components/projects/project/containers/container/container.template.html';
    }
}

export default () => new ContainerDirective()
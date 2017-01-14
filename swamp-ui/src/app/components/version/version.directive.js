import VersionController from "./version.controller";

class VersionDirective {
    constructor() {
        this.restrict = 'E';
        this.replace = true;
        this.controller = VersionController;
        this.controllerAs = 'versionCtrl';
        this.templateUrl = '/assets/templates/version.template.html';
    }
}

export default () => new VersionDirective()
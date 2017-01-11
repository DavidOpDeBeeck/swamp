import VersionController from "./version.controller";

class VersionDirective {
    constructor() {
        this.restrict = 'E';
        this.replace = true;
        this.controller = VersionController;
        this.controllerAs = 'versionCtrl';
        this.templateUrl = '/app/components/version/version.template.html';
    }
}

export default () => new VersionDirective()
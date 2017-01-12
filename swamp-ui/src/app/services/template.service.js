class TemplateService {
    constructor($resource) {
        this.templateResource = $resource('/app/templates/templates.json');
    }

    getTemplates() {
        return this.templateResource.query().$promise;
    }
}

export default  ['$resource', TemplateService]

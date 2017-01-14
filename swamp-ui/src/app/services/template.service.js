class TemplateService {
    constructor($resource) {
        this.templateResource = $resource('/assets/json/templates.json');
    }

    getTemplates() {
        return this.templateResource.query().$promise;
    }
}

export default ['$resource', TemplateService]

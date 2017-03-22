class TemplateController {
    constructor(TemplateService) {
        this.templateService = TemplateService;
        this.getTemplates();
    }

    getTemplates() {
        this.templateService.getTemplates()
            .then(templates => this.templates = templates);
    }
}

export default ['TemplateService', TemplateController]
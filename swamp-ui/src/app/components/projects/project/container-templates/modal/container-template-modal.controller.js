class ContainerTemplateModalController {
    constructor(ProjectService, containerTemplate, project, update, $uibModalInstance) {
        this.projectService = ProjectService;
        this.project = project;
        this.containerTemplate = containerTemplate;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.update ? this.updateContainer() : this.create();
    }

    create() {
        this.projectService.createContainerTemplate(this.project.id, this.containerTemplate)
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }

    updateContainer() {
        this.containerTemplate.$update()
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }
}

export default ['ProjectService', 'containerTemplate', 'project', 'update', '$uibModalInstance', ContainerTemplateModalController]
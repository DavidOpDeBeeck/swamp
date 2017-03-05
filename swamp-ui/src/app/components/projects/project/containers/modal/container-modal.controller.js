class ContainerModalController {
    constructor(ProjectService, container, project, update, $uibModalInstance) {
        this.projectService = ProjectService;
        this.project = project;
        this.container = container;
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
        this.projectService.createContainer(this.project.id, this.container)
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }

    updateContainer() {
        this.container.$update()
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }
}

export default ['ProjectService', 'container', 'project', 'update', '$uibModalInstance', ContainerModalController]
class ProjectModalController {
    constructor(ProjectService, project, update, $uibModalInstance) {
        this.projectService = ProjectService;
        this.project = project;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.update ? this.updateProject() : this.create();
    }

    create() {
        this.projectService.createProject(this.project)
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }

    updateProject() {
        this.project.$update()
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }
}

export default ['ProjectService', 'project', 'update', '$uibModalInstance', ProjectModalController]
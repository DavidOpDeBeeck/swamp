class ProjectModalController {
    constructor(project, update, $uibModalInstance) {
        this.project = project;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.$uibModalInstance.close(this.project);
    }
}

export default ['project', 'update', '$uibModalInstance', ProjectModalController]
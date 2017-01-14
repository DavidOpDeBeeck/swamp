class ContainerModalController {
    constructor(container, update, $uibModalInstance) {
        this.container = container;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.$uibModalInstance.close(this.container);
    }
}

export default ['container', 'update', '$uibModalInstance', ContainerModalController]
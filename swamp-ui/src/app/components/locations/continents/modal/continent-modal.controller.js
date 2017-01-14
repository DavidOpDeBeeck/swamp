class ContinentModalController {
    constructor(continent, update, $uibModalInstance) {
        this.continent = continent;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.$uibModalInstance.close(this.continent);
    }
}

export default ['continent', 'update', '$uibModalInstance', ContinentModalController]

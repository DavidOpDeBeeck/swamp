class ContinentModalController {
    constructor(LocationService, continent, update, $uibModalInstance) {
        this.locationService = LocationService;
        this.continent = continent;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.update ? this.updateContinent() : this.create();
    }

    create() {
        this.locationService.createContinent(this.continent)
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }

    updateContinent() {
        this.continent.$update()
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }
}

export default ['LocationService', 'continent', 'update', '$uibModalInstance', ContinentModalController]

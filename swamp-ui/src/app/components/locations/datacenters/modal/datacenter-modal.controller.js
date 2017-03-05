class DatacenterModalController {
    constructor(LocationService, datacenter, continent, update, $uibModalInstance) {
        this.locationService = LocationService;
        this.datacenter = datacenter;
        this.continent = continent;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.update ? this.updateDatacenter() : this.create();
    }

    create() {
        this.locationService.createDatacenter(this.continent.id, this.datacenter)
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }

    updateDatacenter() {
        this.datacenter.$update()
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }
}

export default ['LocationService', 'datacenter', 'continent', 'update', '$uibModalInstance', DatacenterModalController]


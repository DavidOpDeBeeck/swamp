class ServerModalController {
    constructor(LocationService, server, datacenter, continent, update, $uibModalInstance) {
        this.locationService = LocationService;
        this.server = server;
        this.datacenter = datacenter;
        this.continent = continent;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.update ? this.updateServer() : this.create();
    }

    create() {
        this.locationService.createServer(this.continent.id, this.datacenter.id, this.server)
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }

    updateServer() {
        this.datacenter.$update()
            .then(() => this.$uibModalInstance.close(), errors => this.errors = errors);
    }
}

export default ['LocationService', 'server', 'datacenter', 'continent', 'update', '$uibModalInstance', ServerModalController]


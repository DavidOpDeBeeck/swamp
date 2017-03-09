import DatacenterModalController from "./modal/datacenter-modal.controller";

class DatacentersController {
    constructor(LocationService, continent, $uibModal) {
        this.continent = continent;
        this.locationService = LocationService;
        this.$uibModal = $uibModal;
        this.getAllDatacenters();
    }

    getAllDatacenters() {
        this.locationService.getAllDatacenters(this.continent.id)
            .then((datacenters) => this.datacenters = datacenters);
    }

    create() {
        let modal = this.createModal({}, false);
        modal.result.then(() => this.getAllDatacenters());
    }

    edit(datacenter) {
        let modal = this.createModal(datacenter, true);
        modal.result.then(() => this.getAllDatacenters());
    }

    createModal(datacenter, update) {
        return this.$uibModal.open({
            backdrop: 'static',
            controllerAs: 'DatacenterModalCtrl',
            controller: DatacenterModalController,
            templateUrl: "/assets/templates/datacenter-modal.template.html",
            resolve: {
                datacenter: () => datacenter,
                continent: () => this.continent,
                update: update
            }
        });
    }

    delete(datacenter) {
        datacenter.$delete()
            .then(() => this.getAllDatacenters());
    }
}

export default ['LocationService', 'continent', '$uibModal', DatacentersController]

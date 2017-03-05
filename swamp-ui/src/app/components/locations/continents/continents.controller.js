import ContinentModalController from "./modal/continent-modal.controller";

class ContinentsController {
    constructor(LocationService, $uibModal) {
        this.locationService = LocationService;
        this.$uibModal = $uibModal;
        this.getAllContinents();
    }

    getAllContinents() {
        this.locationService.getAllContinents()
            .then((continents) => this.continents = continents);
    }

    create() {
        let modal = this.createModal({}, false);
        modal.result.then(() => this.getAllContinents());
    }

    edit(continent) {
        let modal = this.createModal(continent, true);
        modal.result.then(() => this.getAllContinents());
    }

    createModal(continent, update) {
        return this.$uibModal.open({
            backdrop: 'static',
            controllerAs: 'ContinentModalCtrl',
            controller: ContinentModalController,
            templateUrl: "/assets/templates/continent-modal.template.html",
            resolve: {
                continent: () => continent,
                update: update
            }
        });
    }

    delete(continent) {
        continent.$delete()
            .then(() => this.getAllContinents());
    }
}

export default ['LocationService', '$uibModal', ContinentsController]

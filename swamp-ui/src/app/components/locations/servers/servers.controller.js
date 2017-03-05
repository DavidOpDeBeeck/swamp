import ServerModalController from "./modal/server-modal.controller";

class ServersController {
    constructor(LocationService, continent, datacenter, $uibModal) {
        this.continent = continent;
        this.datacenter = datacenter;
        this.locationService = LocationService;
        this.$uibModal = $uibModal;
        this.getAllServers();
    }

    getAllServers() {
        this.locationService.getAllServers(this.continent.id, this.datacenter.id)
            .then((servers) => this.servers = servers);
    }

    create() {
        let modal = this.createModal({}, false);
        modal.result.then(() => this.getAllServers());
    }

    edit(server) {
        let modal = this.createModal(server, true);
        modal.result.then(() => this.getAllServers());
    }

    createModal(server, update) {
        return this.$uibModal.open({
            backdrop: 'static',
            controllerAs: 'ServerModalCtrl',
            controller: ServerModalController,
            templateUrl: "/assets/templates/server-modal.template.html",
            resolve: {
                server: () => server,
                datacenter: () => this.datacenter,
                continent: () => this.continent,
                update: update
            }
        });
    }

    delete(server) {
        server.$delete()
            .then(() => this.getAllServers());
    }
}

export default ['LocationService', 'continent', 'datacenter', '$uibModal', ServersController]

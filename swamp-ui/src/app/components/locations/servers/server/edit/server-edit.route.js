import ServerEditController from "./server-edit.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.datacenter.servers.server.edit', {
        url: "/edit",
        onEnter: ['$uibModal', 'continent', 'datacenter', 'server', ($uibModal, continent, datacenter, server) => {
            $uibModal.open({
                templateUrl: "/app/components/locations/servers/server/edit/server-edit.template.html",
                controller: ServerEditController,
                controllerAs: 'ServerEditCtrl',
                backdrop: 'static',
                resolve: {
                    continent: continent,
                    datacenter: datacenter,
                    server: server
                }
            });
        }]
    })
}];

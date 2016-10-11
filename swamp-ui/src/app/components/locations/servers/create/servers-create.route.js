import ServersCreateController from "./servers-create.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.datacenter.servers.create', {
        url: "/create",
        data: {
            displayName: 'create',
        },
        onEnter: ['$uibModal', 'continent', 'datacenter', ($uibModal, continent, datacenter) => {
            $uibModal.open({
                templateUrl: '/app/components/locations/servers/create/servers-create.template.html',
                controller: ServersCreateController,
                controllerAs: 'ServerCreateCtrl',
                backdrop: 'static',
                resolve: {
                    continent: continent,
                    datacenter: datacenter
                }
            });
        }]
    });
}];

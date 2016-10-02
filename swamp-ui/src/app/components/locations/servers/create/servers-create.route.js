export default ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('continents.datacenters.servers.create', {
        url: "/create",
        resolve: {
            continent: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getContinent($stateParams['continentId'])],
            datacenter: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getDatacenter($stateParams['continentId'], $stateParams['datacenterId'])]
        },
        onEnter: ['$uibModal', 'continent', 'datacenter', ($uibModal, continent, datacenter) => {
            $uibModal.open({
                templateUrl: '/app/components/locations/servers/create/servers-create.template.html',
                controller: 'ServersCreateController',
                controllerAs: 'server',
                backdrop: 'static',
                resolve: {
                    continent: continent,
                    datacenter: datacenter
                }
            });
        }]
    });
};

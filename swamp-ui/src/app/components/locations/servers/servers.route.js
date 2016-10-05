export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('continents.continent.datacenters.servers', {
        url: "/servers",
        templateUrl: '/app/components/locations/servers/servers.template.html',
        controller: 'ServersController as servers',
        resolve: {
            continent: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getContinent($stateParams['continentId'])],
            datacenter: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getDatacenter($stateParams['continentId'], $stateParams['datacenterId'])]
        }
    });
}];

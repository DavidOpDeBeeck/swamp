export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.datacenter.servers.server', {
        url: "/{serverId:.{36}}",
        template: '<ui-view />',
        resolve: {
            server: ['LocationService', 'continent', 'datacenter', '$stateParams', (LocationService, continent, datacenter, $stateParams) => LocationService.getServer(continent.id, datacenter.id, $stateParams['serverId'])]
        }
    });
}];

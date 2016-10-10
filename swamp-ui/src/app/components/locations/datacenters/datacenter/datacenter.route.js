export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.datacenter', {
        url: "/{datacenterId:.{36}}",
        template: '<ui-view />',
        data: {
            displayName: "{{ datacenter.id }}"
        },
        resolve: {
            datacenter: ['LocationService', 'continent', '$stateParams', (LocationService, continent, $stateParams) => LocationService.getDatacenter(continent.id, $stateParams['datacenterId'])]
        }
    });
}];

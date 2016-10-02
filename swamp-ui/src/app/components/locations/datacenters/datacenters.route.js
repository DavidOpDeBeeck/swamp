export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('continents.datacenters', {
        url: "/:continentId/datacenters",
        templateUrl: '/app/components/locations/datacenters/datacenters.template.html',
        controller: 'DatacentersController as datacenters',
        resolve: {
            continent: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getContinent($stateParams['continentId'])]
        }
    });
}];

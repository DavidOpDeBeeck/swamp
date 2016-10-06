import DatacentersController from "./datacenters.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider
        .state('continents.continent.datacenters', {
            url: "/datacenters",
            templateUrl: '/app/components/locations/datacenters/datacenters.template.html',
            controller: DatacentersController,
            controllerAs: 'DatacentersCtrl'
        })
        .state('continents.continent.datacenters.datacenter', {
            url: "/:datacenterId",
            template: '<ui-view />',
            resolve: {
                datacenter: ['LocationService', 'continent', '$stateParams', (LocationService, continent, $stateParams) => LocationService.getDatacenter(continent.id, $stateParams['datacenterId'])]
            }
        });
}];

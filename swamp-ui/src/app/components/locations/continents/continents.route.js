import ContinentsController from "./continents.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider
        .state('continents', {
            url: "/continents",
            templateUrl: '/app/components/locations/continents/continents.template.html',
            controller: ContinentsController,
            controllerAs: 'ContinentsCtrl'
        })
        .state('continents.continent', {
            url: "/:continentId",
            template: '<ui-view />',
            resolve: {
                continent: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getContinent($stateParams['continentId'])]
            }
        });
}];

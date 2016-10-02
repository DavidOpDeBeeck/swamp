export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('continents.datacenters.create', {
        url: "/create",
        resolve: {
            continent: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getContinent($stateParams['continentId'])]
        },
        onEnter: ['$uibModal', 'continent', ($uibModal, continent) => {
            $uibModal.open({
                templateUrl: '/app/components/locations/datacenters/create/datacenters-create.template.html',
                controller: 'DatacentersCreateController',
                controllerAs: 'datacenter',
                backdrop: 'static',
                resolve: {
                    continent: continent
                }
            });
        }]
    })
}];

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent', {
        url: "/{continentId:.{36}}",
        template: '<ui-view />',
        data: {
            displayName: "{{ continent.name }}",
            disabled: true
        },
        resolve: {
            continent: ['LocationService', '$stateParams', (LocationService, $stateParams) => LocationService.getContinent($stateParams['continentId'])]
        }
    });
}];

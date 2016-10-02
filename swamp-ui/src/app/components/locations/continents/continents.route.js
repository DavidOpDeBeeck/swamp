export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('continents', {
        url: "/continents",
        templateUrl: '/app/components/locations/continents/continents.template.html',
        controller: 'ContinentsController as continents'
    });
}];

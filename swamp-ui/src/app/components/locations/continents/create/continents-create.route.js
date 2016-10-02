export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('continents.create', {
        url: "/create",
        onEnter: ['$uibModal', ($uibModal) => {
            $uibModal.open({
                templateUrl: "/app/components/locations/continents/create/continents-create.template.html",
                controller: 'ContinentsCreateController',
                controllerAs: 'continent',
                backdrop: 'static'
            });
        }]
    })
}];

import ContinentsController from "./continents.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents', {
        url: "/continents",
        data: {
            displayName: "continents",
            disabled: false
        },
        templateUrl: '/assets/templates/continents.template.html',
        controller: ContinentsController,
        controllerAs: 'ContinentsCtrl'
    });
}];

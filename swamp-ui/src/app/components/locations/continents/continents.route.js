import ContinentsController from "./continents.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents', {
        url: "/continents",
        data: {
            displayName: "continents",
            disabled: false
        },
        templateUrl: '/app/components/locations/continents/continents.template.html',
        controller: ContinentsController,
        controllerAs: 'ContinentsCtrl'
    });
}];

import DatacentersController from "./datacenters.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider
        .state('continents.continent.datacenters', {
            url: "/datacenters",
            data: {
                displayName: "{{ continent.name }}",
                disabled: false
            },
            templateUrl: '/assets/templates/datacenters.template.html',
            controller: DatacentersController,
            controllerAs: 'DatacentersCtrl'
        });
}];

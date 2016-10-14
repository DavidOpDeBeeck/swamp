import DatacentersController from "./datacenters.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider
        .state('continents.continent.datacenters', {
            url: "/datacenters",
            data: {
                displayName: "datacenters",
                disabled: false
            },
            templateUrl: '/app/components/locations/datacenters/datacenters.template.html',
            controller: DatacentersController,
            controllerAs: 'DatacentersCtrl'
        });
}];

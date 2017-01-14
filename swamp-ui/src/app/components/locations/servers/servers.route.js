import ServersController from "./servers.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.datacenter.servers', {
        url: "/servers",
        data: {
            displayName: "servers",
            disabled: false
        },
        templateUrl: '/assets/templates/servers.template.html',
        controller: ServersController,
        controllerAs: 'ServersCtrl'
    });
}];

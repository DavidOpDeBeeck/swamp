import ServersController from "./servers.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.datacenter.servers', {
        url: "/servers",
        templateUrl: '/app/components/locations/servers/servers.template.html',
        controller: ServersController,
        controllerAs: 'ServersCtrl'
    });
}];

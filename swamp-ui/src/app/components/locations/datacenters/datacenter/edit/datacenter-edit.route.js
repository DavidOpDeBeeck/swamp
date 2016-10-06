import DatacenterEditController from "./datacenter-edit.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.datacenter.edit', {
        url: "/edit",
        onEnter: ['$uibModal', 'continent', 'datacenter', ($uibModal, continent, datacenter) => {
            $uibModal.open({
                templateUrl: "/app/components/locations/datacenters/datacenter/edit/datacenter-edit.template.html",
                controller: DatacenterEditController,
                controllerAs: 'DatacenterEditCtrl',
                backdrop: 'static',
                resolve: {
                    continent: continent,
                    datacenter: datacenter
                }
            });
        }]
    })
}];

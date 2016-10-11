import DatacentersCreateController from "./datacenters-create.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.datacenters.create', {
        url: "/create",
        data: {
            displayName: 'create',
        },
        onEnter: ['$uibModal', 'continent', ($uibModal, continent) => {
            $uibModal.open({
                templateUrl: '/app/components/locations/datacenters/create/datacenters-create.template.html',
                controller: DatacentersCreateController,
                controllerAs: 'DatacentersCreateCtrl',
                backdrop: 'static',
                resolve: {
                    continent: continent
                }
            });
        }]
    })
}];

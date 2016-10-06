import ContinentEditController from "./continent-edit.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.continent.edit', {
        url: "/edit",
        onEnter: ['$uibModal', 'continent', ($uibModal, continent) => {
            $uibModal.open({
                templateUrl: "/app/components/locations/continents/continent/edit/continent-edit.template.html",
                controller: ContinentEditController,
                controllerAs: 'ContinentEditCtrl',
                backdrop: 'static',
                resolve: {
                    continent: continent
                }
            });
        }]
    })
}];

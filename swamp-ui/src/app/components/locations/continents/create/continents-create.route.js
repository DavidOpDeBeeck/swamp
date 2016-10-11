import ContinentsCreateController from "./continents-create.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('continents.create', {
        url: "/create",
        data: {
            displayName: 'create',
        },
        onEnter: ['$uibModal', ($uibModal) => {
            $uibModal.open({
                templateUrl: "/app/components/locations/continents/create/continents-create.template.html",
                controller: ContinentsCreateController,
                controllerAs: 'ContinentsCreateCtrl',
                backdrop: 'static'
            });
        }]
    })
}];

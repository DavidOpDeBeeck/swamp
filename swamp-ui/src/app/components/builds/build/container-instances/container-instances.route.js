import ContainerInstancesController from "./container-instances.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('builds.build.containerInstances', {
        url: "/containerInstances",
        data: {
            displayName: 'container-instances',
            disabled: false
        },
        templateUrl: '/assets/templates/container-instances.template.html',
        controller: ContainerInstancesController,
        controllerAs: 'ContainerInstancesCtrl'
    });
}];

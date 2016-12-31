import ContainerInstancesController from "./container-instances.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projectInstances.projectInstance.containerInstances', {
        url: "/containerInstances",
        data: {
            displayName: 'containerInstances',
            disabled: false
        },
        templateUrl: '/app/components/project-instances/project-instance/container-instances/container-instances.template.html',
        controller: ContainerInstancesController,
        controllerAs: 'ContainerInstancesCtrl'
    });
}];

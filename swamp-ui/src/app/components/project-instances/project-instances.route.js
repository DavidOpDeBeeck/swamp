import ProjectInstancesController from "./project-instances.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projectInstances', {
        url: "/projectInstances",
        data: {
            displayName: 'project-instances',
            disabled: false
        },
        templateUrl: '/app/components/project-instances/project-instances.template.html',
        controller: ProjectInstancesController,
        controllerAs: 'ProjectInstancesCtrl'
    });
}];

import ProjectInstancesController from "./project-instances.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projectInstances', {
        url: "/projectInstances",
        data: {
            displayName: 'project-instances',
            disabled: false
        },
        templateUrl: '/assets/templates/project-instances.template.html',
        controller: ProjectInstancesController,
        controllerAs: 'ProjectInstancesCtrl'
    });
}];

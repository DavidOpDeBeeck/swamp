import ContainersController from "./containers.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project.containers', {
        url: "/containers",
        data: {
            displayName: '{{ project.name }}',
            disabled: false
        },
        templateUrl: '/assets/templates/containers.template.html',
        controller: ContainersController,
        controllerAs: 'ContainersCtrl'
    });
}];

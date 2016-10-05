import ContainersController from "./containers.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project.containers', {
        url: "/containers",
        templateUrl: '/app/components/projects/project/containers/containers.template.html',
        controller: ContainersController,
        controllerAs: 'ContainersCtrl'
    });
}];

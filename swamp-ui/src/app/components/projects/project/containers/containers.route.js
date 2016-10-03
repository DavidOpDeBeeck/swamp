export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.project.containers', {
        url: "/containers",
        templateUrl: '/app/components/projects/project/containers/containers.template.html',
        controller: 'ContainersController as containers'
    });
}];

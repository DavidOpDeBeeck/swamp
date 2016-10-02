export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.containers', {
        url: "/:projectId/containers",
        templateUrl: '/app/components/projects/containers/containers.template.html',
        controller: 'ContainersController as containers',
        resolve: {
            project: ['ProjectService', '$stateParams', (ProjectService, $stateParams) => ProjectService.getProject($stateParams['projectId'])]
        }
    });
}];

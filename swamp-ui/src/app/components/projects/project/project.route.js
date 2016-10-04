export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.project', {
        url: "/{projectId:[a-zA-Z0-9_.-]{36}}",
        template: '<ui-view />',
        resolve: {
            project: ['ProjectService', '$stateParams', (ProjectService, $stateParams) => ProjectService.getProject($stateParams['projectId'])]
        }
    });
}];

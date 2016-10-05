export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project', {
        url: "/{projectId:.{36}}",
        template: '<ui-view />',
        resolve: {
            project: ['ProjectService', '$stateParams', (ProjectService, $stateParams) => ProjectService.getProject($stateParams['projectId'])]
        }
    });
}];

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project', {
        url: "/{projectId:.{36}}",
        template: '<ui-view />',
        data: {
            displayName: '{{ project.name }}',
            disabled: true
        },
        resolve: {
            project: ['ProjectService', '$stateParams', (ProjectService, $stateParams) => ProjectService.getProject($stateParams['projectId'])]
        }
    });
}];

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project.containers.container', {
        url: "/{containerId:.{36}}",
        template: '<ui-view />',
        resolve: {
            container: ['ProjectService', 'project', '$stateParams', (ProjectService, project, $stateParams) => ProjectService.getContainer(project.id, $stateParams['containerId'])]
        }
    });
}];

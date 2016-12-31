export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projectInstances.projectInstance', {
        url: "/{projectInstanceId:.{36}}",
        template: '<ui-view />',
        data: {
            displayName: '{{ projectInstance.name }}',
            disabled: true
        },
        resolve: {
            projectInstance: ['ProjectInstanceService', '$stateParams', (ProjectInstanceService, $stateParams) => ProjectInstanceService.getProjectInstance($stateParams['projectInstanceId'])]
        }
    });
}];

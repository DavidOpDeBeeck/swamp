export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project.container-templates.container-template', {
        url: "/{containerTemplateId:.{36}}",
        template: '<ui-view />',
        data: {
            displayName: '{{ containerTemplate.id }}',
            disabled: true
        },
        resolve: {
            containerTemplate: [
                'ProjectService',
                'project',
                '$stateParams',
                (ProjectService, project, $stateParams) =>
                    ProjectService.getContainerTemplate(project.id, $stateParams['containerTemplateId'])]
        }
    });
}];

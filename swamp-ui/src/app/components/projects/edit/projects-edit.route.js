export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.edit', {
        url: "/:projectId/edit",
        resolve: {
            project: ['ProjectService', '$stateParams', (ProjectService, $stateParams) => ProjectService.getProject($stateParams['projectId'])]
        },
        onEnter: ['$uibModal', 'project', ($uibModal, project) => {
            $uibModal.open({
                templateUrl: "/app/components/projects/edit/projects-edit.template.html",
                controller: 'ProjectsEditController',
                controllerAs: 'project',
                backdrop: 'static',
                resolve: {
                    project: project
                }
            });
        }]
    })
}];

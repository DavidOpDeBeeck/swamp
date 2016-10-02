export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.containers.create', {
        url: "/create",
        resolve: {
            project: ['ProjectService', '$stateParams', (ProjectService, $stateParams) => ProjectService.getProject($stateParams['projectId'])]
        },
        onEnter: ['$uibModal', 'project', ($uibModal, project) => {
            $uibModal.open({
                templateUrl: '/app/components/projects/containers/create/containers-create.template.html',
                controller: 'ContainersCreateController',
                controllerAs: 'container',
                backdrop: 'static',
                resolve: {
                    project: project
                }
            });
        }]
    })
}];

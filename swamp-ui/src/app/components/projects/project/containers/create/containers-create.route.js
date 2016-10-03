export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.project.containers.create', {
        url: "/create",
        onEnter: ['$uibModal', 'project', ($uibModal, project) => {
            $uibModal.open({
                templateUrl: '/app/components/projects/project/containers/create/containers-create.template.html',
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

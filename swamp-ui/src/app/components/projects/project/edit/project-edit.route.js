export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.project.edit', {
        url: "/edit",
        onEnter: ['$uibModal', 'project', ($uibModal, project) => {
            $uibModal.open({
                templateUrl: "/app/components/projects/project/edit/project-edit.template.html",
                controller: 'ProjectEditController',
                controllerAs: 'ProjectEditCtrl',
                backdrop: 'static',
                resolve: {
                    project: project
                }
            });
        }]
    })
}];

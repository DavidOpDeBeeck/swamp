export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects.create', {
        url: "/create",
        onEnter: ['$uibModal', ($uibModal) => {
            $uibModal.open({
                templateUrl: "/app/components/projects/create/projects-create.template.html",
                controller: 'ProjectsCreateController',
                controllerAs: 'project',
                backdrop: 'static'
            });
        }]
    })
}];

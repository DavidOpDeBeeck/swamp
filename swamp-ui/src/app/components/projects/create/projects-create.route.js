import ProjectsCreateController from "./projects-create.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.create', {
        url: "/create",
        data: {
            displayName: 'create',
            disabled: true
        },
        onEnter: ['$uibModal', ($uibModal) => {
            $uibModal.open({
                templateUrl: "/app/components/projects/create/projects-create.template.html",
                controller: ProjectsCreateController,
                controllerAs: 'ProjectsCreateCtrl',
                backdrop: 'static'
            });
        }]
    })
}];

import ContainersCreateController from "./containers-create.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project.containers.create', {
        url: "/create",
        onEnter: ['$uibModal', 'project', ($uibModal, project) => {
            $uibModal.open({
                templateUrl: '/app/components/projects/project/containers/create/containers-create.template.html',
                controller: ContainersCreateController,
                controllerAs: 'ContainersCreateCtrl',
                backdrop: 'static',
                resolve: {
                    project: project
                }
            });
        }]
    })
}];

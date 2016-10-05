import ContainerEditController from "./container-edit.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project.containers.container.edit', {
        url: "/edit",
        onEnter: ['$uibModal', 'project', 'container', ($uibModal, project, container) => {
            $uibModal.open({
                templateUrl: '/app/components/projects/project/containers/container/edit/container-edit.template.html',
                controller: ContainerEditController,
                controllerAs: 'ContainerEditCtrl',
                backdrop: 'static',
                resolve: {
                    project: project,
                    container: container
                }
            });
        }]
    })
}];

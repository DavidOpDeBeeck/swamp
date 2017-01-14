import ContainerModalController from "./modal/container-modal.controller";

class ContainersController {
    constructor(ProjectService, project, $uibModal) {
        this.project = project;
        this.$uibModal = $uibModal;
        this.projectService = ProjectService;
        this.getProjectContainers();
    }

    getProjectContainers() {
        this.projectService.getProjectContainers(this.project.id)
            .then((containers) => this.containers = containers);
    }

    create() {
        let modal = this.createModal({}, false);
        modal.result.then((container) => {
            this.projectService.createContainer(this.project.id, container)
                .then(() => this.getProjectContainers());
        });
    }

    edit(container) {
        let modal = this.createModal(container, true);
        modal.result.then((container) => {
            container.$update()
                .then(() => this.getProjectContainers());
        });
    }

    createModal(container, update) {
        return this.$uibModal.open({
            backdrop: 'static',
            controllerAs: 'ContainerModalCtrl',
            controller: ContainerModalController,
            templateUrl: "/assets/templates/container-modal.template.html",
            resolve: {
                container: () => container,
                update: update
            }
        });
    }

    delete(container) {
        container.$delete()
            .then(() => this.getProjectContainers());
    }
}

export default ['ProjectService', 'project', '$uibModal', ContainersController]
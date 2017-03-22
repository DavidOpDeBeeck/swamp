import ContainerTemplateModalController from "./modal/container-template-modal.controller";

class ContainerTemplatesController {
    constructor(ProjectService, project, $uibModal) {
        this.project = project;
        this.$uibModal = $uibModal;
        this.projectService = ProjectService;
        this.getProjectContainerTemplates();
    }

    getProjectContainerTemplates() {
        this.projectService.getProjectContainerTemplates(this.project.id)
            .then(templates => this.containerTemplates = templates);
    }

    create() {
        let modal = this.createModal({}, false);
        modal.result.then(() => this.getProjectContainerTemplates());
    }

    edit(containerTemplate) {
        let modal = this.createModal(containerTemplate, true);
        modal.result.then(() => this.getProjectContainerTemplates());
    }

    createModal(containerTemplate, update) {
        return this.$uibModal.open({
            backdrop: 'static',
            controllerAs: 'ContainerModalCtrl',
            controller: ContainerTemplateModalController,
            templateUrl: "/assets/templates/container-template-modal.template.html",
            resolve: {
                containerTemplate: () => containerTemplate,
                project: () => this.project,
                update: update
            }
        });
    }

    delete(container) {
        container.$delete()
            .then(() => this.getProjectContainerTemplates());
    }
}

export default ['ProjectService', 'project', '$uibModal', ContainerTemplatesController]
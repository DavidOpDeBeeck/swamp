import ProjectModalController from "./modal/project-modal.controller";

class ProjectsController {
    constructor(ProjectService, $uibModal) {
        this.projectService = ProjectService;
        this.$uibModal = $uibModal;
        this.getAllProjects();
    }

    getAllProjects() {
        this.projectService.getAllProjects()
            .then((projects) => this.projects = projects);
    }

    deploy(project) {
        project.$deploy()
            .then(() => this.getAllProjects());
    }

    create() {
        let modal = this.createModal({}, false);
        modal.result.then((project) => {
            this.projectService.createProject(project)
                .then(() => this.getAllProjects());
        });
    }

    edit(project) {
        let modal = this.createModal(project, true);
        modal.result.then((project) => {
            project.$update()
                .then(() => this.getAllProjects());
        });
    }

    createModal(project, update) {
        return this.$uibModal.open({
            backdrop: 'static',
            controllerAs: 'ProjectModalCtrl',
            controller: ProjectModalController,
            templateUrl: "/assets/templates/project-modal.template.html",
            resolve: {
                project: () => project,
                update: update
            }
        });
    }

    delete(project) {
        project.$delete()
            .then(() => this.getAllProjects());
    }
}

export default ['ProjectService', '$uibModal', ProjectsController]
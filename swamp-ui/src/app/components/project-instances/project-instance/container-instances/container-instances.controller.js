class ContainerInstancesController {
    constructor(ProjectInstanceService, projectInstance) {
        this.projectInstance = projectInstance;
        this.projectInstanceService = ProjectInstanceService;
        this.getProjectInstanceContainerInstances();
    }

    getProjectInstanceContainerInstances() {
        this.projectInstanceService.getProjectInstanceContainerInstances(this.projectInstance.projectInstanceId)
            .then((containerInstances) => this.containerInstances = containerInstances);
    }
}

export default ['ProjectInstanceService', 'projectInstance', ContainerInstancesController]
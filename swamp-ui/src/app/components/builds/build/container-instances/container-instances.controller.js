class ContainerInstancesController {
    constructor(BuildService, build) {
        this.build = build;
        this.buildService = BuildService;
        this.getProjectInstanceContainerInstances();
    }

    getProjectInstanceContainerInstances() {
        this.buildService.getBuildContainerInstances(this.build.buildId)
            .then((containerInstances) => this.containerInstances = containerInstances);
    }
}

export default ['BuildService', 'build', ContainerInstancesController]
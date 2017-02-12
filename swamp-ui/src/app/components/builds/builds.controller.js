class BuildsController {
    constructor(BuildService) {
        BuildService.getAllProjects()
            .then((projects) => this.projects = projects);
    }
}

export default ['BuildService', BuildsController]
class ProjectInstancesController {
    constructor(ProjectInstanceService, $stomp) {
        this.projectInstanceService = ProjectInstanceService;
        this.getAllProjects();
        $stomp
            .connect('http://localhost:8081/schedule')
            .then(() => {
                $stomp.subscribe('/topic/container-updates', (payload) => {
                    console.log(payload);
                })
            });
    }

    getAllProjects() {
        this.projectInstanceService.getAllProjects()
            .then((projects) => this.projects = projects);
    }
}

export default ['ProjectInstanceService', '$stomp', ProjectInstancesController]
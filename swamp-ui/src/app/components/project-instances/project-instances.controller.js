class ProjectInstancesController {
    constructor(ProjectInstanceService, $stomp) {
        this.projectInstanceService = ProjectInstanceService;
        this.getAllProjectInstances();
        $stomp
            .connect('http://localhost:8081/schedule')
            .then(() => {
                $stomp.subscribe('/topic/container-updates', (payload) => {
                    console.log(payload);
                })
            });
    }

    getAllProjectInstances() {
        this.projectInstanceService.getAllProjectInstances()
            .then((projectInstances) => this.projectInstances = projectInstances);
    }
}

export default ['ProjectInstanceService', '$stomp', ProjectInstancesController]
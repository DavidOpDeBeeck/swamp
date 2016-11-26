class SchedulerController {
    constructor(SchedulerService, $stomp) {
        this.schedulerService = SchedulerService;
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
        this.schedulerService.getAllProjects()
            .then((projects) => this.projects = projects);
    }
}

export default ['SchedulerService', '$stomp', SchedulerController]
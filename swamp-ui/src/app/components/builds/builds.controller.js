class BuildsController {
    constructor(BuildService, NotificationService, $timeout) {
        this.builds = {};
        this.$timeout = $timeout;
        this.buildService = BuildService;
        this.notificationService = NotificationService;
        this.initialize();
        this.initializeListeners();
    }

    initialize() {
        this.buildService.getAllProjects()
            .then(projects => this.initializeProjects(projects));
    }

    initializeListeners() {
        this.notificationService.on({
            eventTypes: ['BuildCreatedEvent'],
            callback: event => {
                this.$timeout(() => {
                    this.buildService.getBuild(event.buildId).then(build => {
                        let index = this.projects.findIndex(p => p.projectId = event.projectId);
                        this.projects[index].builds.push(build);
                    });
                }, 2000);
            }
        });
    }

    initializeProjects(projects) {
        this.projects = projects;
        this.projects.forEach(project => {
            project.builds.forEach(build => this.initializeBuild(build));
        });
    }

    initializeBuild(build) {
        this.builds[build.buildId] = build.status;
        // TODO: dont listen of build is finished
        this.notificationService.on({
            eventTypes: ['BuildFinishedEvent'],
            identifier: event => event.buildId === build.buildId,
            callback: event => this.builds[event.buildId] = 'FINISHED'
        });
    }

    inProgress(build) {
        return this.builds[build.buildId] === 'INPROGRESS';
    }
}

export default ['BuildService', 'NotificationService', '$timeout', BuildsController]
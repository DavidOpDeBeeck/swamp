class BuildsController {
    constructor(BuildService, NotificationService) {
        this.builds = {};
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
            eventTypes: ['BuildInitializedEvent'],
            callback: event => this.initialize()
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

export default ['BuildService', 'NotificationService', BuildsController]
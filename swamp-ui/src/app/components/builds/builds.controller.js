class BuildsController {
    constructor(BuildService, NotificationService, $timeout) {
        this.builds = {};
        BuildService.getAllProjects()
            .then((projects) => {
                this.projects = projects;
                this.projects.forEach(project => {
                    project.builds.forEach(build => {
                        this.builds[build.buildId] = build.status;
                        NotificationService.on({
                            eventTypes : ['BuildFinishedEvent'],
                            buildId: build.buildId,
                            callback: event => {
                                $timeout(() => this.builds[event.buildId] = 'FINISHED');
                            }
                        });
                    });
                });
            });
    }

    inProgress(build) {
        return this.builds[build.buildId] === 'INPROGRESS';
    }
}

export default ['BuildService', 'NotificationService', '$timeout', BuildsController]
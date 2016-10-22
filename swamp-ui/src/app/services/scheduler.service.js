class SchedulerService {
    constructor($resource) {
        this.schedulerProjectResource = $resource('/api/scheduler/projects/:id', {id: '@id'});
        this.schedulerContainerResource = $resource('/api/scheduler/projects/:projectId/containers/:containerId', {
            projectId: '@projectId',
            containerId: '@containerId'
        }, {
            'start' : {
                method: 'POST',
                params: {'action' : 'start'}
            },
            'stop' : {
                method: 'POST',
                params: {'action' : 'stop'}
            },
            'restart' : {
                method: 'POST',
                params: {'action' : 'restart'}
            }
        });
    }

    getProject(id) {
        return this.schedulerProjectResource.get({id: id}).$promise;
    }

    getContainer(projectId, containerId) {
        return this.schedulerContainerResource.get({projectId: projectId, containerId: containerId})
            .$promise
            .then((container) => this.mapInstance(projectId, container));
    }

    getProjectContainers(projectId) {
        return this.schedulerContainerResource.query({projectId: projectId})
            .$promise
            .then((containers) => containers.map((container) => this.mapInstance(projectId, container)));
    }

    getAllProjects() {
        return this.schedulerProjectResource.query().$promise;
    }

    mapInstance(projectId, instance) {
        instance['projectId'] = projectId;
        instance['containerId'] = instance.container.id;
        return instance;
    }
}

export default  ['$resource', SchedulerService]

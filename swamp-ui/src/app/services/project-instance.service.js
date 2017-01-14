class ProjectInstanceService {
    constructor($resource) {
        this.projectInstanceResource = $resource('/api/projectInstances/:projectInstanceId', {projectInstanceId: '@projectInstanceId'});
        this.containerInstanceResource = $resource('/api/projectInstances/:projectInstanceId/containerInstances/:containerInstanceId', {
            projectInstanceId: '@projectInstanceId',
            containerInstanceId: '@containerInstanceId'
        }, {
            'start': {
                method: 'POST',
                params: {'action': 'start'}
            },
            'stop': {
                method: 'POST',
                params: {'action': 'stop'}
            },
            'restart': {
                method: 'POST',
                params: {'action': 'restart'}
            }
        });
    }

    getProjectInstance(id) {
        return this.projectInstanceResource.get({projectInstanceId: id}).$promise;
    }

    getContainerInstance(projectInstanceId, containerInstanceId) {
        return this.containerInstanceResource
            .get({projectInstanceId: projectInstanceId, containerInstanceId: containerInstanceId})
            .$promise
            .then((container) => this.mapInstance(projectInstanceId, container));
    }

    getProjectInstanceContainerInstances(projectInstanceId) {
        return this.containerInstanceResource
            .query({projectInstanceId: projectInstanceId})
            .$promise
            .then((containerInstances) => containerInstances.map((containerInstance) => this.mapInstance(projectInstanceId, containerInstance)));
    }

    getAllProjects() {
        return this.projectInstanceResource.query().$promise;
    }

    mapInstance(projectId, instance) {
        instance['projectInstanceId'] = projectId;
        return instance;
    }
}

export default ['$resource', ProjectInstanceService]

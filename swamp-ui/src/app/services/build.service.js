class BuildService {
    constructor($resource) {
        this.buildResource = $resource('/api/builds/:buildId', {buildId: '@buildId'});
        this.containerInstanceResource = $resource('/api/builds/:buildId/containerInstances/:containerInstanceId', {
            buildId: '@buildId',
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
        this.containerInstanceLogResource = $resource('/api/builds/:buildId/containerInstances/:containerInstanceId/logs', {
            buildId: '@buildId',
            containerInstanceId: '@containerInstanceId'
        });
    }

    getBuild(id) {
        return this.buildResource.get({buildId: id}).$promise;
    }

    getContainerInstance(buildId, containerInstanceId) {
        return this.containerInstanceResource
            .get({
                buildId: buildId,
                containerInstanceId: containerInstanceId
            })
            .$promise
            .then((container) => this.mapInstance(buildId, container));
    }

    getContainerInstanceLogs(buildId, containerInstanceId) {
        return this.containerInstanceLogResource
            .get({buildId: buildId, containerInstanceId: containerInstanceId}).$promise;
    }

    getBuildContainerInstances(buildId) {
        return this.containerInstanceResource
            .query({buildId: buildId})
            .$promise
            .then((containerInstances) => containerInstances.map((containerInstance) => this.mapInstance(buildId, containerInstance)));
    }

    getAllProjects() {
        return this.buildResource.query().$promise;
    }

    mapInstance(buildId, instance) {
        instance['buildId'] = buildId;
        return instance;
    }
}

export default ['$resource', BuildService]

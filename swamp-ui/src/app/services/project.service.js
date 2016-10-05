class ProjectService {
    constructor($resource) {
        this.projectResource = $resource('/api/projects/:id', {id: '@id'}, {
            create: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
            }
        });
        this.containerResource = $resource('/api/projects/:projectId/containers/:containerId', {
            projectId: '@projectId',
            containerId: '@containerId'
        }, {
            create: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
            }
        });
    }

    createProject(project) {
        return this.projectResource.create(project).$promise;
    }

    updateProject(project) {
        return this.projectResource.update(project).$promise;
    }

    getProject(id) {
        return this.projectResource.get({id: id}).$promise;
    }

    createContainer(container) {
        return this.containerResource.create(container).$promise;
    }

    updateContainer(container) {
        return this.containerResource.update(container).$promise;
    }

    getContainer(projectId, containerId) {
        return this.containerResource.get({projectId: projectId, containerId: containerId})
            .$promise
            .then((container) => this.mapContainer(projectId, container));
    }

    getProjectContainers(projectId) {
        return this.containerResource.query({projectId: projectId})
            .$promise
            .then((containers) => containers.map((container) => this.mapContainer(projectId, container)));
    }

    mapContainer(projectId, container) {
        container['projectId'] = projectId;
        container['containerId'] = container.id;
        return container;
    }

    getAllProjects() {
        return this.projectResource.query().$promise;
    }
}

export default  ['$resource', ProjectService]

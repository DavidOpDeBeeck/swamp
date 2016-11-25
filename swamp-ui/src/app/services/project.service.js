class ProjectService {
    constructor($resource) {
        this.projectResource = $resource('/api/projects/:id', {id: '@id'}, {
            create: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
            },
            deploy: {
                method: 'POST',
                params: {action: 'deploy'}
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

    getProject(id) {
        return this.projectResource.get({id: id}).$promise;
    }

    createContainer(container) {
        console.log(container);
        return this.containerResource.create(container).$promise;
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

    getAllProjects() {
        return this.projectResource.query().$promise;
    }
    
    mapContainer(projectId, container) {
        container['projectId'] = projectId;
        container['containerId'] = container.id;
        return container;
    }
}

export default  ['$resource', ProjectService]

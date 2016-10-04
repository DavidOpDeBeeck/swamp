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

    createContainer(container) {
        return this.containerResource.create(container).$promise;
    }

    getProject(id) {
        return this.projectResource.get({id: id}).$promise;
    }

    getProjectContainers(projectId) {
        return this.containerResource.query({projectId: projectId})
            .$promise
            .then((containers) => {
                return containers.map((container) => {
                    container['projectId'] = projectId;
                    container['containerId'] = container.id;
                    return container;
                });
            });
    }

    getAllProjects() {
        return this.projectResource.query().$promise;
    }
}

export default  ['$resource', ProjectService]

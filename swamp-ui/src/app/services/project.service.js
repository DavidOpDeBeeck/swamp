class ProjectService {
    constructor($resource) {
        this.projectResource = $resource('/api/projects/:id', {id: '@id'}, {
            create: {method: 'POST'},
            update: {method: 'PUT'},
            deploy: {method: 'POST', params: {action: 'deploy'}}
        });
        this.containerTemplateResource = $resource('/api/projects/:projectId/container-templates/:containerTemplateId', {
            projectId: '@projectId',
            containerTemplateId: '@containerTemplateId'
        }, {
            create: {method: 'POST'},
            update: {method: 'PUT'}
        });
    }

    createProject(project) {
        return this.projectResource.create(project).$promise;
    }

    getProject(id) {
        return this.projectResource.get({id: id}).$promise;
    }

    getProjectContainerTemplates(projectId) {
        return this.containerTemplateResource.query({projectId: projectId})
            .$promise
            .then(this.mapContainerTemplates(projectId));
    }

    getAllProjects() {
        return this.projectResource.query().$promise;
    }

    createContainerTemplate(projectId, containerTemplate) {
        return this.containerTemplateResource
            .create({projectId: projectId}, containerTemplate)
            .$promise;
    }

    getContainerTemplate(projectId, containerId) {
        return this.containerTemplateResource
            .get({projectId: projectId, containerId: containerId})
            .$promise
            .then(this.mapContainerTemplate(projectId));
    }

    mapContainerTemplates(projectId) {
        return templates => templates.map(this.mapContainerTemplate(projectId));
    }

    mapContainerTemplate(projectId) {
        return template => {
            template['projectId'] = projectId;
            template['containerTemplateId'] = template.id;
            return template;
        }
    }
}

export default  ['$resource', ProjectService]

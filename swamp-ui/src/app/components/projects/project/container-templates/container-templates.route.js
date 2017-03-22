import ContainerTemplatesController from "./container-templates.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects.project.container-templates', {
        url: "/container-templates",
        data: {
            displayName: '{{ project.name }}',
            disabled: false
        },
        templateUrl: '/assets/templates/container-templates.template.html',
        controller: ContainerTemplatesController,
        controllerAs: 'ContainerTemplatesCtrl'
    });
}];

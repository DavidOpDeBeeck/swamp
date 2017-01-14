import ProjectsController from "./projects.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects', {
        url: "/projects",
        data: {
            displayName: 'projects',
            disabled: false
        },
        templateUrl: '/assets/templates/projects.template.html',
        controller: ProjectsController,
        controllerAs: 'ProjectCtrl'
    });
}];

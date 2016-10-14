import ProjectsController from "./projects.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('projects', {
        url: "/projects",
        data: {
            displayName: 'projects',
            disabled: false
        },
        templateUrl: '/app/components/projects/projects.template.html',
        controller: ProjectsController,
        controllerAs: 'ProjectCtrl'
    });
}];

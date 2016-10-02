export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('projects', {
        url: "/projects",
        templateUrl: '/app/components/projects/projects.template.html',
        controller: 'ProjectsController as projects'
    });
}];

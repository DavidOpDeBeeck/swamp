import buildsController from "./builds.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('builds', {
        url: "/builds",
        data: {
            displayName: 'builds',
            disabled: false
        },
        templateUrl: '/assets/templates/builds.template.html',
        controller: buildsController,
        controllerAs: 'BuildsCtrl'
    });
}];

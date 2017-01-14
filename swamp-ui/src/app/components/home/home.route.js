export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('home', {
        url: "/",
        templateUrl: '/assets/templates/home.template.html'
    });
}];

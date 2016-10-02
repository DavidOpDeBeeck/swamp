export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('home', {
        url: "/",
        templateUrl: '/app/components/home/home.template.html'
    });
}];

export default ['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    $stateProvider.state('home', {
        url: "/",
        data: {
            displayName: 'home',
            disabled: false
        },
        templateUrl: '/assets/templates/home.template.html'
    });
}];

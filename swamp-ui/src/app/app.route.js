function defaultRoute($urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
}

export default ['$urlRouterProvider', defaultRoute];


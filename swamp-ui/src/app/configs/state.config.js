function SavePreviousStateInRootScope($rootScope) {
    $rootScope.$on('$stateChangeSuccess', (event, to, toParams, from, fromParams) => {
        $rootScope.$previousState = {
            name: from.name,
            params: fromParams
        };
    });
}

export default ['$rootScope', SavePreviousStateInRootScope]

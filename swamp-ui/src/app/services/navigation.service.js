class NavigationService {
    constructor($state, $rootScope) {
        this.state = $state;
        this.rootScope = $rootScope;
    }

    goBack(fallback) {
        let name = this.rootScope.$previousState.name;
        let params = this.rootScope.$previousState.params;

        if (name.length > 0)
            this.state.go(name, params);
        else
            this.state.go(fallback);
    }

    goTo(state, params) {
        this.state.go(state, params, {reload: true});
    }
}

export default ['$state', '$rootScope', NavigationService]

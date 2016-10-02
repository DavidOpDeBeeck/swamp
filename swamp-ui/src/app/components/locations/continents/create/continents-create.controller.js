class ContinentsCreateController {
    constructor(LocationService, $state, $scope) {
        this.$state = $state;
        this.$scope = $scope;
        this.locationService = LocationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.$state.go('continents');
    }

    create() {
        this.locationService.createContinent({
            'name': this.name
        }).then((continent) => {
            this.$scope.$close(true);
            this.$state.go('continents.datacenters', {continentId: continent.id}, {reload: true});
        });
    }
}

export default ['LocationService', '$state', '$scope', ContinentsCreateController]

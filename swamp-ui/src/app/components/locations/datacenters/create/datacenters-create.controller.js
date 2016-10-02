class DatacentersCreateController {
    constructor(LocationService, continent, $state, $scope) {
        this.$state = $state;
        this.$scope = $scope;
        this.continent = continent;
        this.locationService = LocationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.$state.go('continents.datacenters');
    }

    create() {
        this.locationService.createDatacenter({
            'name': this.name,
            'continentId': this.continent.id
        }).then((datacenter) => {
            this.$scope.$close(true);
            this.$state.go('continents.datacenters.servers', {
                continentId: this.continent.id,
                datacenterId: datacenter.id
            }, {reload: true});
        });
    }
}

export default ['LocationService', 'continent', '$state', '$scope', DatacentersCreateController]

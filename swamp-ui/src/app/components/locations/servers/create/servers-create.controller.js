class ServersCreateController {
    constructor(LocationService, continent, datacenter, $state, $scope) {
        this.$state = $state;
        this.$scope = $scope;
        this.continent = continent;
        this.datacenter = datacenter;
        this.locationService = LocationService;
    }

    cancel() {
        this.$scope.$dismiss();
        this.$state.go('continents.datacenters.servers', {
            continentId: this.continent.id,
            datacenterId: this.datacenter.id
        });
    }

    create() {
        console.log(this.CAcertificate);
        this.locationService.createServer({
            'name': this.name,
            'ip': this.ip,
            'CAcertificate': this.CAcertificate,
            'certificate': this.certificate,
            'key': this.key,
            'continentId': this.continent.id,
            'datacenterId': this.datacenter.id
        }).then((server) => {
            this.$scope.$close(true);
            this.$state.go('continents.datacenters.servers', {
                continentId: this.continent.id,
                datacenterId: this.datacenter.id
            }, {reload: true});
        });
    }
}

export default ['LocationService', 'continent', 'datacenter', '$state', '$scope', ServersCreateController]

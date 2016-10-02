class LocationService {
    constructor($resource) {
        this.locationResource = $resource('/api/locations/:id', {id: '@id'});
        this.continentResource = $resource('/api/continents/:id', {id: '@id'}, {
            create: {
                method: 'POST'
            }
        });
        this.datacentersResource = $resource('/api/continents/:continentId/datacenters/:datacenterId', {
            continentId: '@continentId',
            datacenterId: '@datacenterId'
        }, {
            create: {
                method: 'POST'
            }
        });
        this.serversResource = $resource('/api/continents/:continentId/datacenters/:datacenterId/servers/:serverId', {
            continentId: '@continentId',
            datacenterId: '@datacenterId',
            serverId: '@serverId'
        }, {
            create: {
                method: 'POST'
            }
        });
    }

    createContinent(continent) {
        return this.continentResource.create(continent).$promise;
    }

    getContinent(continentId) {
        return this.continentResource.get({
            id: continentId
        }).$promise;
    }

    createDatacenter(datacenter) {
        return this.datacentersResource.create(datacenter).$promise;
    }

    getDatacenter(continentId, datacenterId) {
        return this.datacentersResource.get({
            continentId: continentId,
            datacenterId: datacenterId
        }).$promise;
    }

    createServer(server) {
        return this.serversResource.create(server).$promise;
    }

    getAllContinents() {
        return this.continentResource.query().$promise;
    }

    getAllDatacenters(continentId) {
        return this.datacentersResource.query({
            continentId: continentId
        }).$promise.then((datacenters) => {
            return datacenters.map((datacenter) => {
                datacenter['continentId'] = continentId;
                datacenter['datacenterId'] = datacenter.id;
                return datacenter;
            });
        });
    }

    getAllServers(continentId, datacenterId) {
        return this.serversResource.query({
            continentId: continentId,
            datacenterId: datacenterId
        }).$promise.then((datacenters) => {
            return datacenters.map((server) => {
                server['continentId'] = continentId;
                server['datacenterId'] = datacenterId;
                server['serverId'] = server.id;
                return server;
            });
        });
    }

    getAllLocations() {
        return this.locationResource.query().$promise;
    }
}

export default LocationService

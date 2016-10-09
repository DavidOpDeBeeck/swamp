class LocationService {
    constructor($resource) {
        this.locationResource = $resource('/api/locations/:id', {id: '@id'});
        this.continentResource = $resource('/api/continents/:id', {id: '@id'}, {
            create: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
            }
        });
        this.datacentersResource = $resource('/api/continents/:continentId/datacenters/:datacenterId', {
            continentId: '@continentId',
            datacenterId: '@datacenterId'
        }, {
            create: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
            }
        });
        this.serversResource = $resource('/api/continents/:continentId/datacenters/:datacenterId/servers/:serverId', {
            continentId: '@continentId',
            datacenterId: '@datacenterId',
            serverId: '@serverId'
        }, {
            create: {
                method: 'POST'
            },
            update: {
                method: 'PUT'
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
            })
            .$promise
            .then((datacenter) => this.mapDatacenter(continentId, datacenter));
    }

    createServer(server) {
        return this.serversResource.create(server).$promise;
    }

    getServer(continentId, datacenterId, serverId) {
        return this.serversResource.get({
                continentId: continentId,
                datacenterId: datacenterId,
                serverId: serverId
            })
            .$promise
            .then((server) => this.mapServer(continentId, datacenterId, server));
    }

    getAllContinents() {
        return this.continentResource.query().$promise;
    }

    getAllDatacenters(continentId) {
        return this.datacentersResource.query({
            continentId: continentId
        }).$promise.then((datacenters) => datacenters.map((datacenter) => this.mapDatacenter(continentId, datacenter)));
    }

    getAllServers(continentId, datacenterId) {
        return this.serversResource.query({
            continentId: continentId,
            datacenterId: datacenterId
        }).$promise.then((datacenters) => datacenters.map((server) => this.mapServer(continentId, datacenterId, server)));
    }

    getAllLocations() {
        return this.locationResource.query().$promise;
    }

    mapDatacenter(continentId, datacenter) {
        datacenter['continentId'] = continentId;
        datacenter['datacenterId'] = datacenter.id;
        return datacenter;
    }

    mapServer(continentId, datacenterId, server) {
        server['continentId'] = continentId;
        server['datacenterId'] = datacenterId;
        server['serverId'] = server.id;
        return server;
    }
}

export default  ['$resource', LocationService]

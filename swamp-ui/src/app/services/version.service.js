class VersionService {
    constructor($resource) {
        this.versionResource = $resource('/api/version');
    }

    getVersion() {
        return this.versionResource.get().$promise;
    }
}

export default  ['$resource', VersionService]

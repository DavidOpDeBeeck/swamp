class VersionController {
    constructor(VersionService) {
        this.versionService = VersionService;
        this.getVersion();
    }

    getVersion() {
        this.versionService.getVersion().then((response) => this.version = response.version);
    }
}

export default ['VersionService', VersionController]
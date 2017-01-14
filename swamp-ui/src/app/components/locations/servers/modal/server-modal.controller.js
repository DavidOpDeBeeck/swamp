class ServerModalController {
    constructor(server, update, $uibModalInstance) {
        this.server = server;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.$uibModalInstance.close(this.server);
    }
}

export default ['server', 'update', '$uibModalInstance', ServerModalController]


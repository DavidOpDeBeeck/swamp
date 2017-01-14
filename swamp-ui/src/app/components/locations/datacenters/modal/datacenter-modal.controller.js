class DatacenterModalController {
    constructor(datacenter, update, $uibModalInstance) {
        this.datacenter = datacenter;
        this.update = update;
        this.$uibModalInstance = $uibModalInstance;
    }

    cancel() {
        this.$uibModalInstance.dismiss();
    }

    submit() {
        this.$uibModalInstance.close(this.datacenter);
    }
}

export default ['datacenter', 'update', '$uibModalInstance', DatacenterModalController]


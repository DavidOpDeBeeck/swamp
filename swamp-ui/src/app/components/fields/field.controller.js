class FieldController {
    constructor() {
        this.id = this.label ? this.label.toLowerCase().replace(" ", "-") : "";
    }
}

export default FieldController
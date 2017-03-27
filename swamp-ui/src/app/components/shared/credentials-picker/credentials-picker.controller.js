import Angular from "angular";

class CredentialsPickerController {
    constructor() {
        Angular.extend(this.credentials, {type: this.type});
    }
}

export default CredentialsPickerController
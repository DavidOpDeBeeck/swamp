import FieldController from "../field.controller";

class MultipleFieldsController extends FieldController {
    constructor() {
        super();
        this.list = this.list ? this.list : [];
    }

    add() {
        this.list.push(undefined);
    }

    remove(index) {
        this.list.splice(index, 1);
    }
}

export default MultipleFieldsController
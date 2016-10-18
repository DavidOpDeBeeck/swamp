import FieldController from "./../field.controller";

class MultipleFieldsController extends FieldController {
    constructor() {
        super();
    }

    add() {
        this.list.push(undefined);
    }

    remove(index) {
        this.list.splice(index, 1);
    }
}

export default MultipleFieldsController
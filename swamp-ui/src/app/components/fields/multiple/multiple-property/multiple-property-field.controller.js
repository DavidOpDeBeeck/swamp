import MultipleFieldController from "../multiple.field.controller.js";

class MultiplePropertyFieldController extends MultipleFieldController {
    constructor() {
        super();
    }

    add() {
        let newItem = {};
        newItem[this.nameField] = "";
        newItem[this.valueField] = "";
        this.list.push(newItem);
    }

    remove(index) {
        this.list.splice(index, 1);
    }
}

export default MultiplePropertyFieldController
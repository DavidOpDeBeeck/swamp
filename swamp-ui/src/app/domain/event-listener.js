class EventListener {
    constructor({eventTypes = [], identifier = () => true, callback}) {
        this.eventTypes = eventTypes;
        this.identifier = identifier;
        this.callback = callback;
    }

    on(type, event) {
        let found = this.eventTypes.find(t => t === type) || this.eventTypes.length == 0;
        if (found) {
            let identified = this.identifier(event);
            if (identified) {
                return this.callback;
            }
        }
    }
}

export default EventListener
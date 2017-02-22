const API_URL = 'http://localhost:8081/schedule';
const CONTAINER_CHANNEL = '/topic/container-updates';

class NotificationService {
    constructor($stomp) {
        $stomp.connect(API_URL)
            .then(() => $stomp.subscribe(CONTAINER_CHANNEL, payload => this.handle(payload)));
        this.eventListeners = {};
    }

    handle(payload) {
        let type = payload.type, event = payload.event;
        if (this.eventListeners[type] && this.eventListeners[type][event.containerInstanceId])
            this.eventListeners[type][event.containerInstanceId].forEach(callback => callback(event));
    }

    on({eventTypes, containerInstanceId, callback}) {
        eventTypes.forEach(eventType => {
            if (!this.eventListeners[eventType])
                this.eventListeners[eventType] = {};
            if (!this.eventListeners[containerInstanceId])
                this.eventListeners[eventType][containerInstanceId] = [];
            this.eventListeners[eventType][containerInstanceId].push(callback);
        });
    }
}

export default ['$stomp', NotificationService]

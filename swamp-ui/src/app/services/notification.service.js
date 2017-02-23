const API_URL = 'http://localhost:8081/schedule';
const BUILD_CHANNEL = '/topic/build-updates';
const CONTAINER_CHANNEL = '/topic/container-updates';

class NotificationService {
    constructor($stomp) {
        $stomp.connect(API_URL)
            .then(() => {
                $stomp.subscribe(BUILD_CHANNEL, payload => this.handleBuildEvent(payload));
                $stomp.subscribe(CONTAINER_CHANNEL, payload => this.handleContainerInstanceEvent(payload));
            });
        this.eventListeners = {};
    }

    handleContainerInstanceEvent(payload) {
        let type = payload.type, event = payload.event;
        if (this.eventListeners[type] && this.eventListeners[type][event.containerInstanceId])
            this.eventListeners[type][event.containerInstanceId].forEach(callback => callback(event));
    }

    handleBuildEvent(payload) {
        let type = payload.type, event = payload.event;
        if (this.eventListeners[type] && this.eventListeners[type][event.containerInstanceId])
            this.eventListeners[type][event.containerInstanceId].forEach(callback => callback(event));
    }

    on({eventTypes, containerInstanceId, buildId, callback}) {
        eventTypes.forEach(eventType => {
            if (!this.eventListeners[eventType]) {
                this.eventListeners[eventType] = {};
            }
            if (!this.eventListeners[containerInstanceId]) {
                this.eventListeners[eventType][containerInstanceId] = [];
                this.eventListeners[eventType][containerInstanceId].push(callback);
            }
            if (!this.eventListeners[buildId]) {
                this.eventListeners[eventType][buildId] = [];
                this.eventListeners[eventType][buildId].push(callback);
            }
        });
    }
}

export default ['$stomp', NotificationService]

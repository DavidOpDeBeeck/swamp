import Logger from "domain/logger";
import EventListener from "domain/event-listener";

const BUILD_CHANNEL = '/topic/build-updates';
const CONTAINER_CHANNEL = '/topic/container-updates';

class NotificationService {
    constructor($stomp, $timeout, Notification, BROKER_URL) {
        this.$stomp = $stomp;
        this.$timeout = $timeout;
        this.notification = Notification;
        this.eventListeners = [this.defaultNotificationListener()];
        $timeout(() => this.connectToSocket(BROKER_URL), 1000);
    }

    connectToSocket(brokerUrl) {
        this.$stomp.connect(brokerUrl)
            .then((f) => {
                this.$stomp.subscribe(BUILD_CHANNEL, message => this.handle(message));
                Logger.info("Successfully connected to " + BUILD_CHANNEL);
                this.$stomp.subscribe(CONTAINER_CHANNEL, message => this.handle(message));
                Logger.info("Successfully connected to " + CONTAINER_CHANNEL);
            });
    }

    handle(message) {
        let type = message.type, event = message.event;
        let listeners = this.eventListeners
            .map(listener => listener.on(type, event))
            .filter(callback => callback)
            .forEach(callback => this.$timeout(callback(event, type), 100));
    }

    on({eventTypes, identifier, callback}) {
        this.eventListeners.push(new EventListener({
            eventTypes: eventTypes,
            identifier: identifier,
            callback: callback
        }));
    }

    defaultNotificationListener() {
        return new EventListener({
            callback: (event, type) => {
                switch (type) {
                    case "ContainerInstanceStartedSucceededEvent":
                        this.display("Container started", event.containerInstanceId, "success");
                        break;
                    case "ContainerInstanceStoppedSucceededEvent":
                        this.display("Container stopped", event.containerInstanceId, "warning");
                        break;
                    case "ContainerInstanceRemovedSucceededEvent":
                        this.display("Container removed", event.containerInstanceId, "error");
                        break;
                }
            }
        });
    }

    display(title, message, type) {
        this.notification({title: title, message: message}, type);
    }
}

export default ['$stomp', '$timeout', 'Notification', 'BROKER_URL', NotificationService]

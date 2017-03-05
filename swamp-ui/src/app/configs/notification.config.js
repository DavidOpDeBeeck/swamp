function connectToWebsocket(NotificationService, BROKER_URL) {
    NotificationService.connectToSocket(BROKER_URL)
}

export default ['NotificationService','BROKER_URL', connectToWebsocket];


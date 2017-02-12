function SetupNotifications($stomp, Notification) {
    $stomp
        .connect('http://localhost:8081/schedule')
        .then(() => {
            $stomp.subscribe('/topic/container-updates', payload => handle(payload));
        });

    function handle(payload) {
        let type = payload.type;
        let event = payload.event;
        switch (type) {
            case "ContainerInstanceStartedSucceededEvent":
                display("Container started", event.containerInstanceId, "success");
                break;
            case "ContainerInstanceStoppedSucceededEvent":
                display("Container stopped", event.containerInstanceId, "warning");
                break;
            case "ContainerInstanceRemovedSucceededEvent":
                display("Container removed", event.containerInstanceId, "error");
                break;
        }
    }

    function display(title, message, type) {
        Notification({title: title, message: message}, type);
    }
}

export default ['$stomp', 'Notification', SetupNotifications]

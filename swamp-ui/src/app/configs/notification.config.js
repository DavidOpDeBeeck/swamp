function SetupNotifications($stomp, Notification) {
    $stomp
        .connect('http://localhost:8081/schedule')
        .then(() => {
            $stomp.subscribe('/topic/container-updates', payload => {
                handle(payload);
            })
        });

    function handle( event ) {
        switch (event.type) {
            case "ContainerInstanceStartedEvent":
                display("Container started", event.startedAt);
                break;
            case "ContainerInstanceStoppedEvent":
                display("Container stopped", event.stoppedAt);
                break;
            case "ContainerInstanceRemovedEvent":
                display("Container removed", event.removedAt);
                break;
        }
    }

    function display(title, message) {
        Notification({message: message, title: title});
    }
}

export default ['$stomp', 'Notification', SetupNotifications]

function SetupNotifications($stomp, Notification) {
    $stomp
        .connect('http://localhost:8081/schedule')
        .then(() => {
            $stomp.subscribe('/topic/container-updates', payload => {
                console.log(payload);
                if (payload.type !== "ContainerInstanceLogReceivedEvent")
                    Notification(payload.type);
            })
        });
}

export default ['$stomp', 'Notification', SetupNotifications]

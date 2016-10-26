class Logger {
    static info(msg ) {
        console.log("%c [INFO] " + msg, 'color: #337ab7;');
    }
    static error(msg ) {
        console.log("%c [ERROR] " + msg, 'color: #d9534f;');
    }
}

export default Logger
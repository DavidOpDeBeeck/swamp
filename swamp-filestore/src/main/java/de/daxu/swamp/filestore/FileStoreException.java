package de.daxu.swamp.filestore;

class FileStoreException extends RuntimeException {

    FileStoreException(String message) {
        super(message);
    }

    FileStoreException(Throwable cause) {
        super(cause);
    }

    FileStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}

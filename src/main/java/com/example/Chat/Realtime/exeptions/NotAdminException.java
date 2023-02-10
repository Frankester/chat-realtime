package com.example.Chat.Realtime.exeptions;

public class NotAdminException extends Exception{

    public NotAdminException(String message) {
        super(message);
    }

    public NotAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAdminException(Throwable cause) {
        super(cause);
    }

    protected NotAdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

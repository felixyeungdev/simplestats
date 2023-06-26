package com.williamneild.http;

public enum HttpStatusCode {

    CONTINUE (100, "Continue"),
    PROCESSING (102, "Processing"),

    OK (200, "OK"),
    NO_CONTENT (204, "No Content"),

    MOVED_PERMANENTLY (301, "Moved Permanently"),
    FOUND (302, "Found"),
    SEE_OTHER (303, "See Other"),
    NOT_MODIFIED (304, "Not Modified"),

    BAD_REQUEST (400, "Bad Request"),
    UNAUTHORIZED (401, "Unauthorized"),
    FORBIDDEN (403, "Forbidden"),
    NOT_FOUND (404, "Not Found"),

    INTERNAL_SERVER_ERROR (500, "Internal Server Error"),
    NOT_IMPLEMENTED (501, "Not Implemented"),
    SERVICE_UNAVAILABLE (503, "Service Unavailable"),
    HTTP_VERSION_NOT_SUPPORTED (505, "HTTP Version not supported");

    private int code;
    private String message;

    private HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return getCode() + " " + getMessage();
    }

}

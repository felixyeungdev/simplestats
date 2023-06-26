package com.williamneild.http;

@FunctionalInterface
public interface HttpRequestHandler {

    HttpResponse handle(HttpRequest request);

}
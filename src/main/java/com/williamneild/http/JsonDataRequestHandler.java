package com.williamneild.http;

import java.util.function.Supplier;

public class JsonDataRequestHandler implements HttpRequestHandler {

    private final Supplier<String> dataSupplier;

    public JsonDataRequestHandler(Supplier<String> dataSupplier) {
        this.dataSupplier = dataSupplier;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpResponse response = new HttpResponse(HttpStatusCode.OK);
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Content-Type", "application/json");
        response.setData(dataSupplier.get());
        return response;
    }

}

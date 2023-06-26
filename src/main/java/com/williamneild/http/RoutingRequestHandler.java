package com.williamneild.http;

import org.intellij.lang.annotations.Language;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoutingRequestHandler implements HttpRequestHandler {

    public LinkedList<Route> routes;

    public RoutingRequestHandler() {
        this.routes = new LinkedList<>();
    }

    public void register(@Language("RegExp") String pattern, HttpRequestHandler handler) {
        register(Pattern.compile(pattern), handler);
    }

    public void register(@Language("RegExp") String pattern, String replacementRoute, HttpRequestHandler handler) {
        register(Pattern.compile(pattern), replacementRoute, handler);
    }

    public void register(Pattern pattern, HttpRequestHandler handler) {
        this.routes.addFirst(new Route(pattern, handler));
    }

    public void register(Pattern pattern, String replacementRoute, HttpRequestHandler handler) {
        this.routes.addFirst(new Route(pattern, replacementRoute, handler));
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        String path = request.getPath();

        // normalize path
        if (path.startsWith("/")) path = path.substring(1);
        if (path.isEmpty()) path = "/";

        for (Route route : routes) {
            Matcher matcher = route.getRoutePattern().matcher(path);
            if (matcher.matches()) {
                request.setPath(matcher.replaceFirst(route.getReplacementRoute()));
                return route.getHandler().handle(request);
            }
        }

        return new HttpResponse(HttpStatusCode.BAD_REQUEST);
    }

    private static class Route {

        private final Pattern routePattern;
        private final HttpRequestHandler handler;
        private final String replacementRoute;

        public Route(Pattern routePattern, HttpRequestHandler handler) {
            this.routePattern = routePattern;
            this.replacementRoute = "$0";
            this.handler = handler;
        }

        public Route(Pattern routePattern, String replacementRoute, HttpRequestHandler handler) {
            this.routePattern = routePattern;
            this.replacementRoute = replacementRoute;
            this.handler = handler;
        }

        public Pattern getRoutePattern() {
            return routePattern;
        }

        public HttpRequestHandler getHandler() {
            return handler;
        }

        public String getReplacementRoute() {
            return replacementRoute;
        }

    }

}


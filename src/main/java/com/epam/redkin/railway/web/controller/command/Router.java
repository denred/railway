package com.epam.redkin.railway.web.controller.command;

public class Router {

    public enum RouteType {
        FORWARD,
        REDIRECT
    }

    private RouteType routeType;
    private String pagePath;

    public static class Builder {
        private RouteType routeType = RouteType.REDIRECT;
        private String pagePath;

        public Builder pagePath(String pagePath) {
            this.pagePath = pagePath;
            return this;
        }

        public Builder routeType(RouteType routeType) {
            if (routeType != null) {
                this.routeType = routeType;
            }
            return this;
        }

        public Router build() {
            Router router = new Router();
            router.setRouteType(routeType);
            router.setPagePath(pagePath);
            return router;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public static Router redirect(String command) {
        return Router.builder()
                .routeType(RouteType.REDIRECT)
                .pagePath(command).build();
    }

    public static Router forward(String page) {
        return Router.builder()
                .routeType(RouteType.FORWARD)
                .pagePath(page).build();
    }

}


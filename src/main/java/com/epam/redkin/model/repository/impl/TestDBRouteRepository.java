package com.epam.redkin.model.repository.impl;

import com.epam.redkin.model.repository.RouteRepo;

public class TestDBRouteRepository {
    public static void main(String[] args) {
    RouteRepo rr = new RouteRepoImpl();
        System.out.println(rr.getRouteListWithParameters("Dnipro-Holovnyi","Oleksandriia"));
    }

}

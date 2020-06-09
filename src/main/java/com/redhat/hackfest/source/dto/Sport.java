package com.redhat.hackfest.source.dto;

import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Sport {
    public String sport;
    public List<Team> team;
}

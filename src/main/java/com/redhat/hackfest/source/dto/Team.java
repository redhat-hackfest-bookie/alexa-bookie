package com.redhat.hackfest.source.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Team {
    public String name;
    public Integer score;
}

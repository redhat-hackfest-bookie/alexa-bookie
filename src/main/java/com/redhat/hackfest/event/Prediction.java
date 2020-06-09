package com.redhat.hackfest.event;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Prediction {
    public Long id;
    public int homeScore;
    public String homeTeam;
    public String awayTeam;
    public int awayScore;
}

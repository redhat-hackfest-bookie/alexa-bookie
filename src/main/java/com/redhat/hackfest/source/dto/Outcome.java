package com.redhat.hackfest.source.dto;

import java.math.BigDecimal;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Outcome {
    public BigDecimal pointSpread;
    public BigDecimal totalPoints;
}

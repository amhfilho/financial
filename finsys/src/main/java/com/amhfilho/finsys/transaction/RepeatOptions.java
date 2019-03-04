package com.amhfilho.finsys.transaction;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class RepeatOptions {
    private LocalDate start;
    private Integer numberOfRepetitions;
    private TemporalUnit unit;

    public static RepeatOptions montly(Integer numberOfRepetitions){
        return new RepeatOptions(LocalDate.now(), numberOfRepetitions, ChronoUnit.MONTHS);
    }

    public static RepeatOptions yearly(Integer numberOfRepetitions){
        return new RepeatOptions(LocalDate.now(), numberOfRepetitions, ChronoUnit.YEARS);
    }

    public RepeatOptions(LocalDate start, Integer numberOfRepetitions, TemporalUnit unit) {
        this.start = start;
        this.numberOfRepetitions = numberOfRepetitions;
        this.unit = unit;
    }

    public Integer getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    public TemporalUnit getUnit() {
        return unit;
    }


}

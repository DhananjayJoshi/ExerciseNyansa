package com.test.nyansa;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Utils {

    public static LocalDate convertFromEpoch(long epochTime) {
        LocalDate date = Instant.ofEpochMilli(epochTime * 1000).atZone(ZoneId.of("GMT")).toLocalDate();
        return date;
    }
}

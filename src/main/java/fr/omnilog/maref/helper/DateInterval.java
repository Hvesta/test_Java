package fr.omnilog.maref.helper;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateInterval {

    public static boolean isSmallProject(LocalDate startDate, LocalDate endDate) {
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        return diff < 91;
    }

    public static boolean isMediumProject(LocalDate startDate, LocalDate endDate) {
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        return diff >= 91 && diff < 730;
    }

    public static boolean isLargeProject(LocalDate startDate, LocalDate endDate) {
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        return diff > 730;
    }

}

package fr.omnilog.maref.helper;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateInterval {

    public static boolean isSmallProject(LocalDate startDate, LocalDate endDate) {
        endDate = replaceByNowIfEndDateIsNull(endDate);
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        return diff < 91;
    }

    public static boolean isMediumProject(LocalDate startDate, LocalDate endDate) {
        endDate = replaceByNowIfEndDateIsNull(endDate);
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        return diff >= 91 && diff <= 730;
    }

    public static boolean isLargeProject(LocalDate startDate, LocalDate endDate) {
        endDate = replaceByNowIfEndDateIsNull(endDate);
        long diff = ChronoUnit.DAYS.between(startDate, endDate);
        return diff > 730;
    }

    public static LocalDate replaceByNowIfEndDateIsNull(LocalDate endDate) {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        return endDate;
    }

}

package com.tr.rdss.generic.model.iqm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ModelUtil {

    /**
     * GMT time zone
     */
    private static final TimeZone GMTTimeZone = TimeZone.getTimeZone("GMT");

    /**
     * default date format
     */
    public static final String FULLDATEFORMAT = "dd-MMM-yyyy HH:mm:ss";

    /**
     * @return validation result (ture|false)
     * @param attr - attribute
     * check whether the input attribute is valid, with the criteria <br>
     *   * effectiveFrom &lt;= sysdate &lt; effectiveTo
     */
    public static boolean isCurrentAttribute(Attribute attr) {
        Date effectiveFromDate = formatDate(attr.getEffectiveFrom());
        Date effectiveToDate = formatDate(attr.getEffectiveTo());

        Date current = Calendar.getInstance().getTime();
        if (effectiveFromDate != null && effectiveToDate != null) {
            return (!current.before(effectiveFromDate))
                && current.before(effectiveToDate);
        } else if (effectiveFromDate == null && effectiveToDate != null) {
            return current.before(effectiveToDate);
        } else if (effectiveToDate == null && effectiveFromDate != null) {
            return !current.before(effectiveFromDate);
        } else {
            return true;
        }
    }

    /**
     * @return Date object transformed from the input dateString
     * @param dateString - date info given by a String object, always assume the input dateString is of GMT time zone.      <br>
     * If the input dateString contains space, it will be parsed by the following formats in sequence (till one parsing succeeds)<br>
     *   * "dd-MMM-yyyy HH:mm"      <br>
     *   * "dd-MMM-yyyy HH:mm:ss"   <br>
     *   * "yyyy-MM-dd HH:mm:ss"    <br>
     * otherwise, it will be parsed by the following formats in sequence    <br>
     *   * "dd-MMM-yyyy"            <br>
     *   * "yyyy-MM-dd"             <br>
     */
    public static Date formatDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat format1 = null;
        SimpleDateFormat format2 = null;
        SimpleDateFormat format3 = null;
        if (dateString.contains(" ")) {
            format1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            format2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            format1 = new SimpleDateFormat("dd-MMM-yyyy");
            format2 = new SimpleDateFormat("dd-MMM-yyyy");
            format3 = new SimpleDateFormat("yyyy-MM-dd");
        }

        format1.setTimeZone(GMTTimeZone);
        format2.setTimeZone(GMTTimeZone);
        format3.setTimeZone(GMTTimeZone);

        Date date = null;
        try {
            date = format1.parse(dateString);
        } catch (ParseException e1) {
            try {
                date = format2.parse(dateString);
            } catch (ParseException e2) {
                try {
                    date = format3.parse(dateString);
                } catch (ParseException e3) {
                    e3.printStackTrace();
                    System.err.println("failed to parse dateStr(" + dateString + ") with: format("
                        + format1 + "|" + format2 + "|" + format3 + ")"
                    );
                }
            }

        }

        return date;
    }

    /**
     * @return String type of date info with the default format, in GMT time zone
     * @param date - date info
     * @see FULLDATEFORMAT  
     */
    public static String formatDateString(java.util.Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(FULLDATEFORMAT);
        df.setTimeZone(GMTTimeZone);
        return df.format(date);
    }
}

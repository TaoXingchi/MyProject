package test;

import java.time.LocalDate;

/**
 * ClassName: Vo <br/>
 * Description: <br/>
 * date: 2020/7/24 9:39<br/>
 *
 * @author TXC<br />
 */
public class Vo {
    private Integer Year ;
    private Integer Month;
    private Integer Day;
    private LocalDate Sdate;
    private LocalDate Edate ;
    private Integer Quarter;
    private DateType dateType;


    public enum DateType{

        YEAR("year"),

        MONTH("month"),

        DAY("day"),

        TIMEPERIOD("timeperiod"),

        QUARTER("querter");

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        DateType(String value) {
            this.value = value;
        }
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public Integer getMonth() {
        return Month;
    }

    public void setMonth(Integer month) {
        Month = month;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public LocalDate getSdate() {
        return Sdate;
    }

    public void setSdate(LocalDate sdate) {
        Sdate = sdate;
    }

    public LocalDate getEdate() {
        return Edate;
    }

    public void setEdate(LocalDate edate) {
        Edate = edate;
    }

    public Integer getQuarter() {
        return Quarter;
    }

    public void setQuarter(Integer quarter) {
        Quarter = quarter;
    }

    public DateType getDateType() {
        return dateType;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }
}

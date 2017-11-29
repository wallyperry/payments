package ren.perry.payments.bean;

/**
 * @author perry
 * @date 2017/11/28
 * WeChat 917351143
 */

public class OverTimeBean {

    private long overMinutes;
    private int selectMonth;
    private int selectDay;
    private int selectHours;
    private int selectMinutes;
    private double cateOverHoursMoney;  //加班时薪


    public OverTimeBean() {
    }

    public long getOverMinutes() {
        return overMinutes;
    }

    public void setOverMinutes(long overMinutes) {
        this.overMinutes = overMinutes;
    }

    public int getSelectMonth() {
        return selectMonth;
    }

    public void setSelectMonth(int selectMonth) {
        this.selectMonth = selectMonth;
    }

    public int getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(int selectDay) {
        this.selectDay = selectDay;
    }

    public int getSelectHours() {
        return selectHours;
    }

    public void setSelectHours(int selectHours) {
        this.selectHours = selectHours;
    }

    public int getSelectMinutes() {
        return selectMinutes;
    }

    public void setSelectMinutes(int selectMinutes) {
        this.selectMinutes = selectMinutes;
    }

    public double getCateOverHoursMoney() {
        return cateOverHoursMoney;
    }

    public void setCateOverHoursMoney(double cateOverHoursMoney) {
        this.cateOverHoursMoney = cateOverHoursMoney;
    }
}

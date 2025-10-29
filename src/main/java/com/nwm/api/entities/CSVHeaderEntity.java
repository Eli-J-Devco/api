package com.nwm.api.entities;

import java.util.Arrays;
import java.util.List;

public class CSVHeaderEntity {
    /**
     * Map dữ liệu từ mảng fields (theo thứ tự) vào entity, các trường thiếu sẽ mặc định là "0"
     */
    public static CSVHeaderEntity fromCsvFields(String[] fields) {
        List<String> header = getHeaderList();
        String[] fullFields = new String[header.size()];
        for (int i = 0; i < header.size(); i++) {
            fullFields[i] = (i < fields.length) ? fields[i] : "0";
        }
        CSVHeaderEntity entity = new CSVHeaderEntity();
        entity.setTime(fullFields[0]);
        entity.setUpv1(fullFields[1]);
        entity.setUpv2(fullFields[2]);
        entity.setUpv3(fullFields[3]);
        entity.setUpv4(fullFields[4]);
        entity.setUpv5(fullFields[5]);
        entity.setUpv6(fullFields[6]);
        entity.setIpv1(fullFields[7]);
        entity.setIpv2(fullFields[8]);
        entity.setIpv3(fullFields[9]);
        entity.setIpv4(fullFields[10]);
        entity.setIpv5(fullFields[11]);
        entity.setIpv6(fullFields[12]);
        entity.setUac1(fullFields[13]);
        entity.setUac2(fullFields[14]);
        entity.setUac3(fullFields[15]);
        entity.setIac1(fullFields[16]);
        entity.setIac2(fullFields[17]);
        entity.setIac3(fullFields[18]);
        entity.setStatus(fullFields[19]);
        entity.setError(fullFields[20]);
        entity.setTemp(fullFields[21]);
        entity.setCos(fullFields[22]);
        entity.setFac(fullFields[23]);
        entity.setPac(fullFields[24]);
        entity.setQac(fullFields[25]);
        entity.setEac(fullFields[26]);
        return entity;
    }
    private String time;
    private String error;
    private String lowAlarm;
    private String highAlarm;
    private String upv1;
    private String upv2;
    private String upv3;
    private String upv4;
    private String upv5;
    private String upv6;
    private String ipv1;
    private String ipv2;
    private String ipv3;
    private String ipv4;
    private String ipv5;
    private String ipv6;
    private String uac1;
    private String uac2;
    private String uac3;
    private String iac1;
    private String iac2;
    private String iac3;
    private String status;
    private String error2;
    private String temp;
    private String cos;
    private String fac;
    private String pac;
    private String qac;
    private String eac;

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getLowAlarm() { return lowAlarm; }
    public void setLowAlarm(String lowAlarm) { this.lowAlarm = lowAlarm; }
    public String getHighAlarm() { return highAlarm; }
    public void setHighAlarm(String highAlarm) { this.highAlarm = highAlarm; }
    public String getUpv1() { return upv1; }
    public void setUpv1(String upv1) { this.upv1 = upv1; }
    public String getUpv2() { return upv2; }
    public void setUpv2(String upv2) { this.upv2 = upv2; }
    public String getUpv3() { return upv3; }
    public void setUpv3(String upv3) { this.upv3 = upv3; }
    public String getUpv4() { return upv4; }
    public void setUpv4(String upv4) { this.upv4 = upv4; }
    public String getUpv5() { return upv5; }
    public void setUpv5(String upv5) { this.upv5 = upv5; }
    public String getUpv6() { return upv6; }
    public void setUpv6(String upv6) { this.upv6 = upv6; }
    public String getIpv1() { return ipv1; }
    public void setIpv1(String ipv1) { this.ipv1 = ipv1; }
    public String getIpv2() { return ipv2; }
    public void setIpv2(String ipv2) { this.ipv2 = ipv2; }
    public String getIpv3() { return ipv3; }
    public void setIpv3(String ipv3) { this.ipv3 = ipv3; }
    public String getIpv4() { return ipv4; }
    public void setIpv4(String ipv4) { this.ipv4 = ipv4; }
    public String getIpv5() { return ipv5; }
    public void setIpv5(String ipv5) { this.ipv5 = ipv5; }
    public String getIpv6() { return ipv6; }
    public void setIpv6(String ipv6) { this.ipv6 = ipv6; }
    public String getUac1() { return uac1; }
    public void setUac1(String uac1) { this.uac1 = uac1; }
    public String getUac2() { return uac2; }
    public void setUac2(String uac2) { this.uac2 = uac2; }
    public String getUac3() { return uac3; }
    public void setUac3(String uac3) { this.uac3 = uac3; }
    public String getIac1() { return iac1; }
    public void setIac1(String iac1) { this.iac1 = iac1; }
    public String getIac2() { return iac2; }
    public void setIac2(String iac2) { this.iac2 = iac2; }
    public String getIac3() { return iac3; }
    public void setIac3(String iac3) { this.iac3 = iac3; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getError2() { return error2; }
    public void setError2(String error2) { this.error2 = error2; }
    public String getTemp() { return temp; }
    public void setTemp(String temp) { this.temp = temp; }
    public String getCos() { return cos; }
    public void setCos(String cos) { this.cos = cos; }
    public String getFac() { return fac; }
    public void setFac(String fac) { this.fac = fac; }
    public String getPac() { return pac; }
    public void setPac(String pac) { this.pac = pac; }
    public String getQac() { return qac; }
    public void setQac(String qac) { this.qac = qac; }
    public String getEac() { return eac; }
    public void setEac(String eac) { this.eac = eac; }

    public static List<String> getHeaderList() {
        return Arrays.asList(
            "Time", "Error", "Low_Alarm", "High_Alarm", "Upv1", "Upv2", "Upv3", "Upv4", "Upv5", "Upv6",
            "Ipv1", "Ipv2", "Ipv3", "Ipv4", "Ipv5", "Ipv6", "Uac1", "Uac2", "Uac3", "Iac1", "Iac2", "Iac3",
            "Status", "Error2", "Temp", "cos", "fac", "Pac", "Qac", "Eac"
        );
    }
}

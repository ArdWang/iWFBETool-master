package com.wbt.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 *
 */

public class DataBean {

    /**
     * tips : ok
     * code : 200
     * message : success
     * devicename : zzp
     * deviceaddre : a5:a9:a9:ac:a0:a6
     * devicesymbol : 0
     * datas : [{"probe":"One","temp":21.9,"hum":"64%"}]
     */

    private String tips;
    private String code;
    private String message;
    private String devicename;
    private String deviceaddre;
    private String devicesymbol;
    private List<DatasBean> datas;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getDeviceaddre() {
        return deviceaddre;
    }

    public void setDeviceaddre(String deviceaddre) {
        this.deviceaddre = deviceaddre;
    }

    public String getDevicesymbol() {
        return devicesymbol;
    }

    public void setDevicesymbol(String devicesymbol) {
        this.devicesymbol = devicesymbol;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * probe : One
         * temp : 21.9
         * hum : 64%
         */

        private String probe;
        private double temp;
        private String hum;

        public String getProbe() {
            return probe;
        }

        public void setProbe(String probe) {
            this.probe = probe;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }
    }
}

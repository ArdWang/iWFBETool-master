package com.wbt.bean;

/**
 * Created by Administrator on 2018/1/29.
 */

public class DevicesBean {

    /**
     * code : 200
     * tips : ok
     * message : success
     * deviceBean : {"deviceid":12,"userid":12,"username":"yuan","devicename":"TPP","deviceaddre":"U7:90:EF:00:01:78","addtime":1587142111000}
     */

    private String code;
    private String tips;
    private String message;
    private DeviceBeanBean deviceBean;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeviceBeanBean getDeviceBean() {
        return deviceBean;
    }

    public void setDeviceBean(DeviceBeanBean deviceBean) {
        this.deviceBean = deviceBean;
    }

    public static class DeviceBeanBean {
        /**
         * deviceid : 12
         * userid : 12
         * username : yuan
         * devicename : TPP
         * deviceaddre : U7:90:EF:00:01:78
         * addtime : 1587142111000
         */

        private int deviceid;
        private int userid;
        private String username;
        private String devicename;
        private String deviceaddre;
        private long addtime;

        public int getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(int deviceid) {
            this.deviceid = deviceid;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }
    }
}

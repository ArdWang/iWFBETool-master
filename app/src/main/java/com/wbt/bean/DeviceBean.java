package com.wbt.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/21.
 *
 */

public class DeviceBean {
    /**
     * code : 200
     * tips : ok
     * message : success
     * devices : [{"deviceid":1,"userid":12,"username":"yuan","devicename":"TPYBoard v202","deviceaddre":"a5:d9:79:9c:80:86","addtime":1516528220580},{"deviceid":2,"userid":12,"username":"yuan","devicename":"TPYBoard v202","deviceaddre":"5c:cf:7f:d0:85:65","addtime":1516528220580},{"deviceid":3,"userid":12,"username":"yuan","devicename":"zzp","deviceaddre":"a5:a9:a9:ac:a0:a6","addtime":1516528220589}]
     */

    private String code;
    private String tips;
    private String message;
    private List<DevicesBean> devices;

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

    public List<DevicesBean> getDevices() {
        return devices;
    }

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

    public static class DevicesBean {
        /**
         * deviceid : 1
         * userid : 12
         * username : yuan
         * devicename : TPYBoard v202
         * deviceaddre : a5:d9:79:9c:80:86
         * addtime : 1516528220580
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

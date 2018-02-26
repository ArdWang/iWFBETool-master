package com.wbt.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 *
 */

public class ProbeBean {

    /**
     * code : 200
     * tips : ok
     * message : success
     * probes : [{"probeid":222,"deviceid":1,"probename":"One","probetemp":"21.9","probesymbol":"0","probehum":"64%","probetime":1516608942666},{"probeid":223,"deviceid":1,"probename":"One","probetemp":"21.9","probesymbol":"0","probehum":"64%","probetime":1516608958059},{"probeid":224,"deviceid":1,"probename":"One","probetemp":"21.9","probesymbol":"0","probehum":"64%","probetime":1516608973105},{"probeid":225,"deviceid":1,"probename":"One","probetemp":"21.9","probesymbol":"0","probehum":"64%","probetime":1516608988200}]
     */

    private String code;
    private String tips;
    private String message;
    private List<ProbesBean> probes;

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

    public List<ProbesBean> getProbes() {
        return probes;
    }

    public void setProbes(List<ProbesBean> probes) {
        this.probes = probes;
    }

    public static class ProbesBean {
        /**
         * probeid : 222
         * deviceid : 1
         * probename : One
         * probetemp : 21.9
         * probesymbol : 0
         * probehum : 64%
         * probetime : 1516608942666
         */

        private int probeid;
        private int deviceid;
        private String probename;
        private String probetemp;
        private String probesymbol;
        private String probehum;
        private long probetime;

        public int getProbeid() {
            return probeid;
        }

        public void setProbeid(int probeid) {
            this.probeid = probeid;
        }

        public int getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(int deviceid) {
            this.deviceid = deviceid;
        }

        public String getProbename() {
            return probename;
        }

        public void setProbename(String probename) {
            this.probename = probename;
        }

        public String getProbetemp() {
            return probetemp;
        }

        public void setProbetemp(String probetemp) {
            this.probetemp = probetemp;
        }

        public String getProbesymbol() {
            return probesymbol;
        }

        public void setProbesymbol(String probesymbol) {
            this.probesymbol = probesymbol;
        }

        public String getProbehum() {
            return probehum;
        }

        public void setProbehum(String probehum) {
            this.probehum = probehum;
        }

        public long getProbetime() {
            return probetime;
        }

        public void setProbetime(long probetime) {
            this.probetime = probetime;
        }
    }
}

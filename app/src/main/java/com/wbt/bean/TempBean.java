package com.wbt.bean;

import java.util.List;

/**
 * Created by rnd on 2018/1/18.
 */

public class TempBean {
    /**
     * tips : ok
     * code : 200
     * message : success
     * temps : [{"tpid":2,"tpdvid":"11:00:22:de:32","tpname":"der_aa","tptemp":"22","tpsymbol":"°C","tphum":"55.5%","tptime":1534663426}]
     */

    private String tips;
    private String code;
    private String message;
    private List<TempsBean> temps;

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

    public List<TempsBean> getTemps() {
        return temps;
    }

    public void setTemps(List<TempsBean> temps) {
        this.temps = temps;
    }

    public static class TempsBean {
        /**
         * tpid : 2
         * tpdvid : 11:00:22:de:32
         * tpname : der_aa
         * tptemp : 22
         * tpsymbol : °C
         * tphum : 55.5%
         * tptime : 1534663426
         */

        private int tpid;
        private String tpdvid;
        private String tpname;
        private String tptemp;
        private String tpsymbol;
        private String tphum;
        private Long tptime;

        public int getTpid() {
            return tpid;
        }

        public void setTpid(int tpid) {
            this.tpid = tpid;
        }

        public String getTpdvid() {
            return tpdvid;
        }

        public void setTpdvid(String tpdvid) {
            this.tpdvid = tpdvid;
        }

        public String getTpname() {
            return tpname;
        }

        public void setTpname(String tpname) {
            this.tpname = tpname;
        }

        public String getTptemp() {
            return tptemp;
        }

        public void setTptemp(String tptemp) {
            this.tptemp = tptemp;
        }

        public String getTpsymbol() {
            return tpsymbol;
        }

        public void setTpsymbol(String tpsymbol) {
            this.tpsymbol = tpsymbol;
        }

        public String getTphum() {
            return tphum;
        }

        public void setTphum(String tphum) {
            this.tphum = tphum;
        }

        public Long getTptime() {
            return tptime;
        }

        public void setTptime(Long tptime) {
            this.tptime = tptime;
        }
    }
}

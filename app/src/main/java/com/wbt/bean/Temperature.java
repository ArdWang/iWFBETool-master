package com.wbt.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by rnd on 2018/1/18.
 *
 */
@Entity
public class Temperature implements Comparable<Temperature>{
    /**
     * tpid : 2
     * tpdvid : 11:00:22:de:32
     * tpname : der_aa
     * tptemp : 22
     * tpsymbol : °C
     * tphum : 55.5%
     * tptime : 1534663426
     */
    @Id(autoincrement = true)
    private Long tpid;
    private String tpdvid;
    private String tpname;
    private String tptemp;
    private String tpsymbol;
    private String tphum;
    private Long tptime;
    @Generated(hash = 690521100)
    public Temperature(Long tpid, String tpdvid, String tpname, String tptemp,
            String tpsymbol, String tphum, Long tptime) {
        this.tpid = tpid;
        this.tpdvid = tpdvid;
        this.tpname = tpname;
        this.tptemp = tptemp;
        this.tpsymbol = tpsymbol;
        this.tphum = tphum;
        this.tptime = tptime;
    }
    @Generated(hash = 1726533429)
    public Temperature() {
    }
    public Long getTpid() {
        return this.tpid;
    }
    public void setTpid(Long tpid) {
        this.tpid = tpid;
    }
    public String getTpdvid() {
        return this.tpdvid;
    }
    public void setTpdvid(String tpdvid) {
        this.tpdvid = tpdvid;
    }
    public String getTpname() {
        return this.tpname;
    }
    public void setTpname(String tpname) {
        this.tpname = tpname;
    }
    public String getTptemp() {
        return this.tptemp;
    }
    public void setTptemp(String tptemp) {
        this.tptemp = tptemp;
    }
    public String getTpsymbol() {
        return this.tpsymbol;
    }
    public void setTpsymbol(String tpsymbol) {
        this.tpsymbol = tpsymbol;
    }
    public String getTphum() {
        return this.tphum;
    }
    public void setTphum(String tphum) {
        this.tphum = tphum;
    }
    public Long getTptime() {
        return this.tptime;
    }
    public void setTptime(Long tptime) {
        this.tptime = tptime;
    }

    /** 排序比较时间的大小
     * @param o
     * @return
     */
    @Override
    public int compareTo(Temperature o) {
        if(this.getTptime()>=o.getTptime()){
            return 1;
        }
        return -1;
    }
}

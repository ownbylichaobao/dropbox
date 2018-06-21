package com.lichaobao.dropbox.model.fileentity;

import javax.persistence.*;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
@Entity
@Table(name = "fileinfor")
public class FileInfor {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String uniquesign;

    private String fullname;

    private String category;

    private String uploader;

    private String remark;

    @Column(unique = true)
    private String addr;
    @Column(unique = true)
    private String mini_addr;

    public FileInfor() {
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniquesign() {
        return uniquesign;
    }

    public void setUniquesign(String uniquesign) {
        this.uniquesign = uniquesign;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public FileInfor(String uniquesign, String fullname, String category, String remark,String addr,String uploader) {
        this.uniquesign = uniquesign;
        this.fullname = fullname;
        this.category = category;
        this.remark = remark;
        this.addr = addr;
        this.uploader = uploader;
    }

    public String getMini_addr() {
        return mini_addr;
    }

    public void setMini_addr(String mini_addr) {
        this.mini_addr = mini_addr;
    }

    @Override
    public String toString() {
        return "{"+"\n"+
                "id:"+"\t"+this.id+","+"\n"+
                        "uniquesign:"+"\t"+this.uniquesign+",\n"+
                        "fullname:"+"\t"+this.fullname+",\n"+
                         "uploader:"+"\t"+this.uploader+",\n"+
                        "catagory:"+"\t"+this.category+",\n"+
                        "remark:"+"\t\t"+this.remark+",\n"+
                        "addr:"+"\t\t"+this.addr+"\n"+
                        "}";



    }
}

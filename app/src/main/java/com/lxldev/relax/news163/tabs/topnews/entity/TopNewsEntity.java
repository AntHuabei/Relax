package com.lxldev.relax.news163.tabs.topnews.entity;

public class TopNewsEntity {

    private String lmodify;//: "2018-09-01 16:12:20",
    private String url_3w;//: "http://news.163.com/18/0901/16/DQKMVV820001875N.html",

    private String url;

    private String source;//: "科学网",
    private String title;//: "韩春雨事件调查报告的四大痛点:获利应如何追回?",
    private String digest;//: "昨天夜里，河北科技大学发布了韩春雨团队撤稿论文的调查和处理结果，千呼万唤始出来。一天不到，这条本该置顶的消息迅速被领导班子召开民主生活会、本科暑期招生录取、教师",
    private String imgsrc;//: "http://cms-bucket.nosdn.127.net/2018/09/01/f6bb3502c46b46fc919bec3f030a77e0.png",

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}

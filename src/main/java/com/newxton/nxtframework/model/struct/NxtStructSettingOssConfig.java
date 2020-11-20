package com.newxton.nxtframework.model.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/12
 * @address Shenzhen, China
 */
public class NxtStructSettingOssConfig {

    //存储在本机：local 存储在七牛云：qiniu
    public String ossLocation = "local";//默认值

    //七牛云AccessKey
    public String ossQiniuAccessKey;

    //七牛云SecretKey
    public String ossQiniuSecretKey;

    //七牛云bucket
    public String ossQiniuBucket;

    //七牛云OSS域名
    public String ossQiniuDomain;

    public String getOssLocation() {
        return ossLocation;
    }

    public void setOssLocation(String ossLocation) {
        this.ossLocation = ossLocation;
    }

    public String getOssQiniuAccessKey() {
        return ossQiniuAccessKey;
    }

    public void setOssQiniuAccessKey(String ossQiniuAccessKey) {
        this.ossQiniuAccessKey = ossQiniuAccessKey;
    }

    public String getOssQiniuSecretKey() {
        return ossQiniuSecretKey;
    }

    public void setOssQiniuSecretKey(String ossQiniuSecretKey) {
        this.ossQiniuSecretKey = ossQiniuSecretKey;
    }

    public String getOssQiniuBucket() {
        return ossQiniuBucket;
    }

    public void setOssQiniuBucket(String ossQiniuBucket) {
        this.ossQiniuBucket = ossQiniuBucket;
    }

    public String getOssQiniuDomain() {
        return ossQiniuDomain;
    }

    public void setOssQiniuDomain(String ossQiniuDomain) {
        this.ossQiniuDomain = ossQiniuDomain;
    }
}

package com.sweetorangejuice.artisan.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author fortuneliu
 * @version v0.1
 * MomentsBean类
 *      圈子对象的实体类
 *      包括短文内容、图片数组、标签、日期、作者
 * Created by fortuneliu on 2017/5/3.
 */

public class MomentsBean {
    private String text;                                // 用户输入的文本
    private ArrayList<String> images;                   // 用户传入的图片地址（最多九个）
    private String tag;                                 // 用户选择的标签
    private Date date;                                  // 用户创建的时间
    private String author;                              // 创建的用户

    public void setText(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean addImages(String imgPath) {          //添加图片（传入图片地址）
        if(images.size()<9) {
            images.add(imgPath);
            return true;
        } else
            return false;
    }

    public void removeImages(int index) {               //删除图片（传入index)
        images.remove(index);
    }

    public ArrayList<String> getImages(){
        return this.images;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

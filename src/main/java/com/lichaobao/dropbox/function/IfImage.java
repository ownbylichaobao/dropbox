package com.lichaobao.dropbox.function;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
@Component
public class IfImage {
    /**
     * 判断是否为图片
     * @param meta 文件类型
     * @return
     */
    public boolean isimage(String meta){

        String[] SUPPORT_IMAGE_TYPE = { "jpg", "jpeg", "png", "gif", "bmp", "wbmp" };
        for(String s :SUPPORT_IMAGE_TYPE){
            if(meta.toString().toLowerCase().equals(s)){
                return true;
            }
        }
        return false;
    }
}

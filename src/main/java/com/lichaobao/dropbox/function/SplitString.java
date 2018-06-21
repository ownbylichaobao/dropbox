package com.lichaobao.dropbox.function;


import com.lichaobao.dropbox.model.fun.FileName;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
@Component
public class SplitString {


    /**
     *  分割文件类型及文件名
     * @param fullname 文件全名
     * @return
     */
    public FileName SplitString(String fullname){
        FileName fileName = new FileName();
        for(int i=fullname.length()-1;i>0;i--){
            char c = fullname.charAt(i);
            if(c=='.'){
                fileName.setName( fullname.substring(0,i));
               fileName.setMeta(fullname.substring(i+1)) ;
                return fileName;
            }
        }
        return fileName;
    }
}

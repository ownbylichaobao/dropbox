package com.lichaobao.dropbox.dao;


import com.lichaobao.dropbox.db.FileInforRepository;
import com.lichaobao.dropbox.model.entity.MessageEntity;
import com.lichaobao.dropbox.model.fileentity.FileInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
public class FileInforDao {
    @Autowired
    private FileInforRepository fileInforRepository;

    /**
     * 列出已上传文件信息文件
     * @return object
     */
    public Object FindAll(){
        MessageEntity<List<FileInfor>> messageEntity = new MessageEntity<>();
        List<FileInfor> fileInfors;
        try {
            fileInfors = fileInforRepository.findAll();
            messageEntity.setResult(fileInfors);
            return messageEntity;
        }catch (Exception e){
            messageEntity.setCode(202);
            messageEntity.setMessage("已接受请求未成功");
            return messageEntity;
        }
    }

    /**
     * 通过唯一标识符查看文件信息
     * @param uniquesign 唯一标识符
     * @return FileInfo
     */
    public FileInfor FindFileInforByUniqueSign(String uniquesign){
        return fileInforRepository.findFileInforByUniquesign(uniquesign);
    }

    /**
     * 将新上传的文件信息导入数据库
     * @param fileInfor 文件信息
     * @return MessageEntity
     */
    public MessageEntity<FileInfor> Add(FileInfor fileInfor){
        MessageEntity<FileInfor> messageEntity = new MessageEntity<>();
        FileInfor fileInfor1;
        try {
            fileInfor1 = fileInforRepository.save(fileInfor);
                messageEntity.setResult(fileInfor1);
                return messageEntity;
        }catch (Exception e){
            messageEntity.setCode(202);
            messageEntity.setMessage("已接受请求未成功");
            return messageEntity;
        }
    }

    /**
     * 通过文件全名查找文件信息
     * @param fullname 文件全名
     * @return MessageEntity<List<FileInfor>>
     */
    public MessageEntity<List<FileInfor>> FindByFullName(String fullname){
        MessageEntity<List<FileInfor>> messageEntity = new MessageEntity<>();
        List<FileInfor> fileInfors;
        try {
            String filename = "%"+fullname+"%";
            fileInfors = fileInforRepository.findByFullname(filename);
            messageEntity.setCode(200);
            messageEntity.setResult(fileInfors);
            return messageEntity;
        }catch (Exception e){
            messageEntity.setMessage("已接受请求未成功");
            return messageEntity;
        }
    }

    /**
     * 通过文件备注查找文件信息
     * @param remark 文件备注
     * @return MessageEntity<List<FileInfor>>
     */
    public MessageEntity<List<FileInfor>> FindByRemark(String remark){
        MessageEntity<List<FileInfor>> messageEntity = new MessageEntity<>();
        List<FileInfor> fileInfors = null;
        try {
            fileInfors = fileInforRepository.findByRemark(remark);
            messageEntity.setResult(fileInfors);
            return  messageEntity;
        }catch (Exception e){
            messageEntity.setCode(202);
            messageEntity.setMessage("已接受请求未成功");
            return messageEntity;
        }
    }

    /**
     * 通过唯一标识UniqueSign获取文件保存地址
     * @param uniquesign 唯一标识符
     * @return string
     */
   public String  FindAddrByUniqueSign(String uniquesign){
            return fileInforRepository.findByUniquesign(uniquesign);
   }
   /*通过唯一标识查询全名*/
   public  String FindByUniqueSign(String uniquesign){
          MessageEntity<String> messageEntity = new MessageEntity<>();
              return fileInforRepository.findFullNameByUniquesign(uniquesign);
   }

    /**
     * 通过fullname更新remark
     * @param fullname 文件全名
     * @param remark    文件备注
     * @return  MessageEntity
     */
   public  MessageEntity Update(String fullname ,String remark){
       MessageEntity<List<FileInfor>> messageEntity = new MessageEntity<>();
        List<FileInfor> fileInforlist=null;
       try {
           fileInforlist = fileInforRepository.Update(fullname);
       }catch (Exception e){
           messageEntity.setCode(202);
           messageEntity.setMessage("已接受请求未成功");
           return messageEntity;
       }
       String f =null;
       Set s = new HashSet(fileInforlist);
       if(s.size()==1) {
           for(FileInfor fileInfor :fileInforlist){
               MessageEntity<FileInfor> messageEntity1 =new MessageEntity<>();
               fileInfor.setRemark(remark);
               FileInfor fileInfor1 = fileInforRepository.save(fileInfor);
               messageEntity1.setResult(fileInfor1);
               return messageEntity1;
           }
       }else{
           messageEntity.setMessage("该文件名对应多个文件信息请查找相应文件以唯一标识符为根据进行修改：");
           messageEntity.setCode(201);
           messageEntity.setResult(fileInforlist);
           return messageEntity;
       }
       return null;
   }

    /**
     * 通过唯一标识符更新remark
     * @param uniquesign 唯一标识符
     * @param remark 文件备注
     * @return
     */
   public MessageEntity UpdateByUniquesign(String uniquesign,String remark){
       FileInfor fileInfor =null;
       try {
          fileInfor = fileInforRepository.findFileInforByUniquesign(uniquesign);
          fileInfor.setRemark(remark);
          FileInfor fileInfor1 = fileInforRepository.save(fileInfor);
          MessageEntity<FileInfor> messageEntity =new MessageEntity<FileInfor>(200,"success",fileInfor1);
          return  messageEntity;
       }catch (Exception e){
           MessageEntity messageEntity = new MessageEntity();
           messageEntity.setCode(400);
           messageEntity.setMessage("无效的标识符");
           return messageEntity;
       }
   }

    /**
     * 通过文件类别查找文件
     * @param category 文件类别
     * @return MessageEntity
     */
   public MessageEntity FindByCategory(String category){
       try {
           MessageEntity<List<FileInfor>> messageEntity = new MessageEntity<>();
           messageEntity.setResult(fileInforRepository.findByCategory(category));
           return messageEntity;
       }catch (Exception e){
           MessageEntity messageEntity = new MessageEntity();
           messageEntity.setCode(400);
           messageEntity.setMessage("无效的类别");
           return messageEntity;
       }
   }

    /**
     * 通过文件上传人列出所上传的文件
     * @param uploader 文件上传人
     * @return MessageEntity<List<FileInfor>>
     */
   public MessageEntity<List<FileInfor>> FindByUploader(String uploader){
       MessageEntity<List<FileInfor>> messageEntity = new MessageEntity<>();
     try{
         messageEntity.setResult(fileInforRepository.findByUploader(uploader));
         return messageEntity;
     }catch (Exception e){
         messageEntity.setCode(500);
         messageEntity.setMessage("已接收请求未成功");
         return messageEntity;
     }
   }
}

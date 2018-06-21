package com.lichaobao.dropbox.function;

import com.github.tobato.fastdfs.domain.DefaultThumbImageConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.lichaobao.dropbox.dao.FileInforDao;
import com.lichaobao.dropbox.model.entity.MessageEntity;
import com.lichaobao.dropbox.model.fileentity.FileInfor;
import com.lichaobao.dropbox.model.fun.FileName;
import com.lichaobao.dropbox.model.fun.IpProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
public class FileHandle {
    @Autowired
    SplitString splitString;
    @Autowired
    IfImage ifImage;
    @Autowired
    DefaultFastFileStorageClient defaultFastFileStorageClient;
    @Autowired
    FastFileStorageClient fastFileStorageClient;
    @Autowired
    IpProperties ipProperties;
    @Autowired
    FileInforDao fileInforDao;

    @Autowired
    DefaultThumbImageConfig defaultThumbImageConfig;
    @Autowired
    ThunbImage thunbImage;

    /**
     * 上传文件
     * @param file 文件
     * @param remark 备注
     * @param category 分组
     * @param uploader 上传人
     * @return MessageEntity
     * @throws IOException
     */
    public MessageEntity UpLoad(MultipartFile file, String remark, String category, String uploader) throws IOException {
        MessageEntity<FileInfor> messageEntity = new MessageEntity();
        StorePath storePath;
        FileInfor fileInfor = new FileInfor();
        InputStream inputStream = file.getInputStream();
             if(inputStream==null){
            messageEntity.setCode(500);
            messageEntity.setMessage("未知错误");
            return messageEntity;
        }
        String fullname = file.getOriginalFilename();
        FileName fileName = splitString.SplitString(fullname);
        System.out.println(fileName.getMeta());
        if(ifImage.isimage(fileName.getMeta())){
            /* storePath=defaultFastFileStorageClient.uploadImageAndCrtThumbImage(inputStream,file.getSize(),fileName.getMeta(),null);
            FileName miniurl_split = splitString.SplitString(storePath.getPath().toString());*/
            File file1 = new File(".\\test");
            FileUtils.copyInputStreamToFile(file.getInputStream(),file1);
            storePath = defaultFastFileStorageClient.uploadFile(inputStream,file.getSize(),fileName.getMeta(),null);
            InputStream inputStream_mini = thunbImage.getThumbImageStream(file1,defaultThumbImageConfig.getWidth(), defaultThumbImageConfig.getHeight());
            /*
            * inputStream_mini转file
            * */

            StorePath storePath_mini = defaultFastFileStorageClient.uploadFile(inputStream_mini, inputStream_mini.available(), fileName.getMeta(), null);
//             String mini_url = "http://"+ipProperties.getIp()+"/"+miniurl_split.getName()+"_"+defaultThumbImageConfig.getWidth()+"x"+defaultThumbImageConfig.getHeight()+"."+miniurl_split.getMeta();
           /* String mini_url =miniurl_split.getName()+"_"+defaultThumbImageConfig.getWidth()+"x"+defaultThumbImageConfig.getHeight()+"."+miniurl_split.getMeta();*/
            String mini_url = storePath_mini.getFullPath();
            mini_url = "http://"+ipProperties.getIp()+"/"+mini_url;
             fileInfor.setMini_addr(mini_url);
             System.out.println("yes");
        }else{
             storePath=fastFileStorageClient.uploadFile(inputStream,file.getSize(),fileName.getMeta(),null);
        }
        String url = "http://"+ipProperties.getIp()+"/"+storePath.getFullPath().toString();
        String uniquesign = UUID.randomUUID().toString().replaceAll("-", "");
        fileInfor.setAddr(url);
        fileInfor.setFullname(fullname);
        fileInfor.setCategory(category);
        fileInfor.setRemark(remark);
        fileInfor.setUniquesign(uniquesign);
        fileInfor.setUploader(uploader);
        if(inputStream!=null){
            inputStream.close();
        }
        return fileInforDao.Add(fileInfor);
    }

    /**
     * 下载文件
     * @param uniquesign 唯一标识符
     * @return Object
     * @throws IOException
     */
    public Object Download(String uniquesign) throws IOException {
        MessageEntity<FileInfor> messageEntity = new MessageEntity();
        FileInfor fileInfor;
        try {
            fileInfor = fileInforDao.FindFileInforByUniqueSign(uniquesign);
        }catch (Exception e){
            messageEntity.setCode(400);
            messageEntity.setMessage("无效的标识符");
            return messageEntity;
        }
        String fullname = fileInfor.getFullname();
        int lenth = ipProperties.getIp().length();

        String group=fileInfor.getAddr().substring(6+lenth+2,6+lenth+1+7);
        String realurl=fileInfor.getAddr().substring(6+lenth+2+6+1);
        System.out.println(group);
        System.out.println(realurl);
        byte[] b = fastFileStorageClient.downloadFile(group,realurl,new DownloadByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control","no-cache,no-store,must-revalidate");
        headers.add("Content-Disposition","attachment;filename="+fullname);
        headers.add("Pragma","no-cache");
        headers.add("Expires","0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(b.length)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(b);
    }

    /**
     * 删除文件
     * @param uniquesign 唯一标识符
     * @return MessageEntity
     */
    public  MessageEntity delete(String uniquesign){
        MessageEntity messageEntity = new MessageEntity<>();
        FileInfor fileInfor = fileInforDao.FindFileInforByUniqueSign(uniquesign);
        int lenth = ipProperties.getIp().length();
        String realurl = fileInfor.getAddr().substring(6+lenth+2);
        fastFileStorageClient.deleteFile(realurl);
        return messageEntity;
    }
}

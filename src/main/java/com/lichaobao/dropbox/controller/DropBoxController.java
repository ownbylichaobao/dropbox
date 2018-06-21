package com.lichaobao.dropbox.controller;
import com.lichaobao.dropbox.dao.FileInforDao;
import com.lichaobao.dropbox.function.FileHandle;
import com.lichaobao.dropbox.model.entity.MessageEntity;
import com.lichaobao.dropbox.model.fun.Category;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 * @auther lichaobao
 * @date 2018/4/15
 * @QQ 1527563274
 */
@Controller
@CrossOrigin("*")
@RequestMapping("/fileserver")
public class DropBoxController {
    @Autowired
    FileHandle fileHandle;

    @Autowired
    FileInforDao fileInforDao;

    /**
     * 上传文件
     * @param file      要上传的文件       required
     * @param remark    要上传的文件备注   notRequired
     * @param category  文件分类           notRequired
     * @param uploader  上传者             notRequired
     * @return          MessageEntity
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public MessageEntity Upload(@RequestParam MultipartFile file,
                                HttpServletRequest request) throws IOException {
        String remark = request.getParameter("remark");
        String category=request.getParameter("category");
        String uploader = request.getParameter("uploader");
        if(remark ==null){
            remark = "null";
        }
        if(category==null){
            category="null";
        }
        if(uploader==null){
            uploader="null";
        }
       return  fileHandle.UpLoad(file,remark,category,uploader);
    }

    /**
     * 通过唯一标识符下载文件
     * @param uniquesign 文件唯一标识符
     * @return Object
     * @throws IOException
     */
    @GetMapping("/download")
    public Object Download(@RequestParam String uniquesign) throws IOException {
             return fileHandle.Download(uniquesign);
    }

    /**
     *  通过唯一标识符删除文件
     * @param uniquesign 文件唯一标识符
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public  Object Delete(@RequestParam String uniquesign){
        return fileHandle.delete(uniquesign);
    }
    /**
     * 通过唯一标识符进行更新文件备注
     * {
     *     "remark":"..",
     *     "uniquesign":".."
     * }
     */
    @ResponseBody
    @PutMapping(value = "/updatebyuniquesign",produces = "application/json")
    public Object UpdateByUniqueSign(@RequestBody JSONObject jsonObject){
        String uniquesign = jsonObject.getString("uniquesign");
        String remark = jsonObject.getString("remark");
        return fileInforDao.UpdateByUniquesign(uniquesign,remark);
    }
    /**
    * 通过文件名进行更新文件备注
    * {
    *     "remark":"",
    *     "filename":".."
    * }
    */
    @ResponseBody
   @PutMapping(value = "/update",produces = "application/json")
    public Object Update(@RequestBody JSONObject jsonObject){
        String remark;
        String filename;
       try {
            remark = jsonObject.getString("remark");
            filename = jsonObject.getString("filename");
       } catch (Exception e) {
           return "无效的发送格式";
       }
       return fileInforDao.Update(filename,remark);
   }
   /**
   * 返回所有已上传文件信息
   */
   @ResponseBody
   @GetMapping("/findall")
    public Object FindAll(){
        return fileInforDao.FindAll();
   }

   /**
   * 通过文件全名查询文件信息
   * @Param fullname
   */
   @ResponseBody
   @GetMapping("/findbyfilename")
    public MessageEntity FindByFullname(@RequestParam String fullname){
        return fileInforDao.FindByFullName(fullname);
   }
   /**
   * 通过文件备注查询文件
   * @aram remark 文件备注
   */
   @ResponseBody
   @GetMapping("/findbyremark")
    public MessageEntity FinByRemark(@RequestParam String remark){
        return fileInforDao.FindByRemark(remark);
   }

    /**
     * 通过文件类别查找文件
     * @param category 文件类别
     * @return
     */
   @ResponseBody
   @GetMapping("/findbycategory")
    public MessageEntity FindByCategory(@RequestParam String category){
        for(Category c : Category.values()){
            if(category.equals(c.toString())){
                return fileInforDao.FindByCategory(category);
            }
        }
        return new MessageEntity();
   }

    /**
     * 通过文件上传人查找此人上传的文件
     * @param uploader 文件上传人
     * @return
     */
    @ResponseBody
    @GetMapping("/findbyuploader")
    public MessageEntity FindByUploader(@RequestParam String uploader){
        return fileInforDao.FindByUploader(uploader);
    }




}

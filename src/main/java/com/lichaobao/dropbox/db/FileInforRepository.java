package com.lichaobao.dropbox.db;


import com.lichaobao.dropbox.model.fileentity.FileInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
public interface FileInforRepository extends JpaRepository<FileInfor,Integer> {

    @Query(value = "select * from fileinfor where fullname LIKE ?1",nativeQuery = true)
     List<FileInfor> findByFullname(String fullname);
    @Query(value = "select * from fileinfor where remark = ?1",nativeQuery = true)
    List<FileInfor> findByRemark(String remark);
    @Query(value = "select addr from fileinfor where uniquesign=?1",nativeQuery = true)
    String findByUniquesign(String uniquesign);
    @Query(value="select * from fileinfor where uniquesign=?1",nativeQuery = true)
    FileInfor findFileInforByUniquesign(String uniquesign);
    @Query(value = "select fullname from fileinfor where uniquesign= ?1",nativeQuery = true)
    String  findFullNameByUniquesign(String uniquesign);
    @Query(value = "delete  from fileinfor where uniquesign=?1",nativeQuery = true)
    @Modifying
    void DeleteByUniqueSign(String uniquesign);
    @Query(value = "select * from fileinfor where fullname =?1",nativeQuery = true)
    @Modifying
    List<FileInfor> Update(String fullname);
    @Query(value = "select * from fileinfor where category = ?1",nativeQuery = true)
    List<FileInfor> findByCategory(String category);
    @Query(value = "select uniquesign from fileinfor where fullname = ?1",nativeQuery = true)
    String findUniquesignByFullname(String fullname);
    @Query(value = "select * from fileinfor where uploader = ?1",nativeQuery = true)
    List<FileInfor> findByUploader(String uploader);
}

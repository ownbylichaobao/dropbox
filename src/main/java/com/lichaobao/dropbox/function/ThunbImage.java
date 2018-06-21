package com.lichaobao.dropbox.function;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @auther lichaobao
 * @date 2018/4/14
 * @QQ 1527563274
 */
@Component
public class ThunbImage {
    /**
     * 图片压缩
     * @param file   文件
     * @param width  宽
     * @param height 高
     * @return  ByteArrayInputStream
     * @throws IOException
     */
    public ByteArrayInputStream getThumbImageStream(File file, int width, int height) throws IOException {
        // 在内存当中生成缩略图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        @formatter:off
            Thumbnails
                    .of(file)
                    .size(width, height)
                    .toOutputStream(out);

            out.close();
            //@formatter:on
            return new ByteArrayInputStream(out.toByteArray());
    }
}

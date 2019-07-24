import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author ymbcxb
 * @title
 * @Package PACKAGE_NAME
 * @date 2019/7/24 13:30
 */
public class FileTest {
    public static void main(String[] args) {
        /**
         * 创建文件
         */
        String path = "F:/upload/c.txt";
        File file = new File(path);
        if(!file.exists()){
            File parent = new File(file.getParent());
            parent.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        file = new File("F:/a.txt");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

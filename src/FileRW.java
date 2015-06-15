import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Ge on 2015/6/13.
 */
public class FileRW {
    public static boolean createNewFile(String filePath){
        String filePathConvert = filePath.replaceAll("\\\\","/");

        int index = filePathConvert.lastIndexOf("/");
        String dir = filePathConvert.substring(0,index);

        File fileDir = new File(dir);

        if (fileDir.mkdir()){
            File file = new File(filePathConvert);
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            System.out.println("Make dir failed!");
            return false;
        }
    }

    public static boolean writeToFile(String content, String filePath, boolean isAppend){
        boolean isSuccess = true;

        int index = filePath.lastIndexOf("/");
        String dir = filePath.substring(0,index);

        File file = null;
        FileWriter fileWriter = null;
        try {
            file = new File(filePath);
            file.createNewFile();

            fileWriter = new FileWriter(file,isAppend);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        }finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

}

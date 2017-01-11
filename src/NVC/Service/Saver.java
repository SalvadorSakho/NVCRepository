package NVC.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;
import java.io.IOException;

/**
 * Created by ${BIM} on 24.02.2016.
 */
public class Saver {
    private FileChannel sourceChannel;
    private FileChannel destChannel;

    public Saver() {
    }

    public void copyFileUsingChannel(File source, File dest) {

        try {
            sourceChannel = new FileInputStream(source).getChannel();///Из этого файла
            destChannel = new FileOutputStream(dest).getChannel();///В этот файл
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
            sourceChannel.close();
            destChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
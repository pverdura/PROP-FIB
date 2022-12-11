package Codi.Excepcions;

import java.io.File;
import java.io.IOException;

public class FileNoExisteixException extends IOException {

    File file;

    public FileNoExisteixException(File file) {
        super("Error: no existeix cap directori: " + file.getAbsolutePath().toString());
        this.file = file;
    }

    public String toString () {
        return "Error: no existeix cap directori:" + this.file.getAbsolutePath().toString() ;
    }
}

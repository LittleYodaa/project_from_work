package pl.programisci.task_crud.ftp;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "ftp")
public class FtpConfiguration {

    private String server;
    private String username;
    private String password;
    private String remoteInputDirectory;
    private String remoteArchiveDirectory;
    private String remoteFile;
    private String localFilePath;

    //VFS.getManager tworzymy za każdym razem od nowa
    public FileObject getRemoteFileObject(String filePath) throws FileSystemException {
        // Zarządzanie plikami (VFS - Virtual File System)
        FileSystemManager fileSystemManager = VFS.getManager();
        // Obiekt przechowujący konfigurację, ustawia takie parametry jak dane uwierzytelniające lub tryb pasywny
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        // Używany do przechowywania danych uwierzytelniających jak login i hasło, w tym przypadku domena jest nullem
        StaticUserAuthenticator staticUserAuthenticator = new StaticUserAuthenticator(null, username, password);
        // Ustawia uwierzytelnianie użytkownika (staticUserAuthenticator) w systemie plików (fileSystemOptions)
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, staticUserAuthenticator);
        // Ustawia tryb pasywny pomocny przy zaporach sieciowych w naszym systemie plików (fileSystemOptions)
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(fileSystemOptions, true);
        // Metoda tworzy połączenie zdalne do serwera plików FTP aby uzyskąć obiekt pliku (FileObject)
        return fileSystemManager.resolveFile(server + filePath, fileSystemOptions);
    }


    public FileObject getLocalFileObject(String filePath) throws FileSystemException {
        FileSystemManager fileSystemManager = VFS.getManager();
        FileSystemOptions fileSystemOptions = setFileSystemOptions();
        return fileSystemManager.resolveFile(filePath, fileSystemOptions);
    }

    private FileSystemOptions setFileSystemOptions(){
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        StaticUserAuthenticator staticUserAuthenticator = new StaticUserAuthenticator(null, username, password);
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, staticUserAuthenticator);
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(fileSystemOptions, true);
        return fileSystemOptions;
    }

    public void closeFileObject(FileObject fileObject) {
        if (fileObject != null) {
            try {
                fileObject.close();
            } catch (FileSystemException e) {
                e.printStackTrace();
            }
        }
    }

//    public void closeVFSManager() {
//        ((DefaultFileSystemManager) fileSystemManager).close();
//    }
}



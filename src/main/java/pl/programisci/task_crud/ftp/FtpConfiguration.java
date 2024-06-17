package pl.programisci.task_crud.ftp;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
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

    public FileObject getRemoteFileObject(String filePath) throws FileSystemException {
        FileSystemManager fileSystemManager = fileSystemManager();
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        StaticUserAuthenticator staticUserAuthenticator = new StaticUserAuthenticator(null, username, password);
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, staticUserAuthenticator);
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(fileSystemOptions, true);
        return fileSystemManager.resolveFile(server + filePath, fileSystemOptions);
    }

    public FileObject getLocalFileObject(String filePath) throws FileSystemException {
        FileSystemManager fileSystemManager = fileSystemManager();
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        StaticUserAuthenticator staticUserAuthenticator = new StaticUserAuthenticator(null, username, password);
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, staticUserAuthenticator);
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(fileSystemOptions, true);
        return fileSystemManager.resolveFile(filePath, fileSystemOptions);
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

    private FileSystemManager fileSystemManager() {
        try {
            return VFS.getManager();
        } catch (FileSystemException e) {
            throw new RuntimeException(e);
        }
    }

}

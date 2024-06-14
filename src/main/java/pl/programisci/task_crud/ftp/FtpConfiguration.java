package pl.programisci.task_crud.ftp;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FtpConfiguration {
    private final static String SERVER_ADDRESS = "ftp://localhost:21";
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "mypass";

    public FileObject getRemoteFileObject(String filePath) throws FileSystemException {
        FileSystemManager fileSystemManager = fileSystemManager();
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        StaticUserAuthenticator staticUserAuthenticator = new StaticUserAuthenticator(null, USERNAME, PASSWORD);
        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(fileSystemOptions, staticUserAuthenticator);
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(fileSystemOptions, true);
        return fileSystemManager.resolveFile(SERVER_ADDRESS + filePath, fileSystemOptions);
    }

    public FileObject getLocalFileObject(String filePath) throws FileSystemException {
        FileSystemManager fileSystemManager = fileSystemManager();
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        StaticUserAuthenticator staticUserAuthenticator = new StaticUserAuthenticator(null, USERNAME, PASSWORD);
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

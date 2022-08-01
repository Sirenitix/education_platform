package swag.rest.education_platform.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DropBoxService {
    private final DbxClientV2 dropboxClient;

    public void uploadFile(MultipartFile file, String path) throws IOException, DbxException {
        Metadata uploadMetaData = dropboxClient.files().uploadBuilder("/Files/avatars/" + path).withMode(WriteMode.OVERWRITE).uploadAndFinish(file.getInputStream());
    }

    public String getFileDetails(String filePath) {
        try {
            return dropboxClient.sharing().createSharedLinkWithSettings("/Files/avatars/" + filePath).getUrl();

        } catch (DbxException e) {
            throw new RuntimeException("Error getting file metadata from DropBox");
        }
    }



}

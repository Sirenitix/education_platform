package swag.rest.education_platform.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DropBoxService {
    private final DbxClientV2 dropboxClient;

    public void uploadFile(MultipartFile file, String path) throws IOException, DbxException {
        Metadata uploadMetaData = dropboxClient.files().uploadBuilder("/Files/avatars/" + path).uploadAndFinish(file.getInputStream());
    }


}

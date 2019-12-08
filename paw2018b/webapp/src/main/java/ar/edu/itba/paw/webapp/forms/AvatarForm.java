package ar.edu.itba.paw.webapp.forms;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.multipart.MultipartFile;


public class AvatarForm {

//    @FormDataParam("image")
    private MultipartFile avatar;
//
//    public FormDataBodyPart getFileBodyPart() {
//        return fileBodyPart;
//    }
//
//    public byte[] getFileBytes() {
//        if (getFileBodyPart() == null)
//            return null;
//        return getFileBodyPart().getValueAs(byte[].class);
//    }


    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }
}



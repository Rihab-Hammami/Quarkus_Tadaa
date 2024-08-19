package com.neo.exp.entities;

import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.enterprise.context.Dependent;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Dependent
public class FileUploadForm {

    @FormParam("image")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputPart fileData;


    @FormParam("imageType")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileType;

    public  InputPart getFileData() {
        return fileData;
    }

    public void setFileData( InputPart  fileData) {
        this.fileData = fileData;
    }



    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

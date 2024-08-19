package com.neo.exp.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.neo.exp.config.CloudinaryConfig;
import com.neo.exp.entities.FileData;
import com.neo.exp.entities.FileUploadForm;
import com.neo.exp.repositories.FileDataRepository;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MediaService {

    @Inject
    FileDataRepository fileDataRepository;
    @Inject
    FileUploadForm form;

    private final String FOLDER_PATH = "C:\\Users\\pc-RIRI\\Desktop\\MediaFile";

    private String getOriginalFileName(InputPart fileDataPart) {
        try {
            // Extract the Content-Disposition header and get the file name
            String contentDisposition = fileDataPart.getHeaders().getFirst("Content-Disposition");
            for (String cdPart : contentDisposition.split(";")) {
                if (cdPart.trim().startsWith("filename")) {
                    return cdPart.split("=")[1].trim().replaceAll("\"", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    public String uploadDataToFileSystem(@MultipartForm FileUploadForm form) throws IOException {
        InputPart fileDataPart = form.getFileData();

        // Get the original file name from headers
        String originalFileName = getOriginalFileName(fileDataPart);


        String sanitizedFileName = sanitizeFileName(originalFileName);
        String filePath = FOLDER_PATH + File.separator + sanitizedFileName;

        // Debugging output
        System.out.println("Original File Name: " + originalFileName);
        System.out.println("Sanitized File Name: " + sanitizedFileName);
        System.out.println("File Path: " + filePath);

        // Save the file to the file system
        try (InputStream fileDataStream = fileDataPart.getBody(InputStream.class, null)) {
            Files.copy(fileDataStream, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Save file metadata to the database
        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(sanitizedFileName)
                .type(form.getFileType())
                .filePath(filePath)
                .build());

        if (fileData != null) {
            return "File uploaded successfully: " + filePath;
        } else {
            throw new IOException("File upload failed");
        }
    }
    public String extractFormField(Map<String, List<InputPart>> formParts, String fieldName) throws IOException {
        List<InputPart> fieldParts = formParts.get(fieldName);
        if (fieldParts != null && !fieldParts.isEmpty()) {
            InputPart fieldPart = fieldParts.get(0);
            return fieldPart.getBodyAsString(); // Assumes single value field
        }
        return null;
    }

    public List<String> extractFormFieldList(Map<String, List<InputPart>> formParts, String fieldName) throws IOException {
        List<InputPart> fieldParts = formParts.get(fieldName);
        if (fieldParts != null && !fieldParts.isEmpty()) {
            return fieldParts.stream()
                    .map(part -> {
                        try {
                            return part.getBodyAsString();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<InputPart> extractFileParts(MultipartFormDataInput input) {
        return input.getParts().stream()
                .filter(part -> part.getHeaders().getFirst("Content-Disposition").contains("filename"))
                .collect(Collectors.toList());
    }
    public FileUploadForm convertToFileUploadForm(InputPart inputPart) {
        FileUploadForm form = new FileUploadForm();
        form.setFileData(inputPart);
        return form;
    }
}
/*public byte[] downloadIageFromFileSystem(String fileName) throws IOException{
        Optional<FileData> fileData=fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images= Files.readAllBytes(new File(filePath).toPath());
        return images;
    }*/


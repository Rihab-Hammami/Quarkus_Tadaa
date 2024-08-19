package com.neo.exp.Rest;

import com.neo.exp.entities.FileUploadForm;
import com.neo.exp.services.MediaService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/upload")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MediaRessource {

    @Inject
    MediaService mediaService;


    @POST
    @RolesAllowed("employe")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImageToFileSystem(@MultipartForm FileUploadForm form) throws IOException {
        // Delegate file handling to PostService
        try {
            String result = mediaService.uploadDataToFileSystem(form);
            return Response.ok(result).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}

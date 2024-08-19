package com.neo.exp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.neo.exp.services.MediaService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class CloudinaryConfig {

    /*private Cloudinary cloudinary;
    @Inject
    MediaService mediaService;

    @PostConstruct
    public void init() {
        Map<String, String> config = mediaService.getCloudinaryConfig();
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", config.get("dztfvrokf"),
                "api_key", config.get("984157521643377"),
                "api_secret", config.get("c4Br6Ev2pOx6vZt_qvvyZn_a3Bg")));
    }

    @Produces
    public Cloudinary getCloudinary() {
        return cloudinary;
    }*/
}

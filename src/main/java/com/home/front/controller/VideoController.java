package com.home.front.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

import com.home.common.dto.HomeHeader;
import com.home.common.dto.HomeRequest;
import com.home.common.video.dto.ActorDto;
import com.home.common.video.dto.FolderDto;
import com.home.common.video.dto.VideoDto;
import com.home.common.video.dto.findActors.FindActorsRequest;
import com.home.common.video.dto.findByActor.FindByActorRequest;
import com.home.common.video.dto.findByActor.FindByActorRequestWrapper;
import com.home.common.video.dto.findByFolder.FindByFolderRequest;
import com.home.common.video.dto.findByFolder.FindByFolderRequestWrapper;
import com.home.common.video.dto.findFolders.FindFoldersRequest;
import com.home.common.video.dto.findVideos.FindVideosRequest;
import com.home.front.client.VideoClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class VideoController {

    private final VideoClient videoClient;

    @GetMapping("/videos")
    public ModelAndView videos() {
        Map<String, Object> model = new HashMap<>();
        model.put("videos", findVideos());
        model.put("folders", findFolders());
        model.put("actors", findActors());
        return new ModelAndView("videos", model);
    }

    @GetMapping("/videos/findByFolder")
    public ModelAndView findVideosByFolder(String folder) {
        Map<String, Object> model = new HashMap<>();
        model.put("videos", findByFolder(folder));
        model.put("folders", findFolders());
        model.put("actors", findActors());
        return new ModelAndView("videos", model);
    }

    @GetMapping("/videos/findByActor")
    public ModelAndView findVideosByActor(String actor) {
        Map<String, Object> model = new HashMap<>();
        model.put("videos", findByActor(actor));
        model.put("folders", findFolders());
        model.put("actors", findActors());
        return new ModelAndView("videos", model);
    }

    @GetMapping(value = "/videos/stream/**")
    public ResponseEntity<Resource> streamVideo(
            HttpServletRequest request,
            @RequestHeader(value = "Range", required = true) String rangeHeader) {
        return videoClient.streamVideo(
                URLEncoder.encode(request.getRequestURI()
                        .substring(request.getRequestURI().indexOf("/stream/") + "/stream/".length()),
                        StandardCharsets.UTF_8),
                rangeHeader);
    }

    private List<VideoDto> findVideos() {
        return videoClient.findVideos((FindVideosRequest) setHeader(new FindVideosRequest()))
                .getResponse()
                .getVideos();
    }

    private List<VideoDto> findByFolder(String folder) {
        FindByFolderRequestWrapper wrapper = new FindByFolderRequestWrapper();
        wrapper.setRequest(new FindByFolderRequest(folder));
        return videoClient.findByFolder((FindByFolderRequestWrapper) setHeader(wrapper))
                .getResponse()
                .getVideos();
    }

    private List<VideoDto> findByActor(String actor) {
        FindByActorRequestWrapper wrapper = new FindByActorRequestWrapper();
        wrapper.setRequest(new FindByActorRequest(actor));
        return videoClient.findByActor((FindByActorRequestWrapper) setHeader(wrapper))
                .getResponse()
                .getVideos();
    }

    private List<FolderDto> findFolders() {
        return videoClient.findFolders((FindFoldersRequest) setHeader(new FindFoldersRequest()))
                .getResponse()
                .getFolders();
    }

    private List<ActorDto> findActors() {
        return videoClient.findActors((FindActorsRequest) setHeader(new FindActorsRequest()))
                .getResponse()
                .getActors();
    }

    private HomeRequest<?> setHeader(HomeRequest<?> homeRequest) {
        homeRequest.setHeader(createHomeHeader());
        return homeRequest;
    }

    private HomeHeader createHomeHeader() {
        return new HomeHeader(UUID.randomUUID().toString());
    }
}

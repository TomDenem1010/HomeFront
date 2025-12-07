package com.home.front.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
import com.home.common.video.dto.findVideos.FindVideosRequest;
import com.home.front.client.VideoClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class VideoController {

    private final VideoClient videoClient;

    @GetMapping("/videos/findVideos")
    public ModelAndView findVideos() {
        Map<String, Object> model = new HashMap<>();
        model.put(
                "videos",
                videoClient.findVideos((FindVideosRequest) setHeader(new FindVideosRequest()))
                        .getResponse()
                        .getVideos());

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

    private HomeRequest<?> setHeader(HomeRequest<?> homeRequest) {
        homeRequest.setHeader(createHomeHeader());
        return homeRequest;
    }

    private HomeHeader createHomeHeader() {
        return new HomeHeader(UUID.randomUUID().toString());
    }
}

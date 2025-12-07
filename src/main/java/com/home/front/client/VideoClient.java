package com.home.front.client;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.home.common.dto.HomeResponse;
import com.home.common.video.dto.findActors.FindActorsRequest;
import com.home.common.video.dto.findActors.FindActorsResponse;
import com.home.common.video.dto.findByActor.FindByActorRequestWrapper;
import com.home.common.video.dto.findByActor.FindByActorResponse;
import com.home.common.video.dto.findByFolder.FindByFolderRequestWrapper;
import com.home.common.video.dto.findByFolder.FindByFolderResponse;
import com.home.common.video.dto.findFolders.FindFoldersRequest;
import com.home.common.video.dto.findFolders.FindFoldersResponse;
import com.home.common.video.dto.findVideos.FindVideosRequest;
import com.home.common.video.dto.findVideos.FindVideosResponse;

@HttpExchange("/video")
public interface VideoClient {

    @PostExchange("/findVideos")
    public HomeResponse<FindVideosResponse> findVideos(final @RequestBody FindVideosRequest homeRequest);

    @PostExchange("/findFolders")
    public HomeResponse<FindFoldersResponse> findFolders(final @RequestBody FindFoldersRequest homeRequest);

    @PostExchange("/findActors")
    public HomeResponse<FindActorsResponse> findActors(final @RequestBody FindActorsRequest homeRequest);

    @PostExchange("/findByFolder")
    public HomeResponse<FindByFolderResponse> findByFolder(final @RequestBody FindByFolderRequestWrapper homeRequest);

    @PostExchange("/findByActor")
    public HomeResponse<FindByActorResponse> findByActor(final @RequestBody FindByActorRequestWrapper homeRequest);

    @GetExchange("/stream/{videoPath:**}")
    public ResponseEntity<Resource> streamVideo(
            @PathVariable("videoPath") String videoPath,
            @RequestHeader("Range") String rangeHeader);
}

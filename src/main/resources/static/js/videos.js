function playVideo(path) {
    console.log("Lejátszás:", path);

    const videoPlayer = document.getElementById("videoPlayer");
    const videoSource = document.getElementById("videoSource");

    if (!videoPlayer || !videoSource) {
        console.error("Video player elements not found!");
        return;
    }

    // Set video source
    const videoUrl = "/videos/stream/" + path;
    videoSource.src = videoUrl;

    // Update video title
    if (typeof updateVideoTitle === 'function') {
        updateVideoTitle(path);
    }

    // Show video player using the toggle function
    if (typeof toggleVideoVisibility === 'function') {
        toggleVideoVisibility(true);
    } else {
        // Fallback if function not available
        videoPlayer.style.display = "block";
        const placeholder = document.getElementById("videoPlaceholder");
        if (placeholder) placeholder.style.display = "none";
    }

    // Load and play video
    videoPlayer.load();
    videoPlayer.play().catch(function (error) {
        console.error("Video playback error:", error);

        // Hide video player on error
        if (typeof toggleVideoVisibility === 'function') {
            toggleVideoVisibility(false);
        }

        // Reset title on error
        if (typeof updateVideoTitle === 'function') {
            updateVideoTitle(null);
        }

        // Show error in placeholder
        const videoPlaceholder = document.getElementById("videoPlaceholder");
        if (videoPlaceholder) {
            videoPlaceholder.innerHTML = "<h4>❌ Hiba a videó betöltése során</h4><p>" + error.message + "</p>";
        }
    });
}

function updateVideoTitle(videoPath) {
    const videoTitle = document.getElementById("videoTitle");
    if (!videoTitle) return;

    if (videoPath) {
        let fileName = videoPath.split('/').pop();
        fileName = fileName.split('\\').pop();
        fileName = fileName.replace(/\.[^/.]+$/, "");
        fileName = decodeURIComponent(fileName);

        videoTitle.textContent = `🎬 ${fileName}`;
        videoTitle.classList.remove("default");
    } else {
        videoTitle.textContent = "Videólejátszó";
        videoTitle.classList.add("default");
    }
}

function toggleVideoVisibility(showVideo) {
    const videoPlayer = document.getElementById("videoPlayer");
    const videoPlaceholder = document.getElementById("videoPlaceholder");

    if (showVideo) {
        videoPlayer.classList.add("visible");
        videoPlaceholder.classList.add("hidden");
    } else {
        videoPlayer.classList.remove("visible");
        videoPlaceholder.classList.remove("hidden");
    }
}

document.addEventListener('DOMContentLoaded', function () {
    toggleVideoVisibility(false);
    updateVideoTitle(null);
});
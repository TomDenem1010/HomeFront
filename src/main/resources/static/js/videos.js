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

function updateSearchMode() {
    const selectedMode = document.querySelector('input[name="mode"]:checked').value;
    const folderSelection = document.getElementById('folderSelection');
    const actorSelection = document.getElementById('actorSelection');
    
    // Hide all selection dropdowns initially
    if (folderSelection) folderSelection.style.display = 'none';
    if (actorSelection) actorSelection.style.display = 'none';
    
    // Show appropriate dropdown based on selection
    if (selectedMode === 'folder' && folderSelection) {
        folderSelection.style.display = 'block';
    } else if (selectedMode === 'actor' && actorSelection) {
        actorSelection.style.display = 'block';
    }
    
    // If "All" is selected, redirect to /videos to show all videos
    if (selectedMode === 'all') {
        console.log('All videos mode selected - redirecting to /videos');
        window.location.href = '/videos';
    }
}

function selectFolder() {
    const selectedFolder = document.getElementById('folderSelect').value;
    if (selectedFolder) {
        console.log('Selected folder:', selectedFolder);
        // Submit the form to call /videos/findByFolder with the selected folder
        document.getElementById('folderForm').submit();
    }
}

function selectActor() {
    const selectedActor = document.getElementById('actorSelect').value;
    if (selectedActor) {
        console.log('Selected actor:', selectedActor);
        // Submit the form to call /videos/findByActor with the selected actor
        document.getElementById('actorForm').submit();
    }
}
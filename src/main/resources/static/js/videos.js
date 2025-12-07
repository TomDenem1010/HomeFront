function playVideo(path) {
    console.log("Lejátszás:", path);
    const videoPlayer = document.getElementById("videoPlayer");
    videoPlayer.src = "/videos/stream/" + path;
    videoPlayer.play();
}
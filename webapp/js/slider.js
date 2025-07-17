document.addEventListener("DOMContentLoaded", function () {
  const slider = document.querySelector(".slider .slide");
  const images = slider.querySelectorAll("img");
  let currentIndex = 0;

  // Show first image initially
  images[currentIndex].style.opacity = ".25";

  function slide() {
    // Hide current image
    images[currentIndex].style.opacity = "0";

    // Move to next image
    currentIndex = (currentIndex + 1) % images.length;

    // Show next image
    images[currentIndex].style.opacity = "0.25";
  }

  // Change image every 3 seconds
  setInterval(slide, 2000);
});

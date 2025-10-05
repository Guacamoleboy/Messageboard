function createRandomStar() {
    const containers = document.querySelectorAll(".hero, .section-404");

    containers.forEach(container => {
        const star = document.createElement("div");
        star.classList.add("random-star");

        const rect = container.getBoundingClientRect();
        const x = Math.random() * rect.width;
        const y = Math.random() * rect.height;
        const size = Math.floor(Math.random() * 3) + 2;
        const duration = (Math.random() * 3 + 2).toFixed(1);

        star.style.left = `${x}px`;
        star.style.top = `${y}px`;
        star.style.width = `${size}px`;
        star.style.height = `${size}px`;
        star.style.animationDuration = `${duration}s`;

        container.appendChild(star);

        setTimeout(() => {
            star.remove();
        }, duration * 1000);
    });
}

setInterval(createRandomStar, 500);
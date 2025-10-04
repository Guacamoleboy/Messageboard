function createRandomStar() {

    const star = document.createElement("div");
    star.classList.add("random-star");

    const hero = document.querySelector(".hero");
    const heroRect = hero.getBoundingClientRect();
    const x = Math.random() * heroRect.width;
    const y = Math.random() * heroRect.height;
    const size = Math.floor(Math.random() * 3) + 2;
    const duration = (Math.random() * 3 + 2).toFixed(1);

    star.style.left = `${x}px`;
    star.style.top = `${y}px`;
    star.style.width = `${size}px`;
    star.style.height = `${size}px`;
    star.style.animationDuration = `${duration}s`;

    hero.appendChild(star);

    setTimeout(() => {
        star.remove();
    }, duration * 1000);
}

setInterval(createRandomStar, 500);
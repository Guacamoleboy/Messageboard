/*

    Tables for our Database
    Should be 3NF.

    Updated 05/10-2025
    By: Guacamoleboy

    - Guac

*/

CREATE TABLE users (
id SERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
email VARCHAR(100) UNIQUE,
password_hash VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
id SERIAL PRIMARY KEY,
user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE, /* Deletes posts in case a user is dropped */
title VARCHAR(255) NOT NULL,
description TEXT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments (
id SERIAL PRIMARY KEY,
post_id INT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
content TEXT NOT NULL,
upvotes INT DEFAULT 0,
downvotes INT DEFAULT 0,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

/* SMALLINT != INT. It's for optimization reasons since we're only doing +1 or -1 anyways */
CREATE TABLE post_votes (
id SERIAL PRIMARY KEY,
post_id INT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
vote SMALLINT NOT NULL CHECK (vote IN (1, -1)), /* Makes sure to only allow +1 or -1 to prevent spam votes */
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
UNIQUE(post_id, user_id) /* Prevents 2 post_votes from same user on a specific ID */
);

CREATE TABLE comment_votes (
id SERIAL PRIMARY KEY,
comment_id INT NOT NULL REFERENCES comments(id) ON DELETE CASCADE,
user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
vote SMALLINT NOT NULL CHECK (vote IN (1, -1)),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
UNIQUE(comment_id, user_id)
);
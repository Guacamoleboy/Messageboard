/* Removes Data First */
TRUNCATE TABLE comment_votes, post_votes, comments, posts, users RESTART IDENTITY CASCADE;

/* Password hashmap is "1234" for testing purpose */
INSERT INTO users (username, email, password_hash) VALUES
('Fin', 'fin@hader.livet', '$2a$10$7pVwT/7Rp9uK7pLw1oM4XeIDj2cZ5P2g6Fq8zht7HkXsmjU5zV4rS'),
('Diddy', 'diddy@hader.livet', '$2a$10$7pVwT/7Rp9uK7pLw1oM4XeIDj2cZ5P2g6Fq8zht7HkXsmjU5zV4rS'),
('Drake', 'drake@hader.livet', '$2a$10$7pVwT/7Rp9uK7pLw1oM4XeIDj2cZ5P2g6Fq8zht7HkXsmjU5zV4rS');

/* Posts */
INSERT INTO posts (user_id, title, description) VALUES
(1, '1 i chatten hvis I hader livet', 'Ja, den er god nok du.. Thoughts?'),
(2, 'Hvem vil se mine fødder efter en 42km løbetur?', 'De stinker.. Ægte meget.'),
(3, 'Søger ven', 'Kun kvinder mellem 20-25.. Gerne flotte.');

/* Comments */
INSERT INTO comments (post_id, user_id, content) VALUES
(1, 2, 'Hader livet..'),
(1, 3, 'Fredage er også slemme.'),
(2, 1, 'Onsdage hader jeg også livet. Bare lidt mere.'),
(2, 3, 'Hader livet.. her også'),
(3, 1, 'Shit jeg hader livet..');

/* Post votes */
INSERT INTO post_votes (post_id, user_id, vote) VALUES
(1, 2, 1),
(1, 3, 1),
(2, 1, 1),
(2, 3, -1),
(3, 1, 1);

/* Comment Votes */
INSERT INTO comment_votes (comment_id, user_id, vote) VALUES
(1, 1, 1),
(2, 1, 1),
(3, 2, 1),
(4, 2, -1),
(5, 3, 1);

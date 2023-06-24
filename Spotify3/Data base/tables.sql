CREATE TABLE users (
  user_id INT UNSIGNED AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id)
);

CREATE TABLE artists (
  artist_id INT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  bio TEXT,
  photo_url VARCHAR(255),
  PRIMARY KEY (artist_id)
);

CREATE TABLE albums (
  album_id INT UNSIGNED AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  release_date DATE NOT NULL,
  cover_art_url VARCHAR(255),
  artist_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (album_id),
  FOREIGN KEY (artist_id) REFERENCES artists(artist_id)
);

CREATE TABLE songs (
  song_id INT UNSIGNED AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  length INT UNSIGNED NOT NULL,
  track_number INT UNSIGNED NOT NULL,
  album_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (song_id),
  FOREIGN KEY (album_id) REFERENCES albums(album_id)
);

CREATE TABLE playlists (
  playlist_id INT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  owner_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (playlist_id),
  FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

CREATE TABLE playlist_songs (
  playlist_id INT UNSIGNED NOT NULL,
  song_id INT UNSIGNED NOT NULL,
  added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (playlist_id, song_id),
  FOREIGN KEY (playlist_id) REFERENCES playlists(playlist_id),
  FOREIGN KEY (song_id) REFERENCES songs(song_id)
);

CREATE TABLE genres (
  genre_id INT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (genre_id)
);

CREATE TABLE album_genres (
  album_id INT UNSIGNED NOT NULL,
  genre_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (album_id, genre_id),
  FOREIGN KEY (album_id) REFERENCES albums(album_id),
  FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

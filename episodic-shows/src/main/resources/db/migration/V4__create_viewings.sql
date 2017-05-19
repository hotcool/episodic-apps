create table viewings (
  id bigint not null auto_increment primary key,
  show_id bigint not null,
  user_id bigint not null,
  episode_id bigint not null,
  updated_at datetime,
  timecode int,
  FOREIGN KEY (show_id) REFERENCES shows(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (episode_id) REFERENCES episodes(id)
);
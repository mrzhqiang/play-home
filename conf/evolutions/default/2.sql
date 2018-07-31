# --- Built-in data

# --- !Ups

INSERT INTO woof.treasures (id, name, link, description, version, created, modified) VALUES (1, 'Smith', 'http://smith.randall.top', '模仿好玩吧手游《地狱之门》的文字网页游戏。', 1, '2018-07-31 18:14:00.000000', '2018-07-31 18:14:00.000000');
INSERT INTO woof.treasures (id, name, link, description, version, created, modified) VALUES (2, 'Redis', 'http://randal.top/redis', '对《Redis 实战》的相关实践。', 1, '2018-07-31 18:14:00.000000', '2018-07-31 18:14:00.000000');

# --- !Downs

DELETE FROM woof.treasures;



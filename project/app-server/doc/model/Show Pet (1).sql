DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	`id`	int	NOT NULL	DEFAULT AUTO_INCREMENT,
	`image_id`	VARCHAR(255)	NOT NULL	COMMENT '프로필사진 패스',
	`nickname`	VARCHAR	NOT NULL	DEFAULT UK,
	`uesr_id`	VARCHAR	NOT NULL	DEFAULT FK,
	`password`	VARCHAR	NOT NULL,
	`petcategory`	CHAR	NULL,
	`introduction`	VARCHAR	NULL,
	`roll`	VARCHAR(255)	NULL
);

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
	`id`	VARCHAR(255)	NOT NULL,
	`content`	VARCHAR(255)	NOT NULL,
	`tag_id`	VARCHAR(255)	NULL,
	`created_at`	VARCHAR(255)	NOT NULL,
	`aurhtor_id`	int	NOT NULL	DEFAULT AUTO_INCREMENT,
	`image_id`	VARCHAR(255)	NOT NULL	COMMENT '프로필사진 패스',
	`id2`	VARCHAR(255)	NOT NULL,
	`id3`	VARCHAR(255)	NOT NULL
);

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
	`id`	VARCHAR(255)	NOT NULL,
	`notice_content`	VARCHAR(255)	NULL,
	`notice_timestamp`	VARCHAR(255)	NULL,
	`read_or_not`	bool	NULL	DEFAULT bool,
	`Field2`	VARCHAR(255)	NULL,
	`Field3`	VARCHAR(255)	NULL,
	`id2`	int	NOT NULL	DEFAULT AUTO_INCREMENT,
	`image_id`	VARCHAR(255)	NOT NULL	COMMENT '프로필사진 패스'
);

DROP TABLE IF EXISTS `bookmark`;

CREATE TABLE `bookmark` (

);

DROP TABLE IF EXISTS `post_likes`;

CREATE TABLE `post_likes` (
	`like_id`	VARCHAR(255)	NOT NULL,
	`user_id`	int	NOT NULL	DEFAULT AUTO_INCREMENT,
	`post_like`	VARCHAR(255)	NOT NULL
);

DROP TABLE IF EXISTS `best_post_likes`;

CREATE TABLE `best_post_likes` (
	`Key`	VARCHAR(255)	NOT NULL
);

DROP TABLE IF EXISTS `animal_category`;

CREATE TABLE `animal_category` (
	`id`	VARCHAR(255)	NOT NULL,
	`animal_no`	int	NOT NULL
);

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
	`id`	VARCHAR(255)	NOT NULL,
	`tag_name`	VARCHAR(255)	NULL,
	`id2`	VARCHAR(255)	NOT NULL
);

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
	`id`	VARCHAR(255)	NOT NULL,
	`Field`	VARCHAR(255)	NULL,
	`Field2`	VARCHAR(255)	NULL,
	`Field3`	VARCHAR(255)	NULL,
	`Field4`	VARCHAR(255)	NULL,
	`id2`	VARCHAR(255)	NULL
);

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
	`comment_id`	VARCHAR(255)	NOT NULL,
	`user_id3`	int	NOT NULL	DEFAULT AUTO_INCREMENT,
	`image_id`	VARCHAR(255)	NOT NULL,
	`user_id`	VARCHAR(255)	NULL,
	`content`	VARCHAR(255)	NULL,
	`timestamp`	VARCHAR(255)	NULL,
	`Field6`	VARCHAR(255)	NULL,
	`id2`	VARCHAR(255)	NULL
);

DROP TABLE IF EXISTS `interest_animal`;

CREATE TABLE `interest_animal` (
	`id`	int	NOT NULL	DEFAULT AUTO_INCREMENT,
	`id2`	VARCHAR(255)	NOT NULL
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`id`,
	`image_id`
);

ALTER TABLE `post` ADD CONSTRAINT `PK_POST` PRIMARY KEY (
	`id`
);

ALTER TABLE `notice` ADD CONSTRAINT `PK_NOTICE` PRIMARY KEY (
	`id`
);

ALTER TABLE `post_likes` ADD CONSTRAINT `PK_POST_LIKES` PRIMARY KEY (
	`like_id`
);

ALTER TABLE `best_post_likes` ADD CONSTRAINT `PK_BEST_POST_LIKES` PRIMARY KEY (
	`Key`
);

ALTER TABLE `animal_category` ADD CONSTRAINT `PK_ANIMAL_CATEGORY` PRIMARY KEY (
	`id`
);

ALTER TABLE `tag` ADD CONSTRAINT `PK_TAG` PRIMARY KEY (
	`id`
);

ALTER TABLE `file` ADD CONSTRAINT `PK_FILE` PRIMARY KEY (
	`id`
);

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
	`comment_id`,
	`user_id3`,
	`image_id`
);

ALTER TABLE `user` ADD CONSTRAINT `FK_file_TO_user_1` FOREIGN KEY (
	`image_id`
)
REFERENCES `file` (
	`id`
);

ALTER TABLE `post` ADD CONSTRAINT `FK_user_TO_post_1` FOREIGN KEY (
	`aurhtor_id`
)
REFERENCES `user` (
	`id`
);

ALTER TABLE `post` ADD CONSTRAINT `FK_user_TO_post_2` FOREIGN KEY (
	`image_id`
)
REFERENCES `user` (
	`image_id`
);

ALTER TABLE `post` ADD CONSTRAINT `FK_animal_category_TO_post_1` FOREIGN KEY (
	`id2`
)
REFERENCES `animal_category` (
	`id`
);

ALTER TABLE `post` ADD CONSTRAINT `FK_animal_category_TO_post_2` FOREIGN KEY (
	`id3`
)
REFERENCES `animal_category` (
	`id`
);

ALTER TABLE `notice` ADD CONSTRAINT `FK_user_TO_notice_1` FOREIGN KEY (
	`id2`
)
REFERENCES `user` (
	`id`
);

ALTER TABLE `notice` ADD CONSTRAINT `FK_user_TO_notice_2` FOREIGN KEY (
	`image_id`
)
REFERENCES `user` (
	`image_id`
);

ALTER TABLE `post_likes` ADD CONSTRAINT `FK_user_TO_post_likes_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `user` (
	`id`
);

ALTER TABLE `post_likes` ADD CONSTRAINT `FK_post_TO_post_likes_1` FOREIGN KEY (
	`post_like`
)
REFERENCES `post` (
	`id`
);

ALTER TABLE `tag` ADD CONSTRAINT `FK_post_TO_tag_1` FOREIGN KEY (
	`id2`
)
REFERENCES `post` (
	`id`
);

ALTER TABLE `file` ADD CONSTRAINT `FK_post_TO_file_1` FOREIGN KEY (
	`id2`
)
REFERENCES `post` (
	`id`
);

ALTER TABLE `comment` ADD CONSTRAINT `FK_user_TO_comment_1` FOREIGN KEY (
	`user_id3`
)
REFERENCES `user` (
	`id`
);

ALTER TABLE `comment` ADD CONSTRAINT `FK_user_TO_comment_2` FOREIGN KEY (
	`image_id`
)
REFERENCES `user` (
	`image_id`
);

ALTER TABLE `comment` ADD CONSTRAINT `FK_post_TO_comment_1` FOREIGN KEY (
	`id2`
)
REFERENCES `post` (
	`id`
);

ALTER TABLE `interest_animal` ADD CONSTRAINT `FK_user_TO_interest_animal_1` FOREIGN KEY (
	`id`
)
REFERENCES `user` (
	`id`
);

ALTER TABLE `interest_animal` ADD CONSTRAINT `FK_animal_category_TO_interest_animal_1` FOREIGN KEY (
	`id2`
)
REFERENCES `animal_category` (
	`id`
);


CREATE SCHEMA IF NOT EXISTS student_architect_service;

USE student_architect_service;

CREATE TABLE IF NOT EXISTS `board` (
  `board_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS `standard` (
  `standard_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) DEFAULT NULL,
  `board_id` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);

ALTER TABLE `standard` 
ADD CONSTRAINT `fk_standard_board_id` 
FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;

CREATE TABLE IF NOT EXISTS `instructor` (
  `instructor_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `designation` VARCHAR(50) DEFAULT NULL,
  `email` VARCHAR(50) NOT NULL,
  `registration_date` DATE NOT NULL,
  `introduction` VARCHAR(2000) DEFAULT NULL,
  `image` BLOB DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);

ALTER TABLE `instructor` ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC);

CREATE TABLE IF NOT EXISTS `subject` (
  `subject_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) DEFAULT NULL,
  `standard_id` INT NOT NULL,
  `instructor_id` INT DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);

ALTER TABLE `subject` 
ADD CONSTRAINT `fk_subject_standard_id` 
FOREIGN KEY (`standard_id`) REFERENCES `standard` (`standard_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `subject` 
ADD CONSTRAINT `fk_subject_instructor_id` 
FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;

CREATE TABLE IF NOT EXISTS `chapter` (
	`chapter_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`description` VARCHAR(2000) DEFAULT NULL,
	`subject_id` INT NOT NULL,
	`instructor_id` INT DEFAULT NULL,
	`is_mandatory` BOOLEAN DEFAULT FALSE,
	`is_free` BOOLEAN DEFAULT FALSE,
	`time_required_in_sec` INT DEFAULT NULL,
	`created_at` DATETIME NOT NULL,
	`updated_at` DATETIME NOT NULL
);

ALTER TABLE `chapter` 
ADD CONSTRAINT `fk_chapter_subject_id` 
FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `chapter` 
ADD CONSTRAINT `fk_chapter_instructor_id` 
FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;

CREATE TABLE IF NOT EXISTS `content` (
  `content_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `content_type` VARCHAR(20) NOT NULL,
  `content_location` VARCHAR(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `section` (
  `section_id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) DEFAULT NULL,
  `chapter_id` INT NOT NULL,
  `instructor_id` INT DEFAULT NULL,
  `is_mandatory` BOOLEAN DEFAULT FALSE,
  `is_free` BOOLEAN DEFAULT FALSE,
  `time_required_in_sec` INT DEFAULT NULL,
  `content_id` INT DEFAULT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);

ALTER TABLE `section` 
ADD CONSTRAINT `fk_section_chapter_id` 
FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`chapter_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `section` 
ADD CONSTRAINT `fk_section_instructor_id` 
FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `section` 
ADD CONSTRAINT `fk_section_content_id` 
FOREIGN KEY (`content_id`) REFERENCES `content` (`content_id`) 
ON DELETE CASCADE ON UPDATE NO ACTION;
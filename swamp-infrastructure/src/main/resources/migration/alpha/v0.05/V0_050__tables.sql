-- -----------------------------------------------------
-- Table `container`
-- -----------------------------------------------------
ALTER TABLE `container` DROP `name`;

-- -----------------------------------------------------
-- Table `container_aliases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_aliases` (
  `container_id` varchar(255) NOT NULL,
  `aliases` varchar(255) NULL,
  INDEX `fk_container_aliases_idx` (`container_id` ASC),
  CONSTRAINT `fk_container_aliases`
    FOREIGN KEY (`container_id`)
    REFERENCES `container` (`id`)
      ON DELETE CASCADE )
ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `project_instance_view`
-- -----------------------------------------------------
ALTER TABLE `project_instance_view` DROP `name`;
ALTER TABLE `project_instance_view` DROP `description`;
ALTER TABLE `project_instance_view` ADD `project_view_id` varchar(255);

-- -----------------------------------------------------
-- Table `project_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_view` (
  `id` varchar(255) NOT NULL,
  `project_id` varchar(255) NOT NULL,
  `name` varchar(255) NULL,
  `description` varchar(255) NULL,
  `build_sequence` INT(4) NOT NULL,
  PRIMARY KEY (`id`) )
ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` ADD `stop_reason` varchar(255) NULL;
ALTER TABLE `container_instance_view` ADD `remove_reason` varchar(255) NULL;
ALTER TABLE `container_instance_view` DROP COLUMN `log`;


-- -----------------------------------------------------
-- Table `container_instance_warning_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_instance_warning_view` (
  `container_instance_view_id` varchar(255) NOT NULL,
  `warnings` varchar(255) NULL,
  INDEX `fk_container_instance_view_view_idx` (`container_instance_view_id` ASC),
  CONSTRAINT `fk_container_instance_view_view`
    FOREIGN KEY (`container_instance_view_id`)
    REFERENCES `container_instance_view` (`id`)
      ON DELETE CASCADE )
ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `project_instance_view`
-- -----------------------------------------------------
ALTER TABLE `project_instance_view` RENAME TO `build_view`;
ALTER TABLE `project_instance_container_view` RENAME TO `build_container_view`;

ALTER TABLE `build_view` ADD `sequence` INT(4) NOT NULL;
ALTER TABLE `build_view` CHANGE `project_instance_id` `build_id` VARCHAR(255);

ALTER TABLE `build_container_view` ADD `status` VARCHAR(255);
ALTER TABLE `build_container_view` CHANGE `project_instance_view_id` `build_view_id` VARCHAR(255);

ALTER TABLE `build_container_view` DROP FOREIGN KEY `fk_project_instance_container_view`;
ALTER TABLE `build_container_view` ADD CONSTRAINT `fk_build_container_view`
    FOREIGN KEY (`build_view_id`) REFERENCES `build_view` (`id`) ON DELETE CASCADE;

-- -----------------------------------------------------
-- Table `container_instance_log_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `container_instance_log_view` (
  `id` varchar(255) NOT NULL,
  `container_instance_id` varchar(255) NULL,
  `creation_log` LONGTEXT NULL,
  `running_log` LONGTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB;

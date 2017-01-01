-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` DROP `internal_container_name`;
ALTER TABLE `container_instance_view` CHANGE `date_initialized` `initialized_at` datetime;
ALTER TABLE `container_instance_view` CHANGE `date_created` `created_at` datetime;
ALTER TABLE `container_instance_view` CHANGE `date_started` `started_at` datetime;
ALTER TABLE `container_instance_view` CHANGE `date_stopped` `stopped_at` datetime;
ALTER TABLE `container_instance_view` CHANGE `date_removed` `removed_at` datetime;

-- -----------------------------------------------------
-- Table `project_instance_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_instance_view` (
  `id` varchar(255) NOT NULL,
  `project_instance_id` varchar(255) NULL,
  `name` varchar(255) NULL,
  `description` varchar(255) NULL,
  `initialized_at` datetime NULL,
  PRIMARY KEY (`id`) )
ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `project_instance_container_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_instance_container_view` (
  `project_instance_view_id` varchar(255) NOT NULL,
  `container_instance_id` varchar(255) NULL,
  INDEX `fk_project_instance_container_view_idx` (`project_instance_view_id` ASC),
  CONSTRAINT `fk_project_instance_container_view`
    FOREIGN KEY (`project_instance_view_id`)
    REFERENCES `project_instance_view` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION )
ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` CHANGE `internal_container_id` `container_id` varchar(255);

-- -----------------------------------------------------
-- Table `project`
-- -----------------------------------------------------
ALTER TABLE `project` CHANGE `created` `created_at` datetime;
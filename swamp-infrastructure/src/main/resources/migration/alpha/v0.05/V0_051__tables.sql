-- -----------------------------------------------------
-- Table `project_instance_view`
-- -----------------------------------------------------
ALTER TABLE `project_instance_view` DROP `name`;
ALTER TABLE `project_instance_view` DROP `description`;
ALTER TABLE `project_instance_view` ADD `project_view_id` varchar(255);

-- -----------------------------------------------------
-- Table `container_aliases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_view` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NULL,
  `description` varchar(255) NULL,
  PRIMARY KEY (`id`) )
ENGINE=InnoDB;
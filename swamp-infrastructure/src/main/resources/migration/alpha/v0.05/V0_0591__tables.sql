-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` DROP COLUMN `creation_log`;
ALTER TABLE `container_instance_view` DROP COLUMN `running_log`;

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
-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` CHANGE `creation_log` `creation_log` LONGTEXT NULL;
ALTER TABLE `container_instance_view` CHANGE `running_log` `running_log` LONGTEXT NULL;

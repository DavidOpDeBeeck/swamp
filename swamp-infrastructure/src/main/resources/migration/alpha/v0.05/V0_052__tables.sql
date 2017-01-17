-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` ADD `stop_reason` varchar(255) NULL;
ALTER TABLE `container_instance_view` ADD `remove_reason` varchar(255) NULL;
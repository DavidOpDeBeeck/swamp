-- -----------------------------------------------------
-- Table `project_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` ADD `creation_log` TEXT NULL;
ALTER TABLE `container_instance_view` CHANGE `log` `running_log` TEXT NULL;

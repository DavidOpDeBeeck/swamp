-- -----------------------------------------------------
-- Table `project_instance_view`
-- -----------------------------------------------------
RENAME TABLE `project_instance_view` TO `build_view`;
ALTER TABLE `build_view` ADD `sequence` INT(4) NOT NULL;
ALTER TABLE `build_view` ADD `status` varchar(255) NOT NULL;
ALTER TABLE `build_view` CHANGE `project_instance_id` `build_id` VARCHAR(255);

-- -----------------------------------------------------
-- Table `project_instance_view`
-- -----------------------------------------------------
RENAME TABLE `project_instance_container_view` TO `build_container_view`;
ALTER TABLE `build_container_view` CHANGE `project_instance_view_id` `build_view_id` VARCHAR(255);
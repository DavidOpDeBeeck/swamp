-- -----------------------------------------------------
-- Table `project_view`
-- -----------------------------------------------------
ALTER TABLE `project_view` ADD `build_sequence` INT(4) NOT NULL;

-- -----------------------------------------------------
-- Table `build_view`
-- -----------------------------------------------------
ALTER TABLE `build_view` DROP `status`;

-- -----------------------------------------------------
-- Table `project_instance_container_view`
-- -----------------------------------------------------
ALTER TABLE `build_container_view` ADD `status` VARCHAR(255);
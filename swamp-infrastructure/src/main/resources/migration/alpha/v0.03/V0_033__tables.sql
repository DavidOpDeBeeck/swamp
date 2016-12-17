-- -----------------------------------------------------
-- Table `container_instance_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_view` CHANGE `date_scheduled` `date_initialized` datetime;

-- -----------------------------------------------------
-- Table `server_view`
-- -----------------------------------------------------
ALTER TABLE `server_view` DROP `ca_certificate`;
ALTER TABLE `server_view` DROP `certificate`;
ALTER TABLE `server_view` DROP `key`;


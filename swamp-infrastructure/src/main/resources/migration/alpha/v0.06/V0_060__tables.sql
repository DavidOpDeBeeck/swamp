-- -----------------------------------------------------
-- Table `container`
-- -----------------------------------------------------
ALTER TABLE `container` RENAME TO `container_template`;
ALTER TABLE `container_location` RENAME TO `container_template_location`;

ALTER TABLE `port_mapping` CHANGE `container_id` `container_template_id` VARCHAR(255);
ALTER TABLE `environment_variable` CHANGE `container_id` `container_template_id` VARCHAR(255);
ALTER TABLE `container_template_location` CHANGE `container_id` `container_template_id` VARCHAR(255);

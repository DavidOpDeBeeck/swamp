-- -----------------------------------------------------
-- Table `build_container_view`
-- -----------------------------------------------------
ALTER TABLE `build_container_view` DROP FOREIGN KEY `fk_project_instance_container_view`;
ALTER TABLE `build_container_view` ADD CONSTRAINT `fk_build_container_view` 
    FOREIGN KEY (`build_view_id`) REFERENCES `build_view` (`id`) ON DELETE CASCADE;
    
-- -----------------------------------------------------
-- Table `container_aliases`
-- -----------------------------------------------------
ALTER TABLE `container_aliases` DROP FOREIGN KEY `fk_container_aliases`;
ALTER TABLE `container_aliases` ADD CONSTRAINT `fk_container_aliases` 
    FOREIGN KEY (`container_id`) REFERENCES `container` (`id`) ON DELETE CASCADE;
 
-- -----------------------------------------------------
-- Table `container_instance_warning_view`
-- -----------------------------------------------------
ALTER TABLE `container_instance_warning_view` DROP FOREIGN KEY `fk_container_instance_view_view`;
ALTER TABLE `container_instance_warning_view` ADD CONSTRAINT `fk_container_instance_view_view` 
    FOREIGN KEY (`container_instance_view_id`) REFERENCES `container_instance_view` (`id`) ON DELETE CASCADE;

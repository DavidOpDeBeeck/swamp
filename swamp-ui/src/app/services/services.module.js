import angular from "angular";
import angularResource from "angular-resource";
import LocationService from "./location.service";
import ProjectService from "./project.service";
import NavigationService from "./navigation.service";
import ProjectInstanceService from "./project-instance.service";

export default angular.module('swamp.services', [angularResource])
    .service('LocationService', LocationService)
    .service('ProjectService', ProjectService)
    .service('NavigationService', NavigationService)
    .service('ProjectInstanceService', ProjectInstanceService)
    .name;
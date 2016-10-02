import angular from "angular";
import angularResource from "angular-resource";
import LocationService from "./location.service";
import ProjectService from "./project.service";

export default angular.module('swamp.services', [angularResource])
    .service('LocationService', ['$resource', LocationService])
    .service('ProjectService', ['$resource', ProjectService])
    .name;
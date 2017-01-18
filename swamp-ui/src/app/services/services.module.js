import Angular from "angular";
import AngularResource from "angular-resource";
import LocationService from "./location.service";
import ProjectService from "./project.service";
import ProjectInstanceService from "./project-instance.service";
import VersionService from "./version.service";
import TemplateService from "./template.service";

const module = Angular.module('swamp.services', [AngularResource])
    .service('LocationService', LocationService)
    .service('ProjectService', ProjectService)
    .service('ProjectInstanceService', ProjectInstanceService)
    .service('VersionService', VersionService)
    .service('TemplateService', TemplateService);

export default module.name;
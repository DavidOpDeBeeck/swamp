import Angular from "angular";
import AngularResource from "angular-resource";
import LocationService from "./location.service";
import ProjectService from "./project.service";
import BuildService from "./build.service";
import VersionService from "./version.service";
import TemplateService from "./template.service";
import NotificationService from "./notification.service";

const module = Angular.module('swamp.services', [AngularResource])
    .service('LocationService', LocationService)
    .service('ProjectService', ProjectService)
    .service('BuildService', BuildService)
    .service('VersionService', VersionService)
    .service('TemplateService', TemplateService)
    .service('NotificationService', NotificationService);

export default module.name;
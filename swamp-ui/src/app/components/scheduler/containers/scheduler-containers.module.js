import angular from "angular";
import services from "../../../services/services.module";
import SchedulerContainersRoute from "./scheduler-containers.route";

const module = angular.module('swamp.scheduler.containers', [services])
    .config(SchedulerContainersRoute);

export default module.name;

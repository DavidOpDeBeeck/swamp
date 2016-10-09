import angular from "angular";
import services from "../../services/services.module";
import schedulerContainers from "./containers/scheduler-containers.module";
import SchedulerRoute from "./scheduler.route";

const module = angular.module('swamp.scheduler', [services, schedulerContainers])
    .config(SchedulerRoute);

export default module.name;

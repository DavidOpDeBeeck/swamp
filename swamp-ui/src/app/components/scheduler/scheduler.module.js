import angular from "angular";
import services from "../../services/services.module";
import schedulerContainerInstance from "./container-instance/container-instance.module";
import schedulerContainers from "./containers/scheduler-containers.module";
import SchedulerRoute from "./scheduler.route";

const module = angular.module('swamp.scheduler', [services, schedulerContainers, schedulerContainerInstance])
    .config(SchedulerRoute);

export default module.name;

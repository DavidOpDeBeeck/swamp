import SchedulerController from "./scheduler.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('scheduler', {
        url: "/scheduler",
        templateUrl: '/app/components/scheduler/scheduler.template.html',
        controller: SchedulerController,
        controllerAs: 'SchedulerCtrl'
    });
}];

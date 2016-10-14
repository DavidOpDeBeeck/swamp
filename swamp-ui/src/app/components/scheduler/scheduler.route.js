import SchedulerController from "./scheduler.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('scheduler', {
        url: "/scheduler",
        data: {
            displayName: 'scheduler',
            disabled: false
        },
        templateUrl: '/app/components/scheduler/scheduler.template.html',
        controller: SchedulerController,
        controllerAs: 'SchedulerCtrl'
    });
}];

import SchedulerContainersController from "./scheduler-containers.controller";

export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('scheduler.containers', {
        url: "/:projectId/containers",
        data: {
            displayName: '{{ project.id }}',
            disabled: true
        },
        templateUrl: '/app/components/scheduler/containers/scheduler-containers.template.html',
        controller: SchedulerContainersController,
        controllerAs: 'SchedulerContainersCtrl',
        resolve: {
            project: ['SchedulerService', '$stateParams', (SchedulerService, $stateParams) => SchedulerService.getProject($stateParams['projectId'])]
        }
    });
}];

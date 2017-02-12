export default ['$stateProvider', ($stateProvider) => {
    $stateProvider.state('builds.build', {
        url: "/{buildId:.{36}}",
        template: '<ui-view />',
        data: {
            displayName: '{{ build.sequence }}',
            disabled: true
        },
        resolve: {
            build: ['BuildService', '$stateParams', (BuildService, $stateParams) => BuildService.getBuild($stateParams['buildId'])]
        }
    });
}];

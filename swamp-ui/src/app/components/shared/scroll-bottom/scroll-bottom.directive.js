class ScrollBottomDirective {
    constructor($timeout) {
        this.restrict = 'A';
        this.scope = {
            'scrollBottom': '='
        };
        this.timeout = $timeout;
    }

    link($scope, $element) {
        $scope.$watchCollection('scrollBottom', newValue => {
            if ( newValue ) {
                this.timeout(() => $element.scrollTop($element[0].scrollHeight), 0);
            }
        });
    }
}

export default ['$timeout', ($timeout) => new ScrollBottomDirective($timeout)]
class IncludeReplaceDirective {
    constructor() {
        this.require = 'ngInclude';
        this.restrict = 'A';
    }

    link(scope, el, attrs) {
        el.replaceWith(el.children());
    }
}

export default () => new IncludeReplaceDirective()
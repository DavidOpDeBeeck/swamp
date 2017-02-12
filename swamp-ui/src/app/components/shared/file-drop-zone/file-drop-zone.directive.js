// http://stackoverflow.com/questions/32574008/how-to-drag-text-file-into-text-area-to-show-contents-using-angular/32575592

class FileDropZoneDirective {
    constructor() {
        this.restrict = 'A';
        this.scope = {
            'fileDropZone': '='
        };
    }

    link(scope, element, attrs) {
        let setScopeContent = loadedFile => {
            scope.fileDropZone = loadedFile.target.result;
            scope.$apply();
        }
        let handleDropEvent = event => {
            if (event !== null) event.preventDefault();
            var reader = new FileReader();
            reader.onload = setScopeContent;
            reader.readAsText(event.originalEvent.dataTransfer.files[0]);
        }
        let processDragOverOrEnter = event => {
            if (event !== null) event.preventDefault();
            event.originalEvent.dataTransfer.effectAllowed = 'copy';
            return false;
        };
        element.bind('dragover', processDragOverOrEnter);
        element.bind('dragenter', processDragOverOrEnter);
        element.bind('drop', handleDropEvent);
    }
}

export default () => new FileDropZoneDirective()
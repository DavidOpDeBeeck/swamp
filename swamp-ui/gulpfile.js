var gulp = require('gulp');
var browserify = require('browserify');
var browserSync = require('browser-sync').create();
var uglify = require('gulp-uglify');
var buffer = require('gulp-buffer');
var babelify = require('babelify');
var source = require('vinyl-source-stream');
var url = require('url');
var proxy = require('proxy-middleware');
var del = require('del');

var SRC_FOLDER = './src';
var APP_FOLDER = SRC_FOLDER + '/app';
var ASSETS_FOLDER = SRC_FOLDER + '/assets';
var PUBLIC_FOLDER = './public';
var PUBLIC_ASSETS_FOLDER = PUBLIC_FOLDER + '/assets';

function copyScripts() {
    return gulp.src(ASSETS_FOLDER + '/**/*.*')
        .pipe(gulp.dest(PUBLIC_ASSETS_FOLDER + '/'));
}

function copyTemplates() {
    return gulp.src(SRC_FOLDER + '/**/*.html')
        .pipe(gulp.dest(PUBLIC_FOLDER + '/'));
}

function build() {
    return browserify({entries: APP_FOLDER + '/app.js', extensions: ['.js'], debug: true})
        .transform(babelify.configure({
            "presets": ["es2015"]
        }))
        .bundle()
        .pipe(source('app.js'))
        .pipe(buffer())
        .pipe(uglify())
        .pipe(gulp.dest(PUBLIC_FOLDER));
}

function cleanBuild() {
    return del.sync([PUBLIC_FOLDER + '/**/*.*']);
}

function syncBrowserAndProxy() {
    var proxyOptions = url.parse('http://localhost:8081/');
    proxyOptions.route = '/api';

    browserSync.init({
        open: false,
        server: {
            baseDir: PUBLIC_FOLDER,
            routes: {
                '/node_modules': 'node_modules'
            },
            middleware: [proxy(proxyOptions)]
        },
        browser: 'chrome'
    });
}

function reloadBrowserOnFileChanges() {
    gulp.watch(SRC_FOLDER + '/**/*.*', ['build']);
    gulp.watch(PUBLIC_FOLDER + '/**/*.*').on('change', browserSync.reload);
}

gulp.task('clean', cleanBuild);
gulp.task('copyScripts', copyScripts);
gulp.task('copyTemplates', copyTemplates);
gulp.task('build', ['copyTemplates', 'copyScripts'], build);
gulp.task('browser-sync', ['clean', 'build'], syncBrowserAndProxy);

gulp.task('start', ['browser-sync'], reloadBrowserOnFileChanges);
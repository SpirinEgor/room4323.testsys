'use strict';

const gulp = require('gulp');
const ts = require('gulp-typescript');
const del = require('del');
const tslint = require('tslint');
const gulpTsLint = require('gulp-tslint');
const runSequence = require('run-sequence');
const mergeStream = require('merge-stream');


gulp.task('build', function() {
    runSequence('clean', 'copy-static', 'tslint', 'ts');
});

gulp.task('clean', function () {
    return del(['build']);
});

gulp.task('copy-static', function() {
    const src = gulp
        .src(['src/*.js'])
        .pipe(gulp.dest('build/'));

    const image = gulp.src(['src/images/*.*'])
        .pipe(gulp.dest("build/images"));

    const html = gulp.src(['src/**/*.html'])
        .pipe(gulp.dest("build/"));

    const css = gulp.src('src/styles/**/*.*')
        .pipe(gulp.dest("build/styles/"));

    const data = gulp.src(['src/temporary_data/*.json'])
        .pipe(gulp.dest("build/temporary_data/"))

    return mergeStream(src, html, css, image, data);
});

gulp.task('watch', function() {
    gulp.watch('src/**/*.ts', ['ts']);
    gulp.watch('src/**/*.html', ['copy-static']);
    gulp.watch('src/*.js', ['copy-static']);
    gulp.watch('src/images/*.*', ['copy-static']);
    gulp.watch('src/styles/*.css', ['copy-static']);
    gulp.watch('src/temporary_data/*.json', ['copy-static']);
});

gulp.task('tslint', function () {
    const program = tslint.Linter.createProgram('./tsconfig.json');
    return gulp.src(['src/app/**/*.ts'])
        .pipe(gulpTsLint({
            program,
            formatter: 'prose'
        }))
        .pipe(gulpTsLint.report({
            emitError: true
        }));
});

gulp.task('ts', function() {
    const tsProject = ts.createProject('tsconfig.json');
    return tsProject
        .src()
        .pipe(tsProject())
        .pipe(gulp.dest(tsProject.config.compilerOptions.outDir))
});
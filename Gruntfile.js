module.exports = function (grunt) {

    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        sass: {
            options: {
                sourceMap: true
            },
            dist: {
                files: {
                    'dist/main.css': 'main.scss'
                }
            }
        }
    });

    grunt.registerTask('default', ['sass']);

};

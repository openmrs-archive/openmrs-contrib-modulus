module.exports = function(grunt) {

  grunt.initConfig({
    aglio: {
      docs: {
        files: {"html/index.html": "src/*"},
        theme: "default-multi",
        seperator: "\n\n"
      }
    },

    watch: {
      docs: {
        files: 'src/*.apib',
        tasks: ['aglio:docs'],
        options: {
          livereload: true
        }
      }
    },

    connect: {
      docs: {
        options: {
          port: process.env.PORT || 8084,
          base: './html',
          livereload: true
        }
      }
    },

    concat: {
      docs: {
        src: 'src/*.apib',
        dest: 'apiary.apib'
      }
    }
  });



  grunt.loadNpmTasks('grunt-aglio');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-concat');

  grunt.registerTask('default', ['aglio', 'concat']);
  grunt.registerTask('serve', ['default', 'connect', 'watch']);

};

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits("global") // inherit Grails' default dependencies
    log "warn"
    repositories {
        grailsCentral()
    }
    plugins {
        build(":tomcat:$grailsVersion",
              ":hibernate:$grailsVersion",
              ":release:2.0.4") {
            export = false
        }
        runtime(":platform-core:1.0.M6") { excludes 'resources' }
        test(":spock:0.6") {
            export = false
        }
        test(":greenmail:latest.integration") { export = false }
        runtime ":mail:1.0"
    }
}

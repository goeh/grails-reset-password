grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits("global") // inherit Grails' default dependencies
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
    }
    plugins {
        build(":tomcat:$grailsVersion",
                ":release:2.0.2") {
            export = false
        }
        test(":spock:latest.integration") {
            export = false
        }
        test ":greenmail:latest.integration"
        runtime(":hibernate:$grailsVersion") {
            export = false
        }
        runtime ":mail:1.0"
    }
}

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
                ":release:2.0.3") {
            export = false
        }
        runtime ":platform-core:1.0.M2-SNAPSHOT"
        test(":spock:0.6") {
            export = false
        }
        test ":greenmail:latest.integration"
        runtime(":hibernate:$grailsVersion") {
            export = false
        }
        runtime ":mail:1.0"
    }
}

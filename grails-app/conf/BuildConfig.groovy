grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits("global") // inherit Grails' default dependencies
    log "warn"
    legacyResolve false
    repositories {
        grailsCentral()
    }
    dependencies {
        compile 'edu.vt.middleware:vt-password:3.1.2'
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }
    plugins {
        build(":tomcat:$grailsVersion",
              ":hibernate:$grailsVersion",
              ":release:2.2.1") {
            export = false
        }
        runtime(":platform-core:1.0.0") { excludes 'resources' } // we use platform-core events.
        test(":spock:0.7") {
            export = false
            exclude "spock-grails-support"
        }
    }
}

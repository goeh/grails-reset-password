grails.project.work.dir = "target"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    inherits("global") // inherit Grails' default dependencies
    log "warn"
    legacyResolve false
    repositories {
        grailsCentral()
    }
    dependencies {
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }
    plugins {
        build(":tomcat:$grailsVersion",
              ":hibernate:$grailsVersion",
              ":release:2.2.1") {
            export = false
        }
        runtime(":platform-core:1.0.RC5") { excludes 'resources' }
        test(":spock:0.7") {
            export = false
            exclude "spock-grails-support"
        }
    }
}

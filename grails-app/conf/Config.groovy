// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}

grails.mail.port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port

reset.password.email.enabled = true
reset.password.email.layout = "email"
reset.password.email.sender = "root@localhost"
reset.password.hashAlgorithm = "SHA-256"
reset.password.hashIterations = 1000
reset.password.success.url=null
reset.password.questions = [
        'security.question.address.postalcode',
        'security.question.birth.city',
        'security.question.birth.month_year',
        'security.question.family.mother.name.maiden',
        'security.question.family.child.oldest.name.middle',
        'security.question.family.brother.oldest.birth.month_year',
        'security.question.family.sibling.oldest.name.middle',
        'security.question.family.cousin.oldest.name.first_last',
        'security.question.family.wedding.reception.place',
        'security.question.family.wedding.honeymoon.city',
        'security.question.childhood.nickname',
        'security.question.childhood.bestfriend.firstname',
        'security.question.childhood.telephone.number',
        'security.question.childhood.dreamjob',
        'security.question.job.first.city'
]

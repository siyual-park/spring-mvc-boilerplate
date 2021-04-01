package io.github.siyual_park

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@EnableCaching
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

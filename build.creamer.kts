repositories {
    repository {
        name = "central"
        url = "http://repo.maven.apache.org/maven2/"
    }
}

java.version = 16

dependencies {
    dependency("org.apache.logging.log4j:log4j-api:12.17.1")
    dependency("org.apache.logging.log4j:log4j-core:12.17.1")
}

sourceSet {
    name = "test"
}
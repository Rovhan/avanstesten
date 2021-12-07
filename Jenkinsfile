pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'JDK17'
    }
    stages {
        stage('Build') {
            sauce('hanssaus') {
                sauceconnect(useGeneratedTunnelIdentifier: true, verboseLogging: true) {
                    sh 'mvn clean install'
                }
            }
        }
    }
}
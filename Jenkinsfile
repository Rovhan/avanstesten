pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'JDK17'
    }
    stages {
        stage('Build') {
            steps {
                sauce('hanssaus') {
                    sauceconnect(useGeneratedTunnelIdentifier: true, verboseLogging: true) {
                        sh 'mvn clean install'
                    }
                }
                junit '*/target/surefire-reports/*.xml'
                jacoco(execPattern: 'target/jacoco.exec')
            }

        }
    }
}
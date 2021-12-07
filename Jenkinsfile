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
                sh 'mvn surefire-report:report-only'
                sh 'mvn site -DgenerateReports=false'
                jacoco(execPattern: 'target/jacoco.exec')
            }

        }
    }
}
pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'jdk_17.0.1'
    }
    stages {
        stage('Build') {
            steps{
                sh 'mvn clean install'
            }
            post {
                always {
                    junit 'target/surefire-report/**/*.xml'
                }
            }
        }
    }
}
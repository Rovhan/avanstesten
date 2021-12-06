pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'jdk17'
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
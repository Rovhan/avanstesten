pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'JDK17'
    }
    stages {
        stage('Build') {
            steps{
                echo "Chrome driver = ${webdriver.chrome.driver}"
                echo "JAVA_HOME = ${JAVA_HOME}"
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
pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'JDK17'
    }
    stages {
        stage('Build') {
            steps{
                echo "JAVA_HOME = ${JAVA_HOME}"
                sh 'mvn clean install -DJAVA_HOME=/var/jenkins_home/tools/hudson.model.JDK/JDK17/jdk-17.0.1'
            }
            post {
                always {
                    junit 'target/surefire-report/**/*.xml'
                }
            }
        }
    }
}
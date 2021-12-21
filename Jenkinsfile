pipeline {
    environment {
        registry = "jswen19109814/booking"
        dockerImage = ''
    }
    agent any
    tools { 
        maven 'mvn' 
        jdk 'java' 
    }
    stages {
        stage('Docker build') {
            steps {
                echo 'Maven packaging:'
                sh 'mvn package'
                echo 'Building image:'
                script{
                    userimage = docker.build registry + ":booking"
                }  
            }
        }
        stage('Push Image'){
            steps{
                echo 'Pushing image to dockerhub:'
                script{
                    docker.withRegistry('', 'jwdockerhub'){
                        userimage.push()
                    }
                }
            }
        }
    }
}
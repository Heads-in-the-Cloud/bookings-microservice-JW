pipeline {
    environment {
        registry = "902316339693.dkr.ecr.us-east-2.amazonaws.com/jw-bookings"
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
                    bookingimage = docker.build registry + ":bookingimage"
                }  
            }
        }
        stage('Push Image'){
            steps{
                echo 'Pushing image to ECR:'
                script{
                    docker.withRegistry('902316339693.dkr.ecr.us-east-2.amazonaws.com', 'ecr:us-east-2:jw-aws-cred'){
                        bookingimage.push()
                    }
                }
            }
        }
        // stage('Call docker-compose'){
        //     steps{
        //         echo 'Calling docker-compose job'
        //         build job: 'JW-Docker-Compose'
        //     }
        // }
    }
}
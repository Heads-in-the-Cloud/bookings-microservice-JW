pipeline {
    agent any
    environment {
        registry = "902316339693.dkr.ecr.us-east-2.amazonaws.com/jw-bookings"
        dockerImage = ''
    }
    parameters{
        string(name:'sonarqubekey', defaultValue: 'NULL', description: 'sonarqube key')
        
    }
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
        stage('Sonarqube check'){
            steps{
                sh"mvn verify sonar:sonar -Dsonar.projectKey=bookings-microservice -Dsonar.host.url=http://ec2-3-136-83-88.us-east-2.compute.amazonaws.com:9000 -Dsonar.login=${params.sonarqubekey}"
            }
        }
        stage('Push Image'){
            steps{
                echo 'Pushing image to ECR:'
                sh 'docker context use default'
                script{
                    docker.withRegistry('https://902316339693.dkr.ecr.us-east-2.amazonaws.com', 'ecr:us-east-2:jw-aws-cred'){
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
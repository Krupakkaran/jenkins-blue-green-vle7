// Jenkinsfile

pipeline {
    agent any
    environment {
        // CRITICAL: Replace 'tiger004' with your DockerHub username
        IMAGE = "tiger004/sample-java-app:${BUILD_NUMBER}"
        // Set the target environment for the NEW deployment (first run is 'green')
        COLOR = "green" 
        APP_NAME = "myapp"
        SERVICE_NAME = "myapp-service"
    }
    stages {
        stage('Clone Repo') {
            steps {
                // Clones the current repository
                git 'https://github.com/Krupakkaran/jenkins-blue-green-vle7.git'
            }
        }
        stage('Build Artifact & Docker Image') {
            steps {
                // 1. Build Java application (creates JAR file in target/)
                sh 'mvn clean package -DskipTests' 
                // 2. Build Docker image using the Dockerfile in the root
                sh 'docker build -t $IMAGE .'
            }
        }
        stage('Push Image') {
            steps {
                // Authenticates using the 'dockerhub-pass' Secret Text credential ID
                withCredentials([string(credentialsId: 'dockerhub-pass', variable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u tiger004 --password-stdin'
                    sh 'docker push $IMAGE'
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                // 1. Apply the manifest for the new (inactive) color (e.g., deployment-green.yaml)
                sh "kubectl apply -f deployment-${COLOR}.yaml"
                
                // 2. Update the image for the INACTIVE deployment only
                sh "kubectl set image deployment/${APP_NAME}-${COLOR} ${APP_NAME}=$IMAGE --record"
                
                // 3. Wait for the new deployment to be fully ready before switching traffic
                sh "kubectl rollout status deployment/${APP_NAME}-${COLOR}"
            }
        }
        stage('Switch Service') {
            steps {
                // Manual gate for validation before switching live traffic
                input "Switch traffic to $COLOR version? (Review new deployment status before proceeding)"
                
                // Patch the single Service to point its selector to the new color
                sh "kubectl patch service ${SERVICE_NAME} -p \"{\\\"spec\\\": {\\\"selector\\\": {\\\"app\\\": \\\"${APP_NAME}\\\", \\\"color\\\": \\\"${COLOR}\\\"}}}\""
            }
        }
    }
}

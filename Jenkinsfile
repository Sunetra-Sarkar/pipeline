pipeline {
    agent any
    
    environment {
        JAR_FILE = "java-sample-21-1.0.0.jar"
        APP_PORT = '5000'
    }
    
    // tools {
    //     maven 'Maven-3.9.0'
    //     jdk 'JDK-21'
    // }
    
    stages {
        stage('Checkout') {
            steps {
                echo '🔄 Checking out source code...'
                git branch: 'master', url: 'https://github.com/artisantek/jenkins.git'
            }
        }
        
        stage('Tests') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        echo '🧪 Running unit tests...'
                        dir('javaapp-pipeline') {
                            sh '''
                                echo "Starting test execution..."
                                mvn clean test
                            '''
                        }
                    }
                }

                stage('Trivy Scan') {
                    steps {
                        echo '🔒 Running Trivy security scan...'
                        dir('javaapp-pipeline') {
                            sh '''
                                echo "Starting Trivy filesystem scan..."
                                wget -q https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/html.tpl -O html.tpl
                                trivy fs --format template --template "@html.tpl" -o trivy-report.html .
                                echo "Trivy scan completed. Report: trivy-report.html"
                            '''
                        }
                    }
                }
            }
        }

        stage('Sonar Analysis') {
            steps {
                echo '📊 Running SonarQube analysis...'
                dir('javaapp-pipeline') {
                    withSonarQubeEnv('sonar') {
                        sh '''
                            echo "Starting SonarQube analysis..."
                            mvn verify sonar:sonar \
                                -Dsonar.projectKey=javaapp \
                                -Dsonar.projectName=javaapp \
                        '''
                    }
                }
            }
        }
        
        stage('Build') {
            steps {
                echo '🏗️ Building application...'
                dir('javaapp-pipeline') {
                    sh '''
                        echo "Building JAR file..."
                        mvn package -DskipTests=true
                    '''
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo '🚀 Deploying application...'
                dir('javaapp-pipeline/target') {
                    script {
                        // Stop any existing application process
                        sh '''
                            echo "🛑 Stopping any existing application processes..."
                            if pgrep -f "java -jar java-sample-21-1.0.0.jar" > /dev/null; then
                                pkill -f "java -jar java-sample-21-1.0.0.jar"
                                echo "App was running and has been killed."
                            else
                                echo "App is not running."
                            fi
                        '''
                        
                        // Deploy the new application
                        sh '''
                            # Set JENKINS_NODE_COOKIE to prevent Jenkins from killing the process
                            export JENKINS_NODE_COOKIE=dontKillMe
                            
                            # Start the application in background
                            echo "Starting application on port ${APP_PORT}..."
                            nohup java -jar "${JAR_FILE}" > application.log 2>&1 &
                        '''
                    }
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo '🎉 Pipeline completed successfully!'
        }
        failure {
            emailext subject: "Pipeline Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                     body: "Build failed. Please check the logs at ${env.BUILD_URL}",
                     to: "artisantek.adithya@gmail.com"
        }
    }
} 

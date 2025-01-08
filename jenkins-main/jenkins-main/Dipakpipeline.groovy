pipeline {
    agent any
    stages {
        stage ('Pull Code') {
            steps {
                git 'https://github.com/SurajBele/studentapp.ui.git'
                echo 'Pull successful'
            }
        }
        
        stage ('Build') {
            steps {
                sh 'mvn clean package'
                echo 'Build successful'
            }
        }
        
        stage ('Test') {
            steps {
                sh '''
                    mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=studentapp \
                      -Dsonar.host.url=http://54.167.18.79:9000 \
                      -Dsonar.login=sqp_b5179c096793294f3acdff91941e3a15882d0db3
                '''
                echo 'Test successful'
            }
        }
        
        stage ('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                echo 'Quality Gate passed'
            }
        }
        
        stage ('Deploy to Tomcat') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://50.18.36.86:8080/')], contextPath: '/', war: '**/*.war'
                echo 'Deploy successful'
            }
        }
    }
}

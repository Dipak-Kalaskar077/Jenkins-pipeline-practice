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
                    -Dsonar.projectKey=studentapp.ui \
                    -Dsonar.host.url=http://34.204.40.70:9000 \
                    -Dsonar.login=sqp_cf77d07f749142f0375cd7462e379c204ed86d23
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

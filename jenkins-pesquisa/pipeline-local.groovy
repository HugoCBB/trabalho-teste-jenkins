
pipeline {
    agent any
    stages {
        stage('Copiar projeto') {
            steps {
                deleteDir()                 
                sh 'cp -r /projeto/. .'     
            }
        }
        stage('Testes') {
            steps {
                sh '''
                    python3 -m venv venv
                    . venv/bin/activate
                    pip install -q -r requirements.txt
                    pytest --junitxml=resultados.xml || true
                '''
            }
        }
    }
    post {
        always {
            junit 'resultados.xml'   
        }
    }
}

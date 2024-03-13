pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                // Ajoutez ici votre commande de build, par exemple pour un projet Maven :
                // sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
                // Ajoutez ici votre commande pour lancer les tests
                // sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying..'
                // Ajoutez ici vos étapes de déploiement
                // Par exemple, vous pourriez copier le build vers un serveur de production
            }
        }
    }
}

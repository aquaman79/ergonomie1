pipeline {
    agent any
    tools{scannerSonar}

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                // Exemple pour un projet Maven :
                // sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
                // Exemple pour lancer les tests avec Maven :
                // sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Utilisez 'SonarQube' comme nom dans withSonarQubeEnv puisque c'est le nom de votre configuration
                withSonarQubeEnv('SonarQube') {
                    // La commande Maven pour lancer une analyse SonarQube.
                    // Assurez-vous d'inclure l'URL de SonarQube si nécessaire et le jeton d'authentification pour les versions sécurisées de SonarQube.
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000'
                    // Pour les projets non Maven, utilisez sonar-scanner avec les options appropriées
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying..'
                // Vos commandes de déploiement ici
            }
        }
    }
}

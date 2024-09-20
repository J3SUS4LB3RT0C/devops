def call(){

    pipeline{

        agent any

        tools{
            nodejs 'NodeJS'
        }
        
        environment{
            projectName = "${env.GIT_URL_1}".replaceAll('.+/(.+)\\.git', '$1').toLowerCase()
        }

        stages{
            stage('Fase 2: Construcción de imagen en Docker Desktop') {
                steps {
                    script {
                        def buildimage = new org.devops.lb_buildimagen()
                        buildimage.buildImageDocker("${projectName}")
                    }
                }
            }

            stage('Fase 2: Alojando la imagen en Docker Hub') {
                steps {
                    script {
                        def publishimage = new org.devops.lb_publicardockerhub()
                        publishimage.cargarDockerHub("${projectName}")
                    }
                }
            }

            stage('Fase 2: Desplegando el contenedor') {
                steps {
                    script {
                        def deployingContainer = new org.devops.lb_deploydocker()
                        deployingContainer.despliegueContenedor("${projectName}")
                    }
                }
            }

            stage('Fase 2: Análisis de OWASP') {
                steps {
                    script {
                        def analisysOWASP = new org.devops.lb_owasp()
                        analisysOWASP.analisisOWASP()
                    }
                }
            }

            /*stage('Fase 1: Proceso de construcción') {
                steps {
                    script {
                        def cloneapp = new org.devops.lb_buildartefacto()
                        cloneapp.clone()
                        def buildapp = new org.devops.lb_buildartefacto()
                        buildapp.install()
                    }
                }
            }

            stage('Fase 1: Análisis de Sonarqube'){
                steps{
                    script{
                        def test = new org.devops.lb_analisissonarqube()
                        test.runTest()
                        def analisysSonarqube = new org.devops.lb_analisissonarqube()
                        analisysSonarqube.analisys("${projectName}")
                    }
                }
            }*/
        }
    }
}

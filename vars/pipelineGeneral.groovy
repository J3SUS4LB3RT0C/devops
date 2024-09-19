def call(){
    
    pipeline{

        agent any

        tools{
            nodejs 'NodeJS'
        }
        
        environment{
            projectName = "${env.GIT_URL_1}".replaceAll('.+/(.+)\\.git', '$1')toLowerCase()
        }

        stages{
            stage('Construccion App') {
                steps {
                    script {
                        def cloneapp = new org.devops.lb_buildartefacto()
                        cloneapp.clone()
                        def buildapp = new org.devops.lb_buildartefacto()
                        buildapp.install()
                    }
                }
            }

            stage('Sonar Analisis'){
                steps{
                    script{
                        def test = new org.devops.lb_analisissonarqube()
                        test.runTest()
                        def analisysSonarqube = new org.devops.lb_analisissonarqube()
                        analisysSonarqube.analisys("${projectName}")
                    }
                }
            }
        }
    }
}

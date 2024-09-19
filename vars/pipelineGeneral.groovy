def call(){
    
    pipeline{

        agent any

        tools{
            nodejs 'NodeJS'
        }
        
        environment{
            PROJECT = "${env.UrlGitHub}".replaceAll('.+/(.+)\\.git', '$1')toLowerCase()
        }

        stages{
            stage('Construccion App') {
                steps {
                    script {
                        def buildapp = new org.devops.lb_buildartefacto()
                        buildapp.install()
                        def cloneapp = new org.devops.lb_buildartefacto()
                        cloneapp.clone()
                    }
                }
            }

            stage('Sonar Analisis'){
                steps{
                    script{
                        def test = new org.devops.lb_analisissonarqube()
                        test.testCoverage()
                        def analisysSonar = new org.devops.lb_analisissonarqube()
                        analisysSonar.analisisSonar("${PROJECT}")
                    }
                }
            }
        }
    }
}

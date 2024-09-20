package org.devops

def despliegueContenedor(projectName) {
    sh "docker stop ${projectName} || true"
    sh "docker rm ${projectName} || true"
    
    def networkName = env.NET_NAME ?: 'default_network' // Valor por defecto
    println "Network name: ${NameNetwork}"
    
    sh "docker pull jesuscastellanos/${projectName}"
    sh "docker run -d --name ${projectName} --network=${NameNetwork} -p 5174:5174 --user root jesuscastellanos/${projectName}"
}

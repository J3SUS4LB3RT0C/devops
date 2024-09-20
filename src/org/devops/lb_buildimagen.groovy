package org.devops

def buildImageDocker(projectName){
    sh "docker build -t ${projectName} ."
}

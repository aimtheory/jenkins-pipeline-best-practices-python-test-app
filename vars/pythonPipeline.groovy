def call() {
  def p = pipelineCfg()
  
  stage('Checkout') {
    checkout scm
  }

  docker.image(p.type).inside {
    stage('Test') {
      sh p.testCommand
    }
  }

  if (env.BRANCH_NAME == 'master' && p.deployUponTestSuccess == true) {
    docker.image(p.deployToolImage).inside {
      stage('Deploy') {
        sh "echo ${p.deployCommand} ${p.deployEnvironment}"
      }
    }
  }
}

def call() {
  node {
    stage('Checkout') {
      checkout scm
    }
    def p = pipelineCfg()

    docker.image('python').inside('pip install') {
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
}

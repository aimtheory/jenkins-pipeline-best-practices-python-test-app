def call() {
  node {
    stage('Checkout') {
      checkout scm
    }
    def p = pipelineCfg()

    docker.image('python:2.7.15-alpine').inside() {
      stage('Test') {
        sh 'pip install -r requirements.txt'
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

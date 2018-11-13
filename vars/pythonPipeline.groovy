def call() {
  docker.image(pipelineCfg()['pipelineType']).inside {
    stage('Test') {
      sh pipelineCfg()['testCommand']
    }
  }

  if (env.BRANCH_NAME == 'master') {
    docker.image(pipelineCfg()['deployToolImage'].inside {
      stage('Deploy') {
        sh pipelineCfg()['deployCommand']
      }
    }
  }
}

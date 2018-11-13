def call() {
  node {
    stage('Checkout') {
      checkout scm
    }
    Eval.me("${pipelineCfg().pipelineType}Pipeline()")
  }
}

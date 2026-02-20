#!/bin/bash

status=${1:-'成功'}

curl 'https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=24349abd-7c29-4837-8758-60f144f24d06' \
-H 'Content-Type: application/json' \
-d "{
    \"msgtype\": \"markdown\",
    \"markdown\": {
        \"content\": \"GitlabCI-$CI_PROJECT_NAME-$CI_PIPELINE_IID:
            触发分支: $CI_COMMIT_REF_NAME
            提交人员:  $CI_COMMIT_AUTHOR
            提交代码: [Code Review]($CI_PROJECT_URL/-/commit/$CI_COMMIT_SHA?wwopeninbrowser=2)
            提交时间: $CI_COMMIT_TIMESTAMP
            提交描述: $CI_COMMIT_MESSAGE
            构建状态: $status
            详情请看: [$CI_JOB_URL]($CI_JOB_URL?wwopeninbrowser=2)\"
    }
}"

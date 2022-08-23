# !/bin/bash

#[참고]
#https://blog.naver.com/PostView.nhn?blogId=occidere&logNo=221285584339&categoryNo=0&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView

# 현재 프로젝트 명
project_name=tomcatEncryptModule

# Local Maven Repository 경로
local_git_bash_maven_repo='/d/project/tomcatEncryptModule-maven-repo'

#test
local_maven_repo2='D:/project/tomcatEncryptModule-maven-repo'

# Local Maven Repository의 snapshots 폴더로 deploy 실행
mvn -Dmaven.test.skip=true -DaltDeploymentRepository=snapshot-repo::default::file://${local_maven_repo2}/snapshots clean deploy

# Local Maven Repository로 이동
cd ${local_git_bash_maven_repo}

# git add & commit & push 진행
# deploy key를 등록했기 때문에 id, pw 입력 없이 진행 가능
git status
git add .
git status
git commit -m "release new version of ${project_name}"
git push origin main


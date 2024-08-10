FROM node:latest

WORKDIR /app

# docker run 명령어에 추가해줌, init 만 해도 프로젝트가 생성
ENTRYPOINT ["yarn"]

# node image build
# docker build -t node-util .

# node container 로 프로젝트 생성
# docker run -it -v /home/ygs/StudyDocker/utility:/app node-util npm init

# Entry 설정 이후
# docker run -it -v /home/ygs/StudyDocker/utility:/app mynpm init
# docker run -it -v /home/ygs/StudyDocker/utility:/app mynpm install expresss --save
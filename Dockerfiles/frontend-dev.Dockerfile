FROM node:latest

WORKDIR /app

COPY ./frontend/package.json ./

RUN yarn install

COPY ./frontend .

# vite permission deni 뜰 경우
RUN chmod 777 ./node_modules/.bin/vite

CMD ["yarn", "dev"]

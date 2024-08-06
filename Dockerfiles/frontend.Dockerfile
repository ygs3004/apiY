FROM node:latest

WORKDIR /app

COPY ./frontend/package.json ./

RUN yarn install

COPY ./frontend .

CMD ["yarn", "dev"]

FROM node:latest

WORKDIR /app

COPY ./frontend/package.json ./

RUN npm install

COPY ./frontend .

# vite permission deni 뜰 경우
RUN chmod 777 ./node_modules/.bin/vite

CMD ["npm", "run", "dev"]

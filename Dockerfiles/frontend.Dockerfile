FROM node:latest AS builder

WORKDIR /app

COPY ./frontend/package.json ./

RUN npm install

COPY ./frontend .

COPY .env .

# vite permission deni 뜰 경우
RUN chmod 777 ./node_modules/.bin/vite

RUN npm run build

FROM nginx

COPY --from=builder /app/dist /usr/share/nginx/html

COPY ./frontend/conf/nginx.conf /etc/nginx/conf.d/default.conf

CMD [ "nginx", "-g", "daemon off;" ]

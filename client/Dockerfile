# этап сборки (build stage)
FROM node:16.17.0 as build-stage

WORKDIR /app
COPY package*.json yarn.lock ./

RUN yarn install

COPY . .

RUN yarn run build

# этап production (production-stage)
FROM nginx:stable-alpine as production-stage

# Add nginx config
COPY docker/nginx/prod.conf /temp/prod.conf
RUN envsubst /app < /temp/prod.conf > /etc/nginx/conf.d/default.conf

COPY --from=build-stage /app/dist /usr/share/nginx/html

EXPOSE 8081

CMD ["nginx", "-g", "daemon off;"]
FROM node:15 AS build

RUN mkdir -p /app
WORKDIR /app
COPY package.json /app
RUN yarn install
COPY . /app
RUN yarn build --prod

FROM nginx:1.19.7
COPY --from=build /app/dist/frontend/ /usr/share/nginx/html

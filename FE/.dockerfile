FROM node:18-alpine AS builder

WORKDIR /app

COPY package.json .

RUN npm install

COPY . .

RUN npm run build

FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf

WORKDIR /code

COPY --from=builder /app/dist .

EXPOSE 8000

CMD ["nginx", "-g", "daemon off;"]
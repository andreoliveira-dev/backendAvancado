FROM node:18-alpine

WORKDIR /app

COPY ./frontend/package*.json ./frontend/

RUN npm install

COPY . .

EXPOSE 4200

CMD ["npm", "start"]
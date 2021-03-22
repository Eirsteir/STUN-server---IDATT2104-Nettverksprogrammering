FROM node:13.12.0-alpine

WORKDIR /app

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent

COPY . ./

CMD ["node", "server.js"]
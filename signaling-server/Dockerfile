FROM node:15.12.0-alpine3.10

WORKDIR /app

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent

COPY . ./

CMD ["node", "server.js"]